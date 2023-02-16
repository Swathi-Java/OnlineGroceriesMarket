package com.lancesoft.omg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.omg.entity.AddressEntity;

public interface AddressDao extends JpaRepository<AddressEntity, String> {
    List<AddressEntity> findByuserName(String userName);
}
