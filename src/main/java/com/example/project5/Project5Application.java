package com.example.project5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Project5Application {

	public static void main(String[] args) {
		SpringApplication.run(Project5Application.class, args);
	}

}
