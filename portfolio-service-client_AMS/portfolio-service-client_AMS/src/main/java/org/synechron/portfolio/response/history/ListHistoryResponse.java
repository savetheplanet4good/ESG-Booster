package org.synechron.portfolio.response.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListHistoryResponse extends HistoryResponse{

    private List<ListHistory> esgHistory;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListHistory {
        private Double esgCombinedScore;
        private Double govScore;
        private Double socialScore;
        private Double envScore;
        private Double esgScore;
        private String year;
    }

    public static class ListHistoryBuilder{
        private Double esgCombinedScore;
        private Double govScore;
        private Double socialScore;
        private Double envScore;
        private Double esgScore;
        private String year;

        public ListHistoryBuilder withESGCombinedScore(Double esgCombinedScore){
            this.esgCombinedScore =esgCombinedScore;
            return this;
        }

        public ListHistoryBuilder withGovScore(Double govScore){
            this.govScore =govScore;
            return this;
        }

        public ListHistoryBuilder withSocialScore(Double socialScore){
            this.socialScore =socialScore;
            return this;
        }

        public ListHistoryBuilder withEnvScore(Double envScore){
            this.envScore =envScore;
            return this;
        }

        public ListHistoryBuilder withESGScore(Double esgScore){
            this.esgScore =esgScore;
            return this;
        }

        public ListHistoryBuilder withYear(String year){
            this.year = year;
            return this;
        }

        public ListHistory build(){
            return new ListHistory(esgCombinedScore,govScore,socialScore,envScore,esgScore,year);
        }

    }
}
