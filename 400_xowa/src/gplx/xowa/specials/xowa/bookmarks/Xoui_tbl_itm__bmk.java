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
package gplx.xowa.specials.xowa.bookmarks; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.json.*;
import gplx.xowa.users.data.*; import gplx.xowa.users.bmks.*;
import gplx.xowa.html.xouis.tbls.*; import gplx.xowa.html.xouis.fmtrs.*;
public class Xoui_tbl_itm__bmk implements Xoui_tbl_itm {
	private final Xoud_bmk_itm_tbl tbl;
	private final Json_wtr json_wtr = new Json_wtr();
	private final Xoui_cells_fmtr cells_fmtr = new Xoui_cells_fmtr();
	private final Xoui_val_fmtr edit_val_fmtr = Xoui_val_fmtr_.new_edit();
	private final Xoui_val_fmtr view_val_fmtr = Xoui_val_fmtr_.new_view();
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	public Xoui_tbl_itm__bmk(Xoud_bmk_itm_tbl tbl) {this.tbl = tbl;}
	public byte[] Key() {return key;} private static final byte[] key = Bry_.new_a7("bmk");
	public Xoui_btn_itm[] View_btns() {return view_btns;}
	public Xoui_btn_itm[] Edit_btns() {return edit_btns;}
	public Xoui_col_itm[] Cols() {return cols;}
	public Xoui_row_itm[] Rows() {return rows;} public void Rows_(Xoui_row_itm[] v) {this.rows = v;} private Xoui_row_itm[] rows;
	public void Del(byte[] row_pkey_bry) {
		int bmk_id = To_bmk_id(row_pkey_bry);
		tbl.Delete(bmk_id);
	}
	public String Edit(byte[] row_id, byte[] row_pkey_bry) {
		return Write_cells(edit_val_fmtr, edit_btns, row_id, Get_row_by_pkey(row_pkey_bry));
	}
	public String Save(Xow_wiki wiki, byte[] row_id, byte[] row_pkey_bry, Xoui_val_hash hash) {
		int bmk_id = To_bmk_id(row_pkey_bry);
		byte[] name = hash.Get_val_as_bry_or_fail(Key_name);
		byte[] url_bry = hash.Get_val_as_bry_or_fail(Key_url);
		Xoa_url_parser url_parser = wiki.App().Utl__url_parser();
		Xoa_url url = url_parser.Parse(url_bry);
		Xoui_row_itm row_itm = Get_row_by_pkey(row_pkey_bry);
		Set_vals(tmp_bfr, row_itm.Vals(), name, url_bry);
		tbl.Update(bmk_id, -1, -1, name, url.Wiki_bry(), url_bry, Bry_.Empty);
		return Write_cells(view_val_fmtr, view_btns, row_id, row_itm);
	}
	public void Set_order_adj(byte[] row_pkey, boolean adj_down) {throw Exc_.new_unimplemented();}

	private int To_bmk_id(byte[] row_pkey_bry) {return Bry_.Xto_int_or_fail(row_pkey_bry);}
	private Xoui_row_itm Get_row_by_pkey(byte[] row_pkey_bry) {
		int len = rows.length;
		for (int i = 0; i < len; ++i) {
			Xoui_row_itm row = rows[i];
			if (Bry_.Eq(row.Pkey(), row_pkey_bry)) return row;
		}
		return null;
	}
	private String Write_cells(Xoui_val_fmtr val_fmtr, Xoui_btn_itm[] btns, byte[] row_id, Xoui_row_itm row) {
		cells_fmtr.Ctor(val_fmtr, btns);
		cells_fmtr.Init(row_id, row);
		cells_fmtr.XferAry(tmp_bfr, 0);
		json_wtr.Doc_bgn();
		json_wtr.Kv_bfr("html", tmp_bfr);
		json_wtr.Doc_end();
		return json_wtr.To_str_and_clear();
	}
	private static final byte[] Key_name = Bry_.new_a7("name"), Key_url = Bry_.new_a7("url");
	private static final Xoui_col_itm[] cols = new Xoui_col_itm[]
	{ new Xoui_col_itm(Xoui_col_itm.Type_id_str, 150, "name"	, "Name")
	, new Xoui_col_itm(Xoui_col_itm.Type_id_str, 350, "url"	, "Url")
	};
	private static final Xoui_btn_itm[] view_btns = new Xoui_btn_itm[]
	{ new Xoui_btn_itm("rows__edit"		, "edit.png"	, "edit")
	, new Xoui_btn_itm("rows__delete"	, "delete.png"	, "delete")
	};
	private static final Xoui_btn_itm[] edit_btns = new Xoui_btn_itm[]
	{ new Xoui_btn_itm("rows__save"		, "save.png"	, "save")
	, new Xoui_btn_itm("rows__cancel"	, "cancel.png"	, "cancel")
	};
	private static final Bry_fmtr url_fmtr = Bry_fmtr.new_("<a href='/site/~{url}'>~{url}</a>", "url");
	public static Xoui_tbl_itm new_(Xoud_bmk_itm_tbl dom_tbl, Xoud_bmk_itm_row[] dom_rows) {
		Xoui_tbl_itm rv = new Xoui_tbl_itm__bmk(dom_tbl);
		int len = dom_rows.length;
		Xoui_row_itm[] gui_rows = new Xoui_row_itm[len];
		Bry_bfr tmp_bfr = Bry_bfr.new_(255);
		for (int i = 0; i < len; ++i) {
			Xoud_bmk_itm_row dom_row = dom_rows[i];
			Xoui_val_itm[] vals = new Xoui_val_itm[2];
			Set_vals(tmp_bfr, vals, dom_row.Name(), dom_row.Url());
			Xoui_row_itm gui_row = new Xoui_row_itm(rv, Int_.Xto_bry(dom_row.Id()), vals);
			gui_rows[i] = gui_row;
		}
		rv.Rows_(gui_rows);
		return rv;
	}
	private static void Set_vals(Bry_bfr tmp_bfr, Xoui_val_itm[] vals, byte[] name, byte[] url) {
		vals[0] = new Xoui_val_itm(name, name);
		url_fmtr.Bld_bfr_many(tmp_bfr, url);
		vals[1] = new Xoui_val_itm(url, tmp_bfr.Xto_bry_and_clear());
	}
}
