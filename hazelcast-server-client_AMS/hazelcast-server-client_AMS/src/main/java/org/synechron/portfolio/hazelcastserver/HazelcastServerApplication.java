package org.synechron.portfolio.hazelcastserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HazelcastServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HazelcastServerApplication.class, args);
	}

}
