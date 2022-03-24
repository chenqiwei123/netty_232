package com.example.netty.netty.Controller;

import jdk.internal.dynalink.linker.LinkerServices;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.*;

@Component
public class RedisConnect {
    @Autowired
    private ConfigRedisBeanValue configRedisBeanValue;
//    public Jedis getRedisConnect(){
//        JedisShardInfo jedisShardInfo = new JedisShardInfo(configRedisBeanValue.baosight_ip, configRedisBeanValue.baosight_Port);
//        jedisShardInfo.setPassword("baosight");//密码认证
//        Jedis jedis = new Jedis(jedisShardInfo);
//        return jedis;
//    }

    public static void main(String[] args) {
    }
}
