package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastYearESGScores {

    private Double esgScore;
    private Double esgCombinedScore;
    private Double envScore;
    private Double socialScore;
    private Double govScore;
}
