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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.brys.fmtrs.*; import gplx.core.errs.*;
import gplx.langs.jsons.*; import gplx.langs.htmls.*;
import gplx.xowa.users.data.*; import gplx.xowa.users.bmks.*;
import gplx.xowa.htmls.bridges.*; import gplx.xowa.htmls.bridges.dbuis.*; import gplx.xowa.htmls.bridges.dbuis.tbls.*; import gplx.xowa.htmls.bridges.dbuis.fmtrs.*;
public class Dbui_tbl_itm__history implements Dbui_tbl_itm {
	private final    Xoa_app app; private final    Xoud_history_tbl tbl;
	private final    Dbui_tbl_fmtr tbl_fmtr = new Dbui_tbl_fmtr();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
	private final    Bridge_msg_bldr msg_bldr;
	public Dbui_tbl_itm__history(Xoa_app app, Xoud_history_tbl tbl) {this.app = app; this.tbl = tbl; this.msg_bldr = app.Html__bridge_mgr().Msg_bldr();}
	public byte[] Key() {return key;} private static final    byte[] key = Bry_.new_a7("history");
	public Dbui_btn_itm[] View_btns() {return view_btns;}
	public Dbui_btn_itm[] Edit_btns() {return Dbui_btn_itm.Ary_empty;}
	public Dbui_col_itm[] Cols() {return cols;}
	public void Reg(Bridge_cmd_mgr bridge_mgr) {
		Dbui_cmd_mgr dbui_mgr = Dbui_cmd_mgr.Instance;
		dbui_mgr.Init_by_bridge(bridge_mgr);
		dbui_mgr.Add(this);
	}
	private final    List_adp select_list = List_adp_.New();
	public void Select(Bry_bfr bfr, int top) {
		tbl.Select_by_top(select_list, 100);
		Xoud_history_row[] db_rows = (Xoud_history_row[])select_list.To_ary_and_clear(Xoud_history_row.class);
		Xow_wiki usr_wiki = app.User().Wikii();
		byte[] option_link = usr_wiki.Html__lnki_bldr().Href_(Bry_.new_a7("home"), usr_wiki.Ttl_parse(Bry_.new_a7("Options/PageHistory"))).Img_16x16(gplx.xowa.htmls.core.htmls.utls.Xoh_img_path.Img_option).Bld_to_bry();// HOME
		byte[] delete_confirm_msg = app.Api_root().Usr().Bookmarks().Delete_confirm() ? Msg__delete_confirm : Bry_.Empty;
		tbl_fmtr.Write(bfr, this, option_link, delete_confirm_msg, To_ui_rows(db_rows));
	}	private static final    byte[] Msg__delete_confirm = Bry_.new_a7(" data-dbui-delete_confirm_msg='Are you sure you want to delete this row?'");
	public String Del(byte[] row_id, byte[] row_pkey) {
		Xoud_history_row db_row = Get_db_row(row_pkey); if (db_row == null) return Fail_missing_row(row_pkey);
		tbl.Delete(db_row.Id());
		return msg_bldr.To_json_str__empty();
	}
	public String Edit(byte[] row_id, byte[] row_pkey) {throw Err_.new_unimplemented();}
	public String Save(byte[] row_id, byte[] row_pkey, Dbui_val_hash vals) {throw Err_.new_unimplemented();}
	public String Reorder(byte[][] pkeys, int owner) {throw Err_.new_unimplemented();}
	public Dbui_row_itm[] To_ui_rows(Xoud_history_row[] db_rows) {
		int len = db_rows.length;
		Dbui_row_itm[] rv = new Dbui_row_itm[len];
		for (int i = 0; i < len; ++i)
			rv[i] = Get_ui_row(db_rows[i]);
		return rv;
	}
	private Xoud_history_row Get_db_row(byte[] pkey) {
		int id = Bry_.To_int(pkey);
		return tbl.Select_or_null(id);
	}
	private Dbui_row_itm Get_ui_row(Xoud_history_row row) {return Get_ui_row(Int_.To_bry(row.Id()), row.Wiki(), row.Url(), row.Count(), row.Time());}
	private Dbui_row_itm Get_ui_row(byte[] pkey, byte[] wiki, byte[] url, int count, DateAdp time) {
		Dbui_val_itm[] vals = new Dbui_val_itm[4];
		vals[0] = new Dbui_val_itm(url, url_fmtr.Bld_bry_many(tmp_bfr, Gfh_utl.Escape_for_atr_val_as_bry(tmp_bfr, Byte_ascii.Apos, url)));
		vals[1] = new Dbui_val_itm(wiki, wiki);
		byte[] count_bry = Int_.To_bry(count);
		vals[2] = new Dbui_val_itm(count_bry, count_bry);
		byte[] time_bry = Bry_.new_u8(time.XtoStr_fmt_yyyy_MM_dd_HH_mm_ss());
		vals[3] = new Dbui_val_itm(time_bry, time_bry);
		return new Dbui_row_itm(this, pkey, vals);
	}
	private String Fail_missing_row(byte[] row_pkey) {
		return msg_bldr.Clear().Notify_fail_(Err_msg.To_str("Item has been deleted", "key", row_pkey)).Notify_hint_("Please reload the page").To_json_str();
	}
	private static final    Dbui_col_itm[] cols = new Dbui_col_itm[]
	{ new Dbui_col_itm(Dbui_col_itm.Type_id_str		, 300, "page"		, "Page")
	, new Dbui_col_itm(Dbui_col_itm.Type_id_str		, 150, "wiki"		, "Wiki")
	, new Dbui_col_itm(Dbui_col_itm.Type_id_int		,  80, "views"		, "Views")
	, new Dbui_col_itm(Dbui_col_itm.Type_id_datetime, 100, "time"		, "Time")
	};
	private static final    Dbui_btn_itm[] view_btns = new Dbui_btn_itm[]
	{ new Dbui_btn_itm("rows__delete"	, "delete.png"	, "delete")
	};
	private static final    Bry_fmtr url_fmtr = Bry_fmtr.new_("<a href='/site/~{url}'>~{url}</a>", "url");
	public static Dbui_tbl_itm__history get_or_new(Xoa_app app, Xoud_history_tbl db_tbl) {
		if (I == null) {
			I = new Dbui_tbl_itm__history(app, db_tbl);
			I.Reg(app.Html__bridge_mgr().Cmd_mgr());
		}
		return I;
	}	private static Dbui_tbl_itm__history I;
}
