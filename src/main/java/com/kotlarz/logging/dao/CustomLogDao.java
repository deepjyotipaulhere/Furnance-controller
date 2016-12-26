package com.kotlarz.logging.dao;

import org.springframework.data.repository.CrudRepository;

import com.kotlarz.logging.domain.CustomLogEntry;

public interface CustomLogDao extends CrudRepository<CustomLogEntry, Long> {

}
