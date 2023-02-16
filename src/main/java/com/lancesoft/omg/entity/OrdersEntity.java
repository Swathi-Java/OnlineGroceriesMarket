package com.lancesoft.omg.entity;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersEntity {
	
 private String orderid;
 private String userName;
 
 @OneToOne(cascade = CascadeType.ALL)
 private AddressEntity address;
 
 @OneToOne(cascade = CascadeType.ALL)
 private MyCartList mycartlist;
 
 private LocalDate date;
 private String deliveryTime;
 private String paymentMode;
 private String paymentStatus;
}
