
package org.synechron.portfolio.hazelcastserver.mapstore;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapLoaderLifecycleSupport;
import com.hazelcast.core.MapStore;
import com.hazelcast.spring.context.SpringAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.synechron.esg.model.AlternativesObject;
import org.synechron.portfolio.hazelcastserver.repository.AlternativeRepository;

import java.util.*;


@SpringAware
@Component
public class AlternativeMapStore implements MapStore<Integer, AlternativesObject>, MapLoaderLifecycleSupport {

    Logger logger = LoggerFactory.getLogger(AlternativeMapStore.class);


    @Autowired
    private AlternativeRepository alternativeRepository;

    public AlternativeMapStore() {
        logger.info("Invoking the Alternative Map Store ");
    }

    @Override
    public AlternativesObject load(Integer integer) {
        Optional<AlternativesObject> alternatives = alternativeRepository.findByAlternativeId(integer);
        return alternatives.isPresent() ? alternatives.get() : null;
    }

    @Override
    public Map<Integer, AlternativesObject> loadAll(Collection<Integer> collection) {
        Map<Integer, AlternativesObject> result = new HashMap<>();
        logger.info("******************************      Loading All Data for Given Keys   ******************************");
        for (Integer key : collection) {

            AlternativesObject alternativesObject = this.load(key);
            if (alternativesObject != null) {
                result.put(key, alternativesObject);
            }
        }
        return result;
    }

    @Override
    public Iterable<Integer> loadAllKeys() {

        Map<Integer, AlternativesObject> alternativesObjectMap = new HashMap<>();
        List<AlternativesObject> applicationComponentSettingsList = alternativeRepository.findAll();
        for (AlternativesObject alternativesObject : applicationComponentSettingsList) {
            alternativesObjectMap.put(alternativesObject.getAlternativeId(), alternativesObject);
        }
        return alternativesObjectMap.keySet();
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
    public void store(Integer integer, AlternativesObject alternativesObject) {

        logger.info("Persisiting the new record to databse");
        alternativeRepository.save(alternativesObject);
    }

    @Override
    public void storeAll(Map<Integer, AlternativesObject> map) {
        //keeping it empty as we will stroing the entry one by one
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

