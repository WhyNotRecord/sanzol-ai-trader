package binance.futures.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Candle
{
	private long openTime;
	private float openPrice;
	private float highPrice;
	private float lowPrice;
	private float closePrice;
	private BigDecimal volume;
	private BigDecimal quoteVolume;
	private Long count;

	public Candle()
	{
		//
	}

	public Candle(long openTime, float openPrice, float highPrice, float lowPrice, float closePrice,
								BigDecimal volume, BigDecimal quoteVolume, Long count)
	{
		this.openTime = openTime;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.quoteVolume = quoteVolume;
		this.count = count;
	}

	public long getOpenTime()
	{
		return openTime;
	}

	public void setOpenTime(long openTime)
	{
		this.openTime = openTime;
	}

	public float getOpenPrice()
	{
		return openPrice;
	}

	public void setOpenPrice(float openPrice)
	{
		this.openPrice = openPrice;
	}

	public float getHighPrice()
	{
		return highPrice;
	}

	public void setHighPrice(float highPrice)
	{
		this.highPrice = highPrice;
	}

	public float getLowPrice()
	{
		return lowPrice;
	}

	public void setLowPrice(float lowPrice)
	{
		this.lowPrice = lowPrice;
	}

	public float getClosePrice()
	{
		return closePrice;
	}

	public void setClosePrice(float closePrice)
	{
		this.closePrice = closePrice;
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

	public Long getCount()
	{
		return count;
	}

	public void setCount(Long count)
	{
		this.count = count;
	}

	// ---- CALCULATED FIELDS -------------------------------------------------

	public ZonedDateTime getOpenTimeZoned()
	{
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(openTime), ZoneOffset.UTC);
	}

	// ---- TO STRING ---------------------------------------------------------
	
	@Override
	/*public String toString()
	{
		return "Candle [openPrice=" + openPrice + ", highPrice=" + highPrice + ", lowPrice=" + lowPrice + ", closePrice=" + closePrice + ", volume=" + volume + ", quoteVolume=" + quoteVolume + ", count=" + count + "]";
	}*/
	public String toString() {
		return String.format("Time: %s. Low: %s. High: %s. Open: %s. Close: %s. Volume: %s. Quoted vol: %s",
				new Date(openTime), lowPrice, highPrice, openPrice, closePrice, volume, quoteVolume);
	}

}
