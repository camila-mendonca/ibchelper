package com.ibc.ibchelper.form;

import java.util.Set;

public class VolunteerForm {
	
	private String name;
	private String email;
	private String password;
	private String confirmPassword;
	private String countryCode;
	private String phoneNumber;
	private String country;
	private String city;	
	
	private Set<Long> volunteerType;
	private String volunteerDetails;
	
	private Boolean availableWeekdays;
	private Boolean availableWeekends;
	private String availabilityExceptions;
	
	private Boolean isHosting;
	private String accAddress;
	private Integer accBedsAdults;
	private Integer accBedsKids;
	private Boolean accPets;
	private String accRestrictions;
	private String accNotes;
	
	private Boolean isDriving;
	private String veType;
	private Set<String> veTypeHelp;
	private Integer veSeats;
	private Set<String> veDestination;
	private Boolean veDrivesToBorder;
	private String veRestrictions;
	private String veNotes;
	public VolunteerForm() {
		super();
	}
	
	public VolunteerForm(String name, String email, String password, String confirmPassword, String countryCode,
			String phoneNumber, String country, String city, Set<Long> volunteerType, String volunteerDetails,
			Boolean availableWeekdays, Boolean availableWeekends, String availabilityExceptions, Boolean isHosting,
			String accAddress, Integer accBedsAdults, Integer accBedsKids, Boolean accPets, String accRestrictions,
			String accNotes, Boolean isDriving, String veType, Set<String> veTypeHelp, Integer veSeats,
			Set<String> veDestination, Boolean veDrivesToBorder, String veRestrictions, String veNotes) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.countryCode = countryCode;
		this.phoneNumber = phoneNumber;
		this.country = country;
		this.city = city;
		this.volunteerType = volunteerType;
		this.volunteerDetails = volunteerDetails;
		this.availableWeekdays = availableWeekdays;
		this.availableWeekends = availableWeekends;
		this.availabilityExceptions = availabilityExceptions;
		this.isHosting = isHosting;
		this.accAddress = accAddress;
		this.accBedsAdults = accBedsAdults;
		this.accBedsKids = accBedsKids;
		this.accPets = accPets;
		this.accRestrictions = accRestrictions;
		this.accNotes = accNotes;
		this.isDriving = isDriving;
		this.veType = veType;
		this.veTypeHelp = veTypeHelp;
		this.veSeats = veSeats;
		this.veDestination = veDestination;
		this.veDrivesToBorder = veDrivesToBorder;
		this.veRestrictions = veRestrictions;
		this.veNotes = veNotes;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Set<Long> getVolunteerType() {
		return volunteerType;
	}
	public void setVolunteerType(Set<Long> volunteerType) {
		this.volunteerType = volunteerType;
	}
	public String getVolunteerDetails() {
		return volunteerDetails;
	}
	public void setVolunteerDetails(String volunteerDetails) {
		this.volunteerDetails = volunteerDetails;
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
	public String getAccAddress() {
		return accAddress;
	}
	public void setAccAddress(String accAddress) {
		this.accAddress = accAddress;
	}
	public Integer getAccBedsAdults() {
		return accBedsAdults;
	}
	public void setAccBedsAdults(Integer accBedsAdults) {
		this.accBedsAdults = accBedsAdults;
	}
	public Integer getAccBedsKids() {
		return accBedsKids;
	}
	public void setAccBedsKids(Integer accBedsKids) {
		this.accBedsKids = accBedsKids;
	}
	public Boolean getAccPets() {
		return accPets;
	}
	public void setAccPets(Boolean accPets) {
		this.accPets = accPets;
	}
	public String getAccRestrictions() {
		return accRestrictions;
	}
	public void setAccRestrictions(String accRestrictions) {
		this.accRestrictions = accRestrictions;
	}
	public String getAccNotes() {
		return accNotes;
	}
	public void setAccNotes(String accNotes) {
		this.accNotes = accNotes;
	}
	public String getVeType() {
		return veType;
	}
	public void setVeType(String veType) {
		this.veType = veType;
	}
	public Set<String> getVeTypeHelp() {
		return veTypeHelp;
	}
	public void setVeTypeHelp(Set<String> veTypeHelp) {
		this.veTypeHelp = veTypeHelp;
	}
	public Integer getVeSeats() {
		return veSeats;
	}
	public void setVeSeats(Integer veSeats) {
		this.veSeats = veSeats;
	}
	public Set<String> getVeDestination() {
		return veDestination;
	}
	public void setVeDestination(Set<String> veDestination) {
		this.veDestination = veDestination;
	}
	public Boolean getVeDrivesToBorder() {
		return veDrivesToBorder;
	}
	public void setVeDrivesToBorder(Boolean veDrivesToBorder) {
		this.veDrivesToBorder = veDrivesToBorder;
	}
	public String getVeRestrictions() {
		return veRestrictions;
	}
	public void setVeRestrictions(String veRestrictions) {
		this.veRestrictions = veRestrictions;
	}
	public String getVeNotes() {
		return veNotes;
	}
	public void setVeNotes(String veNotes) {
		this.veNotes = veNotes;
	}
	public Boolean getIsHosting() {
		return isHosting;
	}
	public void setIsHosting(Boolean isHosting) {
		this.isHosting = isHosting;
	}
	public Boolean getIsDriving() {
		return isDriving;
	}
	public void setIsDriving(Boolean isDriving) {
		this.isDriving = isDriving;
	}
	
	

}
