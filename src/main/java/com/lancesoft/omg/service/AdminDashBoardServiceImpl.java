package com.lancesoft.omg.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancesoft.omg.dao.AdminDashBoardDao;
import com.lancesoft.omg.dao.InventoryDao;
import com.lancesoft.omg.dto.CategoriesDto;
import com.lancesoft.omg.entity.CategoriesEntity;
import com.lancesoft.omg.entity.Inventory;
import com.lancesoft.omg.exception.CustomException;
@Service
public class AdminDashBoardServiceImpl implements AdminDashBoardService{
	@Autowired
	private AdminDashBoardDao adminDashBoardDao;
	@Autowired
	private InventoryDao inventoryDao;
	
	private static Logger logger = Logger.getLogger(AdminDashBoardServiceImpl.class);

	public Boolean addcat(CategoriesDto categoriesDto) {
		logger.info("Admin Dash Board add category started..");
		ModelMapper modelMapper = new ModelMapper();
		CategoriesEntity categoriesEntity = new CategoriesEntity();

		// Checking categories entities are initialized or not
		if (categoriesDto == null) {
			throw new RuntimeException("null found in Categories please check");
		} else if (adminDashBoardDao.existsBycatName(categoriesDto.getCatName())) {
			throw new CustomException("Category Already exists");
		}

		modelMapper.map(categoriesDto, categoriesEntity);
		logger.info("Admin Dash Board add category end..");
		try {
			adminDashBoardDao.save(categoriesEntity);
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	@Override
	public List<CategoriesEntity> getCategories() {
		List<CategoriesEntity> categories= adminDashBoardDao.findAll();
		if(!categories.isEmpty())
		{
			return categories;
		}
		 throw new CustomException("Categories are empty");
	}

	@Override
	public Inventory addinventory(Inventory inventory) {
		if(inventoryDao.existsByproductName(inventory.getProductName()))
		{
			throw new CustomException("product is already exists");
		}
		else if(inventory==null)
		{
			throw new CustomException("please give the product Details");
		}
		
		 return inventoryDao.save(inventory);   
		
	}

	@Override
	public List<Inventory> getInventory() {
		
		return inventoryDao.findAll();
	}

}
