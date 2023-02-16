package com.lancesoft.omg.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lancesoft.omg.dto.RegistrationDto;
import com.lancesoft.omg.entity.ChangePasswordEntity;
import com.lancesoft.omg.entity.RegistrationEntity;
import com.lancesoft.omg.exception.UserAlreadyExists;

@Service
public interface RegistrationService {
	
  RegistrationEntity save(RegistrationDto registrationDto)throws UserAlreadyExists;
   String getMyToken(HttpServletRequest httpServletRequest);
   RegistrationEntity getprofile(String userName);
   public RegistrationEntity updateprofule(RegistrationEntity registrationEntity);
   public boolean  changepassword(RegistrationEntity registrationEntity, ChangePasswordEntity changePasswordEntity);
}
