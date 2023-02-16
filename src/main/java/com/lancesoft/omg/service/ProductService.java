package com.lancesoft.omg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lancesoft.omg.dto.ProductsDto;
import com.lancesoft.omg.entity.ProductsEntity;

@Service
public interface ProductService {
 public ProductsEntity addproducts(ProductsDto productsDto);
public List<ProductsEntity> getProducts();
}
