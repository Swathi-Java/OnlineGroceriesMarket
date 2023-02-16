package com.lancesoft.omg.service;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OtpGeneratorservice {
	@Autowired
	private HttpSession httpsession;
     String phoneNumber;
     int Otp;
	
     private static Logger logger=Logger.getLogger(OtpGeneratorservice.class);
     
	public Integer generateOTP(String phoneNumber)
	{
		logger.info("Generate Otp method start");
		this.phoneNumber=phoneNumber;
	
		int min = 100000;
		int max = 999999;
		
		int Otp = (int) (Math.random() * (max - min + 1) + min);
		this.Otp=Otp;
	
		
		return Otp;
	}
	public void setOtp()
	{
		logger.info("setOtp Otp executed..");
		httpsession.setAttribute("otpGenerated", Otp);
		httpsession.setAttribute("phoneNumber", phoneNumber);
	}
}
