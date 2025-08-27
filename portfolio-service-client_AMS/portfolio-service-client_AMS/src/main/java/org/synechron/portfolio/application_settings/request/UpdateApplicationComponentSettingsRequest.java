package org.synechron.portfolio.application_settings.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.ApplicationComponentSettings;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationComponentSettingsRequest {

    private List<ApplicationComponentSettings> applicationComponentSettings;
}

