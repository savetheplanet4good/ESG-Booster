package org.synechron.esg.alternative.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.synechron.esg.alternative.constant.Constant;
import org.synechron.esg.alternative.enums.ESGSourceEnum;
import org.synechron.esg.alternative.enums.ESGTargetType;
import org.synechron.esg.alternative.error.InvalidResponseException;
import org.synechron.esg.model.AlternativesCompany;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.CompanyESGScore;
import org.synechron.esg.model.Portfolio;
import org.synechron.esg.alternative.request.AlternativesInvestSuitRequest;
import org.synechron.esg.alternative.response.*;
import org.synechron.esg.alternative.service.Alternative;
import org.synechron.esg.alternative.service.AlternativeRequestTransformService;
import org.synechron.esg.alternative.service.InvestSuitRequestTransformFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Invest suit service.
 */
@Service("investSuitService")
public class InvestSuitService implements Alternative {

    private static final Logger log = LoggerFactory.getLogger(InvestSuitService.class);

    @Value("${alternative.url}")
    private String alternativeUrl;

    @Value("${alternative.investible-universe.exempt}")
    private String exemptISIN;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private DataLakeServiceProxy dataLakeProxy;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InvestSuitRequestTransformFactory investSuitRequestTransformFactoryImpl;

    @Autowired
    private CalculationProxy calculationProxy;

    @Override
    public AlternativeResponse getAlternatives(List<String> isin, String portfolioId, String esgTargetType) throws Exception, InvalidResponseException {


        IMap<String, Portfolio> dataStorePortfolio = hazelcastInstance.getMap("portfolios");
        //getting specific portfolio
        Portfolio portfolio = dataStorePortfolio.get(portfolioId);
        //getting TransformImpl on the basis of InvestbaleUniverse Type
        AlternativeRequestTransformService service = investSuitRequestTransformFactoryImpl.
                getAlternativeTransformServiceImpl(portfolio.getInvestableUniverseType());
        AlternativeResponse response = null;
        //getting total weitage without FUND
        Double weitageWithoutFund = portfolio.getCompanies().stream().filter(company -> !Constant.FUND.equalsIgnoreCase(company.getIsinType())).mapToDouble(company -> company.getWt()).sum();
        InvestSuitResponse resp = new InvestSuitResponse();
        try {
            //getting invest Suit Response
            resp = getInvestSuitResponse(portfolioId, isin, esgTargetType);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        if (resp != null && resp.getRecommended_trades() != null && !resp.getRecommended_trades().isEmpty()) {
            Map<String, Double> recomendedMap = resp.getRecommended_trades();
            List<String> responseISIN = new ArrayList<>();
            int alternativeId = 1;
            // taking only positive values
            for (Map.Entry<String, Double> entry : recomendedMap.entrySet()) {
                if (entry.getValue() > 0.00)
                    responseISIN.add(entry.getKey());
            }
            //Getting ESG Data For Alternatives
            ResponseEntity<List<CompanyESGScore>> dataLakeResponse = dataLakeProxy.getCompaniesESGScore(responseISIN);
            //Populating Alternatives
            List<Alternatives> alternatives = convertToAlternatives(resp, portfolio.getInvestableUniverseType(), dataLakeResponse.getBody(), weitageWithoutFund);
            response = new AlternativeResponse(alternatives, convertToHistoricalPerformance(resp.getHistorical_performance()), new PortfolioSimulationResponse(), HttpStatus.OK, 200);

            if (alternatives != null && !alternatives.isEmpty()) {
                List<Company> portfolioCompanies = portfolio.getCompanies();
                // removing companies for which Alternatives are Calculated, to populated
                // Simulation portfolio
                for (String isinNumber : isin) {
                    portfolioCompanies.removeIf(company -> company.getIsin().equals(isinNumber));
                }
                // Adding Alternatives to Company list , to populate
                // Simulation portfolio
                Map<String, Double> companies = resp.getRecommended_trades();
                for (Alternatives alt : response.getAlternatives()) {
                    portfolioCompanies.add(new Company(alt.getIsin(), alt.getName(), alt.getHoldingValue()));
                }

                // calling calculation component for calculating all the values
                // of Simulation portfolio
                ResponseEntity<Portfolio> calculationresponse = calculationProxy.calculate(portfolio);
                log.debug("CalculationResponse :{} ", calculationresponse);
                if (calculationresponse.getStatusCode().equals(HttpStatus.OK)) {
                    Portfolio calculatedPortfolio = (Portfolio) calculationresponse.getBody();
                    response.setAlternatives(alternatives);
                    response.setSimulatedPortfolio(service.getPortfolioSimulationResponse(calculatedPortfolio));
                } else {
                    log.error("Error {} :", calculationresponse.getStatusCode());
                    log.error("Error {} :", calculationresponse.getBody());
                }
            }
        } else {
            response = new AlternativeResponse(null, null, null, HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
        return response;
    }


    private InvestSuitResponse getInvestSuitResponse(String portfolioId, List<String> isin, String esgTargetType) throws InvalidResponseException {


        IMap<String, Portfolio> dataStore =
                hazelcastInstance.getMap("portfolios");
        // getting specific portfolio
        Portfolio portfolio = dataStore.get(portfolioId);
        // Transforming REFENITIV or YahooFinance specific data
        AlternativeRequestTransformService service = investSuitRequestTransformFactoryImpl.getAlternativeTransformServiceImpl(portfolio.getInvestableUniverseType());
        AlternativesInvestSuitRequest requestAlt = service.getAlternativeRequest(portfolio.getCompanies());

        /** start populating investible universe */
        ResponseEntity<List<CompanyESGScore>> dataLakeResponse = dataLakeProxy.getAllInvestibleCompanies();
        List<AlternativesCompany> inv = this.getInvestibleUniverse(portfolioId, hazelcastInstance, dataLakeResponse.getBody()).stream().filter(a -> !a.getIsinType().equalsIgnoreCase(Constant.FUND)).collect(Collectors.toList());
        inv = service.getInvestibleUniverse(inv, portfolio.getInvestableUniverseFilter());
        List<String> investibleUniverse = inv.stream().map(AlternativesCompany::getIsin).collect(Collectors.toList());

        for (Company company : portfolio.getCompanies()) {
            investibleUniverse.remove(company.getIsin());
        }

        String[] isinExempt = exemptISIN.split("\\|");

        for(String s : isinExempt){
            investibleUniverse.remove(s);
        }

        /** end of populating investible universe*/

        /** start populating investsuit request object*/
        List<String> invest = new ArrayList<>(investibleUniverse);
        requestAlt.setInstr_to_replace(isin);
        requestAlt.setInvestable_universe(invest);
        requestAlt.setEsg_target(ESGTargetType.valueOf(esgTargetType).getTargetValue());
        requestAlt.setEsg_source(ESGSourceEnum.valueOf(portfolio.getInvestableUniverseType()).getValue());
        log.debug("Invest Suit Request {}", requestAlt);
        /** end populating investsuit request object*/

        InvestSuitResponse resp = new InvestSuitResponse();
        String response = null;
        try {
            final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            final HttpClient httpClient = HttpClientBuilder.create()
                    .setRedirectStrategy(new LaxRedirectStrategy())
                    .build();
            factory.setHttpClient(httpClient);
            restTemplate.setRequestFactory(factory);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // calling invest suit api
            HttpEntity<AlternativesInvestSuitRequest> entity = new HttpEntity<AlternativesInvestSuitRequest>(requestAlt, headers);
            /** transforming response to object */
            response = restTemplate.postForObject(alternativeUrl, entity, String.class);
            response = StringEscapeUtils.unescapeHtml(response);
            response = response.replaceAll("\\\\", "");
            response = response.replace("\"{\"", "{\"");
            response = response.replace("}}\"", "}}");
            //response = response.replaceAll("\\","");
            resp = objectMapper.readValue(response, InvestSuitResponse.class);
            /** end of transforming object*/

            log.debug("Invest Suit Response : {}", response);
        } catch (JsonParseException jsonParseException) {
            log.error("Invalid JSON Format Received");

            if (response.contains("'status_code': 500")) {
                // asynchroinusly store the request and response to the DB through Cache
                throw new InvalidResponseException(HttpStatus.BAD_REQUEST, "", resp, requestAlt);
                //storeInvestSuiteRequestAndResponse(new FailedInvestSuiteRequests(requestAlt.hashCode(),requestAlt,response,new Date()));
            }

        } catch (Exception ex) {
            log.error("Error : {}", ex.getStackTrace());
        }
        if (response.contains("'status_code': 500")) {
            // asynchroinusly store the request and response to the DB through Cache
            throw new InvalidResponseException(HttpStatus.BAD_REQUEST, "", resp, requestAlt);
            //storeInvestSuiteRequestAndResponse(new FailedInvestSuiteRequests(requestAlt.hashCode(),requestAlt,response,new Date()));
        }
        return resp;
    }
}
