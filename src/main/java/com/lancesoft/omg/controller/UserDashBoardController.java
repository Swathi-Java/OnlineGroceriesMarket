package com.lancesoft.omg.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.omg.dao.MyCartDao;
import com.lancesoft.omg.dao.ProductsDao;
import com.lancesoft.omg.dto.AddressDto;
import com.lancesoft.omg.entity.AddToCart;
import com.lancesoft.omg.entity.AddressEntity;
import com.lancesoft.omg.entity.MyCart;
import com.lancesoft.omg.entity.MyCartList;
import com.lancesoft.omg.entity.ProductsEntity;
import com.lancesoft.omg.exception.CustomException;
import com.lancesoft.omg.service.RegistrationServiceImpl;
import com.lancesoft.omg.service.UserDashBoardServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserDashBoardController {
	
	@Autowired
	ProductsDao productsDao;
	@Autowired
	RegistrationServiceImpl registrationServiceImpl;
	@Autowired
	UserDashBoardServiceImpl userDashBoardServiceImpl;
	@PostMapping("/mycart")
		public MyCart addtocart(@RequestBody AddToCart addToCart,MyCart myCart,HttpServletRequest httpServletRequest)
		{
			ProductsEntity productsEntity=productsDao.findByProductid(addToCart.getProductid());
			if(productsEntity.getStatus().equals("not avilable"))
			{
				throw new CustomException("product is not avilable");
			}
			myCart.setProduct(productsEntity);
			myCart.setQuantity(addToCart.getQuantity());
			String userName=registrationServiceImpl.getMyToken(httpServletRequest);
			return userDashBoardServiceImpl.addtocart(myCart,userName) ;
		}
	@GetMapping("/getMycartList")
	public MyCartList mycartlist(MyCartList myCartList ,HttpServletRequest httpServletRequest)
	{
		String username=registrationServiceImpl.getMyToken(httpServletRequest);
		return userDashBoardServiceImpl.mycartlist(username, myCartList);
	}
	@PutMapping("/updatemycart")
	public MyCartList updatemycart(@RequestParam String cartid,@RequestParam long quantity, MyCartList myCartList ,HttpServletRequest httpServletRequest)
	{
		String username=registrationServiceImpl.getMyToken(httpServletRequest);
		return userDashBoardServiceImpl.updatemycartlist(cartid,quantity, myCartList, username);
	}
	@GetMapping("/getallCartLists")
	public MyCartList getcartlist(HttpServletRequest httpServletRequest,MyCartList myCartList)
	{
		String username=registrationServiceImpl.getMyToken(httpServletRequest);
		return userDashBoardServiceImpl.getmyCartList(username,myCartList);
	
	}
	@DeleteMapping("deletemycartlist")
	public void deletemycartlist( HttpServletRequest httpServletRequest,MyCartList  mycartlist)
	{
		String username=registrationServiceImpl.getMyToken(httpServletRequest);
		 userDashBoardServiceImpl.deletemycartlist(mycartlist, username);
	}
	@DeleteMapping("/deleteCartITem")
	public void deleteCartItem(@RequestParam String cartid , HttpServletRequest httpServletRequest,MyCart myCart)
	{
		String username1=registrationServiceImpl.getMyToken(httpServletRequest);
		 userDashBoardServiceImpl.deleteCartItem(cartid,myCart, username1);
	}
	@PostMapping("/saveaddress")
	public AddressEntity saveaddress(HttpServletRequest httpServletRequest,@RequestBody AddressDto addressDto)
	{
		String userName=registrationServiceImpl.getMyToken(httpServletRequest);
		return userDashBoardServiceImpl.saveaddress(userName,addressDto);
		
	}
}
