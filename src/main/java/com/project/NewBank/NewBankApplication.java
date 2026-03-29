package com.project.NewBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NewBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewBankApplication.class, args);
	}

}
