package com.ibc.ibchelper.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ibc.ibchelper.repository.UserRepository;

@Repository
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRep;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		
		UserDetails user = userRep.findUserByUsername(username);
		return user;
	}

}
