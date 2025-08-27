package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ESGScore {
	
    private Double esgCombinedScore;
    private Double socialScore;
    private Double govScore;
    private Double envScore;
	private String isin;
    private String companyName;
}
