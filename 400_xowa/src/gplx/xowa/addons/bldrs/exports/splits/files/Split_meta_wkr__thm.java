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
package gplx.xowa.addons.bldrs.exports.splits.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
import gplx.fsdb.meta.*; import gplx.fsdb.data.*;
import gplx.xowa.addons.bldrs.exports.splits.metas.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
class Split_meta_wkr__thm extends Split_meta_wkr_base {
	private final    Split_rslt_wkr__thm rslt_wkr = new Split_rslt_wkr__thm();
	private final    Db_conn atr_conn;
	private Fsd_thm_tbl tbl; private Db_stmt stmt;
	public Split_meta_wkr__thm(Split_ctx ctx, Db_conn atr_conn) {
		this.atr_conn = atr_conn;
		ctx.Rslt_mgr().Reg_wkr(rslt_wkr);
	}
	@Override public byte Tid() {return Split_page_list_type_.Tid__fsdb_thm;}
	@Override public void On_nth_new(Split_ctx ctx, Db_conn trg_conn) {
		this.tbl = new Fsd_thm_tbl(trg_conn, Bool_.N, Fsm_mnt_mgr.Mnt_idx_main, Bool_.Y);
		tbl.Create_tbl();
		this.stmt = trg_conn.Stmt_insert(tbl.Tbl_name(), tbl.flds);
	}
	@Override public void On_nth_rls(Split_ctx ctx, Db_conn trg_conn) {this.stmt = Db_stmt_.Rls(stmt);}
	@Override protected String Load_sql(Db_attach_mgr attach_mgr, int ns_id, int score_bgn, int score_end) {
		attach_mgr.Conn_links_(new Db_attach_itm("atr_db", atr_conn));
		return String_.Concat_lines_nl
		( "SELECT  t.thm_id, t.thm_owner_id, t.thm_w, t.thm_h, t.thm_time, t.thm_page, t.thm_bin_db_id, t.thm_size, t.thm_modified, t.thm_hash, fir.page_id"
		, "FROM    <atr_db>fsdb_thm t"
		, "        JOIN fsdb_img_regy fir ON t.thm_owner_id = fir.fil_id AND t.thm_id = fir.thm_id"
		, "WHERE   fir.page_score >= {0}"
		, "AND     fir.page_score <  {1}"
		, "AND     fir.page_ns = {2}"
		, "AND     fir.img_type = 1"
		, "ORDER BY page_id"
		);
	}
	@Override protected Object Load_itm(Db_rdr rdr) {
		Fsd_thm_itm itm = Fsd_thm_itm.new_();
		tbl.Ctor_by_load(itm, rdr, rdr.Read_int("thm_owner_id"));
		return itm;
	}
	@Override protected void Save_itm(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Object itm_obj) {
		Fsd_thm_itm itm = (Fsd_thm_itm)itm_obj;
		int thm_id = itm.Thm_id();
		tbl.Insert(stmt, thm_id, itm.Fil_id(), itm.W(), itm.H(), itm.Time(), itm.Page(), ctx.File_size_calc().Idx(), itm.Size(), itm.Modified(), itm.Hash());
		rslt_wkr.On__nth__itm(itm.Db_row_size(), thm_id);
	}
}
