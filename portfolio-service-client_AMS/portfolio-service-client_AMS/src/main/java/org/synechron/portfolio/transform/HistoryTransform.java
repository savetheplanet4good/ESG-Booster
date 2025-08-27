package org.synechron.portfolio.transform;

import org.synechron.portfolio.enums.PortfolioTypeEnum;
import org.synechron.portfolio.enums.ResponseTypeENUM;
import org.synechron.esg.model.PortfolioHistoryResponse;
import org.synechron.portfolio.response.history.HistoryResponse;
import org.synechron.portfolio.response.history.LineChartHistoryResponse;
import org.synechron.portfolio.response.history.ListHistoryResponse;
import org.synechron.portfolio.utils.PortfolioUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class HistoryTransform {

    private HistoryTransform() {
        throw new IllegalStateException("HistoryTransform class");
    }

    public static HistoryResponse transform(List<PortfolioHistoryResponse> portfolios, String type, String portfoliosIsinsType) {
        HistoryResponse response;

        switch (ResponseTypeENUM.valueOf(type)) {
            case LIST:
                response = transformList(portfolios);
                break;
            case LINECHART:
                response = transformLineChart(portfolios, portfoliosIsinsType);
                break;
            default:
                response = transformList(portfolios);

        }

        return response;
    }

    private static HistoryResponse transformList(List<PortfolioHistoryResponse> portfolioHistoryResp) {
        List<ListHistoryResponse.ListHistory> listHistories = new ArrayList<>();
        for (PortfolioHistoryResponse portfolioHistory :portfolioHistoryResp){
            listHistories.add(new ListHistoryResponse.ListHistoryBuilder()
                    .withEnvScore(PortfolioUtils.formatDouble(portfolioHistory.getEnvScore()))
                    .withSocialScore(PortfolioUtils.formatDouble(portfolioHistory.getSocialScore()))
                    .withGovScore(PortfolioUtils.formatDouble(portfolioHistory.getGovScore()))
                    .withESGScore(PortfolioUtils.formatDouble(portfolioHistory.getEsgScore()))
                    .withESGCombinedScore(PortfolioUtils.formatDouble(portfolioHistory.getEsgCombinedScore()))
                    .withYear(portfolioHistory.getYear())
                    .build());

        }

        return new ListHistoryResponse(listHistories);
    }

    private static HistoryResponse transformLineChart(List<PortfolioHistoryResponse> portfoliosHistory, String portfoliosIsinsType) {
        List<Double> esgScore = portfoliosHistory.stream().map(portfolioHistoryObj -> PortfolioUtils.formatDouble(portfolioHistoryObj.getEsgScore())).collect(Collectors.toList());
        List<Double> envScore = portfoliosHistory.stream().map(portfolioHistoryObj -> PortfolioUtils.formatDouble(portfolioHistoryObj.getEnvScore())).collect(Collectors.toList());
        List<Double> socialScore = portfoliosHistory.stream().map(portfolioHistoryObj-> PortfolioUtils.formatDouble(portfolioHistoryObj.getSocialScore())).collect(Collectors.toList());
        List<Double> govScore = portfoliosHistory.stream().map(portfolioHistoryObj -> PortfolioUtils.formatDouble(portfolioHistoryObj.getGovScore())).collect(Collectors.toList());
        List<Double> esgCombinedScore = portfoliosHistory.stream().map(portfolioHistoryObj -> PortfolioUtils.formatDouble(portfolioHistoryObj.getEsgCombinedScore())).collect(Collectors.toList());
        List<String> year = portfoliosHistory.stream().map(PortfolioHistoryResponse::getYear).collect(Collectors.toList());
        List<String> esgMsciScore = portfoliosHistory.stream().map(PortfolioHistoryResponse::getEsgMsciScore).collect(Collectors.toList());

        if(PortfolioTypeEnum.FUND.getValue().equalsIgnoreCase(portfoliosIsinsType)){
            return new LineChartHistoryResponse.LineChartHistoryResponseBuilder()
                    .withESGScore(esgScore)
                    .withEnvScore(null)
                    .withSocialScore(null)
                    .withGovScore(null)
                    .withESGCombinedScore(null)
                    .withYear(year)
                    .withEsgMsciScore(esgMsciScore)
                    .build();
        }else {
            return new LineChartHistoryResponse.LineChartHistoryResponseBuilder()
                    .withESGScore(esgScore)
                    .withEnvScore(envScore)
                    .withSocialScore(socialScore)
                    .withGovScore(govScore)
                    .withESGCombinedScore(esgCombinedScore)
                    .withYear(year)
                    .withEsgMsciScore(null)
                    .build();
        }
    }
}
