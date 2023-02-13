package technicals.test;

import java.util.Arrays;
import java.util.List;

import exchanges.binance.BinanceApiClient;
import exchanges.binance.BinanceCandle;
import exchanges.binance.BinanceUtils;
import exchanges.binance.BinanceIntervalType;
import technicals.indicators.oscillator.WilliamsR;
import technicals.model.TechCandle;
import technicals.model.oscillator.WilliamsREntry;

public class WilliamsR_
{

	public static void main(String[] args) throws Exception
	{
		// List<KucoinCandle> lstKucoinCandle = KucoinApiClient.getKlines("BTC-USDT", KucoinIntervalType._1d, 30);
		// TechCandle[] candles = KucoinCandleUtils.toCandleArray(lstKucoinCandle);

		// List<BybitCandle> lstBybitCandle = BybitApiClient.getKlines("BTCUSDT", BybitIntervalType._1d, 30);
		// TechCandle[] candles = BybitCandleUtils.toCandleArray(lstBybitCandle);

		List<BinanceCandle> lstBinanceCandles = BinanceApiClient.getKlines("BTCUSDT", BinanceIntervalType._1d, 30);
		TechCandle[] candles = BinanceUtils.toCandleArray(lstBinanceCandles);

		WilliamsREntry[] entries = WilliamsR.calculate(candles, 14);

		Arrays.stream(entries).forEach(s -> System.out.println(s));		
	}

}
