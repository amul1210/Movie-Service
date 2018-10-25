package com.stackroute.movieservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
//@EnableDiscoveryClient
public class MovieServicesApplication {
	static Logger logger= LoggerFactory.getLogger(MovieServicesApplication.class);
	public static void main(String[] args) {
		logger.debug("This is a debug message");
		logger.info("This is an info message");
		logger.warn("This is a warn message");
		logger.error("This is an error message");
		logger.info("project is working");
		SpringApplication.run(MovieServicesApplication.class, args);
	}
}
