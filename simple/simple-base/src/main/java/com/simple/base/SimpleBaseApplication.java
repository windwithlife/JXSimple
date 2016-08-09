package com.simple.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableJpaAuditing
@SpringBootApplication
public class SimpleBaseApplication {

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SimpleBaseApplication.class, args);
	}

}
