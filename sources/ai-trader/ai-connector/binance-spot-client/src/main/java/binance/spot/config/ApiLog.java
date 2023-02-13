package binance.spot.config;

import aitrader.util.log.LogAppenders;
import aitrader.util.log.LogService;

public class ApiLog
{
	private static LogService LOG = LogAppenders.addAppender("API");

	public static String getLOG()
	{
		return LOG.getLOG();
	}

	public static void debug(String msg)
	{
		LOG.debug(msg);
	}

	public static void info(String msg)
	{
		LOG.info(msg);
	}

	public static void warn(String msg)
	{
		LOG.warn(msg);
	}

	public static void error(String msg)
	{
		LOG.error(msg);
	}

	public static void error(Exception ex)
	{
		LOG.error(ex);
	}

}
