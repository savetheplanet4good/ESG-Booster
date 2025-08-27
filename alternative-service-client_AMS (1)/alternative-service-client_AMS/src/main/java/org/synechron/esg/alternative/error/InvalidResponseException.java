package org.synechron.esg.alternative.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.synechron.esg.alternative.request.AlternativesInvestSuitRequest;
import org.synechron.esg.alternative.response.InvestSuitResponse;

/**
 * The type Invalid response exception.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidResponseException extends Throwable {

    private HttpStatus status;
    private String message;
    private InvestSuitResponse resp;
    private AlternativesInvestSuitRequest requestAlt;
}
