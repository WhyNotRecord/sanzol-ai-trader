
package binance.futures.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopLongShortAccountRatio {
    private String symbol;
    private BigDecimal longShortRatio;
    private BigDecimal longAccount;
    private BigDecimal shortAccount;
    private Long timestamp;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getLongShortRatio() {
        return longShortRatio;
    }

    public void setLongShortRatio(BigDecimal longShortRatio) {
        this.longShortRatio = longShortRatio;
    }

    public BigDecimal getLongAccount() {
        return longAccount;
    }

    public void setLongAccount(BigDecimal longAccount) {
        this.longAccount = longAccount;
    }

    public BigDecimal getShortAccount() {
        return shortAccount;
    }

    public void setShortAccount(BigDecimal shortAccount) {
        this.shortAccount = shortAccount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TopLongShortAccountRatio [symbol=" + symbol + ", longShortRatio=" + longShortRatio 
               + ", longAccount=" + longAccount + ", shortAccount=" + shortAccount 
               + ", timestamp=" + timestamp + "]";
    }
}
