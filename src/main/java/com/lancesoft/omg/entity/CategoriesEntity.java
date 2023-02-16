package com.lancesoft.omg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.lancesoft.omg.idGenerator.CustomIdGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesEntity {
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY, generator = "user_sql")
    @GenericGenerator(name="user_sql", strategy="com.lancesoft.omg.idGenerator.CustomIdGenerator", parameters = {
            @Parameter(name=CustomIdGenerator.INCREMENT_PARAM, value="1"),
            @Parameter(name=CustomIdGenerator.VALUE_PREFIX_PARAMAETER, value="CTG"),
            @Parameter(name=CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value="%03d")
    })
	private String catId;
	private String catName;
	

}
