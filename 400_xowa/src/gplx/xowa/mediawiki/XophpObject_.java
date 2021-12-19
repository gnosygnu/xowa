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
package gplx.xowa.mediawiki;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
public class XophpObject_ {
	public static final Object False = null; // handles code like "if ($var === false)" where var is an Object;
	public static boolean is_true(Object val) {return !empty_obj(val);}
	public static boolean is_false(Object val) {return empty_obj(val);}
	public static boolean is_null(Object val) {return val == null;}

	// REF.PHP:http://php.net/manual/en/function.empty.php
	public static boolean empty(String v)     {return v == null || StringUtl.IsNullOrEmpty(v);}
	public static boolean empty(byte[] v)     {return v == null || v.length == 0;}
	public static boolean empty(boolean v)       {return v == false;}
	public static boolean empty(int v)        {return v == 0;}
	public static boolean empty(double v)     {return v == 0;}
	public static boolean empty_obj(Object v) {
		if (v == null)
			return true;

		Class<?> type = ClassUtl.TypeByObj(v);
		if (ClassUtl.Eq(type, BoolUtl.ClsRefType)) {
			if (!BoolUtl.Cast(v))
				return true;
		}
		else if (ClassUtl.Eq(type, IntUtl.ClsRefType)) {
			if (IntUtl.Cast(v) == 0)
				return true;
		}
		else if (ClassUtl.Eq(type, DoubleUtl.ClsRefType)) {
			if (DoubleUtl.Cast(v) == 0)
				return true;
		}
		else if (ClassUtl.Eq(type, StringUtl.ClsRefType)) {
			String s = StringUtl.Cast(v);
			if (StringUtl.IsNullOrEmpty(s) || StringUtl.Eq(s, "0"))
				return true;
		}
		else {
			if (ClassUtl.IsArray(type)) {
				if (ArrayUtl.Len(ArrayUtl.Cast(v)) == 0)
					return true;
			}
		}
		return false;
	}
	public static boolean isset(byte[] v)   {return v != null;}
	public static boolean isset(int v)      {return v != NULL_INT;}
	public static boolean isset(double v)   {return v != NULL_DOUBLE;}
	public static boolean isset_obj(Object v){return v != null;}
	public static boolean istrue(int v)     {return v != NULL_INT;}
	public static boolean isnumeric(byte[] src) {
		if (src == null) return false;
		int len = src.length;
		for (int i = 0; i < len; i++) {
			byte b = src[i];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					break;
				default:
					return false;
			}
		}
		return true;
	}

	public static boolean is_null(int v) {return v == NULL_INT;}
	public static final int NULL_INT = IntUtl.MaxValue;
	public static final double NULL_DOUBLE = DoubleUtl.MinValue;
	public static final byte[] NULL_BRY = null;
	public static Object coalesce(Object val, Object if_null) {return val == null ? if_null : val;}

	// REF.PHP:https://www.php.net/manual/en/function.is-object.php
    public static boolean is_object(Object o) {
    	return o != null;
    }

	// REF.PHP:https://www.php.net/manual/en/language.operators.comparison.php#language.operators.comparison.ternary
	// equivalent to ?:
    public static Object Elvis(Object o, Object or) {return is_true(o) ? o : or;}

    // equivalent to ??
	public static Object Coalesce(Object o, Object or) {return isset_obj(o) ? o : or;}

	// equivalent to (string)o; see https://www.php.net/manual/en/function.array-diff.php
	public static String ToStr(Object o) {
		return o.toString();
	}
}
