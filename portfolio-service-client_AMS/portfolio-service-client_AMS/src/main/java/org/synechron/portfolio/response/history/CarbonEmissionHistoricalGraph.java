package org.synechron.portfolio.response.history;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarbonEmissionHistoricalGraph implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<CarbonEmissionResponse> emissionResponse = new ArrayList<>();
	private List<CompanySectorAveragePerYear> sectorAveragePerYears = new ArrayList<>();
	private List<CarbonEmissionScopeResponse> emissionScopeResponse = new ArrayList<>();
}
