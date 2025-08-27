package org.synechron.portfolio.response.history;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarbonEmissionScopeResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private Double co2_equivalent_emissions_direct_scope_1;
	private Double co2_equi_emissions_indirect_scope_2;
	private Double co2_equi_emissions_indirect_scope_3;
	private String year;
}
