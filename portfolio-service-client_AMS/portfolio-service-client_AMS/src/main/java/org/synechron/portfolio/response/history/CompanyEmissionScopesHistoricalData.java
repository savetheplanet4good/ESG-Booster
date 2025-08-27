package org.synechron.portfolio.response.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEmissionScopesHistoricalData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String scopeName;
    private List<CarbonEmissionResponse> emissionScopesResponse = new ArrayList<>();
}
