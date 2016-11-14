package com.kotlarz.temp.sensors;

public class TemperatureSample {
	private TemperatureSensor sensor;
	private float temperature;

	public TemperatureSample(TemperatureSensor sensor, float temperature) {
		super();
		this.sensor = sensor;
		this.temperature = temperature;
	}

	public TemperatureSensor getSensor() {
		return sensor;
	}

	public void setSensor(TemperatureSensor sensor) {
		this.sensor = sensor;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

}
