package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity   //开启security
public class DemoApplication {

	public static void main(String[] args) {
		System.setProperty("jasypt.encryptor.password", "PEB123@321BEP");
		SpringApplication.run(DemoApplication.class, args);
	}
}