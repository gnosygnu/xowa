/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.json; import gplx.*;
public class Json_kv_ary_srl {
	public static KeyVal Kv_by_itm(Json_itm itm) {
		switch (itm.Tid()) {
			case Json_itm_.Tid_kv:
				Json_itm_kv kv = (Json_itm_kv)itm;
				return KeyVal_.new_(kv.Key_as_str(), Val_by_itm(kv.Val()));
			default:
				throw Err_.unhandled(itm.Tid());
		}
	}
	private static Object Val_by_itm(Json_itm itm) {
		switch (itm.Tid()) {
			case Json_itm_.Tid_bool:		return Bool_.XtoStr_lower(Bool_.cast_(itm.Data()));
			case Json_itm_.Tid_int:
			case Json_itm_.Tid_null:
			case Json_itm_.Tid_string:
			case Json_itm_.Tid_decimal:		return itm.Data();
			case Json_itm_.Tid_array:		return Val_by_itm_ary((Json_itm_ary)itm);
			case Json_itm_.Tid_nde:			return Val_by_itm_nde((Json_itm_nde)itm);
			case Json_itm_.Tid_kv:			// kv should never be val; EX: "a":"b":c; not possible
			default:						throw Err_.unhandled(itm.Tid());
		}
	}
	private static KeyVal[] Val_by_itm_ary(Json_itm_ary itm) {
		int subs_len = itm.Subs_len();
		KeyVal[] rv = new KeyVal[subs_len];
		for (int i = 0; i < subs_len; i++) {
			Json_itm sub = itm.Subs_get_at(i);
			KeyVal kv = KeyVal_.new_(Int_.XtoStr(i + Int_.Base1), Val_by_itm(sub));
			rv[i] = kv;
		}
		return rv;
	}
	public static KeyVal[] Val_by_itm_nde(Json_itm_nde itm) {
		int subs_len = itm.Subs_len();
		KeyVal[] rv = new KeyVal[subs_len];
		for (int i = 0; i < subs_len; i++) {
			Json_itm sub = itm.Subs_get_at(i);
			rv[i] = Kv_by_itm(sub);
		}
		return rv;
	}
}
