package org.synechron.news.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class Name : NewFeedsResponseDto
 * Purpose : News feeds responseDto object
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFeedsResponseDto {
    private String isin;
    private List<NewsDataLakeResponseDto> newsdata = new ArrayList<>();
}
