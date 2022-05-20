package com.mypoc.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultitenantApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class[]{MultitenantApplication.class, CustomWebApplicationInitializer.class}, args);

	}

}
