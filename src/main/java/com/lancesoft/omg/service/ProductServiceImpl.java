package com.lancesoft.omg.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.lancesoft.omg.dao.AdminDashBoardDao;
import com.lancesoft.omg.dao.ProductsDao;
import com.lancesoft.omg.dto.ProductsDto;
import com.lancesoft.omg.entity.CategoriesEntity;
import com.lancesoft.omg.entity.ProductsEntity;
import com.lancesoft.omg.exception.CustomException;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductsDao productsDao;
	@Autowired
	AdminDashBoardDao adminDashBoardDao;
	@Override
	public ProductsEntity addproducts(ProductsDto productsDto) {
		ModelMapper modelMapper=new ModelMapper();
		ProductsEntity productsEntity=new ProductsEntity();
		if(productsDto==null)
		{
			throw new RuntimeException("products Dto is null please check it");
		}
		else if(productsDao.existsByproductName(productsDto.getProductName()))
				throw new CustomException("Product is Already exists");
		modelMapper.map(productsDto,productsEntity);
			CategoriesEntity categoriesEntity=adminDashBoardDao.findByCatName(productsEntity.getCategoriesEntity().getCatName());
			productsEntity.setCategoriesEntity(categoriesEntity);
			return productsDao.save(productsEntity);
	}
	@Override
	public List<ProductsEntity> getProducts() {
		
		return productsDao.findAll();
	}
	

	
}
