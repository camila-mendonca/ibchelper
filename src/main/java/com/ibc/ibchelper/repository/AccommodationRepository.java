package com.ibc.ibchelper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibc.ibchelper.entity.Accommodation;

@Repository
public interface AccommodationRepository extends CrudRepository<Accommodation, Long>{

}
