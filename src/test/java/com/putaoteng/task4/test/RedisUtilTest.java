package com.putaoteng.task4.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.putaoteng.task6.model.BasicVo;
import com.putaoteng.task6.model.ExcellentStudent;
import com.putaoteng.task6.utils.RedisUtil;
import com.putaoteng.task6.utils.SerializingUtil;

import redis.clients.jedis.Jedis;

public class RedisUtilTest {
	private Jedis jedis;
	private RedisUtil redisUtil;
	
	@Before
	public void setUp() throws Exception {
		redisUtil = new RedisUtil();
		jedis = redisUtil.getJedis();
	}

	@Test
	public void test() {
		jedis.set("zhangsan", "hahahaha");
		
		redisUtil.returnResource();
	}
	
	@Test
	public void testBean(){
		ExcellentStudent student = new ExcellentStudent();
		student.setId(1);
		student.setName("zhangsan");
		student.setImgUrl("/images/3.jpg");
		student.setProfession("Java");
		student.setDesire("牛逼");
		student.setCreateAt(System.currentTimeMillis());
		student.setUpdateAt(System.currentTimeMillis());
		
		jedis.del("excellent");
		
		jedis.set(SerializingUtil.serialize("excellent"), SerializingUtil.serialize(student));
		
		
		byte[] byteArray = jedis.get(SerializingUtil.serialize("excellent"));
		
		ExcellentStudent student2 = (ExcellentStudent) SerializingUtil.deserialize(byteArray);
		
		System.out.println(student2.toString());
		
		redisUtil.returnResource();
	}
	
	@Test
	public void testList(){
		ExcellentStudent student = new ExcellentStudent();
		student.setId(1);
		student.setName("zhangsan");
		student.setImgUrl("/images/3.jpg");
		student.setProfession("Java");
		student.setDesire("牛逼");
		student.setCreateAt(System.currentTimeMillis());
		student.setUpdateAt(System.currentTimeMillis());
		
		ExcellentStudent student2 = new ExcellentStudent();
		student2.setId(2);
		student2.setName("lisi");
		student2.setImgUrl("/images/hahaha.png");
		student2.setProfession("Web");
		student2.setDesire("超级牛逼");
		student2.setCreateAt(System.currentTimeMillis());
		student2.setUpdateAt(System.currentTimeMillis());
		
		List<BasicVo> list = new ArrayList<BasicVo>(); 
		list.add(student);
		list.add(student2);
		
		jedis.del("excellentList");
		
		jedis.set(SerializingUtil.serialize("excellentList"), SerializingUtil.serialize(list));
		
		byte[] byteArray = jedis.get(SerializingUtil.serialize("excellentList"));
		
		@SuppressWarnings("unchecked")
		List<BasicVo> list2 = (List<BasicVo>) SerializingUtil.deserialize(byteArray);
		
		for (BasicVo basicVo : list2) {
			System.out.println(basicVo);
		}
		
		
		byte[] byteArray2 = jedis.get(SerializingUtil.serialize("Hahaha"));
		
		if (byteArray2 == null)
			System.out.println("ok");
		
		/*@SuppressWarnings("unchecked")
		List<BasicVo> list3 = (List<BasicVo>) SerializingUtil.deserialize(byteArray2);
		*/

		redisUtil.returnResource();
	}

}
