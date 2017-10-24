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
package gplx.xowa.addons.bldrs.exports.splits.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.bldrs.exports.splits.metas.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
import gplx.xowa.htmls.core.dbs.*;
public class Split_wkr__html implements Split_wkr {
	private Xoh_src_tbl_mgr src_tbl_mgr;
	private Xowd_html_tbl tbl; private Db_stmt stmt;
	private final    Xowd_html_row trg_itm = new Xowd_html_row();
	private final    Split_rslt_wkr__html rslt_wkr = new Split_rslt_wkr__html();
	public void Split__init(Split_ctx ctx, Xow_wiki wiki, Db_conn wkr_conn) {
		this.src_tbl_mgr = new Xoh_src_tbl_mgr(wiki);
		ctx.Rslt_mgr().Reg_wkr(rslt_wkr);
	}
	public void Split__trg__nth__new(Split_ctx ctx, Db_conn trg_conn) {
		this.tbl = new Xowd_html_tbl(trg_conn);
		Dbmeta_fld_list trg_flds = Make_flds_for_split(tbl.Flds());
		trg_conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl.Tbl_name(), trg_flds));
		this.stmt = trg_conn.Stmt_insert(tbl.Tbl_name(), trg_flds);
	}
	public void Split__trg__nth__rls(Split_ctx ctx, Db_conn trg_conn) {
		trg_conn.Meta_idx_create(tbl.Tbl_name(), "merge", "trg_db_id", "page_id");
		trg_conn.Meta_idx_create(tbl.Tbl_name(), "blob" , "trg_db_id", "blob_len", "page_id");
		tbl.Rls();
	}
	public void Split__exec(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Xowd_page_itm page, int page_id) {
		// load data
		if (page.Redirected()) return;	// redirects won't have html
		int html_db_id = page.Html_db_id(); if (html_db_id == -1) return;	// ignore pages that don't generate html; EX: Mediawiki:Commons.css
		Xoh_page_tbl_itm src_itm = src_tbl_mgr.Get_or_load(html_db_id);
		if (!src_itm.Html_tbl().Select_as_row(trg_itm, page_id)) throw Err_.new_wo_type("could not find html", "page_id", page_id);
		byte[] body = trg_itm.Body();
		int body_len = body.length;
		byte[] display_ttl = trg_itm.Display_ttl();
		byte[] content_sub = trg_itm.Content_sub();
		byte[] sidebar_div = trg_itm.Sidebar_div();

		// calc db_idx based on db_size
		int db_row_size = Xowd_html_row.Db_row_size_fixed + display_ttl.length + content_sub.length + sidebar_div.length  + body_len;
		int trg_db_id = ctx.Html_size_calc().Size_cur_add_(db_row_size);

		// do insert
		stmt.Clear().Val_int("page_id", page_id);
		stmt.Val_int("trg_db_id", trg_db_id);
		stmt.Val_int("blob_len"	, body_len);
		tbl.Fill_stmt(stmt, trg_itm.Head_flag(), trg_itm.Body_flag(), display_ttl, content_sub, sidebar_div, body);
		stmt.Exec_insert();
		rslt_wkr.On__nth__itm(db_row_size, page_id);
	}
	public void Split__trg__1st__new(Split_ctx ctx, Db_conn trg_conn) {}						// html_dbs have no core tables
	public void Split__pages_loaded(Split_ctx ctx, int ns_id, int score_bgn, int score_end) {}	// html_wkr has no caching
	public void Split__term(Split_ctx ctx) {}													// html_wkr has no cleanup
	private static Dbmeta_fld_list Make_flds_for_split(Dbmeta_fld_list flds) {
		Dbmeta_fld_list rv = new Dbmeta_fld_list();
		rv.Add(flds.Get_by("page_id"));
		rv.Add_int("trg_db_id");
		rv.Add_int("blob_len");
		rv.Add(flds.Get_by("head_flag"));
		rv.Add(flds.Get_by("body_flag"));
		rv.Add(flds.Get_by("display_ttl"));
		rv.Add(flds.Get_by("content_sub"));
		rv.Add(flds.Get_by("sidebar_div"));
		rv.Add(flds.Get_by("body"));
		return rv;
	}
}
