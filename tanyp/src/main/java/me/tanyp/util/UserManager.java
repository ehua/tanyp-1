package me.tanyp.util;

import me.tanyp.controller.common.SystemException;
import me.tanyp.controller.common.UserException;
import me.tanyp.entity.User;
import me.tanyp.util.basic.KryoSerializer;
import me.tanyp.util.basic.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * Created by Tan Youping on 2018/1/9
 */
public class UserManager {

    private static volatile UserManager manager;
    private String redisHost;
    private int redisPort;
    private String redisPassword = null;
    private int poolMaxTotal = 200;
    private int poolMaxIdle = 50;
    private long maxWaitMillis = 60000;
    private int timeout = 3000;
    private int expireTime = 60 * 120; // 30m
    private static final String SESSION_USER_MAPPING_KEY = "mapping.session.and.user";
    private static final String SESSION_FIELD_KEY = "session";
    private static final String USER_FIELD_KEY = "user";
    private JedisPool pool = null;
    private static final ThreadLocal<String> sessionHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> nonRequestUserHolder = new ThreadLocal<>();

    public void setRedisHost(String host){
        this.redisHost = host;
    }
    public void setRedisPort(int port){
        this.redisPort = port;
    }
    public void setRedisPassword(String password){
        this.redisPassword = password;
    }
    public void setMaxWaitMillis(long maxWaitMillis){
        this.maxWaitMillis = maxWaitMillis;
    }
    public void putSessionId(String sessionId){
        sessionHolder.set(sessionId);
    }

    public String getSessionId(){
        return sessionHolder.get();
    }

    public static UserManager get(){
        return manager;
    }

    @PostConstruct
    public void init(){
        manager = this;
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(this.poolMaxTotal);
        config.setMaxIdle(this.poolMaxIdle);
        config.setMaxWaitMillis(this.maxWaitMillis);
        this.pool = new JedisPool(config,this.redisHost,this.redisPort,this.timeout,this.redisPassword);
    }

    private JedisPool getPool() {
        return get().pool;
    }

    public void saveUser(User user, boolean isAgainLogin) throws UserException{
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            String userKey = user.getId();
            byte[] userBytes = jedis.hget(userKey.getBytes(), USER_FIELD_KEY.getBytes());
            if(isAgainLogin){
                String sessionId = jedis.hget(userKey, SESSION_FIELD_KEY);
                if(StringUtils.isNotEmpty(sessionId)){
                    jedis.hdel(SESSION_USER_MAPPING_KEY, sessionId);
                }
            }else{
                if(userBytes != null){
                    String originalSessionId = jedis.hget(userKey, SESSION_FIELD_KEY);
                    String sessionId = sessionHolder.get();
                    String originalUserKey = jedis.hget(SESSION_USER_MAPPING_KEY, originalSessionId);
                    if(StringUtils.isNotEmpty(originalSessionId) && StringUtils.isNotEmpty(originalUserKey)
                            && !originalSessionId.equals(sessionId)){
                        throw new UserException();
                    }
                }
            }
            jedis.hset(userKey,SESSION_FIELD_KEY,sessionHolder.get());
            jedis.hset(userKey.getBytes(),USER_FIELD_KEY.getBytes(), KryoSerializer.serialize(user));
            jedis.expire(userKey,expireTime);
            mappingSessionAndUser(jedis,sessionHolder.get(),userKey);
        }catch (UserException e) {
            e.printStackTrace();
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }

    }

    public void removeUser(String userKey){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            if(nonRequestUserHolder.get() == null){
                String sessionId = getSessionId();
                if(StringUtils.isNotEmpty(sessionId)){
                    jedis.hdel(SESSION_USER_MAPPING_KEY, sessionId);
                }
            }
            if(StringUtils.isNotEmpty(userKey)) {
                jedis.expire(userKey, 0);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public void logout() {
        String sessionId = getSessionId();
        if(StringUtils.isNotEmpty(sessionId)){
            String userKey = getUserKey(sessionId);
            removeUser(userKey);
        }
    }


    public boolean isLogin() {
        String userKey = getCurrentUserKey();
        if(StringUtils.isEmpty(userKey)) {
            return false;
        }else{
            User user = getCurrentUser();
            if(user == null){
                return false;
            }
        }
        return true;
    }

    public void validIsLogin() throws Exception {
        if(!isLogin()) {
            throw new SystemException("Sorry, you haven't logged in ！！s");
        }
    }


    /** 强制下线 */
    public void forcedOffline(String userNo, String orgCode) {
        String userKey = orgCode + "_" + userNo;
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            String sessionId = jedis.hget(userKey, SESSION_FIELD_KEY);
            if(StringUtils.isNotEmpty(sessionId)){
                jedis.hdel(SESSION_USER_MAPPING_KEY, sessionId);
            }
            if(StringUtils.isNotEmpty(userKey)) {
                jedis.expire(userKey, 0);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public User getCurrentUser(){
        Jedis jedis = null;
        User user = null;
        try{
            jedis = pool.getResource();
            String userKey = getCurrentUserKey();
            if(StringUtils.isEmpty(userKey)) {
                return null;
            }
            byte[] userBytes = jedis.hget(userKey.getBytes(), USER_FIELD_KEY.getBytes());
            if(userBytes != null){
                user = KryoSerializer.deserialize(User.class,userBytes);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            if (jedis != null)
                jedis.close();
        }
        return user;
    }

    private void mappingSessionAndUser(Jedis jedis,String sessionId,String tenantAndUserNo){
        jedis.hset(SESSION_USER_MAPPING_KEY,sessionId,tenantAndUserNo);
    }

    private String getUserKey(String sessionId){
        if(StringUtils.isEmpty(sessionId)) {
            return null;
        }
        String userKey = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if(jedis.hexists(SESSION_USER_MAPPING_KEY, sessionId)){
                userKey = jedis.hget(SESSION_USER_MAPPING_KEY, sessionId);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            if (jedis != null)
                jedis.close();
        }

        return userKey;
    }
    private String getCurrentUserKey(){
        String userKey = nonRequestUserHolder.get();
        if(StringUtils.isNotEmpty(userKey)) {
            return userKey;
        }
        return getUserKey(sessionHolder.get());
    }

}
