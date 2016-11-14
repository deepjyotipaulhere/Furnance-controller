package com.kotlarz.temp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.kotlarz.temp.exceptions.SensorNotFoundException;
import com.kotlarz.temp.sensors.TemperatureSensor;

@Repository
public class TempDao {
	@Autowired
	JdbcTemplate template;

	public List<TemperatureSensor> getAll() {
		return template.query("SELECT * FROM temperature_sensors", TemperatureSensor.getRowMapper());
	}

	public TemperatureSensor getByName(String name) throws SensorNotFoundException {
		List<TemperatureSensor> sensorList = template.query(
				"SELECT * FROM temperature_sensors WHERE sensor_name = '" + name + "'",
				TemperatureSensor.getRowMapper());
		if (sensorList == null || sensorList.isEmpty())
			throw new SensorNotFoundException();
		return sensorList.get(0);
	}
}
