/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.commons;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.FloatUtl;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
public class GfoDecimalUtl {
	public static final String Cls_val_name = "decimal";
	public static final Class<?> Cls_ref_type = GfoDecimal.class;
	public static final GfoDecimal
		Zero   = new GfoDecimal(0),
		One    = new GfoDecimal(1),
		Neg1   = new GfoDecimal(-1),
		E      = NewByDouble(Math.E),
		PI     = NewByDouble(Math.PI);
	public static GfoDecimal CastOrNull(Object obj) {return obj instanceof GfoDecimal ? (GfoDecimal)obj : null;}
	public static GfoDecimal NewByBase1000(long v) {return NewByDivide(v, 1000);}
	public static GfoDecimal NewByParts1000(long num, int frc) {return NewByDivide((num * (1000)) + frc, 1000);}
	public static GfoDecimal NewByDivideSafe(long lhs, long rhs) {return rhs == 0 ? Zero : NewByDivide(lhs, rhs);}
	public static GfoDecimal NewByDivide(long lhs, long rhs) {
		return new GfoDecimal(new BigDecimal(lhs).divide(new BigDecimal(rhs), ContextGfoDefault));
	}
	public static GfoDecimal NewByInt(int v) {return new GfoDecimal(new BigDecimal(v));}
	public static GfoDecimal NewByLong(long v) {return new GfoDecimal(new BigDecimal(v));}
	public static GfoDecimal NewByFloat(float v) {return new GfoDecimal(new BigDecimal(v));}
	public static GfoDecimal NewByDouble(double v) {return new GfoDecimal(new BigDecimal(v));}
	public static GfoDecimal NewByDoubleThruStr(double v) {return new GfoDecimal(BigDecimal.valueOf(v));}
	public static GfoDecimal NewDb(Object v) {return new GfoDecimal((BigDecimal)v);}
	public static GfoDecimal NewByPow10(int v) {return new GfoDecimal(new BigDecimal(1).scaleByPowerOfTen(v));}
	public static GfoDecimal NewByParts(long num, int frc) {
		int pow10 = ToPow10(frc);
		return NewByDivide((num * (pow10)) + frc, pow10);
	}
	private static int ToPow10(int v) {
		if        (v >        -1 && v <            10) return 10;
		else if (v >         9 && v <           100) return 100;
		else if (v >        99 && v <          1000) return 1000;
		else if (v >       999 && v <         10000) return 10000;
		else if (v >      9999 && v <        100000) return 100000;
		else if (v >     99999 && v <       1000000) return 1000000;
		else if (v >    999999 && v <      10000000) return 10000000;
		else if (v >   9999999 && v <     100000000) return 100000000;
		else if (v >  99999999 && v <    1000000000) return 1000000000;
		else throw ErrUtl.NewArgs("value must be between 0 and 1 billion", "v", v);
	}
	public static GfoDecimal Parse(String raw) {
		try {
			DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(Locale.US);    // always parse as US format; EX:".9" should not be ",9" in german; DATE:2016-01-31
			nf.setParseBigDecimal(true);
			// 2020-08-27|ISSUE#:565|Parse 'e' as 'E'; PAGE:en.w:Huntington_Plaza
			if (raw.contains("e")) {
				raw = raw.replace("e", "E");
			}
			// 2021-02-13|ISSUE#:838|Parse '.' as '0.'; PAGE:en.w:2019_FIVB_Volleyball_Women%27s_Challenger_Cup#Pool_A
			if (raw.startsWith(".")) {
				raw = "0" + raw;
			}
			BigDecimal bd = (BigDecimal)nf.parse(raw);
			return new GfoDecimal(bd);
		} catch (ParseException e) {
			throw ErrUtl.NewArgs("Decimal_adp_", "parse to decimal failed", "raw", raw);
		}
	}
	public static String CalcPctStr(long dividend, long divisor, String fmt) {
		if (divisor == 0) return "%ERR";
		return GfoDecimalUtl.NewByFloat(FloatUtl.Div(dividend, divisor) * 100).ToStr(fmt) + "%";
	}
	public static final MathContext ContextGfoDefault = new MathContext(14, RoundingMode.HALF_UP);    // changed from 28 to 14; DATE:2015-07-31
}
