package com.lancesoft.omg.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lancesoft.omg.dao.RegistrationDao;
import com.lancesoft.omg.dto.Authorities;
import com.lancesoft.omg.dto.RegistrationDto;
import com.lancesoft.omg.entity.ChangePasswordEntity;
import com.lancesoft.omg.entity.RegistrationEntity;
import com.lancesoft.omg.exception.CustomException;
import com.lancesoft.omg.exception.NotValidPasswordException;
import com.lancesoft.omg.exception.UserAlreadyExists;
import com.lancesoft.omg.jwt.JwtUtil;

@Service
public class RegistrationServiceImpl implements RegistrationService {
	
@Autowired
RegistrationDao registrationDao;
@Autowired
OtpGeneratorservice otpGeneratorservice;
@Autowired
HttpSession httpSession;
@Autowired
PasswordEncoder passwordEncoder;
@Autowired
JwtUtil jwtUtil;


	private static Logger logger=Logger.getLogger(RegistrationServiceImpl.class);
	@Override
	public RegistrationEntity save(RegistrationDto registrationDto) {
		
		ModelMapper modelmapper=new ModelMapper();
		
		RegistrationEntity registrationEntity=new RegistrationEntity();
		
		otpGeneratorservice.setOtp();
		
		if(!validateOtp(registrationDto.getPhoneNumber(),registrationDto.getUserOtp()))
		{
			throw new UserAlreadyExists("Not Valid PhoneNumber Or Otp");
		}
		else {
		if(registrationDto==null)
		
			throw new RuntimeException("RegistrationDto is null please check it");
		
		else
			modelmapper.map(registrationDto, registrationEntity);
		
		if(registrationDao.existsByUserName(registrationEntity.getUserName())||registrationDao.existsByPhoneNumber(registrationEntity.getPhoneNumber()))
		{
			throw new UserAlreadyExists("User Already exists");
		}
		Authorities authorities=new Authorities();
		authorities.setRole("User");
			
		List<Authorities> authority=new ArrayList<Authorities>();
		authority.add(authorities);
		registrationEntity.setAuthorities(authority);
		
		if(!(registrationEntity.getPassword().equals(registrationEntity.getConfirmPassword())))
		{
			throw new NotValidPasswordException("Password and confirm password must be match");
		}
		registrationEntity.setPassword(passwordEncoder.encode(registrationEntity.getPassword()));//d
		registrationEntity.setConfirmPassword(passwordEncoder.encode(registrationEntity.getConfirmPassword()));//d

		
		return registrationDao.save(registrationEntity);
	}
	}
	private boolean validateOtp(String phoneNumber, Integer userOtp) {
		logger.info("User validate Otp method start.. ");
		
	    Integer CatcheOtp=(Integer) httpSession.getAttribute("otpGenerated");
	    
	    System.out.println(CatcheOtp+">>>>>>>>>>>>>>>>>>>>>>");
	    
	    String sessionphoneNumber=(String) httpSession.getAttribute("phoneNumber");
	    
	    if(CatcheOtp!=null&&CatcheOtp.equals(userOtp)&&phoneNumber.equals(sessionphoneNumber))
	    {
	    	//httpSession.invalidate();
	    	logger.info("End of validate Otp method(Valid Otp) !!");
	    	return true;
	    }
	    logger.info("End of validate Otp method (Invalid Otp)!!");
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
				throw new CustomException("Invalid session please login again");
		}
		else 
			throw new CustomException("Invalid session please login again");
		return userName;
	}
	@Override
	public RegistrationEntity getprofile(String userName) {
		
		return registrationDao.findByUserName(userName);
	}
	public RegistrationEntity updateprofule(RegistrationEntity registrationEntity) {
		
		return registrationDao.save(registrationEntity);
	}
	@Override
	public boolean changepassword(RegistrationEntity registrationEntity,ChangePasswordEntity changePasswordEntity) {
		String oldpassword=registrationEntity.getPassword();
		boolean ismatch=passwordEncoder.matches(changePasswordEntity.getOldPassword(),oldpassword);
		if(ismatch)
		{
			if(changePasswordEntity.getNewPassword().equals(changePasswordEntity.getConfirmPassword()))
			{
				String enternewpassword=passwordEncoder.encode(changePasswordEntity.getNewPassword());
				registrationEntity.setPassword(enternewpassword);
				registrationEntity.setConfirmPassword(enternewpassword);
				registrationDao.save(registrationEntity);
				return true;
			}
			else throw new CustomException("new password and confirm password must be match");
		}
		else throw new CustomException("old password must be match");
	}
		
	}
		
	


