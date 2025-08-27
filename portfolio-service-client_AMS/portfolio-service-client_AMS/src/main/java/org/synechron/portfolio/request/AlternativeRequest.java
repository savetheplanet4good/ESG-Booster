package org.synechron.portfolio.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlternativeRequest {

    private List<String> isin;
    private String portfolioId;
    private String esgTargetType;

}
