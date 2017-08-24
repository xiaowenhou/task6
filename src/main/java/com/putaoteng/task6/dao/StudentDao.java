package com.putaoteng.task6.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.putaoteng.task6.model.BasicVo;

@Repository (value="studentDao")
public interface StudentDao extends BasicVoDao {
	public List<BasicVo> findByStudentName(String name);
}
