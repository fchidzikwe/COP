package com.fortune;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.fortune.service.StorageService;


@SpringBootApplication
@ComponentScan(basePackages = "com.fortune")
@EnableAutoConfiguration
public class CopApplication implements CommandLineRunner{
	
	@Resource
    StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(CopApplication.class, args);
	}
	
	  @Override
	    public void run(String... args) throws Exception {
	        storageService.deleteAll();
	        storageService.init();
	    }
}
