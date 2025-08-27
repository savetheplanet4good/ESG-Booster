package org.synechron.portfolio.application_settings.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadApplicationComponentSettingsResponse {

    private HttpStatus status;
    private String message;
}
