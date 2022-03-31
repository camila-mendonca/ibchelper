package com.ibc.ibchelper.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VolunteerType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="vol_type_id")
	private Long volTypeId;
	private String name;
	private String description;
	
	public VolunteerType() {
		super();
	}
	public VolunteerType(Long volTypeId, String name, String description) {
		super();
		this.volTypeId = volTypeId;
		this.name = name;
		this.description = description;
	}
	public Long getVolTypeId() {
		return volTypeId;
	}
	public void setVolTypeId(Long volTypeId) {
		this.volTypeId = volTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
