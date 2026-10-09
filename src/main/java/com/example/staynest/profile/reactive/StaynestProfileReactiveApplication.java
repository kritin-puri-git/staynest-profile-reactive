package com.example.staynest.profile.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class StaynestProfileReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(StaynestProfileReactiveApplication.class, args);
	}

}
