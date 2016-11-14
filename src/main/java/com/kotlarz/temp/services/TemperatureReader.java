package com.kotlarz.temp.services;

import com.kotlarz.temp.exceptions.SensorNotFoundException;
import com.kotlarz.temp.sensors.TemperatureSensor;

public interface TemperatureReader {
	public float getTemperatureFrom(TemperatureSensor sensor) throws SensorNotFoundException;
}
