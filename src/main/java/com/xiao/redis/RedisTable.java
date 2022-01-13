package com.xiao.redis;

import lombok.Data;

import java.util.Date;

@Data
public class RedisTable {
    private Long redisId;
    private String redisType;//redis数据类型set/list/hash/sortedset/string
    private String redisKey;
    private String objectName;
    private String redisValue;
    private String keyToken;
    private String score;
    private Date createTime;
    private Date updateTime;
    private String macIp;
    private String port;
    private String appCode;
    private String remark;
    private String isModify;
}
