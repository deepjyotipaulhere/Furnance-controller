package com.kotlarz.temp.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kotlarz.temp.dao.TempDao;
import com.kotlarz.temp.exceptions.SensorNotFoundException;
import com.kotlarz.temp.sensors.TemperatureSensor;

@Service(value = "WindowsTemperatureSensor")
public class TemperatureSensorWindowsImpl implements TemperatureReader {
	@Autowired
	TempDao tempRepository;

	public float getTemperatureFrom(TemperatureSensor sensor) throws SensorNotFoundException {
		return (new Random().nextFloat()) * 15 + 40;
	}

}
