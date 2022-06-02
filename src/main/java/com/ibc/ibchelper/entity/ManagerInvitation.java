package com.ibc.ibchelper.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class ManagerInvitation implements Serializable{
	
	private static final long serialVersionUID = 4496581203383289188L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "invitation_id")
	private Long id;	
	private String token;
	@Email
	@NotBlank(message = "An email address is required")
	private String email;
	private Boolean valid;
	@CreationTimestamp
	private LocalDateTime createdDate;
	public ManagerInvitation() {
		super();
	}
	public ManagerInvitation(Long id, String token,
			@Email @NotBlank(message = "An email address is required") String email, Boolean valid,
			LocalDateTime createdDate) {
		super();
		this.id = id;
		this.token = token;
		this.email = email;
		this.valid = valid;
		this.createdDate = createdDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
}
