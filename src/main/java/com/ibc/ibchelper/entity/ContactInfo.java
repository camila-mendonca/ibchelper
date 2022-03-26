package com.ibc.ibchelper.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class ContactInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "contactinfo_id")
	private Long contactInfoId;
	@NotNull
	private String name;
	@Email
	private String email;
	@Pattern(regexp = "^[0-9]+$")
	private String countryCode;
	@Pattern(regexp = "^[0-9]+$")
	private String phoneNumber;
	private String website;
	@NotNull
	private String typeAssistance;
	@Column(length = 2000)
	private String notes;
	private String area;
	private boolean isPublic;
	@Enumerated(EnumType.STRING)
	private Language language;
	@ManyToMany
	@JoinTable(name="contact_language",joinColumns = @JoinColumn(name="contactinfo_id"),inverseJoinColumns = @JoinColumn(name="language_id"))
	private Set<Languages> languages = new HashSet<>();
	
	public ContactInfo() {
		super();
	}
	
	public ContactInfo(Long contactInfoId, @NotNull String name, @Email String email,
			@Pattern(regexp = "^[0-9]+$") String countryCode, @Pattern(regexp = "^[0-9]+$") String phoneNumber,
			String website, @NotNull String typeAssistance, String notes, String area, boolean isPublic,
			Language language, Set<Languages> languages) {
		super();
		this.contactInfoId = contactInfoId;
		this.name = name;
		this.email = email;
		this.countryCode = countryCode;
		this.phoneNumber = phoneNumber;
		this.website = website;
		this.typeAssistance = typeAssistance;
		this.notes = notes;
		this.area = area;
		this.isPublic = isPublic;
		this.language = language;
		this.languages = languages;
	}

	public Long getContactInfoId() {
		return contactInfoId;
	}

	public void setContactInfoId(Long contactInfoId) {
		this.contactInfoId = contactInfoId;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTypeAssistance() {
		return typeAssistance;
	}

	public void setTypeAssistance(String typeAssistance) {
		this.typeAssistance = typeAssistance;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Set<Languages> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<Languages> languages) {
		this.languages = languages;
	}
	
	

}
