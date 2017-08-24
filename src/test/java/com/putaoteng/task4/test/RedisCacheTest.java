package com.putaoteng.task4.test;


import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisCacheTest {
	private Jedis jedis;
	
	@Before
	public void setUp() throws Exception {
		jedis = new Jedis("120.77.169.243", 6379);
//		jedis.auth("123456789");
	}

	@Test
	public void testString() {
		//添加
		jedis.set("name", "xiaowenhou");
		System.out.println(jedis.get("name"));
		
		//拼接
		jedis.append("name", "is my ID");
		System.out.println(jedis.get("name"));
		
		//删除
		jedis.del("name");
		System.out.println(jedis.get("name"));
		
		jedis.mset("name", "zhangsan", "profession", "Java", "age", "25");
		System.out.println("Before: "+jedis.get("name")+"-"+jedis.get("profession")+"-"+jedis.get("age"));
		jedis.incr("age");
		System.out.println("After: "+jedis.get("name")+"-"+jedis.get("profession")+"-"+jedis.get("age"));
	}

}
