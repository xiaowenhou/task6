package com.putaoteng.task6.dao;

import org.springframework.stereotype.Repository;

import com.putaoteng.task6.model.BasicVo;

@Repository (value="userDao")
public interface UserDao extends BasicVoDao {
	public BasicVo findByUsername(String username);
}
