package org.synechron.portfolio.application_settings.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.portfolio.application_settings.response.LoadApplicationComponentSettingsResponse;
import org.synechron.portfolio.application_settings.response.UpdateApplicationComponentSettingResponse;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.esg.model.*;
import org.synechron.portfolio.constant.Constant;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ApplicationComponentSettingsServiceImpl implements ApplicationComponentSettingsService {

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Override
    public LoadApplicationComponentSettingsResponse loadAllApplicationSettings() throws IOException {

        List<ApplicationComponentSettings> settings = null;

        //Read from file
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(Constant.DEFAULT_APPLICATION_COMPONENT_SETTINGS_FILE_NAME);
        settings = mapper.readValue(inputStream, new TypeReference<List<ApplicationComponentSettings>>(){});

        //Insert into database
        for (ApplicationComponentSettings applicationComponentSettings : settings)
            insertSettings(applicationComponentSettings);

        return new LoadApplicationComponentSettingsResponse(HttpStatus.OK, "Application Component Settings loaded Successfully.");
    }

    @Override
    public List<ApplicationComponentSettings> getAllApplicationSettings()  {

        IMap<String, ApplicationComponentSettings> applicationSettingIMap = hazelcastInstance.getMap(Constant.APPLICATION_SETTINGS_CACHE);

        //Check if cache is empty
        if (applicationSettingIMap.values().isEmpty()) {

            //Load the full cache as when the cache ttl elapse cache is empty even though db contains portfolios
            applicationSettingIMap.loadAll(Boolean.TRUE);
            if (applicationSettingIMap.values().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database is Empty.");
            }
        }

        return (List<ApplicationComponentSettings>) applicationSettingIMap.values();
    }

    @Override
    public UpdateApplicationComponentSettingResponse updateApplicationSettings(List<ApplicationComponentSettings> applicationComponentSettingsList) {

        HttpStatus status = HttpStatus.OK;
        String message = "Application Settings Updated Successfully.";
        IMap<String, ApplicationComponentSettings> applicationSettingIMap = hazelcastInstance.getMap(Constant.APPLICATION_SETTINGS_CACHE);

        //Update into database
        try{
            for (ApplicationComponentSettings applicationComponentSetting : applicationComponentSettingsList)
                updateApplicationSettings(applicationComponentSetting);

            //Update the whole dataset into cache
            applicationSettingIMap.loadAll(Boolean.TRUE);
        }catch (Exception ex){
            status = HttpStatus.BAD_REQUEST;
            message = "Application Settings Updation Failed.";
        }

        return new UpdateApplicationComponentSettingResponse(status, message);
    }

    public void insertSettings(ApplicationComponentSettings applicationComponentSettings){

        IMap<String, ApplicationComponentSettings> applicationSettingIMap = hazelcastInstance.getMap(Constant.APPLICATION_SETTINGS_CACHE);
        applicationSettingIMap.put(applicationComponentSettings.getPropertyDisplayName(), applicationComponentSettings);
    }

    public UpdateApplicationComponentSettingResponse updateApplicationSettings(ApplicationComponentSettings applicationComponentSettings) {

        HttpStatus status;
        String message;

        IMap<String, ApplicationComponentSettings> applicationSettingIMap = hazelcastInstance.getMap(Constant.APPLICATION_SETTINGS_CACHE);
        try {
            applicationSettingIMap.lock(applicationComponentSettings.getPropertyDisplayName());
            ApplicationComponentSettings updatedApplicationComponentSettings = applicationSettingIMap.put(applicationComponentSettings.getPropertyDisplayName(), applicationComponentSettings, 1200, TimeUnit.SECONDS);
            if (updatedApplicationComponentSettings != null) {
                message = "Application Setting Updated Successfully.";
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.BAD_REQUEST;
                message = "Application Setting Updation Failed.";
            }
        } catch (Exception exception) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Exception occurred while updating the application settings.";
        } finally {
            applicationSettingIMap.unlock(applicationComponentSettings.getPropertyDisplayName());
        }

        return new UpdateApplicationComponentSettingResponse(status, message);
    }
    
    @Override
    public Map<String, ApplicationComponentSettings> getInitialApplicationComponentSettings(){
    	
    	List<ApplicationComponentSettings> applicationComponentSettings = getAllApplicationSettings();

    	HashMap<String, ApplicationComponentSettings> initialComponentSettings = new HashMap<>();
    	applicationComponentSettings.forEach(component ->
    		initialComponentSettings.put(component.getProperty(), component)
    	);

    	return initialComponentSettings;
    }
}
    


