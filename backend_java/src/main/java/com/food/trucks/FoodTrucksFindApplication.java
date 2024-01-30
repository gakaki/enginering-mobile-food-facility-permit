package com.food.trucks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.food.trucks")
public class FoodTrucksFindApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodTrucksFindApplication.class, args);
	}

}
