package org.synechron.news.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 *
 * Class Name : NewsFeedsDto
 * Purpose : News feeds responseDto object
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsFeedsDto {

    private String source;
    private String type;
    private Double sentiment;
    private String title;
    private String description;
    private Timestamp time;
    private String isin;
    private String url;
    private String companyName;
}
