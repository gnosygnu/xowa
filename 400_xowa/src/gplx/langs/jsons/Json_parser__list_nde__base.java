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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
import gplx.core.primitives.*;
public class Json_parser__list_nde__base extends Json_parser__itm__base {
	public void Parse_grp(String context, Json_grp grp) {
		this.context = context;
		int len = grp.Len();
		for (int i = 0; i < len; ++i) {
			Json_nde sub = null;
			if (grp.Tid() == Json_itm_.Tid__nde) {
				Json_kv kv = Json_nde.cast(grp).Get_at_as_kv(i);
				sub = kv.Val_as_nde();
			}
			else {
				sub = Json_nde.cast(grp.Get_at(i));
			}
			Parse_nde(context, sub);
		}
	}
	public void Parse_nde(String context, Json_nde nde) {
		this.cur_itm = nde;
		for (int j = 0; j < keys_len; ++j)
			atrs[j] = null;
		int atr_len = nde.Len();
		for (int j = 0; j < atr_len; ++j) {
			Json_kv atr = nde.Get_at_as_kv(j);
			Object idx_obj = hash.Get_by_bry(atr.Key_as_bry());
			if (idx_obj == null) {Warn("unknown json parser key", atr); continue;}
			int idx_int = ((Int_obj_val)idx_obj).Val();
			atrs[idx_int] = atr;
		}
		Parse_hook_nde(nde, atrs);
	}
	public void Parse_to_list_as_bry(String context, Json_ary ary, Ordered_hash list) {
		this.cur_itm = ary;
		int len = ary.Len();
		for (int i = 0; i < len; ++i) {
			byte[] val = ary.Get_at(i).Data_bry();
			list.Add(val, val);
		}
	}
	public void Parse_to_list_as_kv(String context, Json_nde nde, Ordered_hash list) {
		this.cur_itm = nde;
		int len = nde.Len();
		for (int i = 0; i < len; ++i) {
			Json_kv sub = nde.Get_at_as_kv(i);
			byte[] key = sub.Key_as_bry();
			byte[] val = Parse_to_list_as_kv__get_val(sub, key);
			list.Add(key, Keyval_.new_(String_.new_u8(key), String_.new_u8(val)));
		}
	}
	@gplx.Virtual protected byte[] Parse_to_list_as_kv__get_val(Json_kv sub, byte[] key) {return sub.Val_as_bry();}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {}
}
