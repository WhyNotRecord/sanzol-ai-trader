
package binance.futures.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenInterest {
    private String symbol;
    private BigDecimal openInterest;
    private Long time;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(BigDecimal openInterest) {
        this.openInterest = openInterest;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
