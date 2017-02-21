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
public class Math_ {
	public static double	Pow(double val, double exponent) {return java.lang.Math.pow(val, exponent);}
	public static double	Pi = java.lang.Math.PI; 
	public static double	E = java.lang.Math.E; 
	public static int		Ceil_as_int(double v) {return (int)Ceil(v);}
	public static double	Ceil(double v) {return java.lang.Math.ceil(v);}
	public static int		Floor_as_int(double v) {return (int)Floor(v);}
	public static double	Floor(double v) {return java.lang.Math.floor(v);}
	public static double	Round(double v, int places) {
				return java.math.BigDecimal.valueOf(v).setScale(places, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
			}
	public static int Trunc(double v) {return (int)v;}
	public static double Exp(double v) {return java.lang.Math.exp(v);}
	public static double Log(double v) {return java.lang.Math.log(v);}
	public static double Sin(double v) {return java.lang.Math.sin(v);}
	public static double Cos(double v) {return java.lang.Math.cos(v);}
	public static double Tan(double v) {return java.lang.Math.tan(v);}
	public static double Asin(double v) {return java.lang.Math.asin(v);}
	public static double Acos(double v) {return java.lang.Math.acos(v);}
	public static double Atan(double v) {return java.lang.Math.atan(v);}
	public static double Sqrt(double v) {return java.lang.Math.sqrt(v);}
	public static int	Abs(int val)	{return val > 0 ? val : val * -1;}
	public static long	Abs(long val)	{return val > 0 ? val : val * -1;}
	public static float	Abs(float val)	{return val > 0 ? val : val * -1;}
	public static double Abs_double(double val)	{return val > 0 ? val : val * -1;}
	public static int	Log10(int val) {
		if (val <= 0) return Int_.Min_value;
		int rv = -1, baseVal = 10;
		while (val != 0) {
			val = (val / baseVal);
			rv++;
		}
		return rv;
	}
	public static int Div_safe_as_int(int val, int divisor) {return divisor == 0 ? 0 : val / divisor;}
	public static long Div_safe_as_long(long val, long divisor) {return divisor == 0 ? 0 : val / divisor;}
	public static double Div_safe_as_double(double val, double divisor) {return divisor == 0 ? 0 : val / divisor;}
	public static int Min(int val0, int val1) {return val0 < val1 ? val0 : val1;}
	public static int Max(int val0, int val1) {return val0 > val1 ? val0 : val1;}
	public static int[] Base2Ary(int v, int max) {
		int[] idxs = new int[32];
		int cur = v, mult = max, idx = 0;
		while (mult > 0) {
			int tmp = cur / mult;
			if (tmp >= 1) {
				idxs[idx++] = mult;
				cur -= mult;
			}
			mult /= 2;
		}
		int[] rv = new int[idx];
		for (int i = 0; i < idx; i++)
			rv[i] = idxs[idx - i - 1];
		return rv;
	}
}