package com.putaoteng.task6.dao;

import org.springframework.stereotype.Repository;

import com.putaoteng.task6.model.Profession;

@Repository(value = "professionDao")
public interface ProfessionDao extends BasicVoDao {
	public Profession findByProfession(String profession);
}
