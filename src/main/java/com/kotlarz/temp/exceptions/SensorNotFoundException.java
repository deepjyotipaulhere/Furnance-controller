package com.kotlarz.temp.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
public class SensorNotFoundException extends Exception {

	public SensorNotFoundException() {
	}

	public SensorNotFoundException(Exception e) {
		super(e);
	}

}
