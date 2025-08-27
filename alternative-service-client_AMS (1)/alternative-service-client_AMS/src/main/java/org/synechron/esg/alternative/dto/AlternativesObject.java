package org.synechron.esg.alternative.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.synechron.esg.alternative.dto.cachekey.AlternativeCacheKey;
import org.synechron.esg.alternative.response.AlternativeResponse;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Alternatives object.
 */
@Document(collection = "alternative")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativesObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private AlternativeResponse alternativeResponse;
    private AlternativeCacheKey alternativeCacheKey;
    private Integer alternativeId;
    private Date createdDate;

}
