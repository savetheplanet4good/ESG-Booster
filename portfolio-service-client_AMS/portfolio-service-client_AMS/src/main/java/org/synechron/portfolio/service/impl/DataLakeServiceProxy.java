package org.synechron.portfolio.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synechron.esg.model.CompanyESGScore;
import org.synechron.esg.model.CompanyCardAndESGDetailsDatalakeDto;
import org.synechron.portfolio.dto.DailyESGScoreRequest;
import org.synechron.portfolio.response.dto.*;
import org.synechron.portfolio.response.history.CarbonEmissionHistoricalGraph;

import java.util.List;

@FeignClient(fallback = DataLakeServiceProxyFallback.class, url = "${datalake.url}", value = "dl")
public interface DataLakeServiceProxy {

    @RequestMapping(value = "/datalake/companyesg", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<List<CompanyESGScore>> getCompaniesESGScore(@RequestBody List<String> isinCodes);

    @RequestMapping(value = "/datalake/companyesg", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<CompanyESGScore>> getCompanies();

    @RequestMapping(value = "/datalake/all-investible-universe-companies", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<CompanyESGScore>> getAllInvestibleCompanies();

    @GetMapping(value = "/datalake/historicalesg/{isin}/{investibleUniverseType}")
    public ResponseEntity<List<CompanyHistoricalData>> getHistoricalData(@PathVariable("isin") String isin, @PathVariable("investibleUniverseType") String investibleUniverseType);

    @GetMapping(value = "/datalake/historicalesgforecast/{isin}/{investibleUniverseType}")
    public ResponseEntity<List<CompanyHistoricalDataForForecast>> getHistoricalDataForForecast(@PathVariable("isin") String isin, @PathVariable("investibleUniverseType") String investibleUniverseType);

    @GetMapping(value = "/datalake/company/{isin}")
    public ResponseEntity<CompanyESGScore> getCompanyData(@PathVariable("isin") String isin);
    
    @RequestMapping(value = "/datalake/companyEsgByName", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<List<CompanyESGScore>> getCompanyESGScoreByCompanyName(@RequestBody List<String> companyNames);

    @GetMapping(value = "/datalake/company-card-details/{isin}")
    public ResponseEntity<CompanyCardAndESGDetailsDatalakeDto> getCompanyCardDetails(@PathVariable("isin") String isin);

    @GetMapping(value = "/datalake/carbonemissionhistoricalscore/{isin}/{investibleUniverseType}")
	public ResponseEntity<CarbonEmissionHistoricalGraph> getCarbonEmissionHistData(@PathVariable("isin") String isin, @PathVariable("investibleUniverseType") String investibleUniverseType);

    @RequestMapping(value = "/datalake/company-peer-average/{isin}", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<CompanyPeerAverageScore> > getCompanyPeerAverageScore(@PathVariable("isin") String isin);
    
    @RequestMapping(value = "datalake/historicalEsgDaily", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<List<CompanyDailyESGAndSocialPositionigData>> getCompanyDailyESGScore(@RequestBody DailyESGScoreRequest dailyESGScoreRequest);

    @GetMapping(value = "/datalake/fund-companies/{fundIsin}", produces = {"application/json"})
    public ResponseEntity<List<FundCompanyDTO>> getFundCompanies(@PathVariable("fundIsin") String fundIsin);
}
