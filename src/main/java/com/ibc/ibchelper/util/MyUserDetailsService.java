package com.ibc.ibchelper.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ibc.ibchelper.error.UserNotEnabledException;
import com.ibc.ibchelper.error.UserNotFoundException;
import com.ibc.ibchelper.repository.UserRepository;

@Repository
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRep;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		UserDetails user = userRep.findUserByUsername(username);
		if(user==null) throw new UserNotFoundException("NotFound");
		if(!user.isEnabled()) throw new UserNotEnabledException("NotEnabled");
		return user;
	}

}
