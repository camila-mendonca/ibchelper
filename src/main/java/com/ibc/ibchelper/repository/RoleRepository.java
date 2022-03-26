package com.ibc.ibchelper.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibc.ibchelper.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	
	@Query("SELECT r FROM Role r where r.name = ?1")
	public Role findRoleById(String name);

}
