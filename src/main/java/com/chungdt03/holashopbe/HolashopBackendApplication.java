package com.chungdt03.holashopbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.chungdt03.holashopbe.repositories.ProductRepository", enableDefaultTransactions = false)
//@EnableElasticsearchRepositories(basePackages = "com.chungdt03.holashopbe.repositories.ProductSearchRepository", considerNestedRepositories = true, enableDefaultTransactions = false)
public class HolashopBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolashopBackendApplication.class, args);
	}

}
