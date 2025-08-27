package org.synechron.news.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 *
 * Class Name : NewsDto
 * Purpose : News responseDto object
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {

    private String source;
    private String type;
    private Double sentiment;
    private String title;
    private String description;
    private Timestamp time;
    private String isin;
    private String subType;
}
