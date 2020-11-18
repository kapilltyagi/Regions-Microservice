package com.tng.regions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class RegionsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegionsMicroserviceApplication.class, args);
	}

}
