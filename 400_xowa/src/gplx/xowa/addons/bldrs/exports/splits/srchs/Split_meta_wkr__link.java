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
package gplx.xowa.addons.bldrs.exports.splits.srchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.exports.splits.metas.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.dbs.*;
class Split_meta_wkr__link extends Split_meta_wkr_base {
	private final    Srch_db_mgr srch_db_mgr;
	private Srch_link_tbl tbl;
	private Db_stmt stmt;
	private final    Split_rslt_wkr__link rslt_wkr = new Split_rslt_wkr__link();
	public Split_meta_wkr__link(Split_ctx ctx, Srch_db_mgr srch_db_mgr) {
		this.srch_db_mgr = srch_db_mgr;
		ctx.Rslt_mgr().Reg_wkr(rslt_wkr);
	}
	@Override public byte Tid() {return Split_page_list_type_.Tid__srch_link;}
	@Override public void On_nth_new(Split_ctx ctx, Db_conn trg_conn) {
		this.tbl = new Srch_link_tbl(trg_conn);
		Dbmeta_fld_list trg_flds = tbl.Flds().Clone().New_int("trg_db_id");
		trg_conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl.Tbl_name(), trg_flds));
		this.stmt = trg_conn.Stmt_insert(tbl.Tbl_name(), trg_flds);
	}
	@Override public void On_nth_rls(Split_ctx ctx, Db_conn trg_conn) {
		this.stmt = Db_stmt_.Rls(stmt);
		trg_conn.Meta_idx_create(tbl.Tbl_name(), "blob" , "trg_db_id", "word_id", "page_id");
	}
	@Override protected String Load_sql(Db_attach_mgr attach_mgr, int ns_id, int score_bgn, int score_end) {
		int trg_db_id = srch_db_mgr.Tbl__link__get_idx(ns_id);
		attach_mgr.Conn_links_(new Db_attach_itm("link_db", srch_db_mgr.Tbl__link__get_at(trg_db_id).conn));
		return String_.Concat_lines_nl
		( "SELECT  sl.word_id, sl.page_id, sl.link_score, sw.page_id, " + trg_db_id + " AS trg_db_id"
		, "FROM    <link_db>search_link sl"
		, "        JOIN split_search_word sw ON sw.word_id = sl.word_id"
		, "WHERE   sw.page_score >= {0}"
		, "AND     sw.page_score <  {1}"
		, "AND     sw.page_ns = {2}"
		, "GROUP BY sl.word_id, sl.page_id, sl.link_score"
		, "ORDER BY sw.page_id"
		);
	}
	@Override protected Object Load_itm(Db_rdr rdr) {
		Srch_link_row rv = tbl.New_row(rdr);
		rv.Trg_db_id = rdr.Read_int("trg_db_id");
		return rv;
	}
	@Override protected void Save_itm(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Object itm_obj) {
		Srch_link_row itm = (Srch_link_row)itm_obj;
		stmt.Clear();
		tbl.Fill_for_insert(stmt, itm);
		stmt.Val_int("trg_db_id", itm.Trg_db_id);
		stmt.Exec_insert();
		rslt_wkr.On__nth__itm(itm.Db_row_size(), itm.Word_id, itm.Page_id);
	}
}
