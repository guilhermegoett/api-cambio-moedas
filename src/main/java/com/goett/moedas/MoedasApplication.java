package com.goett.moedas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoedasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoedasApplication.class, args);
	}

}
