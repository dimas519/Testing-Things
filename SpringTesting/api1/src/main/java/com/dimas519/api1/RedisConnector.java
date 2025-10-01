package com.dimas519.api1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class RedisConnector {

    @Autowired
    private StringRedisTemplate redisTemplate;



    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
