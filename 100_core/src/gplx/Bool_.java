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
import gplx.langs.gfs.*;
public class Bool_ {
	public static final String Cls_val_name = "bool";
	public static final    Class<?> Cls_ref_type = Boolean.class; 
	public static final boolean		N		= false	, Y			= true;
	public static final byte		N_byte	= 0		, Y_byte	= 1		, __byte = 127;
	public static final int		N_int	= 0		, Y_int		= 1		, __int  =  -1;
	public static final    byte[] N_bry = new byte[] {Byte_ascii.Ltr_n}, Y_bry = new byte[] {Byte_ascii.Ltr_y};
	public static final String		True_str = "true", False_str = "false";
	public static final    byte[] True_bry = Bry_.new_a7(True_str), False_bry = Bry_.new_a7(False_str);

	public static boolean		Cast(Object obj)				{try {return (Boolean)obj;} catch (Exception e) {throw Err_.new_type_mismatch_w_exc(e, boolean.class, obj);}}
	public static boolean		Cast_or(Object obj, boolean or)	{try {return (Boolean)obj;} catch (Exception e) {Err_.Noop(e); return or;}}
	public static boolean		Parse(String raw) {
		if		(	String_.Eq(raw, True_str)
				||	String_.Eq(raw, "True")	// needed for Store_Wtr(){boolVal.toString();}
				)
			return true;
		else if (	String_.Eq(raw, False_str)
				||	String_.Eq(raw, "False")
			)
			return false;
		throw Err_.new_parse_type(boolean.class, raw);
	}
	public static boolean		Parse_or(String raw, boolean or) {
		if		(	String_.Eq(raw, True_str)
				||	String_.Eq(raw, "True")	// needed for Store_Wtr(){boolVal.toString();}
				)
			return true;
		else if (	String_.Eq(raw, False_str)
				||	String_.Eq(raw, "False")
			)
			return false;
		return or;
	}
	public static int Compare(boolean lhs, boolean rhs) {
		if		( lhs ==  rhs)	return CompareAble_.Same;
		else if (!lhs &&  rhs)	return CompareAble_.Less;
		else	/*lhs && !rhs*/ return CompareAble_.More;
	}
	public static boolean		By_int(int v)			{return v == Y_int;}
	public static int		To_int(boolean v)			{return v ? Y_int		: N_int;}
	public static byte		To_byte(boolean v)			{return v ? Y_byte		: N_byte;}
	public static String	To_str_lower(boolean v)	{return v ? True_str	: False_str;}
}
