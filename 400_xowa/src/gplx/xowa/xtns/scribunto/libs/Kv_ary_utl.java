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
package gplx.xowa.xtns.scribunto.libs;
import gplx.langs.jsons.Json_wtr;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.TypeIds;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.KeyVal;
import gplx.types.errs.ErrUtl;
class Kv_ary_utl {
	public static KeyVal[] new_(boolean base_1, Object... vals) {
		int len = vals.length;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; ++i)
			rv[i] = KeyVal.NewInt(i + (base_1 ? 1 : 0), vals[i]);
		return rv;
	}
	public static KeyVal[] new_kvs(KeyVal... vals) {return vals;}
	public static String Ary_to_str(Json_wtr wtr, KeyVal[] ary) {
		wtr.Doc_nde_bgn();
		Ary_to_str(wtr, 0, ary);
		return wtr.Doc_nde_end().To_str_and_clear();
	}
	private static void Ary_to_str(Json_wtr wtr, int indent, KeyVal[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Ary_to_str__itm(wtr, indent, ary[i]);
		}
	}
	private static void Ary_to_str__itm(Json_wtr wtr, int indent, KeyVal itm) {
		Object val = itm.Val();
		Class<?> val_type = val.getClass();
		int type_tid = TypeIds.ToIdByCls(val_type);
		if (type_tid == TypeIds.IdObj) {
			if (ClassUtl.Eq(val_type, KeyVal[].class))
				Ary_to_str__nde(wtr, indent, itm.KeyToStr(), (KeyVal[])itm.Val());
			else if (ClassUtl.IsArray(val_type))
				Ary_to_str__ary(wtr, indent, itm.KeyToStr(), ArrayUtl.Cast(val));
			else
				throw ErrUtl.NewUnhandled(type_tid);
		}
		else
			wtr.Kv_obj(BryUtl.NewU8(itm.KeyToStr()), itm.Val(), type_tid);
	}
	private static void Ary_to_str__ary(Json_wtr wtr, int indent, String key, Object array) {
		wtr.Ary_bgn(key);
		Ary_to_str__ary_itms(wtr, indent + 1, array);
		wtr.Ary_end();
	}
	private static void Ary_to_str__nde(Json_wtr wtr, int indent, String key, KeyVal[] kv) {
		wtr.Nde_bgn(key);
		Ary_to_str(wtr, indent + 1, kv);
		wtr.Nde_end();
	}
	private static void Ary_to_str__ary_itms(Json_wtr wtr, int indent, Object array) {
		int len = ArrayUtl.Len(array);
		for (int j = 0; j < len; ++j) {
			Object itm = ArrayUtl.GetAt(array, j);
			Class<?> itm_type = itm.getClass();
			int itm_type_tid = TypeIds.ToIdByCls(itm_type);
			if (itm_type_tid == TypeIds.IdObj) {
				if (ClassUtl.Eq(itm_type, KeyVal.class))
					Ary_to_str__itm(wtr, indent + 1, (KeyVal)itm);
				else if (ClassUtl.IsArray(itm_type))
					Ary_to_str__ary_itms(wtr, indent + 1, ArrayUtl.Cast(itm));
				else
					throw ErrUtl.NewUnhandled(itm_type);
			}
			else
				wtr.Ary_itm_by_type_tid(itm_type_tid, itm);
		}
	}
}
