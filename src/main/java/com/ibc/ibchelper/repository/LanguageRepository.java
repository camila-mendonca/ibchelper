package com.ibc.ibchelper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibc.ibchelper.entity.Languages;

@Repository
public interface LanguageRepository extends CrudRepository<Languages, Long>{

}
