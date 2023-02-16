package com.lancesoft.omg.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.omg.entity.RegistrationEntity;

public interface RegistrationDao extends JpaRepository<RegistrationEntity, Integer> {
   RegistrationEntity findByUserName(String userName);
   boolean existsByUserName(String userName);
   boolean existsByPhoneNumber(String pHoneNumber);
   RegistrationEntity findByPhoneNumber(String phoneNumber);
}
