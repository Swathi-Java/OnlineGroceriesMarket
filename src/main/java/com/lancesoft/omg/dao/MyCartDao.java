package com.lancesoft.omg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.omg.entity.MyCart;

public interface MyCartDao extends JpaRepository<MyCart,String> {
    List<MyCart> findByuserName(String userNAme);
    MyCart findByCartid(String CartId);
    boolean existsByCartid(String cartid);
    boolean existsByproduct(String product);
    void deleteByCartid(String cartid);
    boolean existsByuserName(String userName);
}
