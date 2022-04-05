package com.ibc.ibchelper.security;

import java.util.Calendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibc.ibchelper.entity.PasswordResetToken;
import com.ibc.ibchelper.repository.PasswordResetTokenRepository;

@Service
@Transactional
public class UserSecurityServiceImpl implements UserSecurityService {

	@Autowired
	private PasswordResetTokenRepository passRep;
	
	@Override
	public String validatePasswordResetToken(String token) {
		final PasswordResetToken passToken = passRep.findByToken(token);
		return !isTokenFound(passToken) ? "invalidToken"
				: isTokenExpired(passToken) ? "expired"
						: null;
	}

	private boolean isTokenExpired(PasswordResetToken passToken) {
		final Calendar cal = Calendar.getInstance();
		return passToken.getExpiryDate().before(cal.getTime());
	}

	private boolean isTokenFound(PasswordResetToken passToken) {
		return passToken != null;
	}

}
