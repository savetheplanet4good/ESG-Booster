package org.synechron.portfolio.response.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineChartHistoryResponse extends HistoryResponse {

    private List<String> year;
    private List<Double> esgScore;
    private List<Double> envScore;
    private List<Double> socialScore;
    private List<Double> govScore;
    private List<Double> esgCombinedScore;
    private List<String> esgMsciScore;

    public static class LineChartHistoryResponseBuilder{
        private List<String> year;
        private List<Double> esgScore;
        private List<Double> envScore;
        private List<Double> socialScore;
        private List<Double> govScore;
        private List<Double> esgCombinedScore;
        private List<String> esgMsciScore;

        public LineChartHistoryResponseBuilder withYear(List<String> year){
            this.year=year;
            return this;
        }

        public LineChartHistoryResponseBuilder withESGScore(List<Double> esgScore){
            this.esgScore=esgScore;
            return this;
        }

        public LineChartHistoryResponseBuilder withEnvScore(List<Double> envScore){
            this.envScore=envScore;
            return this;
        }
        public LineChartHistoryResponseBuilder withSocialScore(List<Double> socialScore){
            this.socialScore=socialScore;
            return this;
        }
        public LineChartHistoryResponseBuilder withGovScore(List<Double> govScore){
            this.govScore=govScore;
            return this;
        }
        public LineChartHistoryResponseBuilder withESGCombinedScore(List<Double> esgCombinedScore){
            this.esgCombinedScore=esgCombinedScore;
            return this;
        }
        public LineChartHistoryResponseBuilder withEsgMsciScore(List<String> esgMsciScore){
            this.esgMsciScore=esgMsciScore;
            return this;
        }
        public LineChartHistoryResponse build(){
            return new LineChartHistoryResponse(year,esgScore,envScore,socialScore,govScore,esgCombinedScore,esgMsciScore);
        }

    }

}
