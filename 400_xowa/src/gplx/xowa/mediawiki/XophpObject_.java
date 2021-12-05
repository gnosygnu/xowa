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
import gplx.Double_;
import gplx.Int_;
import gplx.String_;
import gplx.Type_;
import gplx.objects.arrays.ArrayUtl;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.AsciiByte;
public class XophpObject_ {
	public static final Object False = null; // handles code like "if ($var === false)" where var is an Object;
	public static boolean is_true(Object val) {return !empty_obj(val);}
	public static boolean is_false(Object val) {return empty_obj(val);}
	public static boolean is_null(Object val) {return val == null;}

	// REF.PHP:http://php.net/manual/en/function.empty.php
	public static boolean empty(String v)     {return v == null || String_.Len_eq_0(v);}
	public static boolean empty(byte[] v)     {return v == null || v.length == 0;}
	public static boolean empty(boolean v)       {return v == false;}
	public static boolean empty(int v)        {return v == 0;}
	public static boolean empty(double v)     {return v == 0;}
	public static boolean empty_obj(Object v) {
		if (v == null)
			return true;

		Class<?> type = Type_.Type_by_obj(v);
		if (Type_.Eq(type, BoolUtl.ClsRefType)) {
			if (!BoolUtl.Cast(v))
				return true;
		}
		else if (Type_.Eq(type, Int_.Cls_ref_type)) {
			if (Int_.Cast(v) == 0)
				return true;
		}
		else if (Type_.Eq(type, Double_.Cls_ref_type)) {
			if (Double_.cast(v) == 0)
				return true;
		}
		else if (Type_.Eq(type, String_.Cls_ref_type)) {
			String s = String_.cast(v);
			if (String_.Len_eq_0(s) || String_.Eq(s, "0"))
				return true;
		}
		else {
			if (Type_.Is_array(type)) {
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
	public static final int NULL_INT = Int_.Max_value;
	public static final double NULL_DOUBLE = Double_.MinValue;
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
