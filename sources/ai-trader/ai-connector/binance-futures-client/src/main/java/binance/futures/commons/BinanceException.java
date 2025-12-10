package binance.futures.commons;

public class BinanceException extends RuntimeException
{
	public static final String ALGO_ORDER_API_ERROR_CODE = "-4120";
	public static final String UNKNOWN_ORDER_API_ERROR_CODE = "-2011";
	public static final String NOT_EXISTING_ORDER_API_ERROR_CODE = "-2013";
	private static final long serialVersionUID = 1L;

	private final String errCode;

	public BinanceException(String errCode, String errMsg)
	{
		super(errMsg);
		this.errCode = errCode;
	}

	public BinanceException(int errCode, String errMsg)
	{
		super(errMsg);
		this.errCode = String.valueOf(errCode);
	}

	public BinanceException(String errMsg)
	{
		super(errMsg);
		this.errCode = "";
	}

	public BinanceException(String errMsg, Throwable e)
	{
		super(errMsg, e);
		this.errCode = "";
	}

	public String getErrCode()
	{
		return errCode;
	}

	@Override
	public String getMessage() {
		return errCode == null || errCode.isEmpty() ?
				super.getMessage() : errCode + " : " + super.getMessage();
	}
}
