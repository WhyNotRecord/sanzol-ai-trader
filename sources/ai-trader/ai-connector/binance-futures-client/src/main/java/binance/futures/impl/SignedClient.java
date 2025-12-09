package binance.futures.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import binance.futures.model.AccountInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import binance.futures.commons.BinanceException;
import binance.futures.commons.ResponseStatus;
import binance.futures.commons.Signer;
import binance.futures.config.ApiConstants;
import binance.futures.config.ApiLog;
import binance.futures.enums.NewOrderRespType;
import binance.futures.enums.OrderSide;
import binance.futures.enums.OrderType;
import binance.futures.enums.PositionMode;
import binance.futures.enums.PositionSide;
import binance.futures.enums.TimeInForce;
import binance.futures.enums.WorkingType;
import binance.futures.model.AccountBalance;
import binance.futures.model.Order;
import binance.futures.model.PositionRisk;

public class SignedClient {
	public static final String ALGO_ORDER_API_ERROR_CODE = "-4120";
	private String apiKey;
	private String secretKey;

	public SignedClient(String apiKey, String secretKey) {
		this.apiKey = apiKey;
		this.secretKey = secretKey;
	}

	public static SignedClient create(String apiKey, String secretKey) {
		return new SignedClient(apiKey, secretKey);
	}

	// --------------------------------------------------------------------

	public List<AccountBalance> getBalance() throws Exception {
		final String path = "/fapi/v2/balance";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.GET()
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<AccountBalance> lst = mapper.readValue(jsonString, new TypeReference<List<AccountBalance>>() {
		});

		return lst;
	}

	public List<PositionRisk> getPositionRisk() throws Exception {
		final String path = "/fapi/v1/positionRisk";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.GET()
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<PositionRisk> lst = mapper.readValue(jsonString, new TypeReference<List<PositionRisk>>() {
		});

		return lst;
	}

	public List<Order> getOpenOrders() throws Exception {
		return getOrders(true, null);
	}

	public List<Order> getFilledOrders(String symbol) throws Exception {
		List<Order> orders = getOrders(false, symbol);
		return orders.stream().filter(Order::isFilled).collect(Collectors.toList());
	}

	public List<Order> getOrders(boolean openOnly, String symbol) throws Exception
	{
		final String path = openOnly ? "/fapi/v1/openOrders" : "/fapi/v1/allOrders";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("recvWindow", recvWindow)
			.queryParam("timestamp", timestamp);
		if (symbol != null)
			target = target.queryParam("symbol", symbol);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("X-MBX-APIKEY", apiKey)
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
		List<Order> lst = mapper.readValue(jsonString, new TypeReference<List<Order>>(){});

		return lst;	
	}

	public PositionMode getPositionMode() throws Exception
	{
		final String path = "/fapi/v1/positionSide/dual";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("recvWindow", recvWindow)
			.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();
		
		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("X-MBX-APIKEY", apiKey)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());		

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		JsonNode parent= new ObjectMapper().readTree(jsonString);
		boolean dualSidePosition = parent.path("dualSidePosition").asBoolean();
		
		return dualSidePosition ? PositionMode.HEDGE : PositionMode.ONE_WAY;
	}

	public String setPositionMode(boolean hedge) throws Exception
	{
		final String path = "/fapi/v1/positionSide/dual";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("dualSidePosition", String.valueOf(hedge))
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.POST(HttpRequest.BodyPublishers.noBody())
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		return response.body();
	}

	public AccountInfo getAccountData() throws Exception
	{
		final String path = "/fapi/v2/account";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("recvWindow", recvWindow)
			.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("X-MBX-APIKEY", apiKey)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		AccountInfo result = mapper.readValue(jsonString, AccountInfo.class);
		return result;
	}

	public boolean canTrade() throws Exception {
		AccountInfo accountData = getAccountData();
		return accountData.canTrade();
	}

	public String setLeverage(String symbol, int leverage) throws Exception
	{
		final String path = "/fapi/v1/leverage";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol)
			.queryParam("leverage", leverage)
			.queryParam("recvWindow", recvWindow)
			.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();
		
		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("X-MBX-APIKEY", apiKey)
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());		

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		return response.body();
	}

	public String getLeverageBracket(String symbol) throws Exception
	{
		final String path = "/fapi/v1/leverageBracket";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol)
			.queryParam("recvWindow", recvWindow)
			.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("X-MBX-APIKEY", apiKey)
            .GET()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		return response.body();
	}


	private boolean isConditionalOrderType(OrderType orderType) {
		return orderType == OrderType.STOP_MARKET ||
				orderType == OrderType.TAKE_PROFIT_MARKET ||
				orderType == OrderType.STOP ||
				orderType == OrderType.TAKE_PROFIT ||
				orderType == OrderType.TRAILING_STOP_MARKET;
	}

	public Order postOrder(String symbol, OrderSide side, PositionSide positionSide, OrderType orderType, TimeInForce timeInForce,
												 String quantity, String price, Boolean reduceOnly, String newClientOrderId, String stopPrice,
												 WorkingType workingType, NewOrderRespType newOrderRespType, Boolean closePosition) throws Exception {

		// Проверяем, нужно ли использовать новый алго-сервис
		if (isConditionalOrderType(orderType)) {
			return postAlgoOrder(symbol, side, positionSide, orderType, timeInForce, quantity, price,
					reduceOnly, newClientOrderId, stopPrice, workingType, newOrderRespType, closePosition);
		}

		// Существующий код для обычных ордеров
		final String path = "/fapi/v1/order";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("symbol", symbol)
				.queryParam("side", side)
				.queryParam("positionSide", positionSide)
				.queryParam("type", orderType)
				.queryParam("timeInForce", timeInForce)
				.queryParam("quantity", quantity)
				.queryParam("price", price)
				.queryParam("reduceOnly", reduceOnly)
				.queryParam("newClientOrderId", newClientOrderId)
				.queryParam("stopPrice", stopPrice)
				.queryParam("workingType", workingType)
				.queryParam("newOrderRespType", newOrderRespType)
				.queryParam("closePosition", closePosition)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.POST(HttpRequest.BodyPublishers.noBody())
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200) {
			ApiLog.error("POST ORDER ERROR " + symbol + ", " + side + ", " + positionSide + ", " + orderType + ", "
					+ timeInForce + ", " + quantity + ", " + price + ", " + reduceOnly + ", " + newClientOrderId
					+ ", " + stopPrice + ", " + workingType + ", " + newOrderRespType + ", " + closePosition);

			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.readValue(jsonString, Order.class);

		return order;
	}

	// Новый метод для алго-ордеров
	public Order postAlgoOrder(String symbol, OrderSide side, PositionSide positionSide, OrderType orderType, TimeInForce timeInForce,
														 String quantity, String price, Boolean reduceOnly, String newClientOrderId, String stopPrice,
														 WorkingType workingType, NewOrderRespType newOrderRespType, Boolean closePosition) throws Exception {

		final String path = "/fapi/v1/algoOrder";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("symbol", symbol)
				.queryParam("side", side)
				.queryParam("positionSide", positionSide)
				.queryParam("type", orderType)
				.queryParam("timeInForce", timeInForce)
				.queryParam("quantity", quantity)
				.queryParam("price", price)
				.queryParam("reduceOnly", reduceOnly)
				.queryParam("newClientOrderId", newClientOrderId)
				.queryParam("stopPrice", stopPrice)
				.queryParam("workingType", workingType)
				.queryParam("newOrderRespType", newOrderRespType)
				.queryParam("closePosition", closePosition)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.POST(HttpRequest.BodyPublishers.noBody())
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200) {
			ApiLog.error("POST ALGO ORDER ERROR " + symbol + ", " + side + ", " + positionSide + ", " + orderType + ", "
					+ timeInForce + ", " + quantity + ", " + price + ", " + reduceOnly + ", " + newClientOrderId
					+ ", " + stopPrice + ", " + workingType + ", " + newOrderRespType + ", " + closePosition);

			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.readValue(jsonString, Order.class);

		return order;
	}

	// Метод для отмены всех открытых алго-ордеров
	public String cancelAllOpenAlgoOrders(String symbol) throws Exception {
		final String path = "/fapi/v1/algoOpenOrders";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("symbol", symbol)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.DELETE()
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200) {
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		return response.body();
	}

	// Метод для получения открытых алго-ордеров
	public List<Order> getOpenAlgoOrders(String symbol) throws Exception {
		final String path = "/fapi/v1/openAlgoOrders";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		if (symbol != null) {
			target = target.queryParam("symbol", symbol);
		}

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.GET()
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200) {
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<Order> orders = mapper.readValue(jsonString, new TypeReference<List<Order>>(){});

		return orders;
	}

	// Метод для получения всех алго-ордеров
	public List<Order> getAllAlgoOrders(String symbol, Long startTime, Long endTime, Integer page, Integer pageSize) throws Exception {
		final String path = "/fapi/v1/allAlgoOrders";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		if (symbol != null) {
			target = target.queryParam("symbol", symbol);
		}
		if (startTime != null) {
			target = target.queryParam("startTime", startTime);
		}
		if (endTime != null) {
			target = target.queryParam("endTime", endTime);
		}
		if (page != null) {
			target = target.queryParam("page", page);
		}
		if (pageSize != null) {
			target = target.queryParam("pageSize", pageSize);
		}

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.GET()
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200) {
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		List<Order> orders = mapper.readValue(jsonString, new TypeReference<List<Order>>(){});

		return orders;
	}

	public Order cancelOrder(String symbol, Long orderId, String origClientOrderId) throws Exception
	{
		try {
			// Сначала пытаемся использовать стандартный endpoint для обычных ордеров
			return cancelRegularOrder(symbol, orderId, origClientOrderId);
		} catch (BinanceException e) {
			// Если получили ошибку -4120, пытаемся использовать алго-endpoint
			if (ALGO_ORDER_API_ERROR_CODE.equals(e.getErrCode())) {
				ApiLog.info("Switching to Algo Order API for cancel - OrderId: " + orderId + ", ClientOrderId: " + origClientOrderId);
				return cancelAlgoOrder(symbol, orderId, origClientOrderId);
			}
			// Если другая ошибка, пробрасываем её дальше
			throw e;
		}
	}

	private Order cancelRegularOrder(String symbol, Long orderId, String origClientOrderId) throws Exception
	{
		final String path = "/fapi/v1/order";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("symbol", symbol)
			.queryParam("orderId", orderId)
			.queryParam("origClientOrderId", origClientOrderId)
			.queryParam("recvWindow", recvWindow)
			.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("X-MBX-APIKEY", apiKey)
            .DELETE()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());		

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode(), responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.readValue(jsonString, Order.class);

		return order;			
	}

	public Order cancelAlgoOrder(String symbol, Long orderId, String origClientOrderId) throws Exception {
		final String path = "/fapi/v1/algoOrder";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("symbol", symbol)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		// Для алго-ордеров используем orderId или origClientOrderId
		if (orderId != null) {
			target = target.queryParam("algoId", orderId);
		}
		if (origClientOrderId != null) {
			target = target.queryParam("clientAlgoId", origClientOrderId);
		}

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.DELETE()
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200) {
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode(), responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.readValue(jsonString, Order.class);

		return order;
	}

	public Order queryOrder(String symbol, Long orderId, String origClientOrderId) throws Exception
	{
		try {
			// Сначала пытаемся использовать стандартный endpoint для обычных ордеров
			return queryRegularOrder(symbol, orderId, origClientOrderId);
		} catch (BinanceException e) {
			// Если получили ошибку -4120, пытаемся использовать алго-endpoint
			if (ALGO_ORDER_API_ERROR_CODE.equals(e.getErrCode())) {
				ApiLog.info("Switching to Algo Order API for query - OrderId: " + orderId + ", ClientOrderId: " + origClientOrderId);
				return queryAlgoOrder(symbol, orderId, origClientOrderId);
			}
			// Если другая ошибка, пробрасываем её дальше
			throw e;
		}
	}

	// Переименовываем старый метод для внутреннего использования
	private Order queryRegularOrder(String symbol, Long orderId, String origClientOrderId) throws Exception
	{
		final String path = "/fapi/v1/order";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("symbol", symbol)
				.queryParam("orderId", orderId)
				.queryParam("origClientOrderId", origClientOrderId)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.GET()
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode(), responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.readValue(jsonString, Order.class);

		return order;
	}

	public Order queryAlgoOrder(String symbol, Long orderId, String origClientOrderId) throws Exception {
		final String path = "/fapi/v1/algoOrder";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
				.target(ApiConstants.BASE_URL)
				.path(path)
				.queryParam("symbol", symbol)
				.queryParam("recvWindow", recvWindow)
				.queryParam("timestamp", timestamp);

		// Для алго-ордеров используем orderId или origClientOrderId
		if (orderId != null) {
			target = target.queryParam("algoId", orderId);
		}
		if (origClientOrderId != null) {
			target = target.queryParam("clientAlgoId", origClientOrderId);
		}

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("X-MBX-APIKEY", apiKey)
				.GET()
				.build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode(), responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.readValue(jsonString, Order.class);

		return order;
	}


	// --------------------------------------------------------------------

	public String startUserDataStream() throws Exception 
	{
		final String path = "/fapi/v1/listenKey";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("recvWindow", recvWindow)
			.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("X-MBX-APIKEY", apiKey)
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());		

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		String jsonString = response.body();
		ObjectNode node = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		String listenKey = null;
		if (node.has("listenKey"))
		{
			listenKey = node.get("listenKey").asText();
		}

		return listenKey;	
	}
	
	public String keepUserDataStream() throws Exception 
	{
		final String path = "/fapi/v1/listenKey";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("recvWindow", recvWindow)
			.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("X-MBX-APIKEY", apiKey)
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());		

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		return "OK";		
	}

	public String closeUserDataStream() throws Exception 
	{
		final String path = "/fapi/v1/listenKey";

		String recvWindow = Long.toString(60_000L);
		String timestamp = Long.toString(System.currentTimeMillis());

		WebTarget target = ClientBuilder.newClient()
			.target(ApiConstants.BASE_URL)
			.path(path)
			.queryParam("recvWindow", recvWindow)
			.queryParam("timestamp", timestamp);

		String signature = Signer.createSignature(apiKey, secretKey, target.getUri().getQuery());
		URI uri = target.queryParam("signature", signature).getUri();

		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("X-MBX-APIKEY", apiKey)
            .DELETE()
            .build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());		

		if(response.statusCode() != 200)
		{
			ResponseStatus responseStatus = ResponseStatus.from(response.body());
			throw new BinanceException(responseStatus.getCode() + " : " + responseStatus.getMsg());
		}

		return "OK";		
	}

}
