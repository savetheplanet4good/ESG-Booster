package org.synechron.portfolio.service;

import org.synechron.esg.model.AlternativesCompany;
import org.synechron.esg.model.AlternativesObject;
import org.synechron.esg.model.AlternativeResponse;
import java.util.List;

public interface AlternativesService {

    public AlternativeResponse getAlternatives(String isin, String portfolioId, String esgTarget);

    public List<AlternativesCompany> getAllCompaniesForAlternatives(String portfolioId);

    public AlternativeResponse getAlternative(List<String> isin, String portfolioId, String esgTarget) throws Exception;

    public void removeFromCache(String portfolioId);

    public void cacheAlternatives(String portfolioId) throws Exception;

	public AlternativeResponse getAlternativeFromCache(List<String> isin, String portfolioId, String esgTargetType);

	public List<AlternativesObject> retrieveCachedAlternatives();

    public void evictCache();

    public void loadCachedResponses() throws Exception;
}
