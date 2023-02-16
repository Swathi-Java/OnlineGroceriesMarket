package com.lancesoft.omg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "user_sql")
	@GenericGenerator(name="user_sql",strategy = "com.lancesoft.omg.idGenerator.CustomIdGenerator",parameters = {
			@Parameter(name=CustomIdGenerator.INCREMENT_PARAM ,value="1"),
			@Parameter(name=CustomIdGenerator.VALUE_PREFIX_PARAMAETER,value="INV"),
			@Parameter(name=CustomIdGenerator.NUMBER_FORMAT_PARAMETER,value="03%d")
	})
 private String inventoryid;
 private String productName;
 private int quantity;
}
