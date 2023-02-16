package com.lancesoft.omg.service;

import org.springframework.stereotype.Service;

import com.lancesoft.omg.dto.AddressDto;
import com.lancesoft.omg.entity.AddToCart;
import com.lancesoft.omg.entity.AddressEntity;
import com.lancesoft.omg.entity.MyCart;
import com.lancesoft.omg.entity.MyCartList;

@Service

public interface UserDashBoardService {
	 public MyCart addtocart(MyCart myCart,String userName);
	 public MyCartList mycartlist (String userName,MyCartList myCartList);
	 public MyCartList updatemycartlist(String cartid,long quantity,MyCartList myCartList,String userName);
	 public MyCartList getmyCartList( String userName,MyCartList myCartList);
	 void deletemycartlist(MyCartList myCartList,String username);
	 void deleteCartItem(String cartid, MyCart myCart, String username);
	 public AddressEntity saveaddress(String userName,AddressDto addressDto);
}
