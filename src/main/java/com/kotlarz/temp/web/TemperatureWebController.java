package com.kotlarz.temp.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kotlarz.temp.dao.TempDao;
import com.kotlarz.temp.exceptions.SensorNotFoundException;
import com.kotlarz.temp.sensors.TemperatureSample;
import com.kotlarz.temp.sensors.TemperatureSensor;
import com.kotlarz.temp.services.OneWireFinder;
import com.kotlarz.temp.services.TemperatureReader;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.system.SystemInfo;

@RestController
@RequestMapping("/temp")
public class TemperatureWebController {
	@Resource(name = "WindowsTemperatureSensor")
	TemperatureReader tempReader;

	@Resource(name = "RaspberryTemperatureSensor")
	TemperatureReader raspTempReader;

	@Autowired
	OneWireFinder deviceFinder;

	@Autowired
	TempDao sensorRepository;

	@GetMapping("/read")
	public TemperatureSample getTemperature(@RequestParam(required = true, name = "name") String sensorName)
			throws SensorNotFoundException {
		TemperatureSensor sensor = sensorRepository.getByName(sensorName);
		float temperature = tempReader.getTemperatureFrom(sensor);
		return new TemperatureSample(sensor, temperature);
	}

	@GetMapping("/all")
	public List<TemperatureSample> getAll() throws SensorNotFoundException {
		List<TemperatureSensor> sensorList = sensorRepository.getAll();
		List<TemperatureSample> sampleList = new LinkedList<TemperatureSample>();
		for (TemperatureSensor sensor : sensorList)
			sampleList.add(new TemperatureSample(sensor, tempReader.getTemperatureFrom(sensor)));

		return sampleList;
	}

	@GetMapping("/all/connected")
	public List<W1Device> getAllConnected() throws SensorNotFoundException {
		return deviceFinder.getAllConnected(TmpDS18B20DeviceType.FAMILY_CODE);
	}

	@GetMapping("/core")
	public Map<String, Float> getCoreTemp() throws NumberFormatException, UnsupportedOperationException, IOException, InterruptedException {
		Map<String, Float> responseMap = new HashMap<String, Float>();
		responseMap.put("CoreTemperature", SystemInfo.getCpuTemperature());
		return responseMap;
	}
}
