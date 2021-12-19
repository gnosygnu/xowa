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
import gplx.types.commons.lists.CompareAble;
import gplx.types.basics.utls.MathUtl;
import gplx.types.basics.utls.ObjectUtl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
public class GfoDecimal implements CompareAble<GfoDecimal> {
	private final BigDecimal under;
	GfoDecimal(BigDecimal v) {this.under = v;}
	GfoDecimal(int v) {this.under = new BigDecimal(v);}
	public Object Under()                    {return under;}
	public BigDecimal UnderAsNative()        {return under;}
	public int Frac1000()                    {return (int)(under.movePointRight(3).floatValue() % 1000);}
	public GfoDecimal Floor()                {return GfoDecimalUtl.NewByInt(this.ToInt());}
	public GfoDecimal Add(GfoDecimal v)      {return new GfoDecimal(under.add(v.under, GfoDecimalUtl.ContextGfoDefault));}
	public GfoDecimal Subtract(GfoDecimal v) {return new GfoDecimal(under.subtract(v.under, GfoDecimalUtl.ContextGfoDefault));}
	public GfoDecimal Multiply(GfoDecimal v) {return new GfoDecimal(under.multiply(v.under));}
	public GfoDecimal Multiply(long v)       {return new GfoDecimal(under.multiply(new BigDecimal(v)));}
	public GfoDecimal Divide(GfoDecimal v)   {return new GfoDecimal(under.divide(v.under, GfoDecimalUtl.ContextGfoDefault));}
	public GfoDecimal Mod(GfoDecimal v)      {return new GfoDecimal(under.remainder(v.under, GfoDecimalUtl.ContextGfoDefault));}
	public GfoDecimal Abs()                  {return new GfoDecimal(under.abs(GfoDecimalUtl.ContextGfoDefault));}
	public GfoDecimal Pow(int v)             {return new GfoDecimal(under.pow(v, GfoDecimalUtl.ContextGfoDefault));}
	public GfoDecimal Sqrt()                 {return new GfoDecimal(new BigDecimal(MathUtl.Sqrt(under.doubleValue())));}
	public GfoDecimal Truncate()             {return new GfoDecimal(under.intValue());}
	public GfoDecimal RoundNative(int v)     {return new GfoDecimal(under.setScale(v, RoundingMode.HALF_UP));}
	public GfoDecimal Round(int v) {
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
				int base_10 = (int)MathUtl.Pow(10, reqd_places - 1);
				if (under.intValue() / base_10 < 5)
					new_val = BigDecimal.ZERO;
				else
					new_val = new BigDecimal(MathUtl.Pow(10, reqd_places));
			}
			else
				new_val = BigDecimal.ZERO;
		}
		return new GfoDecimal(new_val);
	}
	public GfoDecimal RoundToDefaultPrecision()  {return new GfoDecimal(under.round(GfoDecimalUtl.ContextGfoDefault));}
	public boolean CompGte(GfoDecimal v)         {return under.doubleValue() >= v.under.doubleValue();}
	public boolean CompLte(GfoDecimal v)         {return under.doubleValue() <= v.under.doubleValue();}
	public boolean CompLte(int v)                {return under.doubleValue() <= v;}
	public boolean CompGt(GfoDecimal v)          {return under.doubleValue() > v.under.doubleValue();}
	public boolean CompGt(int v)                 {return under.doubleValue() > v;}
	public boolean CompLt(GfoDecimal v)          {return under.doubleValue() < v.under.doubleValue();}
	public boolean CompLt(int v)                 {return under.doubleValue() < v;}
	public boolean Eq(GfoDecimal v)              {return v.under.doubleValue() == under.doubleValue();}
	public boolean Eq(int v)                     {return under.doubleValue() == v;}
	public int ToInt()                           {return (int)under.doubleValue();}
	public long ToLong()                         {return (long)under.doubleValue();}
	public long ToLongMult1000()                 {return under.movePointRight(3).longValue();}
	public double ToDouble()                     {return under.doubleValue();}
	public String ToStr(String fmt)              {return new DecimalFormat(fmt).format(under);}
	public String ToStr() {
		BigDecimal tmp = under;
		int tmp_scale = tmp.scale();
		if (tmp_scale <= -14) // NOTE: if large number, call .toString which will return exponential notaion (1E##) instead of literal (1000....); 14 matches MW code; DATE:2015-04-10
			return tmp.toString();

		if (tmp_scale > 14)   // NOTE: if small number, round down to remove excessive zeroes; 14 matches PHP/C# values more closely; RoundingMode.Down for same reason; see E, Pi tests
			tmp = tmp.setScale(14, RoundingMode.DOWN);
		return tmp
			.stripTrailingZeros() // NOTE: stripTrailingZeros for exp tests; EX: 120.0 -> 120; 0.01200000000000 -> .012
			.toPlainString();     // NOTE: toPlainString b/c stripTrailingZeros now converts 120 to 1.2E+2 (and any other value that is a multiple of 10)
	}
	@Override public int compareTo(GfoDecimal comp) {return under.compareTo(comp.under);}
	@Override public String toString()              {return under.toString();}
	@Override public boolean equals(Object obj)     {GfoDecimal comp = (GfoDecimal)obj; return ObjectUtl.Eq(under, comp.under);}
	@Override public int hashCode()                 {return under.hashCode();}
}
