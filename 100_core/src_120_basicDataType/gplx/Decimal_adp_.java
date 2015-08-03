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
import java.math.BigDecimal;import java.math.MathContext;import java.math.RoundingMode;public class Decimal_adp_ {
	public static final Class<?> Cls_ref_type = Decimal_adp.class;
	public static Decimal_adp as_(Object obj) {return obj instanceof Decimal_adp ? (Decimal_adp)obj : null;}
	public static final Decimal_adp Zero = new Decimal_adp(0);
	public static final Decimal_adp One = new Decimal_adp(1);
	public static final Decimal_adp Neg1 = new Decimal_adp(-1);
	public static final Decimal_adp Const_e = Decimal_adp_.double_(Math_.E);
	public static final Decimal_adp Const_pi = Decimal_adp_.double_(Math_.Pi);
	public static Decimal_adp base1000_(long v) {return divide_(v, 1000);}
	public static Decimal_adp parts_1000_(long num, int frc) {return divide_((num * (1000)) + frc, 1000);}
	public static Decimal_adp parts_(long num, int frc) {
		//			int log10 = frc == 0 ? 0 : (Math_.Log10(frc) + 1);
		//			int pow10 = (int)Math_.Pow(10, log10);
		int pow10 = XtoPow10(frc);
		return divide_((num * (pow10)) + frc, pow10);
	}
	public static Decimal_adp cast_(Object obj) {return (Decimal_adp)obj;}
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
		public static Decimal_adp divide_(long lhs, long rhs) {		return new Decimal_adp(new BigDecimal(lhs).divide(new BigDecimal(rhs), Gplx_rounding_context));	}	public static Decimal_adp int_(int v) {return new Decimal_adp(new BigDecimal(v));}	public static Decimal_adp long_(long v) {return new Decimal_adp(new BigDecimal(v));}
	public static Decimal_adp float_(float v) {return new Decimal_adp(new BigDecimal(v));}	public static Decimal_adp double_(double v) {return new Decimal_adp(new BigDecimal(v));}
	public static Decimal_adp double_thru_str_(double v) {return new Decimal_adp(BigDecimal.valueOf(v));}
	public static Decimal_adp db_(Object v) {return new Decimal_adp((BigDecimal)v);}	public static Decimal_adp parse_(String raw) {return new Decimal_adp(new BigDecimal(raw));}	public static Decimal_adp pow_10_(int v) {return new Decimal_adp(new BigDecimal(1).scaleByPowerOfTen(v));}
	public static final MathContext RoundDownContext = new MathContext(0, RoundingMode.DOWN);	public static final MathContext Gplx_rounding_context = new MathContext(14, RoundingMode.HALF_UP);	// changed from 28 to 14; DATE:2015-07-31	}
