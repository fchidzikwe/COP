package com.fortune;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.fortune")
@EnableAutoConfiguration
public class CopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CopApplication.class, args);
	}
}
