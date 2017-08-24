package com.putaoteng.task6.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.putaoteng.task6.dao.UserDao;
import com.putaoteng.task6.model.BasicVo;

@Service (value="userDaoService")
public class UserDaoService{
	
	@Resource (name = "userDao")
	private UserDao userDao;
	
	public int save(BasicVo basicVo){
		return userDao.save(basicVo);
	}
	
	public int saveBatch(List<BasicVo> list){
		return userDao.saveBatch(list);
	}
	
	public int update(BasicVo basicVo){
		return userDao.update(basicVo);
	}
	
	public int updateIgnoreNull(BasicVo basicVo){
		return userDao.updateIgnoreNull(basicVo);
	}
	
	public int updateBatch(List<BasicVo> list){
		return userDao.updateBatch(list);
	}
	
	public int delete(BasicVo basicVo){
		return userDao.delete(basicVo);
	}
	
	public int deleteBatch(List<BasicVo> list){
		return userDao.deleteBatch(list);
	}
	
	public int deleteByPK(Long id){
		return userDao.deleteByPK(id);
	}
	
	public int deleteAll(){
		return userDao.deleteAll();
	}
	
	public long count(){
		return userDao.count();
	}
	
	public BasicVo findByPK(Long id){
		return userDao.findByPK(id);
	}
	
//	public List find(Map<String, Object> paramMap, PageBounds pageBounds);
	
	public List<BasicVo> findAll(){
		return userDao.findAll();
	}
	
	public BasicVo findByUsername(String username){
		return userDao.findByUsername(username);
	}
}
