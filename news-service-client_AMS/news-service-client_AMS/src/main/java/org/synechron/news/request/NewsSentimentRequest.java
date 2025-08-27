package org.synechron.news.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsSentimentRequest {
    private List<String> companyIsins;
    private Integer noOfMonths;
}
