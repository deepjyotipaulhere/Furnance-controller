package com.kotlarz.temp.services;

import com.kotlarz.temp.domain.TemperatureSensorDomain;
import com.kotlarz.temp.exceptions.SensorNotFoundException;

public interface TemperatureReader {
	public float getTemperatureFrom(TemperatureSensorDomain sensor) throws SensorNotFoundException;
}
