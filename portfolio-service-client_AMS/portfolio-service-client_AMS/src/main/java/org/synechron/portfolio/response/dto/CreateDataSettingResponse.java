package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDataSettingResponse {

    private HttpStatus status;
    private String message;
    private Map<String, String> errorMap;
}