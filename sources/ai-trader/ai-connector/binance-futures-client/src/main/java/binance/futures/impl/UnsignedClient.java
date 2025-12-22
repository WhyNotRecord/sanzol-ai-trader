package binance.futures.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import binance.futures.commons.BinanceException;
import binance.futures.commons.ResponseStatus;
import binance.futures.config.ApiConstants;
import binance.futures.enums.IntervalType;
import binance.futures.model.Candle;
import binance.futures.model.Depth;
import binance.futures.model.ExchangeInfo;
import binance.futures.model.FundingRate;
import binance.futures.model.OpenInterest;
import binance.futures.model.OpenInterestStats;
import binance.futures.model.PremiumIndex;
import binance.futures.model.SymbolTicker;
import binance.futures.model.TopLongShortAccountRatio;
import binance.futures.model.TopTraderLongShortRatio;

public class UnsignedClient
{

	/**
	 *
	 [
	 1499040000000,      // Open time
	 "0.01634790",       // Open
	 "0.80000000",       // High
	 "0.01575800",       // Low
	 "0.01577100",       // Close
	 "148976.11427815",  // Volume
	 1499644799999,      // Close time
	 "2434.19055334",    // Quote asset volume
	 308,                // Number of trades
	 "1756.87402397",    // Taker buy base asset volume
	 "28.46694368",      // Taker buy quote asset volume
	 "17928899.62484339" // Ignore.
	 ]
	 */
	public static List<Candle> getKlines(String symbol, IntervalType interval, int limit, Long startTime) throws Exception
	{
		final String path = "/fapi/v1/klines";

		Client client = ClientBuilder.newClient();

		WebTarget target = client
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol)
			.queryParam("interval", interval.getCode())
			.queryParam("limit", limit);
		if (startTime != null)
			target = target.queryParam("startTime", startTime);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		String[][] lst = mapper.readValue(jsonString, String[][].class);		    

		// Create list of Kline
		List<Candle> lstResult = new ArrayList<Candle>();
		for (String[] entry : lst)
		{
			Candle kline = new Candle();

			kline.setOpenTime(Long.parseLong(entry[0]));
			kline.setOpenPrice(Float.parseFloat(entry[1]));
			kline.setHighPrice(Float.parseFloat(entry[2]));
			kline.setLowPrice(Float.parseFloat(entry[3]));
			kline.setClosePrice(Float.parseFloat(entry[4]));
			kline.setVolume(new BigDecimal(entry[5]));
			kline.setQuoteVolume(new BigDecimal(entry[7]));
			kline.setCount(Long.valueOf(entry[8]));
			kline.setTakerBuyVol(new BigDecimal(entry[9]));
			kline.setTakerBuyQuoteVol(new BigDecimal(entry[10]));

			lstResult.add(kline);
		}

		return lstResult;			
	}

	public static ExchangeInfo getExchangeInformation() throws Exception
	{
		final String path = "/fapi/v1/exchangeInfo";

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		ExchangeInfo exchangeInfo = mapper.readValue(jsonString, ExchangeInfo.class);

		return exchangeInfo;			
	}

	public static long getServerTime() throws Exception
	{
		final String path = "/fapi/v1/time";

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		HashMap time = mapper.readValue(jsonString, HashMap.class);

		return (long) time.get("serverTime");
	}

	public static List<SymbolTicker> getSymbolTickers() throws Exception
	{
		final String path = "/fapi/v1/ticker/24hr";

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<SymbolTicker> lst = mapper.readValue(jsonString, new TypeReference<List<SymbolTicker>>(){});

		return lst;			
	}	

	public static List<FundingRate> getFundingRate(String symbol, Integer limit) throws Exception
	{
		final String path = "/fapi/v1/fundingRate";

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol)
			.queryParam("limit", limit);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<FundingRate> lst = mapper.readValue(jsonString, new TypeReference<List<FundingRate>>(){});

		return lst;			
	}

	public static List<PremiumIndex> getPremiumIndex(String symbol) throws Exception
	{
		final String path = "/fapi/v1/premiumIndex";

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<PremiumIndex> lst = mapper.readValue(jsonString, new TypeReference<List<PremiumIndex>>(){});

		return lst;			
	}
	
	public static Depth getDepth(String symbol) throws Exception
	{
		return getDepth(symbol, ApiConstants.MAX_DEPTH_LIMIT);
	}
	
	public static Depth getDepth(String symbol, int limit) throws Exception
	{
		final String path = "/fapi/v1/depth";

		Client client = ClientBuilder.newClient();

		WebTarget target = client
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol)
			.queryParam("limit", limit);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		Depth depth = mapper.readValue(jsonString, Depth.class);

		return depth;			
	}
	
	/**
	 * Получить открытый интерес для конкретного символа.
	 * Endpoint: GET /fapi/v1/openInterest
	 * 
	 * @param symbol Символ (например, "BTCUSDT")
	 * @return Информация об открытом интересе
	 * @throws Exception в случае ошибки API
	 */
	public static OpenInterest getOpenInterest(String symbol) throws Exception
	{
		final String path = "/fapi/v1/openInterest";

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		OpenInterest openInterest = mapper.readValue(jsonString, OpenInterest.class);

		return openInterest;			
	}
	
	/**
	 * Получить статистику открытого интереса.
	 * Endpoint: GET /fapi/v1/openInterestHist
	 * 
	 * @param symbol Символ (например, "BTCUSDT")
	 * @param period Период: "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d"
	 * @param limit Ограничение количества записей (по умолчанию 30, максимум 500)
	 * @param startTime Время начала в миллисекундах (опционально)
	 * @param endTime Время окончания в миллисекундах (опционально)
	 * @return Список данных статистики открытого интереса
	 * @throws Exception в случае ошибки API
	 */
	public static List<OpenInterestStats> getOpenInterestStats(String symbol, String period, 
			Integer limit, Long startTime, Long endTime) throws Exception
	{
		final String path = "/fapi/v1/openInterestHist";

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol)
			.queryParam("period", period);
		
		if (limit != null)
			target = target.queryParam("limit", limit);
		if (startTime != null)
			target = target.queryParam("startTime", startTime);
		if (endTime != null)
			target = target.queryParam("endTime", endTime);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<OpenInterestStats> lst = mapper.readValue(jsonString, new TypeReference<List<OpenInterestStats>>(){});

		return lst;			
	}
	
	/**
	 * Получить статистику открытого интереса с настройками по умолчанию.
	 * 
	 * @param symbol Символ (например, "BTCUSDT")
	 * @param period Период: "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d"
	 * @return Список данных статистики открытого интереса
	 * @throws Exception в случае ошибки API
	 */
	public static List<OpenInterestStats> getOpenInterestStats(String symbol, String period) throws Exception
	{
		return getOpenInterestStats(symbol, period, null, null, null);
	}
	
	/**
	 * Получить соотношение длинных и коротких позиций топ-трейдеров (по позициям).
	 * Endpoint: GET /fapi/v1/topLongShortPositionRatio
	 * 
	 * @param symbol Символ (например, "BTCUSDT")
	 * @param period Период: "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d"
	 * @param limit Ограничение количества записей (по умолчанию 30, максимум 500)
	 * @param startTime Время начала в миллисекундах (опционально)
	 * @param endTime Время окончания в миллисекундах (опционально)
	 * @return Список данных соотношения длинных и коротких позиций топ-трейдеров
	 * @throws Exception в случае ошибки API
	 */
	public static List<TopTraderLongShortRatio> getTopTraderLongShortRatio(String symbol, String period, 
			Integer limit, Long startTime, Long endTime) throws Exception
	{
		final String path = "/fapi/v1/topLongShortPositionRatio";

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol)
			.queryParam("period", period);
		
		if (limit != null)
			target = target.queryParam("limit", limit);
		if (startTime != null)
			target = target.queryParam("startTime", startTime);
		if (endTime != null)
			target = target.queryParam("endTime", endTime);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<TopTraderLongShortRatio> lst = mapper.readValue(jsonString, new TypeReference<List<TopTraderLongShortRatio>>(){});

		return lst;			
	}
	
	/**
	 * Получить соотношение длинных и коротких позиций топ-трейдеров с настройками по умолчанию.
	 * 
	 * @param symbol Символ (например, "BTCUSDT")
	 * @param period Период: "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d"
	 * @return Список данных соотношения длинных и коротких позиций топ-трейдеров
	 * @throws Exception в случае ошибки API
	 */
	public static List<TopTraderLongShortRatio> getTopTraderLongShortRatio(String symbol, String period) throws Exception
	{
		return getTopTraderLongShortRatio(symbol, period, null, null, null);
	}
	
	/**
	 * Получить соотношение длинных и коротких счетов топ-трейдеров (по аккаунтам).
	 * Endpoint: GET /fapi/v1/topLongShortAccountRatio
	 * 
	 * @param symbol Символ (например, "BTCUSDT")
	 * @param period Период: "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d"
	 * @param limit Ограничение количества записей (по умолчанию 30, максимум 500)
	 * @param startTime Время начала в миллисекундах (опционально)
	 * @param endTime Время окончания в миллисекундах (опционально)
	 * @return Список данных соотношения длинных и коротких счетов топ-трейдеров
	 * @throws Exception в случае ошибки API
	 */
	public static List<TopLongShortAccountRatio> getTopLongShortAccountRatio(String symbol, String period, 
			Integer limit, Long startTime, Long endTime) throws Exception
	{
		final String path = "/fapi/v1/topLongShortAccountRatio";

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol)
			.queryParam("period", period);
		
		if (limit != null)
			target = target.queryParam("limit", limit);
		if (startTime != null)
			target = target.queryParam("startTime", startTime);
		if (endTime != null)
			target = target.queryParam("endTime", endTime);

		URI uri = target.getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<TopLongShortAccountRatio> lst = mapper.readValue(jsonString, new TypeReference<List<TopLongShortAccountRatio>>(){});

		return lst;			
	}
	
	/**
	 * Получить соотношение длинных и коротких счетов топ-трейдеров с настройками по умолчанию.
	 * 
	 * @param symbol Символ (например, "BTCUSDT")
	 * @param period Период: "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d"
	 * @return Список данных соотношения длинных и коротких счетов топ-трейдеров
	 * @throws Exception в случае ошибки API
	 */
	public static List<TopLongShortAccountRatio> getTopLongShortAccountRatio(String symbol, String period) throws Exception
	{
		return getTopLongShortAccountRatio(symbol, period, null, null, null);
	}

}