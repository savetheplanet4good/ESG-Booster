package org.synechron.portfolio.hazelcastserver.mapstore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.synechron.esg.model.AppSettings;
import org.synechron.portfolio.hazelcastserver.repository.AppSettingsRepository;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapLoaderLifecycleSupport;
import com.hazelcast.core.MapStore;
import com.hazelcast.spring.context.SpringAware;

@SpringAware
@Component
public class AppSettingsMapStore implements MapStore<String, AppSettings>,MapLoaderLifecycleSupport{

	Logger logger = LoggerFactory.getLogger(AppSettingsMapStore.class);
	
	   @Autowired
	   private  AppSettingsRepository appSettingsRepository;
	 
	  public AppSettingsMapStore() {
		  logger.info("Invoking the AppSettings Map Store ");
	  }
	
	@Override
	public AppSettings load(String name) {
		// TODO Auto-generated method stub
		logger.info("****************************** Loading object for Name ******** "+name);
		Optional<AppSettings> appSettings = appSettingsRepository.findByName(name);
        return appSettings.isPresent() ? appSettings.get() : null;
	}

	@Override
	public Map<String, AppSettings> loadAll(Collection<String> names) {
		// TODO Auto-generated method stub
		Map<String, AppSettings> result = new HashMap<>();
		logger.info("******************************      Loading All Data for Given Keys   ******************************"+names);
        for (String name : names) {
        	
        	AppSettings appSetting = this.load(name);
        	System.out.println(name + " AppSettings object is "+appSetting);
            if (appSetting != null) {
                result.put(name, appSetting);
            }
        }
        return result;
	}

	@Override
	public Iterable<String> loadAllKeys() {
		// TODO Auto-generated method stub
		 logger.info("******************************      Loading All keys   ******************************");
		// TODO Auto-generated method stub
		Map<String, AppSettings> appSettingsAll = new HashMap<String, AppSettings>();
       List<AppSettings> AppSettingsObjectsList = appSettingsRepository.findAll();
       logger.info("data is"+AppSettingsObjectsList.size());
       logger.info("data is"+AppSettingsObjectsList);
       for (AppSettings appSetting : AppSettingsObjectsList) {
    	   appSettingsAll.put(appSetting.getName(), appSetting);
       }
       return appSettingsAll.keySet();
	}

	@Override
	public void init(HazelcastInstance hazelcastInstance, Properties properties, String mapName) {
		// TODO Auto-generated method stub
		 hazelcastInstance.getConfig().getManagedContext().initialize(this);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store(String key, AppSettings appSetting) {
		// TODO Auto-generated method stub
		appSettingsRepository.save(appSetting);
	}

	@Override
	public void storeAll(Map<String, AppSettings> map) {
		// TODO Auto-generated method stub
		appSettingsRepository.saveAll(new ArrayList<>(map.values()));
	}

	@Override
	public void delete(String name) {
		// TODO Auto-generated method stub
		appSettingsRepository.deleteByName(name);
	}

	@Override
	public void deleteAll(Collection<String> names) {
		// TODO Auto-generated method stub
		names.forEach(appSettingsRepository::deleteByName);
	}

	

}
