package examples;
import java.io.IOException;
import java.util.List;

import binance.futures.impl.SignedClient;
import binance.futures.model.AccountInfo;
import binance.futures.model.Order;

public class SignedClient_
{

	public static void main(String[] args) throws Exception
	{
		SignedClient client = getSignedClientAlt();

		/*
		System.out.println("");
		System.out.println(client.setLeverage("BTCUSDT", 10));

		try
		{
			client.postOrder(
					"BTCUSDT", OrderSide.BUY, PositionSide.BOTH, OrderType.LIMIT, TimeInForce.GTC,
					"0.001", "15000", null, null, null, WorkingType.CONTRACT_PRICE,
					NewOrderRespType.RESULT, false);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("");
		List<AccountBalance> lstAccountBalance = client.getBalance();
		lstAccountBalance.forEach(s -> System.out.println(s.getAsset() + "\t\t" + s.getBalance() + "\t\t" + s.getWithdrawAvailable()));

		System.out.println("");
		List<PositionRisk> lstPositionRisk = client.getPositionRisk();
		lstPositionRisk.forEach(s -> System.out.println(s.getSymbol() + "\t\t" + s.getPositionAmt()));
 		*/

		System.out.println("");
		List<Order> lstOrders = client.getOpenOrders();
		//lstOrders.forEach(s -> System.out.println(s.getSymbol() + "\t\t" + s.getAvgPrice() + "\t\t" + s.getOrigQty()));
		lstOrders.forEach(System.out::println);

		System.out.println("");
		List<Order> lstOpOrders = client.getFilledOrders("OPUSDT");
		//lstOpOrders.forEach(s -> System.out.println(s.getSymbol() + "\t\t" + s.getAvgPrice() + "\t\t" + s.getOrigQty()));
		lstOpOrders.forEach(System.out::println);

		System.out.println("");
		AccountInfo info = client.getAccountData();
		for (AccountInfo.AccountPosition position : info.getPositions()) {
			System.out.println(position);
		}
		System.out.println(info.canTrade());

		System.out.println("");
		String levResponse = client.getLeverageBracket("OPUSDT");
		System.out.println(levResponse);
	}

	private static SignedClient getSignedClient() throws IOException {
		PrivateConfig.load();
		return SignedClient.create(PrivateConfig.getApiKey(), PrivateConfig.getSecretKey());
	}

	private static SignedClient getSignedClientAlt() throws IOException {
		return SignedClient.create(
				"foo",
				"bar");
	}

}
