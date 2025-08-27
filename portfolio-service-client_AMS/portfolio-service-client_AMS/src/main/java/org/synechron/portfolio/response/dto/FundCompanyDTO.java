package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FundCompanyDTO {

	private String isin;
	private String companyName;
	private String percentHolding;
	private String fundIsin;
	private String fundName;

	@Override
	public String toString() {
		return "FundCompany{" +
				"isin='" + isin + '\'' +
				", companyName='" + companyName + '\'' +
				", percentHolding='" + percentHolding + '\'' +
				", fundIsin='" + fundIsin + '\'' +
				", fundName='" + fundName + '\'' +
				'}';
	}
}
