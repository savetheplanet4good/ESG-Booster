package org.synechron.portfolio.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * The type File validation error.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileValidationError extends Throwable {

    private HttpStatus status;
    private String message;
    private Map<String, String> fileErrorsMap;
}
