package com.kotlarz.temp;

import com.kotlarz.temp.domain.TemperatureSensorDomain;

public class TemperatureSample {
	private TemperatureSensorDomain sensor;
	private float temperature;

	public TemperatureSample(TemperatureSensorDomain sensor, float temperature) {
		super();
		this.sensor = sensor;
		this.temperature = temperature;
	}

	public TemperatureSensorDomain getSensor() {
		return sensor;
	}

	public void setSensor(TemperatureSensorDomain sensor) {
		this.sensor = sensor;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

}
