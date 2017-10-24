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
package gplx.xowa.users.bmks; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.brys.fmtrs.*; import gplx.core.errs.*;
import gplx.langs.jsons.*; import gplx.langs.htmls.*;
import gplx.xowa.users.data.*; import gplx.xowa.users.bmks.*;
import gplx.xowa.htmls.bridges.*; import gplx.xowa.htmls.bridges.dbuis.*; import gplx.xowa.htmls.bridges.dbuis.tbls.*; import gplx.xowa.htmls.bridges.dbuis.fmtrs.*;
public class Dbui_tbl_itm__bmk implements Dbui_tbl_itm {
	private final    Xoa_app app; private final    Xoud_bmk_itm_tbl tbl;
	private final    Dbui_tbl_fmtr tbl_fmtr = new Dbui_tbl_fmtr();
	private final    Dbui_cells_fmtr cells_fmtr = new Dbui_cells_fmtr(); private final    Dbui_val_fmtr edit_val_fmtr = Dbui_val_fmtr_.new_edit(); private final    Dbui_val_fmtr view_val_fmtr = Dbui_val_fmtr_.new_view();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
	private final    Bridge_msg_bldr msg_bldr;
	public Dbui_tbl_itm__bmk(Xoa_app app, Xoud_bmk_itm_tbl tbl) {this.app = app; this.tbl = tbl; this.msg_bldr = app.Html__bridge_mgr().Msg_bldr();}
	public byte[] Key() {return key;} private static final    byte[] key = Bry_.new_a7("bmk");
	public Dbui_btn_itm[] View_btns() {return view_btns;}
	public Dbui_btn_itm[] Edit_btns() {return edit_btns;}
	public Dbui_col_itm[] Cols() {return cols;}
	public void Reg(Bridge_cmd_mgr bridge_mgr) {
		Dbui_cmd_mgr dbui_mgr = Dbui_cmd_mgr.Instance;
		dbui_mgr.Init_by_bridge(bridge_mgr);
		dbui_mgr.Add(this);
	}
	public void Select(Bry_bfr bfr, int owner) {
		Xoud_bmk_itm_row[] db_rows = tbl.Select_grp(owner);
		Xow_wiki usr_wiki = app.User().Wikii();
		byte[] option_link = usr_wiki.Html__lnki_bldr().Href_(Bry_.new_a7("home"), usr_wiki.Ttl_parse(Bry_.new_a7("Options/Bookmarks"))).Img_16x16(gplx.xowa.htmls.core.htmls.utls.Xoh_img_path.Img_option).Bld_to_bry();// HOME
		byte[] delete_confirm_msg = app.Api_root().Usr().Bookmarks().Delete_confirm() ? Msg__delete_confirm : Bry_.Empty;
		tbl_fmtr.Write(bfr, this, option_link, delete_confirm_msg, To_ui_rows(db_rows));
	}	private static final    byte[] Msg__delete_confirm = Bry_.new_a7(" data-dbui-delete_confirm_msg='Are you sure you want to delete this bookmark?'");
	public String Del(byte[] row_id, byte[] row_pkey) {
		Xoud_bmk_itm_row db_row = Get_db_row(row_pkey); if (db_row == null) return Fail_missing_row(row_pkey);
		tbl.Delete(db_row.Id());
		return msg_bldr.To_json_str__empty();
	}
	public String Edit(byte[] row_id, byte[] row_pkey) {
		Xoud_bmk_itm_row db_row = Get_db_row(row_pkey); if (db_row == null) return Fail_missing_row(row_pkey);
		Dbui_row_itm ui_row = Get_ui_row(db_row);
		return Write_cells(edit_val_fmtr, edit_btns, row_id, ui_row);
	}
	public String Save(byte[] row_id, byte[] row_pkey, Dbui_val_hash vals) {
		Xoud_bmk_itm_row db_row = Get_db_row(row_pkey); if (db_row == null) return Fail_missing_row(row_pkey);
		byte[] new_name = vals.Get_val_as_bry("name");
		byte[] new_url_bry = vals.Get_val_as_bry("url");
		byte[] new_comment = vals.Get_val_as_bry("comment");
		Xoa_url new_url = app.User().Wikii().Utl__url_parser().Parse(new_url_bry);
		if (new_url.Page_bry() == null) return msg_bldr.Clear().Notify_fail_(Err_msg.To_str("Url is invalid", "url", new_url.Raw())).To_json_str();
		tbl.Update(db_row.Id(), db_row.Owner(), db_row.Sort(), new_name, new_url.Wiki_bry(), new_url_bry, new_comment);
		Dbui_row_itm ui_row = Get_ui_row(row_pkey, new_name, new_url_bry, new_comment);
		return Write_cells(view_val_fmtr, view_btns, row_id, ui_row);
	}
	public String Reorder(byte[][] pkeys, int owner) {
		Xoud_bmk_itm_row[] db_rows = tbl.Select_grp(Xoud_bmk_mgr.Owner_root);
		int len = db_rows.length; if (len != pkeys.length) return msg_bldr.Clear().Notify_fail_(Err_msg.To_str("Rows have changed")).Notify_hint_("Please reload the page").To_json_str();
		for (int i = 0; i < len; ++i) {
			int old_pkey = db_rows[i].Id();
			int new_pkey = Bry_.To_int_or_neg1(pkeys[i]);
			if (old_pkey == new_pkey) continue;	// order hasn't changed; EX: 5 in list; 4th moved to 5th; 1 through 3 will have same sort order;
			tbl.Update_sort(new_pkey, i);
		}
		return msg_bldr.To_json_str__empty();			
	}
	public Dbui_row_itm[] To_ui_rows(Xoud_bmk_itm_row[] db_rows) {
		int len = db_rows.length;
		Dbui_row_itm[] rv = new Dbui_row_itm[len];
		for (int i = 0; i < len; ++i)
			rv[i] = Get_ui_row(db_rows[i]);
		return rv;
	}
	private String Write_cells(Dbui_val_fmtr val_fmtr, Dbui_btn_itm[] btns, byte[] row_id, Dbui_row_itm row) {
		cells_fmtr.Ctor(val_fmtr, btns);
		cells_fmtr.Init(row_id, row);
		cells_fmtr.Bfr_arg__add(tmp_bfr);
		return app.Html__bridge_mgr().Msg_bldr().Clear().Data("html", tmp_bfr.To_bry_and_clear()).To_json_str();
	}
	private Xoud_bmk_itm_row Get_db_row(byte[] pkey) {
		int bmk_id = Bry_.To_int(pkey);
		return tbl.Select_or_null(bmk_id);
	}
	private Dbui_row_itm Get_ui_row(Xoud_bmk_itm_row row) {return Get_ui_row(Int_.To_bry(row.Id()), row.Name(), row.Url(), row.Comment());}
	private Dbui_row_itm Get_ui_row(byte[] pkey, byte[] name, byte[] url, byte[] comment) {
		Dbui_val_itm[] vals = new Dbui_val_itm[3];
		vals[0] = new Dbui_val_itm(name, Gfh_utl.Escape_html_as_bry(tmp_bfr, name));
		vals[1] = new Dbui_val_itm(url, url_fmtr.Bld_bry_many(tmp_bfr, Gfh_utl.Escape_for_atr_val_as_bry(tmp_bfr, Byte_ascii.Apos, url)));
		vals[2] = new Dbui_val_itm(comment, Gfh_utl.Escape_html_as_bry(comment));
		return new Dbui_row_itm(this, pkey, vals);
	}
	private String Fail_missing_row(byte[] row_pkey) {
		return msg_bldr.Clear().Notify_fail_(Err_msg.To_str("Item has been deleted", "key", row_pkey)).Notify_hint_("Please reload the page").To_json_str();
	}
	private static final    Dbui_col_itm[] cols = new Dbui_col_itm[]
	{ new Dbui_col_itm(Dbui_col_itm.Type_id_str		, 150, "name"		, "Name")
	, new Dbui_col_itm(Dbui_col_itm.Type_id_str		, 300, "url"		, "Url")
	, new Dbui_col_itm(Dbui_col_itm.Type_id_text	, 300, "comment"	, "Comment")
	};
	private static final    Dbui_btn_itm[] view_btns = new Dbui_btn_itm[]
	{ new Dbui_btn_itm("rows__edit"		, "edit.png"	, "edit")
	, new Dbui_btn_itm("rows__delete"	, "delete.png"	, "delete")
	};
	private static final    Dbui_btn_itm[] edit_btns = new Dbui_btn_itm[]
	{ new Dbui_btn_itm("rows__save"		, "save.png"	, "save")
	, new Dbui_btn_itm("rows__cancel"	, "cancel.png"	, "cancel")
	};
	private static final    Bry_fmtr url_fmtr = Bry_fmtr.new_("<a href='/site/~{url}'>~{url}</a>", "url");
	public static Dbui_tbl_itm__bmk get_or_new(Xoa_app app, Xoud_bmk_itm_tbl db_tbl) {
		if (I == null) {
			I = new Dbui_tbl_itm__bmk(app, db_tbl);
			I.Reg(app.Html__bridge_mgr().Cmd_mgr());
		}
		return I;
	}	private static Dbui_tbl_itm__bmk I;
}
