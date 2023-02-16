package com.lancesoft.omg.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import org.hibernate.annotations.Parameter;

import com.lancesoft.omg.idGenerator.CustomIdGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
public class ProductsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "user_sql")
	@GenericGenerator(name="user_sql",strategy = "com.lancesoft.omg.idGenerator.CustomIdGenerator",parameters = {
			@Parameter(name=CustomIdGenerator.INCREMENT_PARAM,value="1"),
			@Parameter(name=CustomIdGenerator.VALUE_PREFIX_PARAMAETER,value="PRD"),
			@Parameter(name=CustomIdGenerator.NUMBER_FORMAT_PARAMETER,value="%03d")
	})
 private String prodid;
 private String productName;
 private String description;
 private int price;
 @CreationTimestamp
 @Temporal(TemporalType.TIMESTAMP)
 private Date addOnDate;
 private String imageUrl;
 private String status;
 @ManyToOne
 @JoinColumn(name="catId")
 private CategoriesEntity categoriesEntity;
}
