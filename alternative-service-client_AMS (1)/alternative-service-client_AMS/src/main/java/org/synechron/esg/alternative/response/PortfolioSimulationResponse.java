package org.synechron.esg.alternative.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.synechron.esg.model.CountryAllocation;
import org.synechron.esg.model.CurrencyAllocation;
import org.synechron.esg.model.Sector;


import java.io.Serializable;
import java.util.List;

/**
 * The type Portfolio simulation response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioSimulationResponse implements Serializable {

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

    private List<String> regions;

    private Sector sector;

    private List<String> currency;

    private CurrencyAllocation currencyAllocation;

    private CountryAllocation countryAllocation;

}
