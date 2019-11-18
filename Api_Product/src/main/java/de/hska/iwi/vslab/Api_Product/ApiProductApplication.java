package de.hska.iwi.vslab.Api_Product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ApiProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProductApplication.class, args);
	}

}
