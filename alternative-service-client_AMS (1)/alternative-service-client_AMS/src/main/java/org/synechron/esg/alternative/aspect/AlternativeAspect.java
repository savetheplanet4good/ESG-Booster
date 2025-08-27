package org.synechron.esg.alternative.aspect;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.synechron.esg.alternative.dto.AlternativesObject;
import org.synechron.esg.alternative.dto.cachekey.AlternativeCacheKey;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.Portfolio;
import org.synechron.esg.alternative.response.AlternativeResponse;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Alternative aspect.
 */
@Aspect
@Component
public class AlternativeAspect {

    private static final Logger LOGGER = LogManager.getLogger(AlternativeAspect.class);

    @Autowired
    private HazelcastInstance hazelcastInstance;


    /**
     * Gets from cache.
     *
     * @param proceedingJoinPoint the proceeding join point
     * @return the from cache
     * @throws Throwable the throwable
     */
    //@Around("execution(* org.synechron.esg.alternative.service.impl.InvestSuitService.getAlternatives(..)))")
    public AlternativeResponse getFromCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        LOGGER.debug("Check for Alternatives in cache");
        Object[] args = proceedingJoinPoint.getArgs();
        IMap<Integer, AlternativesObject> dataStore = hazelcastInstance.getMap("alternatives");
        IMap<String, Portfolio> dataStorePortfolio = hazelcastInstance.getMap("portfolios");
        Portfolio portfolio = dataStorePortfolio.get(args[1]);
        List<String> isin = (List<String>) args[0];
        isin.stream().sorted();
        Map<String, Double> currentPortfolio = portfolio.getCompanies().stream().collect(
                Collectors.toMap(Company::getName, Company::getHoldingValue));
        AlternativeCacheKey cacheKey = new AlternativeCacheKey(isin,
                portfolio.getInvestableUniverseFilter(),
                portfolio.getInvestableUniverseType(),currentPortfolio,(String) args[2]);
        AlternativesObject response = dataStore.get(cacheKey.hashCode());
        if (response != null && response.getAlternativeResponse() != null) {
            return response.getAlternativeResponse();
        } else {
            AlternativeResponse result = (AlternativeResponse) proceedingJoinPoint.proceed();
            //if(result.getAlternatives() != null || !result.getAlternatives().isEmpty())
            //    dataStore.put(cacheKey.hashCode(), new AlternativesObject(result,cacheKey,cacheKey.hashCode(),new Date()));
            return result;
        }
    }



}
