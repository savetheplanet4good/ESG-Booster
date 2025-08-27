package org.synechron.portfolio.hazelcastserver.mapstore;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.synechron.portfolio.hazelcastserver.repository.FailedInvestSuiteRequestsRepository;
import org.synechron.portfolio.response.dto.FailedInvestSuiteRequests;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapLoaderLifecycleSupport;
import com.hazelcast.core.MapStore;
import com.hazelcast.spring.context.SpringAware;


@SpringAware
@Component
public class FailedInvestSuiteRequestsMapStore implements MapStore<Integer, FailedInvestSuiteRequests>, MapLoaderLifecycleSupport {

    Logger logger = LoggerFactory.getLogger(FailedInvestSuiteRequestsMapStore.class);


    @Autowired
    private FailedInvestSuiteRequestsRepository failedInvestSuiteRequestsRepository;

    public FailedInvestSuiteRequestsMapStore() {
        logger.info("Invoking the Failed Invest Suite Requests Map Store ");
    }

    @Override
    public FailedInvestSuiteRequests load(Integer Id) {
        Optional<FailedInvestSuiteRequests> failedInvestSuiteRequests = failedInvestSuiteRequestsRepository.findByFailedInvestSuiteRequestsId(Id);
        return failedInvestSuiteRequests.isPresent() ? failedInvestSuiteRequests.get() : null;
    }

    @Override
    public Map<Integer, FailedInvestSuiteRequests> loadAll(Collection<Integer> collection) {
        Map<Integer, FailedInvestSuiteRequests> result = new HashMap<>();
        logger.info("******************************      Loading All Data for Given Keys   ******************************");
        for (Integer key : collection) {

            FailedInvestSuiteRequests failedAlterntaivesRequestObject = this.load(key);
            if (failedAlterntaivesRequestObject != null) {
                result.put(key, failedAlterntaivesRequestObject);
            }
        }
        return result;
    }

    @Override
    public Iterable<Integer> loadAllKeys() {
        return null;
    }

	@Override
    public void init(HazelcastInstance hazelcastInstance, Properties properties, String mapName) {
        // TODO Auto-generated method stub
        hazelcastInstance.getConfig().getManagedContext().initialize(this);
    }

    @Override
    public void destroy() {
    	// kept it empty as there is no logic to execute on destroy
    }

    @Override
    public void store(Integer integer, FailedInvestSuiteRequests failedInvestSuiteRequestsObject) {
       
        logger.info("Persisiting the new record to databse" );
        failedInvestSuiteRequestsRepository.save(failedInvestSuiteRequestsObject);
    }

    @Override
    public void storeAll(Map<Integer, FailedInvestSuiteRequests> map) {
    //keeping it empty as we will storing the entry one by one 
    }

    @Override
    public void delete(Integer integer) {
     //keeping it empty so that none of the alternative are removed
    }

    @Override
    public void deleteAll(Collection<Integer> collection) {
    //keeping it empty so that none of the alternatives are removed
    }
}
