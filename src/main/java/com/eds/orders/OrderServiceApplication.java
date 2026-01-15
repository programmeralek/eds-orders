package com.eds.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

//exclude = {
//		DataSourceAutoConfiguration.class,
//		HibernateJpaAutoConfiguration.class
//}

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
