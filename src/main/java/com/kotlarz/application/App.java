package com.kotlarz.application;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.kotlarz.database.DatabaseConfig;

@EnableScheduling
@SpringBootApplication
@EnableWebSecurity(debug = true)
@ComponentScan(basePackages = { "com.kotlarz" })
@EnableJpaRepositories(basePackages = { "com.kotlarz" })
public class App {

	private static Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) {
		init(args);
		SpringApplication.run(App.class, args);
	}

	private static void init(String[] args) {
		if (args.length > 0) {
			String configPath = getArg(args, "-config");
			if (configPath != null) {
				log.info("Found config file path " + configPath);
				try {
					DatabaseConfig.initProperties(configPath);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			} else {
				log.warn("Didn't found config file path, using default configuration");
			}
		} else {
			log.warn("Didn't found input parameters");
		}
	}

	private static String getArg(String[] args, String argName) {
		List<String> argList = Arrays.asList(args);
		String arg = null;

		if (argList.contains(argName)) {
			if (argList.indexOf(argName) < (argList.size() - 1)) {
				arg = argList.get(argList.indexOf(argName) + 1);
			}
		}

		return arg;
	}
}