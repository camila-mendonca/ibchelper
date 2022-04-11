package com.ibc.ibchelper.service;

import java.util.Optional;

import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.VerificationToken;
import com.ibc.ibchelper.error.UserAlreadyExistException;

public interface UserService {
	
	public User loadUser(String username);
	public User saveUser(User user) throws UserAlreadyExistException;
	public void createVerificationTokenForUser(User user, String token);
	public VerificationToken getVerificationToken(String token);
	public void updateUser(User user);
	public VerificationToken renewRegistrationToken(String existingToken);
	public User getUserByToken(VerificationToken newToken);
	public void createPasswordResetTokenForUser(User user, String token);
	public Optional<User> getUserByPasswordResetToken(String token);
	public void changeUserPassword(User user, String newPassword);

}
