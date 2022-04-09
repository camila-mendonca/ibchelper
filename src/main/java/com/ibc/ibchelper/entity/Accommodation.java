package com.ibc.ibchelper.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Accommodation implements Serializable{

	private static final long serialVersionUID = -1860385949747607348L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="accm_id")
	private Long accommodationId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn
	private Volunteer volunteer;
	@Column(length = 300)
	@NotNull
	private String address;
	@NotNull
	private Integer bedsAdults;
	@NotNull
	private Integer bedsKids;
	@NotNull
	private Boolean pets;
	@Column(length = 200)
	private String restrictions;
	@Column(length = 300)
	private String notes;
	@CreationTimestamp
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime lastUpdateDate;
	@OneToMany(mappedBy="accommodation")
	@NotNull
	private Set<AccomodationAvailability> periodsAvailable;
	public Accommodation() {
		super();
	}
	public Accommodation(Long accommodationId, Volunteer volunteer, @NotNull String address,
			@NotNull Integer bedsAdults, @NotNull Integer bedsKids, @NotNull Boolean pets, String restrictions,
			String notes, LocalDateTime createdDate, LocalDateTime lastUpdateDate,
			@NotNull Set<AccomodationAvailability> periodsAvailable) {
		super();
		this.accommodationId = accommodationId;
		this.volunteer = volunteer;
		this.address = address;
		this.bedsAdults = bedsAdults;
		this.bedsKids = bedsKids;
		this.pets = pets;
		this.restrictions = restrictions;
		this.notes = notes;
		this.createdDate = createdDate;
		this.lastUpdateDate = lastUpdateDate;
		this.periodsAvailable = periodsAvailable;
	}
	public Long getAccommodationId() {
		return accommodationId;
	}
	public void setAccommodationId(Long accommodationId) {
		this.accommodationId = accommodationId;
	}
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getBedsAdults() {
		return bedsAdults;
	}
	public void setBedsAdults(Integer bedsAdults) {
		this.bedsAdults = bedsAdults;
	}
	public Integer getBedsKids() {
		return bedsKids;
	}
	public void setBedsKids(Integer bedsKids) {
		this.bedsKids = bedsKids;
	}
	public Boolean getPets() {
		return pets;
	}
	public void setPets(Boolean pets) {
		this.pets = pets;
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
	public Set<AccomodationAvailability> getPeriodsAvailable() {
		return periodsAvailable;
	}
	public void setPeriodsAvailable(Set<AccomodationAvailability> periodsAvailable) {
		this.periodsAvailable = periodsAvailable;
	}	
	
}
