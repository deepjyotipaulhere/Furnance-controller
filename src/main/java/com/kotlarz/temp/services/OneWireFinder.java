package com.kotlarz.temp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

@Service
public class OneWireFinder {
	public List<W1Device> getAllConnected(int deviceFamilyId) {
		W1Master master = new W1Master();
		return master.getDevices(deviceFamilyId);
	}
}
