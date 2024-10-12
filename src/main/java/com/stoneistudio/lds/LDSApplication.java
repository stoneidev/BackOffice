package com.stoneistudio.lds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LDSApplication {

	public static void main(String[] args) {
		SpringApplication.run(LDSApplication.class, args);
	}

}
