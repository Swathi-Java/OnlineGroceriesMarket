package com.lancesoft.omg.service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancesoft.omg.entity.SmsEntity;
import com.lancesoft.omg.exception.CustomException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;



@Service
public class SmsService {
	private final String ACCOUNT_SID = "ACc173552c03815035e936c5780f438118";
	private final String AUTH_TOKEN = "1e02475c217d97025a251d99cc1aac68";
	private final String FROM_NUMBER = "+15106169612";

	@Autowired
	OtpGeneratorservice otpGenerator;

	
	Logger logger=Logger.getLogger(SmsService.class);
	
	public void send(SmsEntity smsEntity) {

		logger.info("Sms send method started...");
		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

			Integer Otp = otpGenerator.generateOTP(smsEntity.getPhoneNumber());

			System.out.println(smsEntity.getPhoneNumber()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			String msg = "Your OTP -" + Otp + " Please verify this otp in your application for reset password";

			MessageCreator creater = Message.creator(new PhoneNumber(smsEntity.getPhoneNumber()),
					new PhoneNumber(FROM_NUMBER), msg);
			creater.create();
		} catch (Exception e) {
			System.out.println("Unable to send Otp");
			throw new CustomException("unable to send OTp");
		}
		logger.info("sms sending method stoped");
		
		}
		

	}

