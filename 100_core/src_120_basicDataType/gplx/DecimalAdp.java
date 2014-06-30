/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
public class DecimalAdp implements CompareAble {
	public int compareTo(Object obj) {DecimalAdp comp = (DecimalAdp)obj; return under.compareTo(comp.under);}		

		protected DecimalAdp(BigDecimal v) {this.under = v;} BigDecimal under;
	protected DecimalAdp(int v) {this.under = new BigDecimal(v);}
	public String XtoStr() {
		BigDecimal tmp = under;
		if (tmp.scale() > 14) tmp = tmp.setScale(14, RoundingMode.DOWN);	// NOTE: setting to 14 to match PHP/C# values more closely; RoundingMode.Down for same reason; see E, Pi tests 
		return tmp	.stripTrailingZeros()									// NOTE: stripTrailingZeros for exp tests; EX: 120.0 -> 120; 0.01200000000000 -> .012 
					.toPlainString();										// NOTE: toPlainString b/c stripTrailingZeros now converts 120 to 1.2E+2 (and any other value that is a multiple of 10)
	}
	public String XtoStr(String fmt) {return new DecimalFormat(fmt).format(under);}
	@Override public String toString() {return under.toString();}
	public boolean Eq(DecimalAdp v) {return v.under.doubleValue() == under.doubleValue();}
	public BigDecimal XtoDecimal() {return under;}
	public long XtoLong_Mult1000() {return under.movePointRight(3).longValue();}
	public int Fraction1000() {return  (int)(under.movePointRight(3).floatValue() % 1000);}
	public double XtoDouble() {return under.doubleValue();}
	public int XtoInt() {return (int)under.doubleValue();}
	public long XtoLong() {return (long)under.doubleValue();}
	public DecimalAdp Op_add(DecimalAdp v) 				{return new DecimalAdp(under.add(v.under, DecimalAdp_.Gplx_rounding_context));}
	public DecimalAdp Op_subtract(DecimalAdp v) 		{return new DecimalAdp(under.subtract(v.under, DecimalAdp_.Gplx_rounding_context));}
	public DecimalAdp Op_mult(DecimalAdp v)				{return new DecimalAdp(under.multiply(v.under));}
	public DecimalAdp Op_mult(double v) 				{return new DecimalAdp(under.multiply(new BigDecimal(v, DecimalAdp_.Gplx_rounding_context)));}
	public DecimalAdp Op_mult(long v)					{return new DecimalAdp(under.multiply(new BigDecimal(v)));}
	public DecimalAdp Op_divide(DecimalAdp v)			{return new DecimalAdp(under.divide(v.under, DecimalAdp_.Gplx_rounding_context));}
	public DecimalAdp Op_mod(DecimalAdp v)				{return new DecimalAdp(under.remainder(v.under, DecimalAdp_.Gplx_rounding_context));}
	public DecimalAdp Op_sqrt()							{return new DecimalAdp(new BigDecimal(Math_.Sqrt(under.doubleValue())));}
	public DecimalAdp Op_abs()							{return new DecimalAdp(under.abs(DecimalAdp_.Gplx_rounding_context));}
	public DecimalAdp Op_pow(int v)						{return new DecimalAdp(under.pow(v, DecimalAdp_.Gplx_rounding_context));}
	public DecimalAdp Op_truncate_decimal() 			{return new DecimalAdp(under.intValue());}
	public DecimalAdp Op_round(int v) 					{return new DecimalAdp(under.setScale(v, RoundingMode.HALF_UP));}
	public boolean Comp_gte(DecimalAdp v) 				{return under.doubleValue() >= v.under.doubleValue();}
	public boolean Comp_gte(int v) 						{return under.doubleValue() >= v;}
	public boolean Comp_lte(DecimalAdp v) 				{return under.doubleValue() <= v.under.doubleValue();}
	public boolean Comp_lte(int v) 						{return under.doubleValue() <= v;}
	public boolean Comp_gt(DecimalAdp v) 				{return under.doubleValue() > v.under.doubleValue();}
	public boolean Comp_gt(int v) 						{return under.doubleValue() > v;}
	public boolean Comp_lt(DecimalAdp v)			 	{return under.doubleValue() < v.under.doubleValue();}
	public boolean Comp_lt(int v) 						{return under.doubleValue() < v;}
	public boolean Eq(int v) 							{return under.doubleValue() == v;}
	}
