package com.lancesoft.omg.dto;



import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "registration")
public class RegistrationDto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer user_id;
	@NotBlank(message = "First name should not be null")
	@Size(min = 2, max = 15, message = "First name  should contain 2-15 characters")
	private String firstName;

	@NotBlank(message = "Last name should not be null")
	@Size(min = 2, max = 15, message = "Last name  should contain 2-15 characters")
	private String lastName;

	@NotBlank(message = "userName cannot be empty")
	@Column(name = "userName", nullable = false, unique = true)
	private String userName;

	@NotBlank(message = "Password can't be blank")
	private String password;

	@NotBlank(message = "Password can't be blank")
	private String confirmPassword;

	private String gender;

	@Email(message = "Invalid email address")
	private String email;

	private String phoneNumber;

	private String dob;

	private Integer userOtp;
 
}
