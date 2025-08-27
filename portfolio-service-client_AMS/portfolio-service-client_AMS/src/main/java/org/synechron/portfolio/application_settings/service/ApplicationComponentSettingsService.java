package org.synechron.portfolio.application_settings.service;

import org.synechron.portfolio.application_settings.response.LoadApplicationComponentSettingsResponse;
import org.synechron.portfolio.application_settings.response.UpdateApplicationComponentSettingResponse;
import org.synechron.esg.model.ApplicationComponentSettings;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ApplicationComponentSettingsService {

    LoadApplicationComponentSettingsResponse loadAllApplicationSettings() throws IOException;

    List<ApplicationComponentSettings> getAllApplicationSettings() throws IOException;

    UpdateApplicationComponentSettingResponse updateApplicationSettings(List<ApplicationComponentSettings> applicationComponentSettings);
    
    Map<String, ApplicationComponentSettings> getInitialApplicationComponentSettings() throws IOException;
}
