package com.tanyouping.weixiao.redis;

import com.tanyouping.weixiao.exception.SystemException;
import com.tanyouping.weixiao.util.KryoSerializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisManager {

    private static Log logger = LogFactory.getLog(RedisManager.class);
    private final static byte[] NULL_VALUE = "null".getBytes();

    private static volatile RedisManager manager = null;
    private ThreadLocal<Jedis> jedisThreadLocal = new ThreadLocal<>();
    private Map<KeyPattern, Initializer> initializers = new ConcurrentHashMap<>();
    private List<KeyPattern> keyPatterns = new ArrayList<>();
    private JedisPool pool = null;
    private JedisPoolConfig jedisPoolConfig = null;
    private String host;
    private int port = 6379;
    private int connectionTimeout = Protocol.DEFAULT_TIMEOUT;
    private int soTimeout = Protocol.DEFAULT_TIMEOUT;
    private String password = null;
    private int database = Protocol.DEFAULT_DATABASE;
    private String clientName = null;

    @PostConstruct
    public void init() {
        manager = this;
        pool = new JedisPool(jedisPoolConfig, host, port, connectionTimeout, soTimeout,
                password, database, clientName, false, null, null, null);
    }

    public static RedisManager get() {
        return manager;
    }

    /**
     * 获取redis连接池
     */
    private JedisPool getPool() {
        return get().pool;
    }

    /**
     * 获取Redis资源
     *
     * @return
     */
    public Jedis getResource() {
        Jedis jedis = jedisThreadLocal.get();
        if (jedis == null) {
            jedis = getPool().getResource();
            jedisThreadLocal.set(jedis);
        }
        return jedis;
    }

    /**
     * 关闭Redis资源
     *
     * @param redis
     */
    public void closeResource() {
        Jedis jedis = jedisThreadLocal.get();
        if (jedis != null) {
            jedisThreadLocal.remove();
            jedis.close();
        }
    }

    /**
     * 这个方法一般是在初始化的时候调用，不考虑同步
     *
     * @param keyPattern
     * @param callback
     */
    public void registerInitializer(KeyPattern keyPattern,
                                    Initializer initializer) {
        if(initializer == null){
            throw new SystemException("初始化器不能为空");
        }
        keyPatterns.add(keyPattern);
        initializers.put(keyPattern, initializer);
    }
    /**
     * 删除
     * @param key
     */
    public void del(String key) {
        try {
            Jedis jedis = get().getResource();
            jedis.del(key);
        }finally{
            closeResource();
        }
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value){
        try {
            Jedis jedis = get().getResource();
            byte[] keyBytes = key.getBytes();
            if (value == null) {
                jedis.set(keyBytes, NULL_VALUE);
                return;
            } else {
                doSetInternal(jedis, key, keyBytes, value);
            }
        }finally{
            closeResource();
        }
    }

    public void expire(String key, int timeout){
        try {
            Jedis jedis = get().getResource();
            if (timeout <= 0) {
                jedis.persist(key);
            } else {
                jedis.expire(key, timeout);
            }
        }finally{
            closeResource();
        }
    }

    public void hset(String key, String field, Object value){
        try {
            Jedis jedis = get().getResource();
            byte[] keyBytes = key.getBytes();
            byte[] fieldBytes = field.getBytes();
            if (value == null) {
                jedis.hset(keyBytes, fieldBytes, NULL_VALUE);
            } else {
                jedis.hset(keyBytes, fieldBytes, serialize(value));
            }
        }finally{
            closeResource();
        }
    }

    private byte[] serialize(Object value){
        return KryoSerializer.serialize(value);
    }

    private <T> T deserialize(Class<T> clazz, byte[] b){
        return KryoSerializer.deserialize(clazz, b);
    }

    public <T> T hget(Class<T> clazz, String key, String field){
        try {
            Jedis jedis = get().getResource();
            byte[] b = jedis.hget(key.getBytes(), field.getBytes());
            if (b == null || b.length == 0 || Arrays.equals(b, NULL_VALUE)) {
                return null;
            } else {
                return deserialize(clazz, b);
            }
        }finally{
            closeResource();
        }
    }

    private void doSetInternal(Jedis jedis, String key, byte[] keyBytes, Object value){
        // TODO 优化设计
        if (value instanceof String) {
            jedis.set(key, (String) value);
        } else if (value instanceof Integer) {
            jedis.set(key, String.valueOf(value));
        } else if (value instanceof Long) {
            jedis.set(key, String.valueOf(value));
        } else {
            jedis.set(keyBytes, serialize(value));
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz, String key) {
        byte[] value = null;
        try {
            byte[] keyBytes = key.getBytes();
            Jedis jedis = get().getResource();
            value = jedis.get(keyBytes);
            if (value == null) {
                Initializer initializer = null;
                for (KeyPattern keyPattern : keyPatterns) {
                    if(keyPattern.accept(key)){
                        initializer = initializers.get(keyPattern);
                        break;
                    }
                }
                if(initializer == null){
                    return null;
                }
                InitializeResult initializeResult = null;
                try {
                    initializeResult = initializer.initialize(key);
                }catch (Exception e) {
                    logger.error("缓存初始化器初始化异常",e);
                }

                if (initializeResult.getResult() == null) {
                    jedis.set(keyBytes, NULL_VALUE);
                } else {
                    Object result = initializeResult.getResult();
                    doSetInternal(jedis, key, keyBytes, result);
//					byte[] bytes = Functions.serialize(initializeResult.getResult());
//					// 不使用加锁的方式设置
//					jedis.set(keyBytes, bytes);
                }
                if (initializeResult.getExpireTime() > 0) {
                    jedis.expire(keyBytes, initializeResult.getExpireTime());
                }
                return (T) initializeResult.getResult();
            } else if (Arrays.equals(NULL_VALUE, value)) {
                return null;
            } else {
                // TODO 优化设计
                if (String.class.equals(clazz)) {
                    try {
                        return (T) new String(value, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new SystemException(e);
                    }
                } else if (Integer.class.equals(clazz)) {
                    try {
                        return (T) Integer.valueOf(new String(value, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        throw new SystemException(e);
                    }
                } else if (Long.class.equals(clazz)) {
                    try {
                        return (T) Long.valueOf(new String(value, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        throw new SystemException(e);
                    }
                } else {
                    return deserialize(clazz, value);
                }
            }
        } finally {
            closeResource();
        }
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

}
