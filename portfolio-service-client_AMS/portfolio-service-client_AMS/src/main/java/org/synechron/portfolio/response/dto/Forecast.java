package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {

    private Double esgScore;
    private String date;
    private Double socialScore;
    private Double envScore;
    private Double govScore;
}
