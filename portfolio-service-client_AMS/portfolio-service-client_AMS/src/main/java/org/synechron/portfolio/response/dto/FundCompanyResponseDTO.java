package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FundCompanyResponseDTO {

	private String isin;
	private String companyName;
	private String percentHolding;

	@Override
	public String toString() {
		return "FundCompany{" +
				"isin='" + isin + '\'' +
				", companyName='" + companyName + '\'' +
				", percentHolding='" + percentHolding + '\'' +
				'}';
	}
}
