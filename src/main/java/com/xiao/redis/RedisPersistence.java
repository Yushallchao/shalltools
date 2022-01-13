package com.xiao.redis;

import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class RedisPersistence {

    private String redisIp = "192.168.11.146";
    private int redisPort = 6379;//redis端口

    public static void saveAllRedis(final String redisIp,final int redisPort,final String appCode) {
        Jedis redis = new Jedis(redisIp, redisPort);// 初始化redis
        redis.auth("admin");//redis密码
        Set keys = redis.keys("*");
        Iterator t1 = keys.iterator();
        while (t1.hasNext()) {
            Object obj1 = t1.next();
            saveRedisObject(redis, obj1 + "", redisIp, redisPort + "", appCode);
        }
    }

    private static void saveRedisObject(final Jedis  redis,final String redisKey,final String macIp,final String port,final String appCode) {
        String redisType = redis.type(redisKey);
        RedisTable redisTable = new RedisTable();
        redisTable.setAppCode(appCode);
        redisTable.setCreateTime(new Date());
        redisTable.setMacIp(macIp);
        redisTable.setPort(port);
        redisTable.setRedisKey(redisKey);
        redisTable.setRedisType(redisType);
        redisTable.setRemark("");
        redisTable.setUpdateTime(new Date());

        //set
        if("set".equalsIgnoreCase(redisType)){
            Set<String> setStrings  = redis.smembers(redisKey);
            if(null != setStrings && !setStrings.isEmpty()){
                Iterator setIterator = setStrings.iterator() ;
                while(setIterator.hasNext()){
                    Object obj1 = setIterator.next();
                    redisTable.setRedisValue(obj1+"");
                    printRedis(redisTable);
                }
            }
            //hash
        }else if("hash".equalsIgnoreCase(redisType)){
            Set<String> hashSets = redis.hkeys(redisKey);
            if(null != hashSets && !hashSets.isEmpty()){
                Iterator setIterator = hashSets.iterator() ;
                while(setIterator.hasNext()){
                    String objectName = setIterator.next()+"";
                    redisTable.setObjectName(objectName);
                    redisTable.setRedisValue(redis.hget(redisKey, objectName));
                    printRedis(redisTable);
                }
            }
            //list
        }else if("list".equalsIgnoreCase(redisType)){
            Long listLen = redis.llen(redisKey);
            for (Long i = 0L; i < listLen; i++) {
                redisTable.setRedisValue(redis.lindex(redisKey, i));
                printRedis(redisTable);
            }
            //sortedset
        }else if("sortedset".equalsIgnoreCase(redisType)){
//          Long redisLenth = redis.zcard(redisKey);
            Set<String> sortedsets = redis.zrange(redisKey, 0, -1);
            if(null != sortedsets && !sortedsets.isEmpty()){
                Iterator setIterator = sortedsets.iterator() ;
                while(setIterator.hasNext()){
                    String sortedMember = setIterator.next() +"";
                    redisTable.setRedisValue(sortedMember);
                    redisTable.setScore("" +redis.zscore(redisKey, sortedMember));
                    printRedis(redisTable);
                }
            }
            //string
        }else if("string".equalsIgnoreCase(redisType)){
            redisTable.setRedisValue(redis.get(redisKey));
            printRedis(redisTable);
        }else{
            System.out.println("UnknowRedisType-----redisType: " +redisType+"      objValue: "+redis.get(redisKey));
        }
    }

    /**
     * @description
     * @author Yxc
     * @date 2021/11/22 14:31
     * @param redisTable
     * @return void
     */
    public static void printRedis (RedisTable redisTable) {
        System.out.println("redisType:"+redisTable.getRedisType()
                + " redisKey:"+redisTable.getRedisKey()
                + " ObjectName:"+redisTable.getObjectName()
                + " redisValue:"+redisTable.getRedisValue()
                + " redisScore:"+redisTable.getScore()
        );
    }


}
