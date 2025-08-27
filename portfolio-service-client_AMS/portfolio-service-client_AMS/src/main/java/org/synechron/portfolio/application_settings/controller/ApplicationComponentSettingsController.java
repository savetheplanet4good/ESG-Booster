package org.synechron.portfolio.application_settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.portfolio.application_settings.response.LoadApplicationComponentSettingsResponse;
import org.synechron.portfolio.application_settings.response.UpdateApplicationComponentSettingResponse;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.portfolio.controller.BaseController;
import java.util.List;
import java.util.Map;

@RestController
public class ApplicationComponentSettingsController extends BaseController {

    @Autowired
    ApplicationComponentSettingsService applicationSettingsService;

    @PostMapping("/load-application-settings")
    public LoadApplicationComponentSettingsResponse loadAllApplicationSettings() throws Exception {
        return applicationSettingsService.loadAllApplicationSettings();
    }

    @GetMapping("/application-settings")
    public List<ApplicationComponentSettings> getAllApplicationSettings() throws Exception {
        return applicationSettingsService.getAllApplicationSettings();
    }
    
    @GetMapping("/initial-application-settings")
    public Map<String, ApplicationComponentSettings> getInitialAllApplicationSettings() throws Exception {
    	return applicationSettingsService.getInitialApplicationComponentSettings();
    }

    @ResponseBody
    @PostMapping(value = "/update-application-settings", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UpdateApplicationComponentSettingResponse updateApplicationSettings(@RequestBody List<ApplicationComponentSettings> applicationComponentSettings){

        UpdateApplicationComponentSettingResponse updateApplicationSettingResponse = new UpdateApplicationComponentSettingResponse();

        try {
            updateApplicationSettingResponse = applicationSettingsService.updateApplicationSettings(applicationComponentSettings);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while updating application settings.");
        }

        return updateApplicationSettingResponse;
    }
}
