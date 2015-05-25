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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Scrib_kv_utl_ {
	public static KeyVal[] base1_obj_(Object v) {return new KeyVal[] {KeyVal_.int_(0 + Scrib_core.Base_1, v)};}
	public static KeyVal[] base1_many_(Object... vals) {
		int len = vals.length;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++)
			rv[i] = KeyVal_.int_(i + Scrib_core.Base_1, vals[i]);
		return rv;
	}
	public static KeyVal[] base1_many_ary_(Object... vals) {
		int len = vals.length;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++)
			rv[i] = KeyVal_.int_(i + Scrib_core.Base_1, vals[i]);
		return rv;
	}
	public static KeyVal[] base1_list_(List_adp list) {
		int len = list.Count();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++)
			rv[i] = KeyVal_.int_(i + Scrib_core.Base_1, list.Get_at(i));
		list.Clear();
		return rv;
	}
	public static KeyVal[] flat_many_(Object... vals) {
		int len = vals.length;
		KeyVal[] rv = new KeyVal[len / 2];
		for (int i = 0; i < len; i += 2)
			rv[i / 2] = KeyVal_.obj_(vals[i], vals[i + 1]);
		return rv;
	}
	public static String Val_to_str(KeyVal[] ary, int idx) {
		if (ary == null) throw Err_.new_("ary is null");
		int ary_len = ary.length;
		if (ary_len == 0 && idx == 0) return "";	// NOTE: Modules can throw exceptions in which return value is nothing; do not fail; return ""; EX: -logy; DATE:2013-10-14
		if (idx >= ary_len) throw Err_.new_fmt_("idx is not in bounds: {0} {1}", idx, KeyVal_.Ary_x_to_str(ary));
		Object o = ary[idx].Val();
		try {return (String)o;}
		catch (Exception e) {throw Err_.cast_(e, String.class, o);}
	}
	public static KeyVal[] Val_to_KeyVal_ary(KeyVal[] ary, int idx) {
		if (ary == null) throw Err_.new_("ary is null"); if (idx >= ary.length) throw Err_.new_fmt_("idx is not in bounds: {0} {1}", idx, KeyVal_.Ary_x_to_str(ary));
		try {return (KeyVal[])ary[idx].Val();}
		catch (Exception e) {throw Err_.cast_manual_msg_(e, KeyVal[].class, KeyVal_.Ary_x_to_str(ary));}
	}
}
