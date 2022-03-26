package com.ibc.ibchelper.repository;

import org.springframework.data.repository.CrudRepository;

import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.VerificationToken;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long>{
	VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
}
