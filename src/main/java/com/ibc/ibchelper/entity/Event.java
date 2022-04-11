package com.ibc.ibchelper.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Event implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="event_id")
	private Long eventId;
	@NotNull
	@Column(length=120)
	private String name;
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateEvent;
	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern="HH:mm")
	private Date hourEvent;
	@NotNull
	private String country;
	@NotNull
	private String address;
	@Column(length = 1000)
	private String details;
	@ManyToMany
	@JoinTable(name="event_typeVolunteer",joinColumns = @JoinColumn(name="event_id"),inverseJoinColumns = @JoinColumn(name="vol_type_id"))
	private Set<VolunteerType> neededVolunteers = new HashSet<>();
	public Event() {
		super();
	}
	public Event(Long eventId, @NotNull String name, @NotNull Date dateEvent, @NotNull Date hourEvent,
			@NotNull String country, @NotNull String address, String details, Set<VolunteerType> neededVolunteers) {
		super();
		this.eventId = eventId;
		this.name = name;
		this.dateEvent = dateEvent;
		this.hourEvent = hourEvent;
		this.country = country;
		this.address = address;
		this.details = details;
		this.neededVolunteers = neededVolunteers;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateEvent() {
		return dateEvent;
	}
	public void setDateEvent(Date dateEvent) {
		this.dateEvent = dateEvent;
	}
	public Date getHourEvent() {
		return hourEvent;
	}
	public void setHourEvent(Date hourEvent) {
		this.hourEvent = hourEvent;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Set<VolunteerType> getNeededVolunteers() {
		return neededVolunteers;
	}
	public void setNeededVolunteers(Set<VolunteerType> neededVolunteers) {
		this.neededVolunteers = neededVolunteers;
	}
}
