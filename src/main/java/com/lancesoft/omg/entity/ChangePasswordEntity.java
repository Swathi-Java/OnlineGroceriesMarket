package com.lancesoft.omg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordEntity {
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}
