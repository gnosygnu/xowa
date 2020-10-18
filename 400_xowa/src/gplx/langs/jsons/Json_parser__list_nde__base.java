/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.jsons;

import gplx.Keyval_;
import gplx.Ordered_hash;
import gplx.String_;
import gplx.core.primitives.Int_obj_val;

public class Json_parser__list_nde__base extends Json_parser__itm__base {
	public void Parse_grp(String context, Json_grp grp) {
		this.context = context;
		int len = grp.Len();
		for (int i = 0; i < len; ++i) {
			Json_nde sub = null;
			if (grp.Tid() == Json_itm_.Tid__nde) {
				Json_kv kv = Json_nde.Cast(grp).Get_at_as_kv(i);
				sub = kv.Val_as_nde();
			}
			else {
				sub = Json_nde.Cast(grp.Get_at(i));
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
