package com.kotlarz.temp.sensors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.pi4j.io.w1.W1Device;

public class TemperatureSensor {
	private int id;
	private String sensorName;
	private String address;

	public TemperatureSensor(int id, String sensorName, String address) {
		super();
		this.id = id;
		this.sensorName = sensorName;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public static RowMapper<TemperatureSensor> getRowMapper() {
		return new RowMapper<TemperatureSensor>() {

			@Override
			public TemperatureSensor mapRow(ResultSet arg0, int arg1) throws SQLException {
				String sensorName = arg0.getString("sensor_name");
				String address = arg0.getString("address");
				int id = arg0.getInt("id");
				return new TemperatureSensor(id, sensorName, address);
			}
		};
	}
}
