package org.synechron.portfolio.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.Alternatives;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplaceCompaniesRequest {

    private List<String> companiesToReplace;
    private List<Alternatives> alternative;
    private String portfolioId;

}
