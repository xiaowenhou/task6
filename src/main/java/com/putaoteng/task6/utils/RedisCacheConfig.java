package com.putaoteng.task6.utils;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport{
	private volatile JedisConnectionFactory jedisConnectionFactory;
	private volatile RedisTemplate<String, Object> redisTemplate;
	private volatile RedisCacheManager redisCacheManager;
	
	public RedisCacheConfig() {
		super();
	}

	public RedisCacheConfig(JedisConnectionFactory jedisConnectionFactory, RedisTemplate<String, Object> redisTemplate,
			RedisCacheManager redisCacheManager) {
		super();
		this.jedisConnectionFactory = jedisConnectionFactory;
		this.redisTemplate = redisTemplate;
		this.redisCacheManager = redisCacheManager;
	}

	public JedisConnectionFactory redisConnectionFactory(){
			return jedisConnectionFactory;
	}
	
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf) {
        return redisTemplate;
    }

    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        return redisCacheManager;
    }
	
    //自定义key生成器
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
    
}
