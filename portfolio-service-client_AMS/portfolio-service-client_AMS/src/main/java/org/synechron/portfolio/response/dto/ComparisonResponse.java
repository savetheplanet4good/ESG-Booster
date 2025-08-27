package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.synechron.esg.model.CountryAllocation;
import org.synechron.esg.model.CurrencyAllocation;
import org.synechron.esg.model.Sector;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonResponse {

    @Field(name = "sector")
    private Sector sector;

    @Field(name = "regions")
    private List<String> regions;

    private Double esgScore;

    private Double envScore;

    private Double socialScore;

    private Double govScore;

    private Double esgCombinedScore;

    @Field(name = "currency")
    private List<String> currency;

    private CurrencyAllocation currencyAllocation;

    private CountryAllocation countryAllocation;


}
