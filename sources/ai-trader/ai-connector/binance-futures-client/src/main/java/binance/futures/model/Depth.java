package binance.futures.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Depth
{
	private Long lastUpdateId;
	private List<DepthCell> asks = new ArrayList<DepthCell>();
	private List<DepthCell> bids = new ArrayList<DepthCell>();

	public Long getLastUpdateId()
	{
		return lastUpdateId;
	}

	public void setLastUpdateId(Long lastUpdateId)
	{
		this.lastUpdateId = lastUpdateId;
	}

	public List<DepthCell> getAsks()
	{
		return asks;
	}

	public void setAsks(List<List<BigDecimal>> asks)
	{
		this.asks.clear();
		for (List<BigDecimal> cellList : asks) {
			if (cellList.size() < 2)
				continue;
			this.asks.add(new DepthCell(cellList));
		}
	}

	public List<DepthCell> getBids()
	{
		return bids;
	}

	public void setBids(List<List<BigDecimal>> bids)
	{
		this.bids.clear();
		for (List<BigDecimal> cellList : bids) {
			if (cellList.size() < 2)
				continue;
			this.bids.add(new DepthCell(cellList));
		}
	}

	public static class DepthCell {
		float value;
		BigDecimal amount;

		public DepthCell(List<BigDecimal> lst) {
			value = lst.get(0).floatValue();
			amount = lst.get(1);
		}

		public float getValue() {
			return value;
		}

		public BigDecimal getAmount() {
			return amount;
		}
	}
}
