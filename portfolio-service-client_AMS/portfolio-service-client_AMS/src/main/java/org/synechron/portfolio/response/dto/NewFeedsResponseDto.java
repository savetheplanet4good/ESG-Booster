package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFeedsResponseDto {

    private String isin;
    private List<NewsDataLakeResponseDto> newsdata = new ArrayList<>();
}
