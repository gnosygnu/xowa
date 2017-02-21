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
package gplx.xowa.addons.bldrs.exports.splits.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
public class Split_mgr_init {
	public void Init(Split_ctx ctx, Db_conn wkr_conn, Db_conn page_conn) {
		this.Make_ns_regy	(ctx.Cfg().Force_rebuild(), wkr_conn, ctx.Ns_itms());
		this.Make_page_regy	(ctx.Cfg().Force_rebuild(), wkr_conn, page_conn);
		ctx.Rslt_mgr().Init();
	}
	private void Make_ns_regy(boolean force_rebuild, Db_conn wkr_conn, Split_ns_itm[] ns_itms) {
		if (!(force_rebuild || !wkr_conn.Meta_tbl_exists("ns_regy"))) return;
		Gfo_log_.Instance.Prog("creating ns_regy");
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("ns_regy"
			, Dbmeta_fld_itm.new_int("ns_id").Primary_y_()
			, Dbmeta_fld_itm.new_int("ns_ord")
			));
		int len = ns_itms.length;
		Db_stmt stmt = wkr_conn.Stmt_insert("ns_regy", "ns_id", "ns_ord");
		for (int i = 0; i < len; ++i)
			stmt.Clear().Crt_int("ns_id", ns_itms[i].Ns_id()).Crt_int("ns_ord", i).Exec_insert();
		wkr_conn.Meta_idx_create("ns_regy", "ns_ord", "ns_ord");
	}
	private void Make_page_regy(boolean force_rebuild, Db_conn wkr_conn, Db_conn page_conn) {
		// order pages by page_ns_ord ASC, page_score DESC, page_len DESC, page_id ASC
		if (!(force_rebuild || !wkr_conn.Meta_tbl_exists("page_regy"))) return;
		Gfo_log_.Instance.Prog("creating page_regy");
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("page_regy"
		, Dbmeta_fld_itm.new_int("page_uid").Primary_y_().Autonum_y_()
		, Dbmeta_fld_itm.new_int("page_ns_id")
		, Dbmeta_fld_itm.new_int("page_ns_ord")
		, Dbmeta_fld_itm.new_int("page_score")
		, Dbmeta_fld_itm.new_int("page_len")
		, Dbmeta_fld_itm.new_int("page_id")
		));
		page_conn.Meta_idx_create("page", "page_uid", "page_namespace", "page_score", "page_len", "page_id");	// index page table; will drop below
		Db_attach_mgr attach_mgr = new Db_attach_mgr(wkr_conn, new Db_attach_itm("page_db", page_conn));
		attach_mgr.Exec_sql(String_.Concat_lines_nl	// ANSI.Y
		( "INSERT INTO page_regy (page_ns_id, page_ns_ord, page_score, page_len, page_id)"
		, "SELECT  p.page_namespace, nm.ns_ord, p.page_score, p.page_len, p.page_id"
		, "FROM    <page_db>page p"
		, "        JOIN ns_regy nm ON p.page_namespace = nm.ns_id"
		, "ORDER BY nm.ns_ord, p.page_score DESC, p.page_len DESC, p.page_id"
		));
		wkr_conn.Meta_idx_create("page_regy", "main"		, "page_ns_id", "page_score", "page_len", "page_id");
		wkr_conn.Meta_idx_create("page_regy", "page_id"		, "page_id");
		page_conn.Meta_idx_delete("page", "page_uid");
	}

	public static void Update_page_cols(Db_conn wkr_conn, String tbl_name) {
		wkr_conn.Exec_sql(String_.Format(String_.Concat_lines_nl	// ANSI.Y
		( "UPDATE  {0}"
		, "SET     page_id    = Coalesce((SELECT page_id    FROM page_regy pr WHERE pr.page_uid = {0}.page_uid), -1)"
		, ",       page_ns    = Coalesce((SELECT page_ns_id FROM page_regy pr WHERE pr.page_uid = {0}.page_uid), -1)"
		, ",       page_score = Coalesce((SELECT page_score FROM page_regy pr WHERE pr.page_uid = {0}.page_uid), -1)"
		), tbl_name));
	}
}
