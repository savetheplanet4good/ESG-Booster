package org.synechron.portfolio.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.AlternativesCompany;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.enums.PortfolioTypeEnum;
import org.synechron.portfolio.response.dto.ESGScore;
import org.synechron.portfolio.response.dto.InvestibleUniverseResponse;
import org.synechron.portfolio.response.insights.*;
import org.synechron.portfolio.service.CalculationService;
import org.synechron.portfolio.service.InsightsService;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Service
public class InsightsServiceImpl implements InsightsService {

	private static final Logger log = LoggerFactory.getLogger(InsightsServiceImpl.class);

	@Autowired
	HazelcastInstance hazelcastInstance;

	@Autowired
	private MasterUniverseServiceProxy masterUniverseServiceProxy;

	@Autowired
	private CalculationService calculationService;

	@Autowired
	private ApplicationComponentSettingsService applicationComponentSettingsService;

	@Override
	public SectorDataResponseDto getSectorsAndCount(String portfolioId) {
		List<String> portfolioSectors = new ArrayList<>();
		List<String> investableUniverseSectors = new ArrayList<>();

		// Get portfolio companies sector list
		IMap<String, Portfolio> dataStorePortfolio = hazelcastInstance.getMap("portfolios");
		Portfolio portfolio = dataStorePortfolio.get(portfolioId);
		portfolioSectors = portfolio.getCompanies().stream().map(Company::getSector).distinct()
				.collect(Collectors.toList());
		log.info("Portfolio sectors : " + portfolioSectors);

		// Get investable universe companies sector list
		ResponseEntity<InvestibleUniverseResponse> investibleUniverseResponseResponseEntity = masterUniverseServiceProxy
				.getInvestibleUniverse(portfolio);
		if (investibleUniverseResponseResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
			if (investibleUniverseResponseResponseEntity.getBody() == null) {
				log.error(
						"Error occurred while retrieving investable universe data from master-universe service or received null data.");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error occurred while retrieving investable universe data from master-universe service or received null data.");
			}
		}
		investableUniverseSectors = investibleUniverseResponseResponseEntity.getBody().getCompanies().stream()
				.map(AlternativesCompany::getSector).filter(sectorName -> sectorName != null).distinct()
				.collect(Collectors.toList());
		log.info("Investable universe sectors : " + investableUniverseSectors);

		return new SectorDataResponseDto(Constant.SECTORS_INSIGHT_TITLE, String.format(Constant.SECTORS_INSIGHT_MESSAGE,
				portfolioSectors.size(), investableUniverseSectors.size()), portfolioSectors.size(),
				investableUniverseSectors.size());
	}

	@Override
	public OutlierRangeResponseDto getOutlierRange(String portfolioId) throws IOException {

		OutlierRangeResponseDto outlierRangeResponseDto = new OutlierRangeResponseDto();
		Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

		if(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE)) {

			IMap<String, Portfolio> dataStorePortfolio = hazelcastInstance.getMap("portfolios");
			Portfolio portfolio = dataStorePortfolio.get(portfolioId);
			OptionalDouble minOutlierValue = portfolio.getCompanies().stream().mapToDouble(Company::getOutlierScore).min();
			OptionalDouble maxOutlierValue = portfolio.getCompanies().stream().mapToDouble(Company::getOutlierScore).max();
			log.info("Outlier min value : " + minOutlierValue);
			log.info("Outlier max value : " + maxOutlierValue);

			outlierRangeResponseDto =  new OutlierRangeResponseDto(
					Constant.OUTLIERS_INSIGHT_TITLE,
					String.format(Constant.OUTLIERS_INSIGHT_MESSAGE, (int) minOutlierValue.getAsDouble(),
					(int) maxOutlierValue.getAsDouble()),
					(int) minOutlierValue.getAsDouble(), (int) maxOutlierValue.getAsDouble());
		}else{
			outlierRangeResponseDto = null;
		}

		return outlierRangeResponseDto;
	}

	@Override
	public EsgInfluenceCountResponseDto getPositiveNegativeESGInfluenceCount(String portfolioId) {

		IMap<String, Portfolio> dataStorePortfolio = hazelcastInstance.getMap("portfolios");
		Portfolio portfolio = dataStorePortfolio.get(portfolioId);

		int positiveInfluenceCount;
		int negativeInfluenceCount;

		if (Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE.equalsIgnoreCase(portfolio.getInvestableUniverseType())) {
			positiveInfluenceCount = (int) portfolio.getCompanies().stream()
					.filter(c -> c.getInfluenceESGCombinedScore() >= 0).count();
			negativeInfluenceCount = (int) portfolio.getCompanies().stream()
					.filter(c -> c.getInfluenceESGCombinedScore() < 0).count();
		} else if (Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE
				.equalsIgnoreCase(portfolio.getInvestableUniverseType())) {
			positiveInfluenceCount = (int) portfolio.getCompanies().stream()
					.filter(c -> c.getInfluenceESGScoreForSustainalytics() >= 0).count();
			negativeInfluenceCount = (int) portfolio.getCompanies().stream()
					.filter(c -> c.getInfluenceESGScoreForSustainalytics() < 0).count();
		} else {
			log.error("Invalid data source.");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
		}

		log.info("Positive influence count : " + positiveInfluenceCount);
		log.info("Negative influence count : " + negativeInfluenceCount);
		return new EsgInfluenceCountResponseDto(Constant.INFLUENCES_INSIGHT_TITLE,
				String.format(Constant.INFLUENCES_INSIGHT_MESSAGE, negativeInfluenceCount, positiveInfluenceCount),
				positiveInfluenceCount, negativeInfluenceCount);
	}

	@Override
	public NewsSentimentCountResponseDto getPositiveNegativeNewsSentimentsCount(String portfolioId) throws IOException {

		NewsSentimentCountResponseDto newsSentimentCountResponseDto = new NewsSentimentCountResponseDto();
		Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

		if(applicationComponentSettingsMap.get(Constant.IS_NEWS_ENABLED).getValue().equals(Boolean.TRUE)){

			IMap<String, Portfolio> dataStorePortfolio = hazelcastInstance.getMap("portfolios");
			Portfolio portfolio = dataStorePortfolio.get(portfolioId);

			int positiveSentimentsCount = (int) portfolio.getCompanies().stream().filter(c -> c.getNewsSentimentScore() >= 0).count();
			int negativeSentimentsCount = (int) portfolio.getCompanies().stream().filter(c -> c.getNewsSentimentScore() < 0).count();

			log.info("Positive sentiment count : " + positiveSentimentsCount);
			log.info("Negative sentiment count : " + negativeSentimentsCount);
			newsSentimentCountResponseDto = new NewsSentimentCountResponseDto(Constant.NEWS_INSIGHT_TITLE,
					String.format(Constant.NEWS_INSIGHT_MESSAGE, negativeSentimentsCount, positiveSentimentsCount),
					positiveSentimentsCount, negativeSentimentsCount);
		}else{
			newsSentimentCountResponseDto = null;
		}

		return newsSentimentCountResponseDto;
	}

	@Override
	public ESGScorePercentageChangeDto getESGPercentageChange(String portfolioId) throws IOException {

		ESGScorePercentageChangeDto esgScorePercentageChangeDto = new ESGScorePercentageChangeDto();
		Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

		if(applicationComponentSettingsMap.get(Constant.IS_HISTORICAL_PERFORMANCE_ENABLED).getValue().equals(Boolean.TRUE)) {
			IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
			Double percentageChangeESGCombinedScore = calculationService.getPortfolioInsights(dataStore.get(portfolioId));

			if (percentageChangeESGCombinedScore != null) {
				esgScorePercentageChangeDto = new ESGScorePercentageChangeDto(Constant.ESG_PERCENT_CHANGE_TITLE,
						String.format(Constant.ESG_PERCENT_CHANGE_MESSAGE, percentageChangeESGCombinedScore.toString())
								.concat("%."),
						percentageChangeESGCombinedScore);
			} else {
				esgScorePercentageChangeDto = null;
			}
		}else{
			esgScorePercentageChangeDto = null;
		}

		return esgScorePercentageChangeDto;
	}

	@Override
	public SingleComponentEffectDto getsingleComponentChange(String portfolioId) throws IOException {

		SingleComponentEffectDto esgSingleComponentDto = new SingleComponentEffectDto();
		Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

		if(applicationComponentSettingsMap.get(Constant.IS_HISTORICAL_PERFORMANCE_ENABLED).getValue().equals(Boolean.TRUE)) {

			IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
			ESGScore singleComponentChangeInsight = calculationService.getSingleComponentInsights(dataStore.get(portfolioId));

			Company company = dataStore.get(portfolioId).getCompanies().stream().filter(c -> c.getIsin().equals(singleComponentChangeInsight.getIsin())).collect(Collectors.toList()).get(0);

			//Get Company ESG scores
			Portfolio portfolio = dataStore.get(portfolioId);
			ESGScoreAndPillars esgScoreAndPillarsScores = new ESGScoreAndPillars();
			if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
				esgScoreAndPillarsScores.setEsgCombinedScore(PortfolioUtilities.normaliseDecimals(company.getEsgCombinedScore()));
				esgScoreAndPillarsScores.setEnvScore(PortfolioUtilities.normaliseDecimals(company.getEnvironmentalScore()));
				esgScoreAndPillarsScores.setSocialScore(PortfolioUtilities.normaliseDecimals(company.getSocialScore()));
				esgScoreAndPillarsScores.setGovScore(PortfolioUtilities.normaliseDecimals(company.getGovernenceScore()));
			} else if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE)) {
				esgScoreAndPillarsScores.setEsgCombinedScore(PortfolioUtilities.normaliseDecimals(company.getSustainalyticsTotalESGScore()));
				esgScoreAndPillarsScores.setEnvScore(PortfolioUtilities.normaliseDecimals(company.getSustainlyticsEnvironmentalScore()));
				esgScoreAndPillarsScores.setSocialScore(PortfolioUtilities.normaliseDecimals(company.getSustainlyticsSocialScore()));
				esgScoreAndPillarsScores.setGovScore(PortfolioUtilities.normaliseDecimals(company.getSustainlyticsGovernenceScore()));
			} else {
				log.error("Invalid data source.");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
			}

			//Get Company ESG percent changes
			ESGScoreAndPillars esgScoreAndPillarsPercentChange = new ESGScoreAndPillars(
					PortfolioUtilities.normaliseDecimals(singleComponentChangeInsight.getEsgCombinedScore().doubleValue()),
					PortfolioUtilities.normaliseDecimals(singleComponentChangeInsight.getEnvScore()),
					PortfolioUtilities.normaliseDecimals(singleComponentChangeInsight.getSocialScore()),
					PortfolioUtilities.normaliseDecimals(singleComponentChangeInsight.getGovScore())
			);

			esgSingleComponentDto = new SingleComponentEffectDto(

					Constant.ESG_SINGLE_COMPONENT_TITLE,
					String.format(Constant.ESG_SINGLE_COMPONENT_MESSAGE, singleComponentChangeInsight.getCompanyName(),
							PortfolioUtilities.normaliseDecimals(singleComponentChangeInsight.getEsgCombinedScore()).toString().concat("%"),
							PortfolioUtilities.normaliseDecimals(singleComponentChangeInsight.getEnvScore()).toString().concat("%"),
							PortfolioUtilities.normaliseDecimals(singleComponentChangeInsight.getSocialScore()).toString().concat("%"),
							PortfolioUtilities.normaliseDecimals(singleComponentChangeInsight.getGovScore()).toString().concat("%")),
					singleComponentChangeInsight.getCompanyName(),
					esgScoreAndPillarsScores,
					esgScoreAndPillarsPercentChange)

			;
		}else{
			esgSingleComponentDto = null;
		}

		return esgSingleComponentDto;
	}

	@Override
	public List<AllPortfolioInsightsResponseDto> getAllPortfolioInsights() throws IOException {

		List<AllPortfolioInsightsResponseDto> allPortfolioInsightsResponseDtos = new ArrayList<>();

		IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
		dataStore.loadAll(Boolean.TRUE);

		// Check if cache still empty
		if (!dataStore.values().isEmpty()) {
			List<Portfolio> onlyEquityPortfolios = dataStore.values().stream().filter(portfolio -> PortfolioTypeEnum.EQUITY.getValue().equalsIgnoreCase(portfolio.getPortfolioIsinsType())).collect(Collectors.toList());
			for (Portfolio portfolio : onlyEquityPortfolios) {
				// Percentage change in ESG score
				ESGScorePercentageChangeDto percentageChangeInsight = getESGPercentageChange(portfolio.getPortfolioId());

				// Single component change in ESG score
				SingleComponentEffectDto singleComponentInsight = getsingleComponentChange(portfolio.getPortfolioId());

				// Sectors
				SectorDataResponseDto sectorDataResponseDto = getSectorsAndCount(portfolio.getPortfolioId());

				// News
				NewsSentimentCountResponseDto newsSentimentCountResponseDto = getPositiveNegativeNewsSentimentsCount(portfolio.getPortfolioId());

				// Influence
				EsgInfluenceCountResponseDto esgInfluenceCountResponseDto = getPositiveNegativeESGInfluenceCount(portfolio.getPortfolioId());

				// Outlier
				OutlierRangeResponseDto outlierRangeResponseDto = getOutlierRange(portfolio.getPortfolioId());

				PortfolioInsightsResponseDto portfolioInsightsResponseDto = new PortfolioInsightsResponseDto(
						percentageChangeInsight, singleComponentInsight, sectorDataResponseDto,
						newsSentimentCountResponseDto, esgInfluenceCountResponseDto, outlierRangeResponseDto);
				allPortfolioInsightsResponseDtos.add(new AllPortfolioInsightsResponseDto(portfolio.getPortfolioId(), portfolioInsightsResponseDto));
			}
		} else {
			allPortfolioInsightsResponseDtos = null;
		}

		return allPortfolioInsightsResponseDtos;
	}
}
