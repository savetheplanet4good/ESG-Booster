package org.synechron.portfolio.response.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySectorAveragePerYear implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double avgScore;
    private String year;
}
