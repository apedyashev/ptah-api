package com.example.ptah;

// import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PtahApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtahApplication.class, args);
	}

	// TODO:
	// https://www.baeldung.com/spring-cors#1-javaconfig
	// https://howtodoinjava.com/spring5/webmvc/spring-mvc-cors-configuration/
	// https://www.baeldung.com/spring-security-cors-preflight
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO: allowed origin to config
				registry.addMapping("/login").allowedHeaders("*").exposedHeaders("*")
						.allowedOrigins("http://localhost:4200");
				registry.addMapping("/api/v1/auth/register").allowedHeaders("*").exposedHeaders("*")
						.allowedOrigins("http://localhost:4200");
			}
		};
	}

	// https://github.com/getarrays/employeemanager/blob/master/src/main/java/tech/getarrays/employeemanager/EmployeemanagerApplication.java
	// @Bean
	// public CorsFilter corsFilter() {
	// CorsConfiguration corsConfiguration = new CorsConfiguration();
	// corsConfiguration.setAllowCredentials(true);
	// corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	// corsConfiguration.setAllowedHeaders(Arrays.asList("Origin",
	// "Access-Control-Allow-Origin", "Content-Type",
	// "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
	// "Access-Control-Request-Method",
	// "Access-Control-Request-Headers"));
	// corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type",
	// "Accept", "Authorization",
	// "Access-Control-Allow-Origin", "Access-Control-Allow-Origin",
	// "Access-Control-Allow-Credentials"));
	// corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT",
	// "DELETE", "OPTIONS"));
	// UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new
	// UrlBasedCorsConfigurationSource();
	// urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",
	// corsConfiguration);
	// return new CorsFilter(urlBasedCorsConfigurationSource);
	// }

}
