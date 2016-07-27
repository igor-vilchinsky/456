package com.enrichservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EnrichServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(EnrichServiceApp.class, args);
	}
}
