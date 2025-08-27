package org.synechron.esg.benchmarkservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BenchmarkServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BenchmarkServiceApplication.class, args);
	}
	
	@Bean
    public Sampler defaultSampler() {
	    return Sampler.ALWAYS_SAMPLE;
    }


}
