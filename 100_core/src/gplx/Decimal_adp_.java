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
package gplx;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Decimal_adp_ {
	public static final String Cls_val_name = "decimal";
	public static final    Class<?> Cls_ref_type = Decimal_adp.class;
	public static Decimal_adp as_(Object obj) {return obj instanceof Decimal_adp ? (Decimal_adp)obj : null;}
	public static final    Decimal_adp Zero = new Decimal_adp(0);
	public static final    Decimal_adp One = new Decimal_adp(1);
	public static final    Decimal_adp Neg1 = new Decimal_adp(-1);
	public static final    Decimal_adp Const_e = Decimal_adp_.double_(Math_.E);
	public static final    Decimal_adp Const_pi = Decimal_adp_.double_(Math_.Pi);
	public static Decimal_adp base1000_(long v) {return divide_(v, 1000);}
	public static Decimal_adp parts_1000_(long num, int frc) {return divide_((num * (1000)) + frc, 1000);}
	public static Decimal_adp parts_(long num, int frc) {
		//			int log10 = frc == 0 ? 0 : (Math_.Log10(frc) + 1);
		//			int pow10 = (int)Math_.Pow(10, log10);
		int pow10 = XtoPow10(frc);
		return divide_((num * (pow10)) + frc, pow10);
	}
	public static Decimal_adp cast(Object obj) {return (Decimal_adp)obj;}
	static int XtoPow10(int v) {
		if		(v >		-1 && v <            10) return 10;
		else if (v >		 9 && v <           100) return 100;
		else if (v >		99 && v <          1000) return 1000;
		else if (v >	   999 && v <         10000) return 10000;
		else if (v >	  9999 && v <        100000) return 100000;
		else if (v >	 99999 && v <       1000000) return 1000000;
		else if (v >	999999 && v <      10000000) return 10000000;
		else if (v >   9999999 && v <     100000000) return 100000000;
		else if (v >  99999999 && v <    1000000000) return 1000000000;
		else throw Err_.new_wo_type("value must be between 0 and 1 billion", "v", v);
	}
	public static String CalcPctStr(long dividend, long divisor, String fmt) {
		if (divisor == 0) return "%ERR";
		return Decimal_adp_.float_(Float_.Div(dividend, divisor) * 100).To_str(fmt) + "%";
	}
	public static Decimal_adp divide_safe_(long lhs, long rhs) {return rhs == 0 ? Zero : divide_(lhs, rhs);}
		public static Decimal_adp divide_(long lhs, long rhs) {
		return new Decimal_adp(new BigDecimal(lhs).divide(new BigDecimal(rhs), Gplx_rounding_context));
	}
	public static Decimal_adp int_(int v) {return new Decimal_adp(new BigDecimal(v));}
	public static Decimal_adp long_(long v) {return new Decimal_adp(new BigDecimal(v));}
	public static Decimal_adp float_(float v) {return new Decimal_adp(new BigDecimal(v));}
	public static Decimal_adp double_(double v) {return new Decimal_adp(new BigDecimal(v));}
	public static Decimal_adp double_thru_str_(double v) {return new Decimal_adp(BigDecimal.valueOf(v));}
	public static Decimal_adp db_(Object v) {return new Decimal_adp((BigDecimal)v);}
	public static Decimal_adp parse(String raw) {
		try {
	        DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(Locale.US);	// always parse as US format; EX:".9" should not be ",9" in german; DATE:2016-01-31
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
			return new Decimal_adp(bd);
		} catch (ParseException e) {
			throw Err_.new_("Decimal_adp_", "parse to decimal failed", "raw", raw);
		}
	}
	public static Decimal_adp pow_10_(int v) {return new Decimal_adp(new BigDecimal(1).scaleByPowerOfTen(v));}
	public static final MathContext RoundDownContext = new MathContext(0, RoundingMode.DOWN);
	public static final MathContext Gplx_rounding_context = new MathContext(14, RoundingMode.HALF_UP);	// changed from 28 to 14; DATE:2015-07-31
}
