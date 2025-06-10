package com.edutech.cupones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.edutech"})
public class CuponesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuponesApplication.class, args);
	}

}
