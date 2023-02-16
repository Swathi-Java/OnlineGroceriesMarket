package com.lancesoft.omg.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.lancesoft.omg.idGenerator.CustomIdGenerator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="mycartlist")
@AllArgsConstructor
@NoArgsConstructor
public class MyCartList {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY, generator = "user_sql")
    @GenericGenerator(name="user_sql", strategy="com.lancesoft.omg.idGenerator.CustomIdGenerator", parameters = {
            @Parameter(name=CustomIdGenerator.INCREMENT_PARAM, value="1"),
            @Parameter(name=CustomIdGenerator.VALUE_PREFIX_PARAMAETER, value="CART"),
            @Parameter(name=CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value="%03d")
    })
   private String id;
   private String userName;
   @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name="myCartItems")
   List<MyCart>  myCartItems;
   private long totalCost;
}
