/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx;

public class Double_ {
	public static final String Cls_val_name = "double";
	public static final    Class<?> Cls_ref_type = Double.class; 
	public static final double
	  MinValue		= Double.MIN_VALUE			
	, NaN			= Double.NaN				
	, Inf_pos		= Double.POSITIVE_INFINITY	
	;
	public static final    byte[]
	  NaN_bry		= Bry_.new_a7("NaN")
	, Inf_pos_bry	= Bry_.new_a7("INF")
	;
	public static boolean IsNaN(double v) {return Double.isNaN(v);}	
	public static double cast(Object o)						{try {return (Double)o;} catch(Exception e) {throw Err_.new_type_mismatch_w_exc(e, double.class, o);}}
	public static double parse(String raw)					{try {return Double.parseDouble(raw);} catch(Exception e) {throw Err_.new_parse_exc(e, double.class, raw);}}
	public static double parse_or(String raw, double v)		{try {return Double.parseDouble(raw);} catch(Exception e) {Err_.Noop(e); return v;}}
	public static double coerce_(Object v) {
		try {String s = String_.as_(v); return s == null ? Double_.cast(v) : Double_.parse(s);}
		catch (Exception e) {throw Err_.new_cast(e, double.class, v);}
	}
	public static String To_str(double v) {
				int v_int = (int)v;
		return v - v_int == 0 ? Int_.To_str(v_int) : Double.toString(v);
			}
	public static String To_str_loose(double v) {
		int v_as_int = (int)v;			
		return v == v_as_int
			? Int_.To_str(v_as_int)		// convert to int, and call print String to eliminate any trailing decimal places
			// DATE:2014-07-29; calling ((float)v).toString is better at removing trailing 0s than String.format("%g", v). note that .net .toString() handles it better; EX:2449.600000000000d
			// DATE:2020-08-12; calling ToStrByPrintF b/c better at removing trailing 0s; ISSUE#:697;
			: gplx.objects.primitives.Double_.ToStrByPrintF(v);
	}
	public static int Compare(double lhs, double rhs) {
		if		(lhs == rhs) 	return CompareAble_.Same;
		else if (lhs < rhs)		return CompareAble_.Less;
		else 					return CompareAble_.More;
	}
}
