package com.putaoteng.task6.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.putaoteng.task6.dao.StudentDao;
import com.putaoteng.task6.model.BasicVo;
import com.putaoteng.task6.utils.Log;
import com.putaoteng.task6.utils.LogLevel;
import com.putaoteng.task6.utils.RedisUtil;
import com.putaoteng.task6.utils.SerializingUtil;

import redis.clients.jedis.Jedis;

@Service (value="studentDaoService")
public class StudentDaoService{
	
	@Resource (name = "studentDao")
	private StudentDao studentDao;
	private Jedis jedis;
	private RedisUtil redisUtil;
	
	
	public int save(BasicVo basicVo){
		return studentDao.save(basicVo);
	}
	
	public int saveBatch(List<BasicVo> list){
		return studentDao.saveBatch(list);
	}
	
	public int update(BasicVo basicVo){
		return studentDao.update(basicVo);
	}
	
	public int updateIgnoreNull(BasicVo basicVo){
		return studentDao.updateIgnoreNull(basicVo);
	}
	
	public int updateBatch(List<BasicVo> list){
		return studentDao.updateBatch(list);
	}
	
	public int delete(BasicVo basicVo){
		return studentDao.delete(basicVo);
	}
	
	public int deleteBatch(List<BasicVo> list){
		return studentDao.deleteBatch(list);
	}
	
	public int deleteByPK(Long id){
		return studentDao.deleteByPK(id);
	}
	
	public int deleteAll(){
		return studentDao.deleteAll();
	}
	
	public long count(){
		return studentDao.count();
	}
	
	public BasicVo findByPK(Long id){
		return studentDao.findByPK(id);
	}
	
//	public List find(Map<String, Object> paramMap, PageBounds pageBounds);
	
	@SuppressWarnings("unchecked")
	public List<BasicVo> findAll(){
		List<BasicVo> list = null;
		byte[] byteArray = null;
		//获取Jedis对象
		redisUtil = new RedisUtil();
		jedis = redisUtil.getJedis();
		
		//将key的值转换为字节数组
		byteArray = SerializingUtil.serialize("studentDaoList");
		
		//如果该字节数组存在,则说明redis已经缓存过相应的值,直接存redis中读取数据
		if (byteArray != null && jedis.get(byteArray) != null){
			Log.loggerCreate(LogLevel.ERROR, "This is redis cache..................");
			//将读取的数据反序列化后输出
			list = (List<BasicVo>) SerializingUtil.deserialize(jedis.get(byteArray));
		} else{
			list = studentDao.findAll();
			//将key和value的值序列化后存入redis 
			jedis.set(SerializingUtil.serialize("studentDaoList"), SerializingUtil.serialize(list));
		}
		
		redisUtil.returnResource();
		return list;
	}
	
	public List<BasicVo> findByStudentName(String name){
		return studentDao.findByStudentName(name);
	}
}
