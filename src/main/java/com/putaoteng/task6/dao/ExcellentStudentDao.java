package com.putaoteng.task6.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.putaoteng.task6.model.BasicVo;

@Repository(value = "excellentStudentDao")
public interface ExcellentStudentDao extends BasicVoDao {
	public List<BasicVo> findByExcellentStudentName(String name);
}
