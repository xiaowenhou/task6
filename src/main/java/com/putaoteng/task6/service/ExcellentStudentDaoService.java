package com.putaoteng.task6.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.putaoteng.task6.dao.ExcellentStudentDao;
import com.putaoteng.task6.model.BasicVo;
import com.putaoteng.task6.model.ExcellentStudent;
import com.putaoteng.task6.utils.Cache;
import com.putaoteng.task6.utils.Log;
import com.putaoteng.task6.utils.LogLevel;

@Service (value="excellentStudentDaoService")
public class ExcellentStudentDaoService{
	
	@Resource (name = "excellentStudentDao")
	private ExcellentStudentDao excellentStudentDao;
	
	private Cache cache = new Cache();
	
	
	
	public int save(BasicVo basicVo){
		int result = excellentStudentDao.save(basicVo);
		
		return result;
	}
	
	public int saveBatch(List<BasicVo> list){
		return excellentStudentDao.saveBatch(list);
	}
	
	public int update(BasicVo basicVo){
		return excellentStudentDao.update(basicVo);
	}
	
	public int updateIgnoreNull(BasicVo basicVo){
		return excellentStudentDao.updateIgnoreNull(basicVo);
	}
	
	public int updateBatch(List<BasicVo> list){
		return excellentStudentDao.updateBatch(list);
	}
	
	public int delete(BasicVo basicVo){
		return excellentStudentDao.delete(basicVo);
	}
	
	public int deleteBatch(List<BasicVo> list){
		return excellentStudentDao.deleteBatch(list);
	}
	
	public int deleteByPK(Long id){
		return excellentStudentDao.deleteByPK(id);
	}
	
	public int deleteAll(){
		return excellentStudentDao.deleteAll();
	}
	
	public long count(){
		return excellentStudentDao.count();
	}
	
	public BasicVo findByPK(Long id){
		BasicVo result = (ExcellentStudent) cache.get("excellentStudent"+id);
		if (result == null){
			result = excellentStudentDao.findByPK(id);
			cache.set("excellentStudent"+id, 0, result);
		}
		return result;
	}
	
	public List<BasicVo> findByExcellentStudentName(String name){
		@SuppressWarnings("unchecked")
		List<BasicVo> list = (List<BasicVo>) cache.get("excellentStudentList"+name);	
		if (list == null){
			list = excellentStudentDao.findByExcellentStudentName(name);
			Log.loggerCreate(LogLevel.WARN, "Data from DB-------------------------------");
			
			//如果是由效的数据,则缓存时间较长,如果是空值,则缓存时间较短
			if (!list.isEmpty()){
				cache.set("excellentStudentList"+name, 0, list);
			} else{
				cache.set("excellentStudentList"+name, 60*5, list);
			}
		}
		return excellentStudentDao.findByExcellentStudentName(name);
	}
//	public List find(Map<String, Object> paramMap, PageBounds pageBounds);
	
	@Cacheable(value="excellentStudentList")
	public List<BasicVo> findAll(){
		
		Log.loggerCreate(LogLevel.WARN, "Service---------------------------------------------------");
		return excellentStudentDao.findAll();
	}
}
