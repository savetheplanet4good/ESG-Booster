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
public class PortfolioSimulationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(name = "esgScore")
    private Double esgScore;

    @Field(name = "envScore")
    private Double envScore;

    @Field(name = "socialScore")
    private Double socialScore;

    @Field(name = "govScore")
    private Double govScore;

    @Field(name = "esgCombinedScore")
    private Double esgCombinedScore;

    @Field(name = "regions")
    private List<String> regions;

    @Field(name = "sector")
    private Sector sector;

    @Field(name = "currency")
    private List<String> currency;

    @Field(name = "currencyAllocation")
    private CurrencyAllocation currencyAllocation;

    @Field(name = "countryAllocation")
    private CountryAllocation countryAllocation;
}
