package org.synechron.news.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 *
 * Class Name : NewsDataLakeResponseDto
 * Purpose : News datalake responseDto object
 *
 */
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
    private String companyName;
}
