package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioHistoryResponse implements Serializable {

    private Double esgScore;
    private Double esgCombinedScore;
    private Double socialScore;
    private Double govScore;
    private Double envScore;
    private String year;
    private String esgMsciScore;
}
