package com.ibc.ibchelper.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class AccomodationAvailability implements Serializable{

	private static final long serialVersionUID = -5581798026024955749L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "availability_id")
	private Long availabilityId;
	@ManyToOne
	private Accommodation accommodation;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	public AccomodationAvailability() {
		super();
	}	
	public AccomodationAvailability(Long availabilityId, Accommodation accommodation, @NotNull Date startDate,
			@NotNull Date endDate) {
		super();
		this.availabilityId = availabilityId;
		this.accommodation = accommodation;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public Long getAvailabilityId() {
		return availabilityId;
	}
	public void setAvailabilityId(Long availabilityId) {
		this.availabilityId = availabilityId;
	}
	public Accommodation getAccommodation() {
		return accommodation;
	}
	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
