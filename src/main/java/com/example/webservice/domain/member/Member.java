package com.example.webservice.domain.member;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API에서 사용될 도메인 
 */
@Entity
@Data
@NoArgsConstructor
public class Member implements Serializable {

	@Id @GeneratedValue
	private Long id;
	private String name;
	private String username;
	private String remark;
	
	public Member(String name, String username, String remark) {
		this.name = name;
		this.username = username;
		this.remark = remark;
	}
}
