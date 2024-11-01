package binance.futures.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiLog
{
	private static Logger LOG = LoggerFactory.getLogger(ApiLog.class);

	/*public static String getLOG()
	{
		return LOG.getLOG();
	}*/

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
		LOG.error("Error", ex);
	}

}
