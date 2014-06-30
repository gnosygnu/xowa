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
import java.math.BigDecimal;import java.math.MathContext;import java.math.RoundingMode;public class DecimalAdp_ {
	public static DecimalAdp as_(Object obj) {return obj instanceof DecimalAdp ? (DecimalAdp)obj : null;}
	public static final DecimalAdp Zero = new DecimalAdp(0);
	public static final DecimalAdp One = new DecimalAdp(1);
	public static final DecimalAdp Neg1 = new DecimalAdp(-1);
	public static final DecimalAdp Const_e = DecimalAdp_.double_(Math_.E);
	public static final DecimalAdp Const_pi = DecimalAdp_.double_(Math_.Pi);
	public static DecimalAdp base1000_(long v) {return divide_(v, 1000);}
	public static DecimalAdp parts_1000_(long num, int frc) {return divide_((num * (1000)) + frc, 1000);}
	public static DecimalAdp parts_(long num, int frc) {
		//			int log10 = frc == 0 ? 0 : (Math_.Log10(frc) + 1);
		//			int pow10 = (int)Math_.Pow(10, log10);
		int pow10 = XtoPow10(frc);
		return divide_((num * (pow10)) + frc, pow10);
	}
	public static DecimalAdp cast_(Object obj) {return (DecimalAdp)obj;}
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
		else throw Err_.new_("value must be between 0 and 1 billion").Add("v", v);
	}
	public static String CalcPctStr(long dividend, long divisor, String fmt) {
		if (divisor == 0) return "%ERR";
		return DecimalAdp_.float_(Float_.Div(dividend, divisor) * 100).XtoStr(fmt) + "%";
	}
	public static DecimalAdp divide_safe_(long lhs, long rhs) {return rhs == 0 ? Zero : divide_(lhs, rhs);}
		public static DecimalAdp divide_(long lhs, long rhs) {		return new DecimalAdp(new BigDecimal(lhs).divide(new BigDecimal(rhs), Gplx_rounding_context));	}	public static DecimalAdp int_(int v) {return new DecimalAdp(new BigDecimal(v));}	public static DecimalAdp long_(long v) {return new DecimalAdp(new BigDecimal(v));}
	public static DecimalAdp float_(float v) {return new DecimalAdp(new BigDecimal(v));}	public static DecimalAdp double_(double v) {return new DecimalAdp(new BigDecimal(v));}
	public static DecimalAdp double_thru_str_(double v) {return new DecimalAdp(BigDecimal.valueOf(v));}
	public static DecimalAdp db_(Object v) {return new DecimalAdp((BigDecimal)v);}	public static DecimalAdp parse_(String raw) {return new DecimalAdp(new BigDecimal(raw));}	public static DecimalAdp pow_10_(int v) {return new DecimalAdp(new BigDecimal(1).scaleByPowerOfTen(v));}
	public static final MathContext RoundDownContext = new MathContext(0, RoundingMode.DOWN);	static final MathContext Gplx_rounding_context = new MathContext(28, RoundingMode.HALF_UP);	}
