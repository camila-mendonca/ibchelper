package com.ibc.ibchelper.security;

public interface UserSecurityService {
	
	String validatePasswordResetToken(String token);

}
