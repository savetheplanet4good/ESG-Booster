package org.synechron.esg.masteruniverse.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
class CacheClient {

    @Value("${hazelcast.server.url}")
    private String hazelcastServer;

    @Bean
    public ClientConfig clientConfig() {
        ClientConfig clientConfig = new ClientConfig();

        clientConfig.getNetworkConfig().addAddress(hazelcastServer);
        clientConfig.setProperty("hazelcast.client.invocation.timeout.seconds", "120");
        clientConfig.getNetworkConfig().setConnectionAttemptLimit(20);
        clientConfig.getNetworkConfig().setConnectionAttemptPeriod(20000);
        return clientConfig;
    }

    /**
     * <P>Temporary. Spring Boot should soon autoconfigure
     * the {@code HazelcastInstance} bean.
     * </P>
     */
    @Configuration
    @ConditionalOnMissingBean(HazelcastInstance.class)
    static class HazelcastClientConfiguration {

        @Bean
        public HazelcastInstance hazelcastInstance(ClientConfig clientConfig) {
            return HazelcastClient.newHazelcastClient(clientConfig);
        }
    }


}