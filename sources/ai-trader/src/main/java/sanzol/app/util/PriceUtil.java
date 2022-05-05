package sanzol.app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PriceUtil
{
	private PriceUtil()
	{
		// Hide
	}

	public static BigDecimal priceDistUp(BigDecimal a, BigDecimal b, boolean x100)
	{
		BigDecimal r = b.subtract(a).divide(a, 4, RoundingMode.HALF_UP);
		if (x100)
			return r.multiply(BigDecimal.valueOf(100));
		else
			return r;
	}

	public static BigDecimal priceDistDown(BigDecimal a, BigDecimal b, boolean x100)
	{
		BigDecimal r = a.subtract(b).divide(a, 4, RoundingMode.HALF_UP);
		if (x100)
			return r.multiply(BigDecimal.valueOf(100));
		else
			return r;
	}

	public static String cashFormat(double n, int iteration)
	{
		char[] suff = new char[] { 'K', 'M', 'B', 'T' };

		double d = ((long) n / 100) / 10.0;
		boolean isRound = (d * 10) % 10 == 0;
		return (d < 1000 ? ((d > 99.9 || isRound || (!isRound && d > 9.99) ? (int) d * 10 / 10 : d + "") + "" + suff[iteration]) : cashFormat(d, iteration + 1));
	}

}