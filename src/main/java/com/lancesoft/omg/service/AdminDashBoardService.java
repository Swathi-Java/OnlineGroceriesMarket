package com.lancesoft.omg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lancesoft.omg.dto.CategoriesDto;
import com.lancesoft.omg.entity.CategoriesEntity;
import com.lancesoft.omg.entity.Inventory;
@Service
public interface AdminDashBoardService {
	 public Boolean addcat(CategoriesDto categoriesDto);
	 public List<CategoriesEntity>getCategories( );
	 public Inventory addinventory(Inventory inventory);
	 public List<Inventory> getInventory();
}
