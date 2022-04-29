package com.ibc.ibchelper.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@PrimaryKeyJoinColumn(name="user_id")
public class Volunteer  extends User implements Serializable{

	private static final long serialVersionUID = 7395312206304103662L;

	@NotNull
	@Column(length=60)
	private String city;
	@NotNull
	@Column(length=60)
	private String country;
	@Pattern(regexp = "^[0-9]+$")
	private String countryCode;
	@Pattern(regexp = "^[0-9]+$")
	private String phoneNumber;
	private Boolean verified;
	private Boolean isAvailable;
	private String details;
	private Boolean availableWeekdays;
	private Boolean availableWeekends;
	private String availabilityExceptions;
	
	public Volunteer() {
		super();
	}
	public Volunteer(@NotNull String city, @NotNull String country, @Pattern(regexp = "^[0-9]+$") String countryCode,
			@Pattern(regexp = "^[0-9]+$") String phoneNumber, Boolean verified, Boolean isAvailable, String details,
			Boolean availableWeekdays, Boolean availableWeekends, String availabilityExceptions) {
		super();
		this.city = city;
		this.country = country;
		this.countryCode = countryCode;
		this.phoneNumber = phoneNumber;
		this.verified = verified;
		this.isAvailable = isAvailable;
		this.details = details;
		this.availableWeekdays = availableWeekdays;
		this.availableWeekends = availableWeekends;
		this.availabilityExceptions = availabilityExceptions;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Boolean getVerified() {
		return verified;
	}
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Boolean getAvailableWeekdays() {
		return availableWeekdays;
	}
	public void setAvailableWeekdays(Boolean availableWeekdays) {
		this.availableWeekdays = availableWeekdays;
	}
	public Boolean getAvailableWeekends() {
		return availableWeekends;
	}
	public void setAvailableWeekends(Boolean availableWeekends) {
		this.availableWeekends = availableWeekends;
	}
	public String getAvailabilityExceptions() {
		return availabilityExceptions;
	}
	public void setAvailabilityExceptions(String availabilityExceptions) {
		this.availabilityExceptions = availabilityExceptions;
	}	 

}
