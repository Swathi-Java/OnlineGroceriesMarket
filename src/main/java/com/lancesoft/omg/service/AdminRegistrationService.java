package com.lancesoft.omg.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.lancesoft.omg.dto.AdminRegistrationDto;
import com.lancesoft.omg.dto.ChangePasswordDto;
import com.lancesoft.omg.entity.AdminRegistrationEntity;
import com.lancesoft.omg.entity.ChangePasswordEntity;
import com.lancesoft.omg.entity.RegistrationEntity;


@Service
public interface AdminRegistrationService {
  public AdminRegistrationEntity saveadmin(AdminRegistrationDto adminRegistrationDto);
  public String getMyToken(HttpServletRequest httpServletRequest);
  AdminRegistrationEntity getadmin( String userName);
  public boolean changePassword(AdminRegistrationEntity adminRegistrationEntity,  ChangePasswordEntity changePasswordEntity);
}
