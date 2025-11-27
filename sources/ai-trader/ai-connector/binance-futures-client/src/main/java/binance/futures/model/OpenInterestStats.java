package binance.futures.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenInterestStats {
    private String symbol;
    private BigDecimal sumOpenInterest;
    private BigDecimal sumOpenInterestValue;
    private Long timestamp;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getSumOpenInterest() {
        return sumOpenInterest;
    }

    public void setSumOpenInterest(BigDecimal sumOpenInterest) {
        this.sumOpenInterest = sumOpenInterest;
    }

    public BigDecimal getSumOpenInterestValue() {
        return sumOpenInterestValue;
    }

    public void setSumOpenInterestValue(BigDecimal sumOpenInterestValue) {
        this.sumOpenInterestValue = sumOpenInterestValue;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
