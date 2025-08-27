package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Benchmark implements Serializable{

	private static final long serialVersionUID = 1L;

    @Field(name="indexName")
    private String indexName;

    @Field(name="indexTotalScore")
    private Double indexTotalScore;

    @Field(name="envIndexScore")
    private Double envIndexScore;

    @Field(name="socialIndexScore")
    private Double socialIndexScore;

    @Field(name="govIndexScore")
    private Double govIndexScore;

    @Field(name="esgMsciScore")
    private String esgMsciScore;

    @Field(name="fdNr")
    private Double fdNr;

    @Field(name="fdA")
    private Double fdA;

    @Field(name="fdAA")
    private Double fdAA;

    @Field(name="fdAAA")
    private Double fdAAA;

    @Field(name="fdB")
    private Double fdB;

    @Field(name="fdBB")
    private Double fdBB;

    @Field(name="fdBBB")
    private Double fdBBB;

    @Field(name="fdCCC")
    private Double fdCCC;
}