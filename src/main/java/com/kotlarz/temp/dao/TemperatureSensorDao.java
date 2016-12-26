package com.kotlarz.temp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kotlarz.temp.domain.TemperatureSensorDomain;

@Repository
public interface TemperatureSensorDao extends CrudRepository<TemperatureSensorDomain, Long> {
	public List<TemperatureSensorDomain> findBySensorName(String sensorName);
}
