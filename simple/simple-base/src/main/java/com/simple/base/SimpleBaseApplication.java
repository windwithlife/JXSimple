package com.simple.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class SimpleBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBaseApplication.class, args);
	}

}
