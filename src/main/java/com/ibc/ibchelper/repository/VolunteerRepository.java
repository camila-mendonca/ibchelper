package com.ibc.ibchelper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibc.ibchelper.entity.Volunteer;

@Repository
public interface VolunteerRepository extends CrudRepository<Volunteer, Long>{

}
