package com.hanium.catsby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CatsbyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatsbyApplication.class, args);
	}

}
