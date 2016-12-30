package com.kotlarz.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableScheduling
@SpringBootApplication
//@EnableWebSecurity(debug = true)
@ComponentScan(basePackages = { "com.kotlarz" })
@EnableJpaRepositories(basePackages = { "com.kotlarz" })
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}