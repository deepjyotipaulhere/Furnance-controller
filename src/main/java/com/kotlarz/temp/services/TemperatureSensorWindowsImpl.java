package com.kotlarz.temp.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kotlarz.temp.domain.TemperatureSensorDomain;
import com.kotlarz.temp.exceptions.SensorNotFoundException;

@Service(value = "WindowsTemperatureSensor")
public class TemperatureSensorWindowsImpl implements TemperatureReader {
	@Autowired
	TempSensorService tempRepository;

	public float getTemperatureFrom(TemperatureSensorDomain sensor) throws SensorNotFoundException {
		return (new Random().nextFloat()) * 15 + 40;
	}

}
