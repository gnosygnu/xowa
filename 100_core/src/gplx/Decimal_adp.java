/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

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
public class Decimal_adp implements CompareAble {
	public int compareTo(Object obj) {Decimal_adp comp = (Decimal_adp)obj; return under.compareTo(comp.under);}
		protected Decimal_adp(BigDecimal v) {this.under = v;} private final BigDecimal under;
	protected Decimal_adp(int v) {this.under = new BigDecimal(v);}
	public Object Under() 								{return under;}
	public BigDecimal Under_as_native() 				{return under;}
	public int Precision() 								{return under.precision();}
	public int Frac_1000() 								{return  (int)(under.movePointRight(3).floatValue() % 1000);}
	public boolean Eq(Decimal_adp v) 					{return v.under.doubleValue() == under.doubleValue();}
	public boolean Eq(int v) 							{return under.doubleValue() == v;}
	public String To_str() {
		BigDecimal tmp = under;
		int tmp_scale = tmp.scale();
		if 	(tmp_scale <= -14) return tmp.toString();	// NOTE: if large number, call .toString which will return exponential notaion (1E##) instead of literal (1000....); 14 matches MW code; DATE:2015-04-10
		if 	(tmp_scale > 14)
			tmp = tmp.setScale(14, RoundingMode.DOWN);	// NOTE: if small number, round down to remove excessive zeroes; 14 matches PHP/C# values more closely; RoundingMode.Down for same reason; see E, Pi tests
		return tmp	.stripTrailingZeros()				// NOTE: stripTrailingZeros for exp tests; EX: 120.0 -> 120; 0.01200000000000 -> .012 
					.toPlainString();					// NOTE: toPlainString b/c stripTrailingZeros now converts 120 to 1.2E+2 (and any other value that is a multiple of 10)
	}
	public String To_str(String fmt) 					{
		return new DecimalFormat(fmt).format(under);
	}
	@Override public String toString() 					{return under.toString();}
	public int To_int() 								{return (int)under.doubleValue();}
	public long To_long() 								{return (long)under.doubleValue();}
	public long To_long_mult_1000() 					{return under.movePointRight(3).longValue();}
	public double To_double() 							{return under.doubleValue();}
	public Decimal_adp Add(Decimal_adp v) 				{return new Decimal_adp(under.add(v.under, Decimal_adp_.Gplx_rounding_context));}
	public Decimal_adp Subtract(Decimal_adp v) 			{return new Decimal_adp(under.subtract(v.under, Decimal_adp_.Gplx_rounding_context));}
	public Decimal_adp Multiply(Decimal_adp v)			{return new Decimal_adp(under.multiply(v.under));}
	public Decimal_adp Multiply(double v) 				{return new Decimal_adp(under.multiply(new BigDecimal(v, Decimal_adp_.Gplx_rounding_context)));}
	public Decimal_adp Multiply(long v)					{return new Decimal_adp(under.multiply(new BigDecimal(v)));}
	public Decimal_adp Divide(Decimal_adp v)			{return new Decimal_adp(under.divide(v.under, Decimal_adp_.Gplx_rounding_context));}
	public Decimal_adp Mod(Decimal_adp v)				{return new Decimal_adp(under.remainder(v.under, Decimal_adp_.Gplx_rounding_context));}
	public Decimal_adp Abs()							{return new Decimal_adp(under.abs(Decimal_adp_.Gplx_rounding_context));}
	public Decimal_adp Pow(int v)						{return new Decimal_adp(under.pow(v, Decimal_adp_.Gplx_rounding_context));}
	public Decimal_adp Sqrt()							{return new Decimal_adp(new BigDecimal(Math_.Sqrt(under.doubleValue())));}
	public Decimal_adp Truncate() 						{return new Decimal_adp(under.intValue());}
	public Decimal_adp Round_old(int v) 				{return new Decimal_adp(under.setScale(v, RoundingMode.HALF_UP));}
	public Decimal_adp Round(int v) {
		BigDecimal new_val = null;
		if (v > 0) {
			new_val = under.setScale(v, RoundingMode.HALF_UP);
		}
		else {
			int actl_places = under.precision() - under.scale();
			int reqd_places = -v;
			if (reqd_places < actl_places)
				new_val = under.round(new java.math.MathContext(actl_places - reqd_places, RoundingMode.HALF_UP));
			else if (reqd_places == actl_places) {
				int base_10 = (int)Math_.Pow(10, reqd_places - 1);
				if (under.intValue() / base_10 < 5) 
					new_val = BigDecimal.ZERO;
				else
					new_val = new BigDecimal(Math_.Pow(10, reqd_places));
			}
			else
				new_val = BigDecimal.ZERO;
		} 
		return new Decimal_adp(new_val);
	}
	public boolean Comp_gte(Decimal_adp v) 				{return under.doubleValue() >= v.under.doubleValue();}
	public boolean Comp_gte(int v) 						{return under.doubleValue() >= v;}
	public boolean Comp_lte(Decimal_adp v) 				{return under.doubleValue() <= v.under.doubleValue();}
	public boolean Comp_lte(int v) 						{return under.doubleValue() <= v;}
	public boolean Comp_gt(Decimal_adp v) 				{return under.doubleValue() > v.under.doubleValue();}
	public boolean Comp_gt(int v) 						{return under.doubleValue() > v;}
	public boolean Comp_lt(Decimal_adp v)			 	{return under.doubleValue() < v.under.doubleValue();}
	public boolean Comp_lt(int v) 						{return under.doubleValue() < v;}
	}
