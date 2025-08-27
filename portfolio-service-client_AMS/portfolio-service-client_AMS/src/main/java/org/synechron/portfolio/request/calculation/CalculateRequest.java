package org.synechron.portfolio.request.calculation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.synechron.esg.model.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateRequest {

    private String portfolioId;
    private String portfolioName;
    private String portfolioType;
    private String investableUniverseType;
    private String portfolioUser;
    private List<Company> companies;
    private String calculationType;

}
