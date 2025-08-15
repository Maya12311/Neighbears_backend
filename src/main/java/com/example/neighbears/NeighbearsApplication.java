package com.example.neighbears;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@SpringBootApplication
//@EnableWebSecurity(debug = true)
public class NeighbearsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeighbearsApplication.class, args);
	}



}
