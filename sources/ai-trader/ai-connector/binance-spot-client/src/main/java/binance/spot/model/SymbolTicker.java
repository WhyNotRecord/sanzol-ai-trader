package binance.spot.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolTicker
{
	private String symbol;
	private BigDecimal priceChange;
	private BigDecimal priceChangePercent;
	private BigDecimal weightedAvgPrice;
	private BigDecimal lastPrice;
	private BigDecimal lastQty;
	private BigDecimal openPrice;
	private BigDecimal highPrice;
	private BigDecimal lowPrice;
	private BigDecimal volume;
	private BigDecimal quoteVolume;
	private Long openTime;
	private Long closeTime;
	private Long firstId;
	private Long lastId;
	private Long count;

	public String getSymbol()
	{
		return symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}

	public BigDecimal getPriceChange()
	{
		return priceChange;
	}

	public void setPriceChange(BigDecimal priceChange)
	{
		this.priceChange = priceChange;
	}

	public BigDecimal getPriceChangePercent()
	{
		return priceChangePercent;
	}

	public void setPriceChangePercent(BigDecimal priceChangePercent)
	{
		this.priceChangePercent = priceChangePercent;
	}

	public BigDecimal getWeightedAvgPrice()
	{
		return weightedAvgPrice;
	}

	public void setWeightedAvgPrice(BigDecimal weightedAvgPrice)
	{
		this.weightedAvgPrice = weightedAvgPrice;
	}

	public BigDecimal getLastPrice()
	{
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice)
	{
		this.lastPrice = lastPrice;
	}

	public BigDecimal getLastQty()
	{
		return lastQty;
	}

	public void setLastQty(BigDecimal lastQty)
	{
		this.lastQty = lastQty;
	}

	public BigDecimal getOpenPrice()
	{
		return openPrice;
	}

	public void setOpenPrice(BigDecimal openPrice)
	{
		this.openPrice = openPrice;
	}

	public BigDecimal getHighPrice()
	{
		return highPrice;
	}

	public void setHighPrice(BigDecimal highPrice)
	{
		this.highPrice = highPrice;
	}

	public BigDecimal getLowPrice()
	{
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice)
	{
		this.lowPrice = lowPrice;
	}

	public BigDecimal getVolume()
	{
		return volume;
	}

	public void setVolume(BigDecimal volume)
	{
		this.volume = volume;
	}

	public BigDecimal getQuoteVolume()
	{
		return quoteVolume;
	}

	public void setQuoteVolume(BigDecimal quoteVolume)
	{
		this.quoteVolume = quoteVolume;
	}

	public Long getOpenTime()
	{
		return openTime;
	}

	public void setOpenTime(Long openTime)
	{
		this.openTime = openTime;
	}

	public Long getCloseTime()
	{
		return closeTime;
	}

	public void setCloseTime(Long closeTime)
	{
		this.closeTime = closeTime;
	}

	public Long getFirstId()
	{
		return firstId;
	}

	public void setFirstId(Long firstId)
	{
		this.firstId = firstId;
	}

	public Long getLastId()
	{
		return lastId;
	}

	public void setLastId(Long lastId)
	{
		this.lastId = lastId;
	}

	public Long getCount()
	{
		return count;
	}

	public void setCount(Long count)
	{
		this.count = count;
	}

	@Override
	public String toString()
	{
		return "SymbolTicker [symbol=" + symbol + ", priceChange=" + priceChange + ", priceChangePercent="
				+ priceChangePercent + ", weightedAvgPrice=" + weightedAvgPrice + ", lastPrice=" + lastPrice
				+ ", lastQty=" + lastQty + ", openPrice=" + openPrice + ", highPrice=" + highPrice + ", lowPrice="
				+ lowPrice + ", volume=" + volume + ", quoteVolume=" + quoteVolume + ", openTime=" + openTime
				+ ", closeTime=" + closeTime + ", firstId=" + firstId + ", lastId=" + lastId + ", count=" + count + "]";
	}
	
}
