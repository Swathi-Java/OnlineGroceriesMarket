package com.lancesoft.omg.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.omg.dto.AdminRegistrationDto;

import com.lancesoft.omg.entity.AdminRegistrationEntity;
import com.lancesoft.omg.entity.ChangePasswordEntity;

import com.lancesoft.omg.entity.SmsEntity;
import com.lancesoft.omg.exception.CustomException;
import com.lancesoft.omg.service.AdminRegistrationServiceImpl;

import com.lancesoft.omg.service.SmsService;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminRegistrationController {
	
	

	@Autowired
	private AdminRegistrationServiceImpl adminRegistrationServiceImpl;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
     private SmsService service;
	private  final String TOPIC_DESTINATION="/lesson/sms";
	@PostMapping("/sendotp")
	public ResponseEntity<Boolean> sendotp(@RequestBody SmsEntity smsEntity)
	{
		try
		{
		service.send(smsEntity);
		}
		catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		simpMessagingTemplate.convertAndSend(TOPIC_DESTINATION, getTimeStamp()+"sms has been sent"+smsEntity.getPhoneNumber());
		return new ResponseEntity<>(true,HttpStatus.OK);
	}
	
	private String getTimeStamp() {
		
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	}

	@PostMapping("/register")
	public ResponseEntity<AdminRegistrationEntity> registeradmin(@RequestBody @Valid AdminRegistrationDto adminRegistrationDto)
	{
	  
		return new ResponseEntity<>(adminRegistrationServiceImpl.saveadmin(adminRegistrationDto),HttpStatus.CREATED);
		
	}
	@GetMapping("/profile")
	public AdminRegistrationEntity myprofile(HttpServletRequest httpServletRequest)
	{
		String userName=adminRegistrationServiceImpl.getMyToken(httpServletRequest);
		return adminRegistrationServiceImpl.getadmin(userName);
	}
	@PutMapping("/changepassword")
	public  String changepassword(@RequestBody ChangePasswordEntity changePasswordEntity,HttpServletRequest httpServletRequest)
	{
		String user=adminRegistrationServiceImpl.getMyToken(httpServletRequest);
		AdminRegistrationEntity adminRegistrationEntity=adminRegistrationServiceImpl.getadmin(user);
		boolean changepassword=adminRegistrationServiceImpl.changePassword(adminRegistrationEntity, changePasswordEntity);
		if(changepassword)
		{
			return "password is changed";
		}
		return "Error in changing password";
	}
}
