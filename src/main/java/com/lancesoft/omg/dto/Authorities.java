package com.lancesoft.omg.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor(staticName="build")
@NoArgsConstructor
@Entity
public class Authorities {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer role_id;
	private String role;
}
