package org.synechron.portfolio.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsSentimentsDto {

    private String isin;
    private String sentimentScore;
}
