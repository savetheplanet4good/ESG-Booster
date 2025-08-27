package org.synechron.esg.alternative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;

/**
 * The type Alternative application.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AlternativeApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AlternativeApplication.class, args);
    }
    
     @Bean
    public Sampler defaultSampler() {
	    return Sampler.ALWAYS_SAMPLE;
    }
}
