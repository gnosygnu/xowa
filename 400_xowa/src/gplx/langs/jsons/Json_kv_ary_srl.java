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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
public class Json_kv_ary_srl {
	public static Keyval Kv_by_itm(Json_itm itm) {
		switch (itm.Tid()) {
			case Json_itm_.Tid__kv:
				Json_kv kv = (Json_kv)itm;
				return Keyval_.new_(kv.Key_as_str(), Val_by_itm(kv.Val()));
			default:
				throw Err_.new_unhandled(itm.Tid());
		}
	}
	private static Object Val_by_itm(Json_itm itm) {
		switch (itm.Tid()) {
			case Json_itm_.Tid__bool:		return Bool_.To_str_lower(Bool_.Cast(itm.Data()));
			case Json_itm_.Tid__int:
			case Json_itm_.Tid__null:
			case Json_itm_.Tid__str:
			case Json_itm_.Tid__decimal:	return itm.Data();
			case Json_itm_.Tid__ary:		return Val_by_itm_ary((Json_ary)itm);
			case Json_itm_.Tid__nde:		return Val_by_itm_nde((Json_nde)itm);
			case Json_itm_.Tid__kv:			// kv should never be val; EX: "a":"b":c; not possible
			default:						throw Err_.new_unhandled(itm.Tid());
		}
	}
	private static Keyval[] Val_by_itm_ary(Json_ary itm) {
		int subs_len = itm.Len();
		Keyval[] rv = new Keyval[subs_len];
		for (int i = 0; i < subs_len; i++) {
			Json_itm sub = itm.Get_at(i);
			Keyval kv = Keyval_.new_(Int_.To_str(i + Int_.Base1), Val_by_itm(sub));
			rv[i] = kv;
		}
		return rv;
	}
	public static Keyval[] Val_by_itm_nde(Json_nde itm) {
		int subs_len = itm.Len();
		Keyval[] rv = new Keyval[subs_len];
		for (int i = 0; i < subs_len; i++) {
			Json_itm sub = itm.Get_at(i);
			rv[i] = Kv_by_itm(sub);
		}
		return rv;
	}
}
