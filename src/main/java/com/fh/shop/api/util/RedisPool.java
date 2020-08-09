package com.fh.shop.api.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

    private RedisPool(){

    }

    private static JedisPool jedisPool;

    private static void initPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMinIdle(500);
        jedisPoolConfig.setMaxIdle(500);

        jedisPool = new JedisPool(jedisPoolConfig,"192.168.17.178",6379);
    }

    //只会在jvm加载类的时候创建一次（为了保证只会创建一个连接池）
    //static静态块中只能调用静态方法
    static {
        initPool();
    }

    public static Jedis getResource(){
        return jedisPool.getResource();
    }
}
