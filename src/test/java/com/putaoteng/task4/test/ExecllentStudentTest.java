package com.putaoteng.task4.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.putaoteng.task6.dao.ExcellentStudentDao;
import com.putaoteng.task6.model.BasicVo;
import com.putaoteng.task6.model.ExcellentStudent;
import com.putaoteng.task6.service.ExcellentStudentDaoService;

public class ExecllentStudentTest {
	private ExcellentStudentDao execllentStudentDao;
	
	@Autowired
	private ExcellentStudentDaoService execllentStudentDaoService;
	
	@Before
	public void setUp(){
		@SuppressWarnings("resource")
		ApplicationContext context =
				new ClassPathXmlApplicationContext("application-context.xml");
		execllentStudentDao = (ExcellentStudentDao) context.getBean("excellentStudentDao");
	}
	
	@Test
	public void saveTest() {
		ExcellentStudent es1 = new ExcellentStudent();	
		es1.setName("张三");
		es1.setImgUrl("../images/1234567.png");
		es1.setProfession("java");
		es1.setDesire("牛逼");
		es1.setCreateAt(2017030184549L);
		es1.setUpdateAt(2017030184549L);

		int result = execllentStudentDao.save(es1);
		System.out.println(result);
		
		
		ExcellentStudent es2 = new ExcellentStudent();
		es2.setName("李四");
		es2.setImgUrl("../images/1234567.png");
		es2.setProfession("java");
		es2.setDesire("牛逼");
		es2.setCreateAt(2017030184549L);
		es2.setUpdateAt(2017030184549L);

		result = execllentStudentDao.save(es2);
		System.out.println(result);
		
		ExcellentStudent es3 = new ExcellentStudent();
		es3.setName("王五");
		es3.setImgUrl("../images/1234567.png");
		es3.setProfession("java");
		es3.setDesire("牛逼");
		es3.setCreateAt(2017030184549L);
		es3.setUpdateAt(2017030184549L);

		result = execllentStudentDao.save(es3);
		System.out.println(result);
		
		ExcellentStudent es4 = new ExcellentStudent();
		es4.setName("赵六");
		es4.setImgUrl("../view/images/4.jpg");
		es4.setProfession("WEB");
		es4.setDesire("有志者,事竟成!");
		es4.setCreateAt(2017030184549L);
		es4.setUpdateAt(2017030184549L);

		result = execllentStudentDao.save(es4);
		System.out.println(result);
	}

	/*
	@Test
	public void saveBatchTest() {
		
		List<BasicVo> list = new ArrayList<BasicVo>();
		
		Student student = new Student();
//		student.setId(1);
		student.setName("xiuzhenyuan");
		student.setQqNumber(123456789);
		student.setProfession("java");
		student.setJoinDate("2017年1月1日");
		student.setSchool("家里蹲");
		student.setOnlineNumber("9527");
		student.setDailyLink("www.baidu.com");
		student.setDesire("牛鼻");
		student.setMsgSource("知乎");
		student.setBrother("小师弟兄");
		student.setCreateAt(20170715133333L);
		student.setUpdateAt(20170715133356L);
		list.add(student);
		
		Student student2 = new Student();
		student2.setName("wahahah");
		student2.setQqNumber(123456789);
		student2.setProfession("java");
		student2.setJoinDate("2017年1月1日");
		student2.setSchool("家里蹲");
		student2.setOnlineNumber("9527");
		student2.setDailyLink("www.baidu.com");
		student2.setDesire("牛鼻");
		student2.setMsgSource("知乎");
		student2.setBrother("小师弟兄");
		student2.setCreateAt(20170715133333L);
		student2.setUpdateAt(20170715133356L);
		list.add(student2);
		
		
		int result = studentDao.saveBatch(list);
		System.out.println(result);
	}*/
	
	@Test
	public void updateTest(){
		ExcellentStudent es1 = new ExcellentStudent();
		es1.setId(13);
		es1.setName("小温侯");
		es1.setImgUrl("../images/1234567.png");
		es1.setProfession("java");
		es1.setDesire("niubi");
		es1.setCreateAt(2017030184549L);
		es1.setUpdateAt(2017030184549L);
	
		int result = execllentStudentDao.update(es1);
		System.out.println(result);
	}
	
	@Test
	public void deleteTest(){
		ExcellentStudent es1 = new ExcellentStudent();
		es1.setId(1);
		es1.setName("小温侯");
		es1.setImgUrl("../images/1234567.png");
		es1.setProfession("java");
		es1.setDesire("niubi");
		es1.setCreateAt(2017030184549L);
		es1.setUpdateAt(2017030184549L);
		
		int result = execllentStudentDao.delete(es1);
		System.out.println(result);
	}
	
	@Test
	public void deleteByPKTest(){
		int result = execllentStudentDao.deleteByPK(2L);
		System.out.println(result);
	}
	
	@Test
	public void deleteAllTest(){
		int result = execllentStudentDao.deleteAll();
		System.out.println(result);
	}
	
	@Test
	public void countTest(){
		long result = execllentStudentDao.count();
		System.out.println(result);
	}
	
	@Test
	public void findByPKTest(){
		long id = 6;
		ExcellentStudent es = (ExcellentStudent) execllentStudentDao.findByPK(id);
		
		System.out.println(es.toString());
	}
	
	@Test
	public void findAllTest(){
		List<BasicVo> list = new ArrayList<BasicVo>();
		
		list = execllentStudentDaoService.findAll();
		
		for (BasicVo basicVo : list) {
			System.out.println(basicVo.toString());
		}
	}
}
