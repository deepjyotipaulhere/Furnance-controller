package com.kotlarz.temp.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kotlarz.temp.exceptions.SensorNotFoundException;
import com.kotlarz.temp.sensors.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

@Service(value = "RaspberryTemperatureSensor")
public class TemperatureSensorRaspberry implements TemperatureReader {

	@Override
	public float getTemperatureFrom(TemperatureSensor sensor) throws SensorNotFoundException {
		W1Master master = new W1Master();
		float temperature = 0;
		boolean found = false;

		List<W1Device> deviceList = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
		for (W1Device device : deviceList) {
			if (device.getId().equals(sensor.getAddress())) {
				found = true;
				try {
					temperature = Float.parseFloat(device.getValue());
				} catch (IOException ex) {
					throw new RuntimeException(ex.getMessage());
				}
			}
		}
		if (found)
			return temperature;
		else
			throw new SensorNotFoundException();
	}

}
