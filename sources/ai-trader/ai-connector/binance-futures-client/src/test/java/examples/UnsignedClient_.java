package examples;

import java.util.Date;
import java.util.List;

import binance.futures.enums.IntervalType;
import binance.futures.impl.UnsignedClient;
import binance.futures.model.Candle;
import binance.futures.model.Depth;
import binance.futures.model.FundingRate;
import binance.futures.model.PremiumIndex;
import binance.futures.model.SymbolTicker;

public class UnsignedClient_
{

	public static void main(String[] args) throws Exception
	{
		List<SymbolTicker> lstSymbolTickers = UnsignedClient.getSymbolTickers();
		lstSymbolTickers.forEach(s -> System.out.println(s));		

		List<Candle> lstCandles = UnsignedClient.getKlines("BTCUSDT", IntervalType._1h, 5, null);
		lstCandles.forEach(s -> System.out.println(s));		

		Depth depth = UnsignedClient.getDepth("BTCUSDT", 5);
		System.out.println(depth.getLastUpdateId());

		List<FundingRate> lstFundingRates = UnsignedClient.getFundingRate("CTSIUSDT", 5);
		lstFundingRates.forEach(s -> System.out.println(s));		

		List<PremiumIndex> lstPremiumIndexes = UnsignedClient.getPremiumIndex(null);
		lstPremiumIndexes.forEach(s -> System.out.println(s));

		Long serverTime = UnsignedClient.getServerTime();
		System.out.println(serverTime);
		System.out.println(new Date(serverTime));
	}

}
