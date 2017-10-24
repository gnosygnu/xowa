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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.wikis.pages.*;
public class Xoh_stat_tbl implements Rls_able {
	private static final String tbl_name = "hdump_stats"; private static final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private static final    String
	  fld_page_id = flds.Add_int_pkey("page_id"), fld_wtxt_len = flds.Add_int("wtxt_len"), fld_row_orig_len = flds.Add_int("row_orig_len"), fld_row_zip_len = flds.Add_int("row_zip_len")
	, fld_body_len = flds.Add_int("body_len"), fld_display_ttl_len = flds.Add_int("display_ttl_len"), fld_content_sub_len = flds.Add_int("content_sub_len"), fld_sidebar_div_len = flds.Add_int("sidebar_div_len")
	, fld_js_math = flds.Add_int("js_math"), fld_js_imap = flds.Add_int("js_imap"), fld_js_packed = flds.Add_int("js_packed"), fld_js_hiero = flds.Add_int("js_hiero")
	, fld_a_rhs = flds.Add_int("a_rhs"), fld_lnki_text_n = flds.Add_int("lnki_text_n"), fld_lnki_text_y = flds.Add_int("lnki_text_y")
	, fld_lnke_free = flds.Add_int("lnke_free"), fld_lnke_auto = flds.Add_int("lnke_auto"), fld_lnke_text = flds.Add_int("lnke_text")
	, fld_hdr_1 = flds.Add_int("hdr_1"), fld_hdr_2 = flds.Add_int("hdr_2"), fld_hdr_3 = flds.Add_int("hdr_3"), fld_hdr_4 = flds.Add_int("hdr_4"), fld_hdr_5 = flds.Add_int("hdr_5"), fld_hdr_6 = flds.Add_int("hdr_6")
	, fld_img_full = flds.Add_int("img_full")
	;		
	private final    Db_conn conn; private Db_stmt stmt_insert;
	public Xoh_stat_tbl(Db_conn conn) {
		this.conn = conn;
		this.Create_tbl();
		conn.Stmt_delete(tbl_name).Exec_delete(); // always zap table
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "pkey", fld_page_id)));}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert(Xoh_page hpg, Xoh_stat_itm hzip, int wtxt_len, int row_orig_len, int row_zip_len) {
		Xopg_module_mgr js_mgr = hpg.Head_mgr();
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_page_id				, hpg.Page_id())
			.Val_int(fld_wtxt_len				, wtxt_len)
			.Val_int(fld_row_orig_len			, row_orig_len)
			.Val_int(fld_row_zip_len			, row_zip_len)
			.Val_int(fld_body_len				, Len_or_0(hpg.Db().Html().Html_bry()))
			.Val_int(fld_display_ttl_len		, Len_or_0(hpg.Display_ttl()))
			.Val_int(fld_content_sub_len		, Len_or_0(hpg.Content_sub()))
			.Val_int(fld_sidebar_div_len		, Len_or_0(hpg.Sidebar_div()))
			.Val_bool_as_byte(fld_js_math		, js_mgr.Math_exists())
			.Val_bool_as_byte(fld_js_imap		, js_mgr.Imap_exists())
			.Val_bool_as_byte(fld_js_packed		, js_mgr.Gallery_packed_exists())
			.Val_bool_as_byte(fld_js_hiero		, js_mgr.Hiero_exists())
			.Val_int(fld_a_rhs					, hzip.A_rhs())
			.Val_int(fld_lnki_text_n			, hzip.Lnki_text_n())
			.Val_int(fld_lnki_text_y			, hzip.Lnki_text_y())
			.Val_int(fld_lnke_free				, hzip.Lnke__free())
			.Val_int(fld_lnke_auto				, hzip.Lnke__auto())
			.Val_int(fld_lnke_text				, hzip.Lnke__text())
			.Val_int(fld_hdr_1					, hzip.Hdr_1())
			.Val_int(fld_hdr_2					, hzip.Hdr_2())
			.Val_int(fld_hdr_3					, hzip.Hdr_3())
			.Val_int(fld_hdr_4					, hzip.Hdr_4())
			.Val_int(fld_hdr_5					, hzip.Hdr_5())
			.Val_int(fld_hdr_6					, hzip.Hdr_6())
			.Val_int(fld_img_full				, hzip.Img_full())
		.Exec_insert();
	}
	private int Len_or_0(byte[] bry) {return bry == null ? 0 : bry.length;}
}
