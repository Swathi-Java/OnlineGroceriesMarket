package com.lancesoft.omg.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lancesoft.omg.dto.RegistrationDto;
import com.lancesoft.omg.entity.ChangePasswordEntity;
import com.lancesoft.omg.entity.RegistrationEntity;
import com.lancesoft.omg.entity.SmsEntity;
import com.lancesoft.omg.service.RegistrationServiceImpl;
import com.lancesoft.omg.service.SmsService;
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RegistrationController {
	
	private RegistrationServiceImpl registrationServiceImpl;
	
	public RegistrationController(RegistrationServiceImpl registrationServiceImpl) {
		super();
		this.registrationServiceImpl = registrationServiceImpl;
	}
	
	@Autowired
	SmsService smsService;
	@Autowired
	SimpMessagingTemplate webSocket;
	
	private final String TOPIC_DESTINATION="/lesson/sms";

	@PostMapping("/sendOtp")
	public ResponseEntity<Boolean> smsSubmit(@RequestBody SmsEntity smsEntity) {
		try {

			smsService.send(smsEntity);

		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS has been sent !: " + smsEntity.getPhoneNumber());

		return new ResponseEntity<>(true, HttpStatus.OK);
	}
  
  
	private String getTimeStamp() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	}


@PostMapping("/register")
  public ResponseEntity<RegistrationEntity>  saveuser(@RequestBody @Valid  RegistrationDto registrationDto)
  {
	  return new ResponseEntity<>(registrationServiceImpl.save(registrationDto), HttpStatus.CREATED);
  }
  @GetMapping("/userprofile")
   public ResponseEntity<RegistrationEntity> userprofile(HttpServletRequest httpServletRequest)
   {
	  String user=registrationServiceImpl.getMyToken(httpServletRequest);
	  return new ResponseEntity(registrationServiceImpl.getprofile(user),HttpStatus.OK);
	
   }
  @PutMapping("/updateprofile")
  public RegistrationEntity update(@RequestBody  RegistrationEntity registrationEntity)
  {
	  return registrationServiceImpl.updateprofule(registrationEntity);
  }
 
  @PutMapping("/changepassword")
  public String changepassword(@RequestBody ChangePasswordEntity changePasswordEntity ,HttpServletRequest httpServletRequest)
  {
	  String user=registrationServiceImpl.getMyToken(httpServletRequest);
	  RegistrationEntity registrationEntity=registrationServiceImpl.getprofile(user);
	  boolean changepassword=registrationServiceImpl.changepassword(registrationEntity, changePasswordEntity) ;
	  if(changepassword)
	  {
		  return "password is changed";
	  }
	  return "error in password change";
		  }
  
}
