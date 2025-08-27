package org.synechron.portfolio.response.insights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleComponentEffectDto {

	private String title;
	private String displayMessage;
	private String company;
	private ESGScoreAndPillars scores;
	private ESGScoreAndPillars percentageChange;
}
