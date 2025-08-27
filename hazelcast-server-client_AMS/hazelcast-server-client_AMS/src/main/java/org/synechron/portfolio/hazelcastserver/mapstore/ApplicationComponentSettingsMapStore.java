package org.synechron.portfolio.hazelcastserver.mapstore;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.portfolio.hazelcastserver.repository.ApplicationComponentSettingsRepository;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapLoaderLifecycleSupport;
import com.hazelcast.core.MapStore;
import com.hazelcast.spring.context.SpringAware;

@SpringAware
@Component
public class ApplicationComponentSettingsMapStore implements MapStore<String, ApplicationComponentSettings>,MapLoaderLifecycleSupport {

	Logger logger = LoggerFactory.getLogger(ApplicationComponentSettingsMapStore.class);
	
	@Autowired
	private ApplicationComponentSettingsRepository applicationComponentSettingsRepository;
	
	public ApplicationComponentSettingsMapStore() {
		logger.info("Invoking the ApplicationComponentSettings Map Store ");
	}
	
	@Override
	public ApplicationComponentSettings load(String name) {
		logger.info("****************************** Loading object for Name ******** "+name);
		Optional<ApplicationComponentSettings> applicationComponentSettings = applicationComponentSettingsRepository.findByPropertyDisplayName(name);
        return applicationComponentSettings.isPresent() ? applicationComponentSettings.get() : null;
	}	

	@Override
	public Map<String, ApplicationComponentSettings> loadAll(Collection<String> keys) {
		logger.info("****************************** Loading all data for given keys ******** ");
		Map<String, ApplicationComponentSettings> result = new HashMap<>();
		keys.forEach(name ->{
			ApplicationComponentSettings  applicationComponentSettings = this.load(name);
			if(applicationComponentSettings != null)
				result.put(name, applicationComponentSettings);		
		});
			
		return result;
	}

	@Override
	public Iterable<String> loadAllKeys() {
		Map<String, ApplicationComponentSettings> applicationComponentSettingsMap = new HashMap<>();
		List<ApplicationComponentSettings> applicationComponentSettingsList = applicationComponentSettingsRepository.findAll();
		for (ApplicationComponentSettings applicationComponentSettings : applicationComponentSettingsList) {
			applicationComponentSettingsMap.put(applicationComponentSettings.getPropertyDisplayName(), applicationComponentSettings);
		}
		return applicationComponentSettingsMap.keySet();
	}

	@Override
	public void init(HazelcastInstance hazelcastInstance, Properties properties, String mapName) {

		hazelcastInstance.getConfig().getManagedContext().initialize(this);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store(String key, ApplicationComponentSettings applicationComponentSettings) {
		logger.info("****************************** Save Application Component Settings to the database ******** ");
		applicationComponentSettingsRepository.save(applicationComponentSettings);
		
	}

	@Override
	public void storeAll(Map<String, ApplicationComponentSettings> map) {

		applicationComponentSettingsRepository.saveAll(new ArrayList<>());
	}

	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Collection<String> keys) {
		// TODO Auto-generated method stub
		
	}

}
