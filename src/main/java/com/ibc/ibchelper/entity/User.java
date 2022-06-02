package com.ibc.ibchelper.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable, UserDetails{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	@Email
	@NotBlank(message = "Username is mandatory")
	private String username;
	@NotBlank(message = "Password is mandatory")
	private String password;
	@Transient
	private String confirmPassword;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private Set<Role> roles = new HashSet<>();
	@Enumerated(EnumType.STRING)
	private UserType type;
	
	@Column(name = "enabled")
    private boolean enabled;
	
	@NotBlank(message="Name is mandatory")
	private String name;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="user_vol_type", joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="vol_type_id"))
	private Set<VolunteerType> typesVolunteer;
	@CreationTimestamp
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime lastUpdateDate;	
	public User() {
		super();
	}
	public User(Long userId, @Email @NotBlank(message = "Username is mandatory") String username,
			@NotBlank(message = "Password is mandatory") String password, String confirmPassword, Set<Role> roles,
			UserType type, boolean enabled, @NotBlank(message = "Name is mandatory") String name,
			Set<VolunteerType> typesVolunteer, LocalDateTime createdDate, LocalDateTime lastUpdateDate) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.roles = roles;
		this.type = type;
		this.enabled = enabled;
		this.name = name;
		this.typesVolunteer = typesVolunteer;
		this.createdDate = createdDate;
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}	
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<VolunteerType> getTypesVolunteer() {
		return typesVolunteer;
	}
	public void setTypesVolunteer(Set<VolunteerType> typesVolunteer) {
		this.typesVolunteer = typesVolunteer;
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
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

}
