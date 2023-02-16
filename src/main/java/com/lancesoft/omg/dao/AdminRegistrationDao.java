package com.lancesoft.omg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancesoft.omg.entity.AdminRegistrationEntity;

@Repository
public interface AdminRegistrationDao extends JpaRepository<AdminRegistrationEntity, Integer>{
	 AdminRegistrationEntity findByphoneNumber(String phoneNumber);
	 AdminRegistrationEntity findByuserName(String userName);
	 boolean existsByphoneNumber(String phoneNumber);
	 boolean existsByuserName(String userName);
	
}
