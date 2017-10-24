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
package gplx.xowa.addons.bldrs.exports.splits.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.bulks.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.bldrs.exports.splits.metas.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*; import gplx.xowa.addons.bldrs.exports.splits.mgrs.*;
public class Split_wkr__page implements Split_wkr {
	private final    Split_rslt_wkr__page rslt_wkr = new Split_rslt_wkr__page();
	private Xowd_page_tbl tbl; private Db_stmt stmt;
	public void Split__init(Split_ctx ctx, Xow_wiki wiki, Db_conn wkr_conn) {
		ctx.Rslt_mgr().Reg_wkr(rslt_wkr);
	}
	public void Split__trg__1st__new(Split_ctx ctx, Db_conn trg_conn) {
		Db_conn core_conn = ctx.Wiki().Data__core_mgr().Tbl__cfg().Conn();
		Db_tbl_copy copy_mgr = new Db_tbl_copy();
		copy_mgr.Copy_many(core_conn, trg_conn, "xowa_db", "site_ns", "site_stats", "css_core", "css_file");
		copy_mgr.Copy_one (core_conn, trg_conn, "xowa_cfg", "xowa_cfg__core");
		Update_layouts(trg_conn, ctx.Cfg().Text(), ctx.Cfg().Html(), ctx.Cfg().File());
	}
	public void Split__trg__nth__new(Split_ctx ctx, Db_conn trg_conn) {
		this.tbl = new Xowd_page_tbl(trg_conn, Bool_.N);
		tbl.Create_tbl();
		stmt = trg_conn.Stmt_insert(tbl.Tbl_name(), tbl.Flds__all());
	}
	public void Split__trg__nth__rls(Split_ctx ctx, Db_conn trg_conn) {
		stmt.Rls();
	}
	public void Split__exec(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Xowd_page_itm page, int page_id) {
		tbl.Insert_by_itm(stmt, page, ctx.Html_size_calc().Idx());
		rslt_wkr.On__nth__itm(Xowd_page_itm.Db_row_size_fixed + page.Ttl_page_db().length, page_id);
		rslt_mgr.Score_set(page.Score());
	}
	public void Split__pages_loaded(Split_ctx ctx, int ns_id, int score_bgn, int score_end) {}		// page_wkr has no caching
	public void Split__term(Split_ctx ctx) {}														// page_wkr has no cleanup
	private void Update_layouts(Db_conn trg_conn, Split_type_cfg... cfgs) {
		Db_cfg_tbl cfg_tbl = new Db_cfg_tbl(trg_conn, "xowa_cfg__core");
		for (Split_type_cfg cfg : cfgs) {
			String layout = cfg.Layout(); if (layout == null) continue;
			cfg_tbl.Update_str("xowa.wiki.core", "layout_" + cfg.Key(), layout);
		}
		cfg_tbl.Rls();
	}
}
