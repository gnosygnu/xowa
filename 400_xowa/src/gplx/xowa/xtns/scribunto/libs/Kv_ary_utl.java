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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.core.json.*;
class Kv_ary_utl {
	public static KeyVal[] new_(boolean base_1, Object... vals) {
		int len = vals.length;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; ++i)
			rv[i] = KeyVal_.int_(i + (base_1 ? 1 : 0), vals[i]);
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
		int type_tid = Type_adp_.To_tid_type(val_type);
		if (type_tid == Type_adp_.Tid__obj) {
			if (Type_adp_.Eq(val_type, KeyVal[].class))
				Ary_to_str__nde(wtr, indent, itm.Key(), (KeyVal[])itm.Val());
			else if (Type_adp_.Is_array(val_type))
				Ary_to_str__ary(wtr, indent, itm.Key(), Array_.cast(val));
			else
				throw Err_.new_unhandled(type_tid);
		}
		else
			wtr.Kv_obj(Bry_.new_u8(itm.Key()), itm.Val(), type_tid);
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
		int len = Array_.Len(array);
		for (int j = 0; j < len; ++j) {
			Object itm = Array_.Get_at(array, j);
			Class<?> itm_type = itm.getClass();
			int itm_type_tid = Type_adp_.To_tid_type(itm_type);
			if (itm_type_tid == Type_adp_.Tid__obj) {
				if (Type_adp_.Eq(itm_type, KeyVal.class))
					Ary_to_str__itm(wtr, indent + 1, (KeyVal)itm);
				else if (Type_adp_.Is_array(itm_type))
					Ary_to_str__ary_itms(wtr, indent + 1, Array_.cast(itm));
				else
					throw Err_.new_unhandled(itm_type);
			}
			else
				wtr.Ary_itm_by_type_tid(itm_type_tid, itm);
		}
	}
}
