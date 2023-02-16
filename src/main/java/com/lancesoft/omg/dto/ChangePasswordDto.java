package com.lancesoft.omg.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
private  String oldpassword;
private String newpassword;

}
