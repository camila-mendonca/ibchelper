package com.ibc.ibchelper.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@PrimaryKeyJoinColumn(name="user_id")
public class Volunteer  extends User implements Serializable{

		private static final long serialVersionUID = 1L;
		@NotNull
		@Column(length = 60)
		private String name;
		@NotNull
		@Column(length=60)
		private String city;
		@NotNull
		@Column(length=60)
		private String country;
		@Email
		private String email;
		@Pattern(regexp = "^[0-9]+$")
		private String countryCode;
		@Pattern(regexp = "^[0-9]+$")
		private String phoneNumber;
		private Boolean verified;
		private Boolean isAvailable;
		
		public Volunteer() {
			super();
		}	
		public Volunteer(@NotNull String name, @NotNull String city, @NotNull String country, @Email String email,
				@Pattern(regexp = "^[0-9]+$") String countryCode, @Pattern(regexp = "^[0-9]+$") String phoneNumber,
				Boolean verified, Boolean isAvailable) {
			super();
			this.name = name;
			this.city = city;
			this.country = country;
			this.email = email;
			this.countryCode = countryCode;
			this.phoneNumber = phoneNumber;
			this.verified = verified;
			this.isAvailable = isAvailable;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
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
		 

}
