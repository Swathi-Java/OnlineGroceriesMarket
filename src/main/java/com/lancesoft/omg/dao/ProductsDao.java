package com.lancesoft.omg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.omg.entity.CategoriesEntity;
import com.lancesoft.omg.entity.ProductsEntity;

public interface ProductsDao extends JpaRepository<ProductsEntity, String> {
	  boolean existsByproductName(String productName);
      ProductsEntity findByproductName(String productName);
      ProductsEntity findByProductid(String productid);
      List<ProductsEntity> findByCategoriesEntity(CategoriesEntity categoriesEntity);
}
