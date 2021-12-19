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
package gplx.types.basics.utls;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.errs.ErrUtl;
public class FloatUtl {
	public static final String ClsValName = "float";
	public static final Class<?> ClsRefType = Float.class;
	public static final float NaN = Float.NaN;
	public static boolean IsNaN(float v) {return Float.isNaN(v);}
	public static float Cast(Object obj)  {try {return (Float)obj;} catch(Exception exc) {throw ErrUtl.NewCast(float.class, obj);}}
	public static float Parse(String raw) {try {return Float.parseFloat(raw);} catch(Exception exc) {throw ErrUtl.NewParse(float.class, raw);}}
	public static int Compare(float lhs, float rhs) {
		if      ( lhs == rhs)  return CompareAbleUtl.Same;
		else if ( lhs <  rhs)  return CompareAbleUtl.Less;
		else    /*lhs >  rhs*/ return CompareAbleUtl.More;
	}
	public static String ToStr(float v) {
		int vAsInt = (int)v;
		return v - vAsInt == 0 ? IntUtl.ToStr(vAsInt) : Float.toString(v);
	}
	public static float Div(int val, int divisor) {return (float)val / (float)divisor;}
	public static float Div(long val, long divisor) {return (float)val / (float)divisor;}
	public static int RoundUp(float val) {
		int rv = (int)val;
		return (rv == val) ? rv : rv + 1;
	}
}
