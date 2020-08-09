package com.fh.shop.api;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestRedis {

    Jedis jedis;

    @Test
    public void test1(){
        try {
            jedis = new Jedis("192.168.17.178", 6379);

            jedis.set("productName", "PenSheZhanShi");

            String productName = jedis.get("productName");

            System.out.println(productName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }

    @Test
    public void test2(){
        try {
            jedis = new Jedis("192.168.17.178",6379);

            jedis.del("productName");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }

}
