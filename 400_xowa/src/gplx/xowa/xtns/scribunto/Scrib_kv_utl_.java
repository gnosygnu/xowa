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
package gplx.xowa.xtns.scribunto;
import gplx.types.basics.lists.List_adp;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.basics.utls.StringUtl;
public class Scrib_kv_utl_ {
	public static KeyVal[] base1_obj_(Object v) {return new KeyVal[] {KeyVal.NewInt(0 + Scrib_core.Base_1, v)};}
	public static KeyVal[] base1_many_(Object... vals) {
		int len = vals.length;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++)
			rv[i] = KeyVal.NewInt(i + Scrib_core.Base_1, vals[i]);
		return rv;
	}
	public static KeyVal[] base1_many_ary_(Object... vals) {
		int len = vals.length;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++)
			rv[i] = KeyVal.NewInt(i + Scrib_core.Base_1, vals[i]);
		return rv;
	}
	public static KeyVal[] base1_list_(List_adp list) {
		int len = list.Len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++)
			rv[i] = KeyVal.NewInt(i + Scrib_core.Base_1, list.GetAt(i));
		return rv;
	}
	public static String Val_to_str(KeyVal[] ary, int idx) {
		if (ary == null) throw ErrUtl.NewArgs("ary is null");
		int ary_len = ary.length;
		if (ary_len == 0 && idx == 0) return "";	// NOTE: Modules can throw exceptions in which return value is nothing; do not fail; return ""; EX: -logy; DATE:2013-10-14
		if (idx >= ary_len) throw ErrUtl.NewArgs("idx is not in bounds", "idx", idx, "len", KeyValUtl.AryToStr(ary));
		Object o = ary[idx].Val();
		try {return (String)o;}
		catch (Exception e) {throw ErrUtl.NewCast(e, String.class, o);}
	}
	public static KeyVal[] Val_to_KeyVal_ary(KeyVal[] ary, int idx) {
		if (ary == null) throw ErrUtl.NewArgs("ary is null"); if (idx >= ary.length) throw ErrUtl.NewArgs("idx is not in bounds", "idx", idx, "len", KeyValUtl.AryToStr(ary));
		try {return (KeyVal[])ary[idx].Val();}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "cast as Keyval[] failed", "ary", KeyValUtl.AryToStr(ary));}
	}
	public static Object Get_sub_by_key_or_null(KeyVal[] ary, String key) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			KeyVal sub = ary[i];
			if (StringUtl.Eq(key, sub.KeyToStr())) return sub.Val();
		}
		return null;
	}
}
