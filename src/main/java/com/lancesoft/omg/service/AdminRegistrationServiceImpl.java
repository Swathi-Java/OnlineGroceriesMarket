package com.lancesoft.omg.service;


import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.ExecutionError;
import com.lancesoft.omg.dao.AdminRegistrationDao;
import com.lancesoft.omg.dto.AdminRegistrationDto;
import com.lancesoft.omg.dto.Authorities;
import com.lancesoft.omg.dto.ChangePasswordDto;
import com.lancesoft.omg.entity.AdminRegistrationEntity;
import com.lancesoft.omg.entity.ChangePasswordEntity;
import com.lancesoft.omg.entity.RegistrationEntity;
import com.lancesoft.omg.exception.CustomException;
import com.lancesoft.omg.exception.NotValidException;
import com.lancesoft.omg.exception.UserAlreadyExists;
import com.lancesoft.omg.jwt.JwtUtil;

import io.jsonwebtoken.Jws;

@Service
public class AdminRegistrationServiceImpl implements AdminRegistrationService {
	@Autowired
	HttpSession httpsession;
	@Autowired
	AdminRegistrationDao adminRegistrationDao;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	OtpGeneratorservice otpGeneratorservice;
	@Autowired
	JwtUtil jwtUtil;
	@Override
	public AdminRegistrationEntity saveadmin(AdminRegistrationDto adminRegistrationDto) {
		
		ModelMapper modelMapper=new ModelMapper();
		AdminRegistrationEntity adminRegistrationEntity=new AdminRegistrationEntity();
		otpGeneratorservice.setOtp();
		if(!validateOtp(adminRegistrationDto.getPhoneNumber(),adminRegistrationDto.getUserOtp()))
		{
			throw new NotValidException("please Enter valid otp");
		}
		else 
		{
			if(adminRegistrationDto==null)
			
				throw new CustomException("null found in Adminregistration please check it");
			
			else
				modelMapper.map(adminRegistrationDto, adminRegistrationEntity);
			if(adminRegistrationDao.existsByuserName(adminRegistrationDto.getUserName())
					||adminRegistrationDao.existsByphoneNumber(adminRegistrationEntity.getPhoneNumber()))
			{
				throw new UserAlreadyExists("user Already exists");
			}
			Authorities authorities = new Authorities();
			authorities.setRole("Admin");
			List<Authorities> auth=new ArrayList<Authorities>();
			auth.add(authorities);
			adminRegistrationEntity.setAuthorities(auth);
			if(!adminRegistrationEntity.getPassword().equals(adminRegistrationEntity.getConfirmPassword()))
			{
				throw new CustomException("password And Confirmpassword must be match");
			}
			 adminRegistrationEntity.setPassword(passwordEncoder.encode(adminRegistrationEntity.getPassword()));
			 adminRegistrationEntity.setConfirmPassword(passwordEncoder.encode(adminRegistrationEntity.getConfirmPassword()));
			 return adminRegistrationDao.save(adminRegistrationEntity);
		}
			
		
	}

	private boolean validateOtp(String phoneNumber, Integer userOtp) {
		Integer catcheotp=(Integer) httpsession.getAttribute("otpGenerated");
		String phone=(String) httpsession.getAttribute("phoneNumber");
		if(catcheotp!=null&&catcheotp.equals(userOtp))
		{
			httpsession.invalidate();
			return true;
		}
		
		return false;
	}

	@Override
	public String getMyToken(HttpServletRequest httpServletRequest) {
		String authorizationHeader=httpServletRequest.getHeader("Authorization");
		String token=null;
		String userName=null;
		if(authorizationHeader!=null&&authorizationHeader.startsWith("Bearer"))
		{
			 token=authorizationHeader.substring(7);
			 if(!jwtUtil.isTokenExpired(token))
			 {
				 userName=jwtUtil.extractUsername(token);
			 }
			 else
			 throw new CustomException("session Invalid please login again");
		}
			 else 
				 throw new CustomException("Sesion Invalid please login again");
			 
		
		return userName;
	}

	public AdminRegistrationEntity getadmin(String userName) {
	
		return adminRegistrationDao.findByuserName(userName);
	}

	@Override
	public boolean changePassword(AdminRegistrationEntity adminRegistrationEntity, ChangePasswordEntity changePasswordEntity) {
	
		String oldpassword=adminRegistrationEntity.getPassword();
		//String enterOldPassword = passwordEncoder.encode(changePasswordEntity.getOldPassword());

		boolean isMatch=passwordEncoder.matches(changePasswordEntity.getOldPassword(),oldpassword); 
	     if(isMatch)
	     {
	    	 if(changePasswordEntity.getNewPassword().equals(changePasswordEntity.getConfirmPassword()))
	    	 {
	    		 String enternewpassword=passwordEncoder.encode(changePasswordEntity.getNewPassword());
	    		 adminRegistrationEntity.setPassword(enternewpassword);
	    		 adminRegistrationEntity.setConfirmPassword(enternewpassword);
	    		 adminRegistrationDao.save(adminRegistrationEntity);
	    		 return true;
	    	 }
	    	 else 
	    		 throw new CustomException("oldpassword is not matching");
	     }
	    	 else
	    		 throw new CustomException("newpassword and confirm password must match");
	     }
		
	}
	
	
	


