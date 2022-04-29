package com.ibc.ibchelper.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Vehicle implements Serializable{

	private static final long serialVersionUID = 4876842941033437815L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long carId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn
	private Volunteer volunteer;
	@NotNull
	private String typeCar;
	@NotNull
	private String typeDrivingHelp;
	@NotNull
	private Integer seatsAvailable;
	@NotNull
	private String destination;
	private Boolean drivesToBorder;
	@Column(length = 200)
	private String restrictions;
	@Column(length = 300)
	private String notes;
	@CreationTimestamp
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime lastUpdateDate;
	public Vehicle() {
		super();
	}
	public Vehicle(Long carId, Volunteer volunteer, @NotNull String typeCar, @NotNull String typeDrivingHelp,
			@NotNull Integer seatsAvailable, @NotNull String destination, Boolean drivesToBorder, String restrictions,
			String notes, LocalDateTime createdDate, LocalDateTime lastUpdateDate) {
		super();
		this.carId = carId;
		this.volunteer = volunteer;
		this.typeCar = typeCar;
		this.typeDrivingHelp = typeDrivingHelp;
		this.seatsAvailable = seatsAvailable;
		this.destination = destination;
		this.drivesToBorder = drivesToBorder;
		this.restrictions = restrictions;
		this.notes = notes;
		this.createdDate = createdDate;
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	public String getTypeCar() {
		return typeCar;
	}
	public void setTypeCar(String typeCar) {
		this.typeCar = typeCar;
	}
	public String getTypeDrivingHelp() {
		return typeDrivingHelp;
	}
	public void setTypeDrivingHelp(String typeDrivingHelp) {
		this.typeDrivingHelp = typeDrivingHelp;
	}
	public Integer getSeatsAvailable() {
		return seatsAvailable;
	}
	public void setSeatsAvailable(Integer seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Boolean getDrivesToBorder() {
		return drivesToBorder;
	}
	public void setDrivesToBorder(Boolean drivesToBorder) {
		this.drivesToBorder = drivesToBorder;
	}
	public String getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
}
