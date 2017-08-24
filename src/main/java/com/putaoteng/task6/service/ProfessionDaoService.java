package com.putaoteng.task6.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.putaoteng.task6.dao.ProfessionDao;
import com.putaoteng.task6.model.BasicVo;
import com.putaoteng.task6.model.Profession;
import com.putaoteng.task6.utils.Cache;
import com.putaoteng.task6.utils.Log;
import com.putaoteng.task6.utils.LogLevel;

@Service (value="professionDaoService")
public class ProfessionDaoService{
	@Resource (name = "professionDao")
	private ProfessionDao professionDao;
	private Cache cache = new Cache();
	
	
	
	public int save(BasicVo basicVo){
		return professionDao.save(basicVo);
	}
	
	public int saveBatch(List<BasicVo> list){
		return professionDao.saveBatch(list);
	}
	 
	public int update(BasicVo basicVo){
		return professionDao.update(basicVo);
	}
	
	public int updateIgnoreNull(BasicVo basicVo){
		return professionDao.updateIgnoreNull(basicVo);
	}
	
	public int updateBatch(List<BasicVo> list){
		return professionDao.updateBatch(list);
	}
	
	public int delete(BasicVo basicVo){
		return professionDao.delete(basicVo);
	}
	
	public int deleteBatch(List<BasicVo> list){
		return professionDao.deleteBatch(list);
	}
	
	public int deleteByPK(Long id){
		return professionDao.deleteByPK(id);
	}
	
	public int deleteAll(){
		return professionDao.deleteAll();
	}
	
	public long count(){
		return professionDao.count();
	}
	
	public BasicVo findByPK(Long id){
		return professionDao.findByPK(id);
	}
	
	public Profession findByProfession(String profession){
		Profession p = (Profession) cache.get("profession"+profession);	
		
		if (p == null){
			p = professionDao.findByProfession(profession);
			Log.loggerCreate(LogLevel.WARN, "Data from DB-------------------------------");
			
			//如果是由效的数据,则缓存时间较长,如果是空值,则缓存时间较短
			if (p != null){
				cache.set("profession"+profession, 0, p);
			} else{
				cache.set("profession"+profession, 60*5, p);
			}
		}
		return p;
	}
//	public List find(Map<String, Object> paramMap, PageBounds pageBounds);
	
	
	@SuppressWarnings("unchecked")
	public List<BasicVo> findAll(){
		List<BasicVo> list = (List<BasicVo>) cache.get("professionList");	
		if (list == null){
			list = professionDao.findAll();
			Log.loggerCreate(LogLevel.WARN, "Data from DB-------------------------------");
			
			//如果是由效的数据,则缓存时间较长,如果是空值,则缓存时间较短
			if (!list.isEmpty()){
				cache.set("professionList", 0, list);
			} else{
				cache.set("professionList", 60*5, list);
			}
		}
		return list;
	}
}
