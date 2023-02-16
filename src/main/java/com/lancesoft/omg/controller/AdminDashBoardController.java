package com.lancesoft.omg.controller;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.omg.dao.AdminDashBoardDao;
import com.lancesoft.omg.dto.CategoriesDto;
import com.lancesoft.omg.dto.ProductsDto;
import com.lancesoft.omg.entity.CategoriesEntity;
import com.lancesoft.omg.entity.Inventory;
import com.lancesoft.omg.entity.ProductsEntity;
import com.lancesoft.omg.service.AdminDashBoardServiceImpl;
import com.lancesoft.omg.service.ProductServiceImpl;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AdminDashBoardController {

	@Autowired
	AdminDashBoardServiceImpl adminDashBoardServiceImpl;
	@Autowired
	ProductServiceImpl productServiceImpl;

	
	@PostMapping("/addCategories")
	public ResponseEntity<Boolean> addCategories(@RequestBody CategoriesDto categoriesDto)
	{
		if (adminDashBoardServiceImpl.addcat(categoriesDto)) {
			return new ResponseEntity(true, HttpStatus.CREATED);
		}

		return new ResponseEntity(false, HttpStatus.EXPECTATION_FAILED);
	}
	@GetMapping("/getAllCategories")
	public ResponseEntity<CategoriesEntity> getcategories( )
	{
		return new ResponseEntity(adminDashBoardServiceImpl.getCategories(),HttpStatus.OK);
	}
	@PostMapping("/addInventory")
	public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory)
	{
		return  new ResponseEntity<>(adminDashBoardServiceImpl.addinventory(inventory),HttpStatus.CREATED);
	}
	@GetMapping("/getInventory")
	public List<Inventory> getinventory()
	{
		return adminDashBoardServiceImpl.getInventory();
	}
	@PostMapping("/addProducts")
	public ProductsEntity addproduct(@RequestBody @Valid ProductsDto productsDto)
	{
		return productServiceImpl.addproducts(productsDto); 
	}
	@GetMapping("/getAllProducts")
	public List<ProductsEntity> getproducts()
	{
		return productServiceImpl.getProducts();
	}
}
