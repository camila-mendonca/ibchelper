package com.ibc.ibchelper.service;

import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.VerificationToken;
import com.ibc.ibchelper.error.UserAlreadyExistException;

public interface UserService {
	
	public User loadUser(String username);
	public void saveUser(User user) throws UserAlreadyExistException;
	public void createVerificationTokenForUser(User user, String token);
	public VerificationToken getVerificationToken(String token);
	public void updateUser(User user);
	public VerificationToken renewRegistrationToken(String existingToken);
	public User getUserByToken(VerificationToken newToken);

}
