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
public class CompanyEmissionHistoricalGraph implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CarbonEmissionResponse> emissionResponse = new ArrayList<>();
    private List<CompanySectorAveragePerYear> sectorAveragePerYears = new ArrayList<>();
    private List<CompanyEmissionScopesHistoricalData> emissionScopes = new ArrayList<>();
}
