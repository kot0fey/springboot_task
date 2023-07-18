package com.example.springboot_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EntityScan("com.example.springboot_task.model")
@EnableJpaRepositories
@SpringBootApplication
public class SpringbootTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTaskApplication.class, args);
	}

}
