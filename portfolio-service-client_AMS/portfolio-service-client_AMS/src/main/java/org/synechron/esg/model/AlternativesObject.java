package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.Date;

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
