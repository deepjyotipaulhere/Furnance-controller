package com.kotlarz.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
	
	@GetMapping(value = "/")
	public String getIndex() {
		return "Hello World!";
	}
}
