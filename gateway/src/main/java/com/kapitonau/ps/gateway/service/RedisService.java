/*
package com.kapitonau.ps.gateway.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Сохранение значения
    public void setValue(String key, Object value, Duration ttl) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, value, ttl);
    }

    // Получение значения
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Проверка существования ключа
    public boolean keyExists(String key) {
        Boolean exists = redisTemplate.hasKey(key);
        return exists != null && exists;
    }

    // Удаление ключа
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    // Пример для токенов
    public void saveToken(String tokenId, String token, Duration ttl) {
        setValue("token:" + tokenId, token, ttl);
    }

    public String getToken(String tokenId) {
        return (String) getValue("token:" + tokenId);
    }
}*/
