package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "alternative")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativesObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(name="alternativeCacheKey")
    private AlternativeCacheKey alternativeCacheKey;

    @Field(name="alternativeResponse")
    private AlternativeResponse alternativeResponse;

    @Id
    @Field(name="alternativeId")
    private Integer alternativeId;
    @Field(name="created_date")
    private Date createdDate;
}
