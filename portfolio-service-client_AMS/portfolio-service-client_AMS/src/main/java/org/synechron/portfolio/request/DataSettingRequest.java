package org.synechron.portfolio.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSettingRequest {

    private String refinitiv;

    private String sustainalytics;


}

