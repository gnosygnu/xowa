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
import gplx.core.strings.*;
public class Keyval_ {
	public static final    Keyval[] Ary_empty = new Keyval[0];
	public static Keyval[] Ary(Keyval... ary) {return ary;}
	public static Keyval[] Ary_cast_(Object o) {
		try {return (Keyval[])o;}
		catch (Exception e) {throw Err_.new_cast(e, Keyval.class, o);}
	}
	public static Keyval[] Ary_insert(Keyval[] orig, boolean insert_at_end, Keyval... vals) {
		int orig_len = orig.length, vals_len = vals.length;
		int rv_len = orig_len + vals_len;
		Keyval[] rv = new Keyval[rv_len];
		int vals_bgn = 0		, vals_end = vals_len;
		int orig_bgn = vals_len	, orig_end = rv_len;
		if (insert_at_end) {
			orig_bgn = 0		; orig_end = orig_len;
			vals_bgn = orig_len	; vals_end = rv_len;
		}
		for (int i = orig_bgn; i < orig_end; i++)
			rv[i] = orig[i - orig_bgn];
		for (int i = vals_bgn; i < vals_end; i++)
			rv[i] = vals[i - vals_bgn];
		return rv;
	}
	public static String Ary_to_str(Keyval... ary) {
		String_bldr sb = String_bldr_.new_();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Keyval itm = ary[i];
			if (itm == null) {
				sb.Add("<<NULL>>");
				continue;
			}
			sb.Add(itm.Key()).Add("=");
			Object itm_val = itm.Val();
			if (Type_adp_.Eq_typeSafe(itm_val, Keyval[].class))
				sb.Add(Ary_to_str((Keyval[])itm_val));
			else
				sb.Add(Object_.Xto_str_strict_or_null_mark(itm_val));
			sb.Add_char_nl();
		}
		return sb.To_str();
	}
	public static Object Ary_get_by_key_or_null(Keyval[] ary, String key) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Keyval kv = ary[i];
			if (String_.Eq(kv.Key(), key)) return kv.Val();
		}
		return null;
	}
	public static String Ary__to_str__nest(Keyval... ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		Ary__to_str__nest(bfr, 0, ary);
		return bfr.To_str_and_clear();
	}
	private static void Ary__to_str__nest(Bry_bfr bfr, int indent, Keyval[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Keyval itm = ary[i];
			if (indent > 0)
				bfr.Add_byte_repeat(Byte_ascii.Space, indent * 2);					// add indent	: "  "
			bfr.Add_str_u8(Object_.Xto_str_strict_or_empty(itm.Key())).Add_byte_eq();// add key + eq : "key="
			Object val = itm.Val();
			if (val == null)
				bfr.Add_str_a7(String_.Null_mark);
			else {
				Class<?> val_type = Type_adp_.ClassOf_obj(val);
				if		(Type_adp_.Eq(val_type, Keyval[].class)) {				// val is Keyval[]; recurse
					bfr.Add_byte_nl();												// add nl		: "\n"
					Ary__to_str__nest(bfr, indent + 1, (Keyval[])val);
					continue;														// don't add \n below
				}
				else if (Type_adp_.Eq(val_type, Bool_.Cls_ref_type)) {					// val is boolean
					boolean val_as_bool = Bool_.Cast(val);
					bfr.Add(val_as_bool ? Bool_.True_bry : Bool_.False_bry);		// add "true" or "false"; don't call toString
				}
				else
					bfr.Add_str_u8(Object_.Xto_str_strict_or_null_mark(val));						// call toString()
			}
			bfr.Add_byte_nl();
		}
	}
	public static Keyval as_(Object obj) {return obj instanceof Keyval ? (Keyval)obj : null;}
	public static Keyval new_(String key)				{return new Keyval(Type_adp_.Tid__str, key, key);}
	public static Keyval new_(String key, Object val)	{return new Keyval(Type_adp_.Tid__str, key, val);}
	public static Keyval int_(int key, Object val)		{return new Keyval(Type_adp_.Tid__int, key, val);}
	public static Keyval obj_(Object key, Object val)	{return new Keyval(Type_adp_.Tid__obj, key, val);}
}
