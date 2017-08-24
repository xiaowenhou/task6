package com.putaoteng.task6.utils;

import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUtil {
	private JedisPool jedisPool;
	ResourceBundle bundle = ResourceBundle.getBundle("redis");

	public RedisUtil() {
		ApplicationContext context = new 
				ClassPathXmlApplicationContext("application-context.xml");
		
		jedisPool = (JedisPool) context.getBean("jedisPool");
	}

	public Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();

		return jedis;
	}

	public void returnResource() {
		jedisPool.close();
	}

}
