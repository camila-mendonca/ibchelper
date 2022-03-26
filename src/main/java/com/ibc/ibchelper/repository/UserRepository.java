package com.ibc.ibchelper.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ibc.ibchelper.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	public User findUserByUsername(String username) throws UsernameNotFoundException;

}
