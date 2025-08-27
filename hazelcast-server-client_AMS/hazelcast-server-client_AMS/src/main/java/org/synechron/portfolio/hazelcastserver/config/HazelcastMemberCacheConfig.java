package org.synechron.portfolio.hazelcastserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.synechron.portfolio.hazelcastserver.mapstore.*;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.MapStoreConfig.InitialLoadMode;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.cp.CPSubsystemConfig;
import com.hazelcast.config.cp.FencedLockConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.context.SpringManagedContext;


@Configuration
public class HazelcastMemberCacheConfig {

    @Bean
    public Config hazelcastConfig() {

        ManagementCenterConfig manCenterCfg = new ManagementCenterConfig();
        manCenterCfg.setEnabled(true).setUrl("http://localhost:8090/hazelcast-mancenter");

        PortfolioMapStore portfolioMapstore = new PortfolioMapStore();
        MapStoreConfig portfolioMapStoreConfig = new MapStoreConfig();
        portfolioMapStoreConfig.setEnabled(true).setClassName("org.synechron.portfolio.hazelcastserver.mapstore.PortfolioMapStore");
        portfolioMapStoreConfig.setInitialLoadMode(InitialLoadMode.EAGER);
        portfolioMapStoreConfig.setImplementation(portfolioMapstore);
        //configure MapStore as write - behind by setting the write -delay - seconds property to a
        //value bigger than 0 for write through set it to 0
        portfolioMapStoreConfig.setWriteDelaySeconds(0);

        FailedInvestSuiteRequestsMapStore failedInvestSuiteRequestsMapStore = new FailedInvestSuiteRequestsMapStore();
        MapStoreConfig failedInvestSuiteRequestsMapStoreConfig = new MapStoreConfig();
        failedInvestSuiteRequestsMapStoreConfig.setEnabled(true).setClassName("org.synechron.portfolio.hazelcastserver.mapstore.FailedInvestSuiteRequestsMapStore");
        failedInvestSuiteRequestsMapStoreConfig.setInitialLoadMode(InitialLoadMode.EAGER);
        failedInvestSuiteRequestsMapStoreConfig.setImplementation(failedInvestSuiteRequestsMapStore);
        failedInvestSuiteRequestsMapStoreConfig.setWriteDelaySeconds(0);
        
        ApplicationComponentSettingsMapStore applicationComponentSettingsMapStore = new ApplicationComponentSettingsMapStore();
        MapStoreConfig applicationComponentSettingsMapStoreConfig = new MapStoreConfig();
        applicationComponentSettingsMapStoreConfig.setEnabled(true).setClassName("org.synechron.portfolio.hazelcastserver.mapstore.ApplicationComponentSettingsMapStore");
        applicationComponentSettingsMapStoreConfig.setInitialLoadMode(InitialLoadMode.EAGER);
        applicationComponentSettingsMapStoreConfig.setImplementation(applicationComponentSettingsMapStore);


        AlternativeMapStore alternativeMapStore = new AlternativeMapStore();
        MapStoreConfig alternativeMapStoreConfig = new MapStoreConfig();
        alternativeMapStoreConfig.setEnabled(true).setClassName("org.synechron.portfolio.hazelcastserver.mapstore.AlternativeMapStore");
        alternativeMapStoreConfig.setInitialLoadMode(InitialLoadMode.EAGER);
        alternativeMapStoreConfig.setImplementation(alternativeMapStore);
        alternativeMapStoreConfig.setWriteDelaySeconds(0);

        CPSubsystemConfig cpSubsystemConfig = new CPSubsystemConfig();
        cpSubsystemConfig.setCPMemberCount(3);
        cpSubsystemConfig.addLockConfig(new FencedLockConfig("non-reentrant-lock", 1));
        cpSubsystemConfig.setSessionHeartbeatIntervalSeconds(1);
        cpSubsystemConfig.setSessionTimeToLiveSeconds(5);
        cpSubsystemConfig.setMissingCPMemberAutoRemovalSeconds(10);

        Config config = new Config();
        config.setProperty("hazelcast.logging.type", "slf4j");
        config.setManagedContext(managedContext());
        config.setManagementCenterConfig(manCenterCfg).setCPSubsystemConfig(cpSubsystemConfig)
                .addMapConfig(new MapConfig().setName("portfolios")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU).setBackupCount(2)
                        .setTimeToLiveSeconds(18000).setMapStoreConfig(portfolioMapStoreConfig))
                .addMapConfig(new MapConfig().setName("benchmarks")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU).setBackupCount(2)
                        .setTimeToLiveSeconds(21600))
                .addMapConfig(new MapConfig().setName("companyCards")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setBackupCount(2)
                        .setTimeToLiveSeconds(84600))
                .addMapConfig(new MapConfig().setName("alternativecompanies")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU).setBackupCount(2)
                        .setTimeToLiveSeconds(21600))
                .addMapConfig(new MapConfig().setName("investableUniverse")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU).setBackupCount(2)
                        .setTimeToLiveSeconds(21600))
                .addMapConfig(new MapConfig().setName("alternatives")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU).setBackupCount(2)
                        .setTimeToLiveSeconds(21600)
                        .setMapStoreConfig(alternativeMapStoreConfig))
                .addMapConfig(new MapConfig().setName("portfolioCalculationHistory")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU).setBackupCount(2)
                        .setTimeToLiveSeconds(21600))
                .addMapConfig(new MapConfig().setName("failedAlterntaivesRequests")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU).setBackupCount(2)
                        .setTimeToLiveSeconds(60)
                        .setMapStoreConfig(failedInvestSuiteRequestsMapStoreConfig))
                .addMapConfig(new MapConfig().setName("applicationSettings")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU).setBackupCount(2)
                        .setTimeToLiveSeconds(21600).setMapStoreConfig(applicationComponentSettingsMapStoreConfig)
                );

        //Setting three more members HazelcastInstance hz1 =
        Hazelcast.newHazelcastInstance(config);
        HazelcastInstance hz2 = Hazelcast.newHazelcastInstance(config);
        HazelcastInstance hz3 = Hazelcast.newHazelcastInstance(config);
        return config;
    }

    @Bean
    public SpringManagedContext managedContext() {
        return new SpringManagedContext();
    }
}
