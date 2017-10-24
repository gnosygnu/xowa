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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Scrib_kv_utl_ {
	public static Keyval[] base1_obj_(Object v) {return new Keyval[] {Keyval_.int_(0 + Scrib_core.Base_1, v)};}
	public static Keyval[] base1_many_(Object... vals) {
		int len = vals.length;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++)
			rv[i] = Keyval_.int_(i + Scrib_core.Base_1, vals[i]);
		return rv;
	}
	public static Keyval[] base1_many_ary_(Object... vals) {
		int len = vals.length;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++)
			rv[i] = Keyval_.int_(i + Scrib_core.Base_1, vals[i]);
		return rv;
	}
	public static Keyval[] base1_list_(List_adp list) {
		int len = list.Count();
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++)
			rv[i] = Keyval_.int_(i + Scrib_core.Base_1, list.Get_at(i));
		list.Clear();
		return rv;
	}
	public static Keyval[] flat_many_(Object... vals) {
		int len = vals.length;
		Keyval[] rv = new Keyval[len / 2];
		for (int i = 0; i < len; i += 2)
			rv[i / 2] = Keyval_.obj_(vals[i], vals[i + 1]);
		return rv;
	}
	public static String Val_to_str(Keyval[] ary, int idx) {
		if (ary == null) throw Err_.new_wo_type("ary is null");
		int ary_len = ary.length;
		if (ary_len == 0 && idx == 0) return "";	// NOTE: Modules can throw exceptions in which return value is nothing; do not fail; return ""; EX: -logy; DATE:2013-10-14
		if (idx >= ary_len) throw Err_.new_wo_type("idx is not in bounds", "idx", idx, "len", Keyval_.Ary_to_str(ary));
		Object o = ary[idx].Val();
		try {return (String)o;}
		catch (Exception e) {throw Err_.new_cast(e, String.class, o);}
	}
	public static Keyval[] Val_to_KeyVal_ary(Keyval[] ary, int idx) {
		if (ary == null) throw Err_.new_wo_type("ary is null"); if (idx >= ary.length) throw Err_.new_wo_type("idx is not in bounds", "idx", idx, "len", Keyval_.Ary_to_str(ary));
		try {return (Keyval[])ary[idx].Val();}
		catch (Exception e) {throw Err_.new_exc(e, "scrib", "cast as Keyval[] failed", "ary", Keyval_.Ary_to_str(ary));}
	}
	public static Object Get_sub_by_key_or_null(Keyval[] ary, String key) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Keyval sub = ary[i];
			if (String_.Eq(key, sub.Key())) return sub.Val();
		}
		return null;
	}
}
