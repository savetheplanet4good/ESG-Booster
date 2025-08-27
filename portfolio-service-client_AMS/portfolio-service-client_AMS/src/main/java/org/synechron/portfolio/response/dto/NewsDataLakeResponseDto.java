package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDataLakeResponseDto {

    private String source;
    private String type;
    private Double sentiment;
    private String title;
    private String description;
    private String url;
    private Timestamp time;
}
