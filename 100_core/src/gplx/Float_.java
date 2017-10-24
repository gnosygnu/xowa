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
public class Float_ {
	public static final String Cls_val_name = "float";
	public static final Class<?> Cls_ref_type = Float.class;
	public static final float NaN = Float.NaN;;					
	public static boolean IsNaN(float v) {return Float.isNaN(v);}		
	public static float cast(Object obj)	{try {return (Float)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, float.class, obj);}}
	public static float parse(String raw)	{try {return Float.parseFloat(raw);} catch(Exception exc) {throw Err_.new_parse_exc(exc, float.class, raw);}} 
	public static int Compare(float lhs, float rhs) {
		if		( lhs == rhs)	return CompareAble_.Same;
		else if ( lhs <  rhs)	return CompareAble_.Less;
		else	/*lhs >  rhs*/	return CompareAble_.More;
	}
	public static String To_str(float v) {
				int v_int = (int)v;
		return v - v_int == 0 ? Int_.To_str(v_int) : Float.toString(v);
			}
	public static float Div(int val, int divisor) {return (float)val / (float)divisor;}
	public static float Div(long val, long divisor) {return (float)val / (float)divisor;}
	public static int RoundUp(float val) {
		int rv = (int)val;
		return (rv == val) ? rv : rv + 1;
	}
}
