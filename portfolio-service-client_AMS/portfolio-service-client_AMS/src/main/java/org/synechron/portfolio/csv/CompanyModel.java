package org.synechron.portfolio.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @CsvBindByName(column = "ISIN")
    private String isin;

    @CsvBindByName(column = "Company")
    private String name;

    @CsvBindByName(column = "Weightage")
    private double wt;

    @CsvBindByName(column = "Amount Invested")
    private long amountInvested;
}