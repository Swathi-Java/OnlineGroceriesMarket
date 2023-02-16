package com.lancesoft.omg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancesoft.omg.entity.CategoriesEntity;
@Repository
public interface AdminDashBoardDao extends JpaRepository<CategoriesEntity, String> {
    CategoriesEntity findByCatName(String catName);
    boolean existsBycatName(String catName);
}
