package me.tanyp.util;

import me.tanyp.util.basic.KryoSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by tanyp on 2018/8/13
 */
public class RedisManager {
    //内存可见性
    private static volatile RedisManager manager = null;
    private final static byte[] NULL_VALUE = "null".getBytes();
    private ThreadLocal<Jedis> jedisThreadLocal = new ThreadLocal<>();
    private JedisPool pool = null;
    private JedisPoolConfig jedisPoolConfig = null;
    private String host;
    private int port;
    //连接超时（默认2000ms）
    private int connectionTimeout = Protocol.DEFAULT_TIMEOUT;
    //响应超时（默认2000ms）
    private int soTimeout = Protocol.DEFAULT_TIMEOUT;
    private String password = null;
    //数据库（默认0）
    private int database = Protocol.DEFAULT_DATABASE;
    //客户端名称（默认null）
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

    private JedisPool getPool() {
        return get().pool;
    }

    public Jedis getResource() {
        Jedis jedis = jedisThreadLocal.get();
        if (jedis == null) {
            jedis = getPool().getResource();
            jedisThreadLocal.set(jedis);
        }
        return jedis;
    }

    public void closeResource() {
        Jedis jedis = jedisThreadLocal.get();
        if (jedis != null) {
            jedisThreadLocal.remove();
            jedis.close();
        }
    }

    public void delete(String key) {
        try {
            Jedis jedis = get().getResource();
            jedis.del(key);
        } finally {
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

    public void put(String key, Object value) {
        try {
            Jedis jedis = get().getResource();
            byte[] keyBytes = key.getBytes();
            if (value == null) {
                jedis.set(keyBytes, NULL_VALUE);
                return;
            } else {
                doSetInternal(jedis, key, keyBytes, value);
            }
        } finally {
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

    public <T> T get(Class<T> clazz, String key) {
        try {
            byte[] keyBytes = key.getBytes();
            Jedis jedis = get().getResource();
            byte[] value = jedis.get(keyBytes);
            if (value == null) {
                return null;
            } else if (Arrays.equals(NULL_VALUE, value)) {
                return null;
            } else {
                try {
                    if (String.class.equals(clazz)) {
                        return (T) new String(value, "UTF-8");
                    } else if (Integer.class.equals(clazz)) {
                        return (T) Integer.valueOf(new String(value, "UTF-8"));
                    } else if (Long.class.equals(clazz)) {
                        return (T) Long.valueOf(new String(value, "UTF-8"));
                    } else {
                        return deserialize(clazz, value);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            closeResource();
        }
    }

    private void doSetInternal(Jedis jedis, String key, byte[] keyBytes, Object value) {
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

    private byte[] serialize(Object value) {
        return KryoSerializer.serialize(value);
    }

    private <T> T deserialize(Class<T> clazz, byte[] b) {
        return KryoSerializer.deserialize(clazz, b);
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public JedisPoolConfig getJedisPoolConfig() {
        return jedisPoolConfig;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
