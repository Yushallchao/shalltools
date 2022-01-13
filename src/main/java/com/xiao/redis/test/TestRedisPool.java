package com.xiao.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestRedisPool {
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    private static String ADDR = "192.168.0.100";

    private static int PORT = 6379;

    private static String AUTH = "admin";

    private static int MAX_ACTIVE = 1024;

    private static int MAX_IDLE = 200;

    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    /**
     * @description 初始化JedisPool连接池
     * @author Yxc
     * @date 2021/11/22 14:46
     * @param null
     * @return
     */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
