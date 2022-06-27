package com.ibc.ibchelper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibc.ibchelper.entity.ContactInfo;

@Repository
public interface ContactInfoRepository extends CrudRepository<ContactInfo, Long>{
	public Iterable<ContactInfo> findAllByIsPublicTrue();
}
