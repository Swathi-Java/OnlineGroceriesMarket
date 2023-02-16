package com.lancesoft.omg.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancesoft.omg.dao.AddressDao;
import com.lancesoft.omg.dao.MyCartDao;
import com.lancesoft.omg.dao.MyCartListDao;
import com.lancesoft.omg.dao.ProductsDao;
import com.lancesoft.omg.dto.AddressDto;
import com.lancesoft.omg.entity.AddressEntity;
import com.lancesoft.omg.entity.MyCart;
import com.lancesoft.omg.entity.MyCartList;
import com.lancesoft.omg.entity.ProductsEntity;
import com.lancesoft.omg.exception.CustomException;

@Service
@Transactional
public class UserDashBoardServiceImpl implements UserDashBoardService {
	@Autowired
	ProductsDao productsDao;
	@Autowired
	MyCartDao myCartDao;
	@Autowired
	MyCartListDao myCartListDao;
	@Autowired
	AddressDao addressDao;

	@Override
	public MyCart addtocart(MyCart myCart,String userName) {
		String productadded=myCart.getProduct().getProdid();
		ProductsEntity productsEntity=productsDao.findByProductid(productadded);
		List<MyCart> cart= myCartDao.findByuserName(userName);
		for(int i=0;i<cart.size();i++)
		{
			String productadd=cart.get(i).getProduct().getProdid();
			if(productadd==productadded)
			{
				throw new CustomException("product is already added");
			}
		}
		myCart.setUserName(userName);
		myCart.setProduct(productsEntity);
		return myCartDao.save(myCart);
	}

	@Override
	public MyCartList mycartlist(String userName, MyCartList myCartList) {
		List<MyCart> mycartlist=myCartDao.findByuserName(userName);
		if(mycartlist.isEmpty())
		{
			throw new CustomException("mycart is empty");
		}
		long totalcost=0;
		for(MyCart mycart:mycartlist)
		{
			totalcost=totalcost+mycart.getProduct().getPrice()*mycart.getQuantity();
		}
		if(myCartListDao.existsByUserName(userName))
		{
			MyCartList mycartList=myCartListDao.findByUserName(userName);
			String mycartlistid=mycartList.getId();
			myCartList.setId(mycartlistid);
		}
		myCartList.setUserName(userName);
		myCartList.setMyCartItems(mycartlist);
		myCartList.setTotalCost(totalcost);
		
		return myCartListDao.save(myCartList);
		
	}

	@Override
	public MyCartList updatemycartlist(String cartid,long quantity, MyCartList myCartList, String userName) {
		if(!(myCartDao.existsByCartid(cartid)))
		{
			throw new CustomException("user is not in cartlist");
		}
		MyCart myCart=myCartDao.findByCartid(cartid);
		myCart.setQuantity(quantity);
		myCartDao.save(myCart);
		return this.mycartlist(userName, myCartList);
	}

	@Override
	public MyCartList getmyCartList(String userName, MyCartList myCartList) {
		this.mycartlist(userName,myCartList);
		return myCartListDao.findByUserName(userName);	           
	}

	@Override
	public void deletemycartlist(MyCartList myCartList,String username) {
		if(myCartListDao.existsByUserName(username))
		{
		myCartListDao.deleteByUserName(username);
		}
		else 
			throw new CustomException(" no items in your cartlist");
		
	
		
	}



	public void deleteCartItem(String cartid, MyCart myCart, String username) {
		if(!(myCartDao.existsByCartid(cartid)))
		{
			throw new CustomException("product cannot be deleted it is not in cart");
		}
		myCartDao.deleteByCartid(cartid);
			
	}

	@Override
	public AddressEntity saveaddress(String userName, AddressDto addressDto) {
		ModelMapper modelmapper=new ModelMapper();
		AddressEntity addressEntity=new AddressEntity();
		if(!(addressDto==null))
		{
			modelmapper.map( addressDto, addressEntity);
		}
		addressEntity.setUserName(userName);
		
		if(addressEntity.isCurrentAddress())
		{
			List<AddressEntity> addresslist=addressDao.findByuserName(userName);
			
				if(!(addresslist==null))
					for(AddressEntity address:addresslist)
					{
						if(address.isCurrentAddress())
						{
					String id=address.getId();
					address.setId(id);
					address.setCurrentAddress(false);
					addressDao.save(address);
						}
					}
			}
				return addressDao.save(addressEntity);
	    	}


}
