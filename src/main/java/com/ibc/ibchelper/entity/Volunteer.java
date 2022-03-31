package com.ibc.ibchelper.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name="user_id")
public class Volunteer  extends User implements Serializable{

		private static final long serialVersionUID = 1L;
		
		@NotNull
		@Column(length=60)
		private String city;
		@NotNull
		@Column(length=60)
		private String country;
		private Boolean verified;
		
		public Volunteer() {
			super();
		}	
		public Volunteer(@NotNull String city, @NotNull String country, Boolean verified) {
			super();
			this.city = city;
			this.country = country;
			this.verified = verified;
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
		public Boolean getVerified() {
			return verified;
		}
		public void setVerified(Boolean verified) {
			this.verified = verified;
		}

}
