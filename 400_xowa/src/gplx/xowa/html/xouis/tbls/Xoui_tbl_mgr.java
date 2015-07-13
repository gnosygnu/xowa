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
package gplx.xowa.html.xouis.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.xouis.*;
import gplx.json.*;
import gplx.xowa.html.xouis.fmtrs.*;
public class Xoui_tbl_mgr {
	private final Ordered_hash tbl_hash = Ordered_hash_.new_bry_();
	private final Xoui_tbl_fmtr tbl_fmtr = new Xoui_tbl_fmtr();
	public String Del(Json_doc jdoc) {
		Xoui_tbl_itm tbl_itm = Get_tbl_by_jdoc(jdoc);
		byte[] row_pkey = Get_row_pkey_in_jdoc(jdoc);
		tbl_itm.Del(row_pkey);
		return "{}";
	}
	public void Write(Bry_bfr bfr, Xoui_tbl_itm tbl) {
		tbl_hash.Del(tbl.Key());
		tbl_hash.Add(tbl.Key(), tbl);
		tbl_fmtr.Write(bfr, tbl);
	}
	public String Edit(Json_doc jdoc) {
		Xoui_tbl_itm tbl_itm = Get_tbl_by_jdoc(jdoc);
		byte[] row_id = Get_row_key_in_jdoc(jdoc);
		byte[] row_pkey = Get_row_pkey_in_jdoc(jdoc);
		return tbl_itm.Edit(row_id, row_pkey);
	}
	public String Save(Xow_wiki wiki, Json_doc jdoc) {
		Xoui_tbl_itm tbl_itm = Get_tbl_by_jdoc(jdoc);
		byte[] row_id = Get_row_key_in_jdoc(jdoc);
		byte[] row_pkey = Get_row_pkey_in_jdoc(jdoc);
		return tbl_itm.Save(wiki, row_id, row_pkey, To_hash(jdoc.Get_grp(Arg_data)));
	}
	public void Adj_order(Json_doc jdoc) {
		Xoui_tbl_itm tbl_itm = Get_tbl_by_jdoc(jdoc);
		byte[] row_pkey = Get_row_pkey_in_jdoc(jdoc);
		boolean adj_down = Yn.parse_by_char_or(String_.new_a7(jdoc.Get_val_as_bry_or(Arg_adj_down, null)), false);
		tbl_itm.Set_order_adj(row_pkey, adj_down);
	}
	private Xoui_tbl_itm Get_tbl_by_jdoc(Json_doc jdoc) {
		byte[] key = jdoc.Get_val_as_bry_or(Arg_tbl_key, null); if (key == null) throw Exc_.new_("dbui.mgr; unknown tbl", "jdoc", jdoc.Src());
		Xoui_tbl_itm tbl = (Xoui_tbl_itm)tbl_hash.Get_by(key);	if (tbl == null) throw Exc_.new_("dbui.mgr; unknown tbl key", "key", key);
		return tbl;
	}
	private byte[] Get_row_pkey_in_jdoc(Json_doc jdoc) {
		byte[] rv = jdoc.Get_val_as_bry_or(Arg_row_pkey, null);	if (rv == null) throw Exc_.new_("dbui.mgr; unknown row_pkey", "jdoc", jdoc.Src());
		return rv;
	}
	private byte[] Get_row_key_in_jdoc(Json_doc jdoc) {
		byte[] rv = jdoc.Get_val_as_bry_or(Arg_row_id, null);	if (rv == null) throw Exc_.new_("dbui.mgr; unknown row_id", "jdoc", jdoc.Src());
		return rv;
	}
	private Xoui_val_hash To_hash(Json_grp grp) {
		Xoui_val_hash rv = new Xoui_val_hash();
		int len = grp.Subs_len();
		for (int i = 0; i < len; ++i) {
			Json_itm_kv kv = (Json_itm_kv)grp.Subs_get_at(i);
			Json_itm_nde nde = (Json_itm_nde)kv.Val();
			Json_itm_kv key = (Json_itm_kv)nde.Subs_get_by_key(Arg_key);
			Json_itm_kv val = (Json_itm_kv)nde.Subs_get_by_key(Arg_val);
			Xoui_val_itm fld = new Xoui_val_itm(val.Val().Data_bry(), Bry_.Empty);
			rv.Add(key.Val().Data_bry(), fld);
		}
		return rv;
	}
	private static final byte[]
	  Arg_tbl_key = Bry_.new_a7("tbl_key"), Arg_row_pkey = Bry_.new_a7("row_pkey"), Arg_row_id = Bry_.new_a7("row_id"), Arg_data = Bry_.new_a7("data")
	, Arg_adj_down = Bry_.new_a7("adj_down"), Arg_key = Bry_.new_a7("key"), Arg_val = Bry_.new_a7("val");
}
