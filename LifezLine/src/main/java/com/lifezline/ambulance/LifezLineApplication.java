package com.lifezline.ambulance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "com.lifezline.ambulanceservices", "com.lifezline.ambulancemodel"})
public class LifezLineApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifezLineApplication.class, args);
		
		
		System.out.println("LIFEZLINE");
	}

}
