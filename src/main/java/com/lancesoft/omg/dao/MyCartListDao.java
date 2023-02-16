package com.lancesoft.omg.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.omg.entity.MyCartList;


public interface MyCartListDao extends JpaRepository<MyCartList,String> {
	MyCartList findByUserName(String userName);
     
	void deleteByid(String cartid);
	void deleteByUserName(String userName);

	boolean existsByUserName(String userName);
}
