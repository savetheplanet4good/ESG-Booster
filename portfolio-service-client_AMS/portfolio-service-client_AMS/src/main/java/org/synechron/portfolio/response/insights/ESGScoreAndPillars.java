package org.synechron.portfolio.response.insights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ESGScoreAndPillars {
	
    private Double esgCombinedScore;
    private Double envScore;
    private Double socialScore;
    private Double govScore;
}
