package com.ibc.ibchelper.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibc.ibchelper.entity.PasswordResetToken;
import com.ibc.ibchelper.entity.Role;
import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.VerificationToken;
import com.ibc.ibchelper.error.UserAlreadyExistException;
import com.ibc.ibchelper.repository.PasswordResetTokenRepository;
import com.ibc.ibchelper.repository.RoleRepository;
import com.ibc.ibchelper.repository.UserRepository;
import com.ibc.ibchelper.repository.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService {
//note
	@Autowired
	UserRepository userRep;
	
	@Autowired
	RoleRepository roleRep;
	
	@Autowired
	VerificationTokenRepository tokenRep;
	
	@Autowired
	PasswordResetTokenRepository passRep;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public User loadUser(String username) {
		return userRep.findUserByUsername(username);		
	}

	@Override
	public void saveUser(User user){
		if(userRep.findUserByUsername(user.getUsername())!=null) {
			throw new UserAlreadyExistException("UserAlreadyExist");
		}
		user.setPassword(encoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleRep.findRoleById("ROLE_USER"));
		user.setRoles(roles);
		user.setEnabled(false);
		userRep.save(user);		
	}

	@Override
	public void createVerificationTokenForUser(User user, String token) {
		VerificationToken newToken = new VerificationToken(token, user);
		tokenRep.save(newToken);		
	}

	@Override
	public VerificationToken getVerificationToken(String token) {
		return tokenRep.findByToken(token);
	}

	@Override
	public void updateUser(User user) {
		userRep.save(user);
	}

	@Override
	public VerificationToken renewRegistrationToken(String existingToken) {
		VerificationToken newToken = tokenRep.findByToken(existingToken);
		newToken.updateToken(UUID.randomUUID().toString());
		newToken = tokenRep.save(newToken);
		return newToken;
	}

	@Override
	public User getUserByToken(VerificationToken newToken) {
		VerificationToken token = tokenRep.findByToken(newToken.toString());
		if(token!=null) {
			return token.getUser();
		}
		return null;
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		passRep.save(myToken);
	}

	@Override
	public Optional<User> getUserByPasswordResetToken(String token) {
		return Optional.ofNullable(passRep.findByToken(token).getUser());
	}

	@Override
	public void changeUserPassword(User user, String newPassword) {
		user.setPassword(encoder.encode(newPassword));
		userRep.save(user);
	}

}
