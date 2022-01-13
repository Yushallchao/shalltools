package com.xiao.redis.test;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TestRedis {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TestRedis t = new TestRedis();
        t.setup();
        t.testString();
    }

    private Jedis jedis;
    /**
     * @description 初始化连接
     * @author Yxc
     * @date 2021/11/22 14:41
     * @param
     * @return void
     */
    public void setup() {
        //192.168.11.146:6379
        jedis = new Jedis("192.168.11.146", 6379);
        //密码
        jedis.auth("admin");
    }

    /**
     * @description
     * @author Yxc
     * @date 2021/11/22 14:44
     * @param
     * @return void
     */
    public void testString() {
        jedis.set("name", "xinxin");// key-->name，value-->xinxin
        System.out.println(jedis.get("name"));

        jedis.append("name", " is my lover");
        System.out.println(jedis.get("name"));

        jedis.del("name");
        System.out.println(jedis.get("name"));
        jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
        jedis.incr("age");
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-"
                + jedis.get("qq"));

    }

    /**
     * @description
     * @author Yxc
     * @date 2021/11/22 14:42
     * @param
     * @return void
     */
    public void testMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xinxin");
        map.put("age", "22");
        map.put("qq", "123456");
        jedis.hmset("user", map);

        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rsmap);

        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age"));
        System.out.println(jedis.hlen("user"));
        System.out.println(jedis.exists("user"));
        System.out.println(jedis.hkeys("user"));
        System.out.println(jedis.hvals("user"));

        Iterator<String> iter = jedis.hkeys("user").iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
    }

    /**
     * @description
     * @author Yxc
     * @date 2021/11/22 14:42
     * @param
     * @return void
     */
    public void testList() {
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
        // ����key java framework�д����������
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));

        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "struts");
        jedis.rpush("java framework", "hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));
    }

    /**
     * @description
     * @author Yxc
     * @date 2021/11/22 14:43
     * @param
     * @return void
     */
    public void testSet() {
        jedis.sadd("user", "liuling");
        jedis.sadd("user", "xinxin");
        jedis.sadd("user", "ling");
        jedis.sadd("user", "zhangxinxin");
        jedis.sadd("user", "who");

        jedis.srem("user", "who");
        System.out.println(jedis.smembers("user"));
        System.out.println(jedis.sismember("user", "who"));
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));
    }

    public void test() {
        jedis.del("a");
        jedis.rpush("a", "1");
        jedis.lpush("a", "6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
        System.out.println(jedis.sort("a")); // [1, 3, 6, 9]
        System.out.println(jedis.lrange("a", 0, -1));
    }
}
