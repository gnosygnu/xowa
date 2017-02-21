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
public class Object_ {
	public static final String Cls_val_name = "Object";
	public static final    Object[] Ary_empty = new Object[0];
	public static Object[] Ary(Object... ary) {return ary;}
	public static Object[] Ary_add(Object[] lhs, Object[] rhs) {
		int lhs_len = lhs.length, rhs_len = rhs.length;
		if		(lhs_len == 0) return rhs;
		else if (rhs_len == 0) return lhs;
		int rv_len = lhs_len + rhs_len;
		Object[] rv = new Object[rv_len];
		for (int i = 0; i < lhs_len; ++i)
			rv[i] = lhs[i];
		for (int i = lhs_len; i < rv_len; ++i)
			rv[i] = rhs[i - lhs_len];
		return rv;
	}
	public static boolean Eq(Object lhs, Object rhs) {
		if		(lhs == null && rhs == null)	return true;
		else if (lhs == null || rhs == null)	return false;
		else									return lhs.equals(rhs);		
	}
	public static String Xto_str_or(Object v, String or)		{return v == null ? or					: ToString_lang(v);}
	public static String Xto_str_strict_or_null(Object v)		{return v == null ? null				: ToString_lang(v);}
	public static String Xto_str_strict_or_null_mark(Object v)	{return v == null ? String_.Null_mark	: ToString_lang(v);}
	public static String Xto_str_strict_or_empty(Object v)		{return v == null ? String_.Empty		: ToString_lang(v);}
	private static String ToString_lang(Object v) {
		Class<?> c = v.getClass();
		if		(Type_adp_.Eq(c, String_.Cls_ref_type))		return (String)v;
		else if	(Type_adp_.Eq(c, Bry_.Cls_ref_type))		return String_.new_u8((byte[])v);
		else												return v.toString();	
	}
	public static String Xto_str_loose_or(Object v, String or) {	// tries to pretty-print doubles; also standardizes true/false; DATE:2014-07-14
		if (v == null) return null;
		Class<?> c = Type_adp_.ClassOf_obj(v);
		if		(Type_adp_.Eq(c, String_.Cls_ref_type))		return (String)v;
		else if	(Type_adp_.Eq(c, Bry_.Cls_ref_type))		return String_.new_u8((byte[])v);
		else if (Type_adp_.Eq(c, Bool_.Cls_ref_type))		return Bool_.Cast(v) ? Bool_.True_str : Bool_.False_str;	// always return "true" / "false"
		else if	(Type_adp_.Eq(c, Double_.Cls_ref_type))		return Double_.To_str_loose(Double_.cast(v));
		else												return v.toString();	
	}
	public static final    Object Null = null;
	public static final    byte[] Bry__null = Bry_.new_a7("null");
}