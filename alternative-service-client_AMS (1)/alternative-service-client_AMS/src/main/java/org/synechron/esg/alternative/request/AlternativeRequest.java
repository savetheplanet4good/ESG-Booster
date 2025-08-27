package org.synechron.esg.alternative.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Alternative request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlternativeRequest {

    private List<String> isin;
    private String portfolioId;
    private String esgTargetType;

}
