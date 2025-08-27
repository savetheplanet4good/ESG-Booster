package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(name="companies")
    private List<Company> companies;

    @Field(name="esgScore")
    private Double esgScore;

    @Field(name="envScore")
    private Double envScore;

    @Field(name="socialScore")
    private Double socialScore;

    @Field(name="govScore")
    private Double govScore;

    @Field(name="esgCombinedScore")
    private Double esgCombinedScore;

    @Field(name="contraversyScore")
    private Double contraversyScore;

    @Field(name="sustEnvScore")
    private Double sustainalyticsEnvScore;

    @Field(name="sustSocialScore")
    private Double sustainalyticsSocialScore;

    @Field(name="sustGovScore")
    private Double sustainalyticsGovScore;

    @Field(name="sustEsgCombinedScore")
    private Double sustainalyticsCombinedScore;
}
