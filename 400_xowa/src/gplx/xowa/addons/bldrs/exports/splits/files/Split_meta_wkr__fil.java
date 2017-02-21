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
class Split_meta_wkr__fil extends Split_meta_wkr_base {
	private final    Split_rslt_wkr__fil rslt_wkr = new Split_rslt_wkr__fil();
	private final    Db_conn atr_conn;
	private Fsd_fil_tbl tbl; private Db_stmt stmt;
	public Split_meta_wkr__fil(Split_ctx ctx, Db_conn atr_conn) {
		this.atr_conn = atr_conn;
		ctx.Rslt_mgr().Reg_wkr(rslt_wkr);
	}
	@Override public byte Tid() {return Split_page_list_type_.Tid__fsdb_fil;}
	@Override public void On_nth_new(Split_ctx ctx, Db_conn trg_conn) {
		this.tbl = new Fsd_fil_tbl(trg_conn, Bool_.N, Fsm_mnt_mgr.Mnt_idx_main);
		tbl.Create_tbl();
		this.stmt = trg_conn.Stmt_insert(tbl.Tbl_name(), tbl.flds);
	}
	@Override public void On_nth_rls(Split_ctx ctx, Db_conn trg_conn) {this.stmt = Db_stmt_.Rls(stmt);}
	@Override protected String Load_sql(Db_attach_mgr attach_mgr, int ns_id, int score_bgn, int score_end) {
		attach_mgr.Conn_links_(new Db_attach_itm("atr_db", atr_conn));
		return String_.Concat_lines_nl
		( "SELECT  f.fil_id, f.fil_owner_id, f.fil_xtn_id, f.fil_ext_id, f.fil_bin_db_id, f.fil_name, f.fil_size, f.fil_modified, f.fil_hash, fir.page_id"
		, "FROM    <atr_db>fsdb_fil f"
		, "        JOIN fsdb_fil_regy fir ON f.fil_id = fir.fil_id"
		, "WHERE   fir.page_score >= {0}"
		, "AND     fir.page_score <  {1}"
		, "AND     fir.page_ns = {2}"
		, "ORDER BY page_id"
		);
	}
	@Override protected Object Load_itm(Db_rdr rdr) {
		return tbl.New_by_rdr(Fsm_mnt_mgr.Mnt_idx_main, rdr);
	}
	@Override protected void Save_itm(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Object itm_obj) {
		Fsd_fil_itm itm = (Fsd_fil_itm)itm_obj;
		int fil_id = itm.Fil_id();
		tbl.Insert(stmt, fil_id, itm.Dir_id(), itm.Name(), itm.Xtn_id(), itm.Ext_id(), itm.Size(), ctx.File_size_calc().Idx(), itm.Modified_on(), itm.Hash_md5());
		rslt_wkr.On__nth__itm(itm.Db_row_size(), fil_id);
	}
}
