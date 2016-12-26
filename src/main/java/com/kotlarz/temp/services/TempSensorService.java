package com.kotlarz.temp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kotlarz.temp.dao.TemperatureSensorDao;
import com.kotlarz.temp.domain.TemperatureSensorDomain;

@Service
public class TempSensorService {
	@Autowired
	private TemperatureSensorDao tempSensorDao;

	public List<TemperatureSensorDomain> getAll() {
		return Lists.newLinkedList(tempSensorDao.findAll());
	}

	public List<TemperatureSensorDomain> getByName(String name) {
		return tempSensorDao.findBySensorName(name);
	}
}
