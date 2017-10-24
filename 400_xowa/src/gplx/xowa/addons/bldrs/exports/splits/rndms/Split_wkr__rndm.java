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
package gplx.xowa.addons.bldrs.exports.splits.rndms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.bulks.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.bldrs.exports.splits.metas.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*; import gplx.xowa.addons.bldrs.exports.splits.mgrs.*;
import gplx.xowa.addons.wikis.pages.randoms.*; import gplx.xowa.addons.wikis.pages.randoms.dbs.*; import gplx.xowa.addons.wikis.pages.randoms.bldrs.*; import gplx.xowa.addons.wikis.pages.randoms.mgrs.*;
import gplx.xowa.addons.bldrs.exports.utls.*;
public class Split_wkr__rndm implements Split_wkr {
	private final    Split_rslt_wkr__rndm rslt_wkr = new Split_rslt_wkr__rndm();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New(); private final    Db_attach_mgr attach_mgr = new Db_attach_mgr();
	private Rndm_bldr_wkr bldr;
	private int cur_ns_id = -1;
	public void Split__init(Split_ctx ctx, Xow_wiki wiki, Db_conn wkr_conn) {
		this.bldr = Rndm_addon.Get(wiki).Mgr().New_bldr();
		ctx.Rslt_mgr().Reg_wkr(rslt_wkr);
	}
	public void Split__term(Split_ctx ctx) {
		bldr.Exec_qry_end();
	}
	public void Split__trg__1st__new(Split_ctx ctx, Db_conn trg_conn) {
		Rndm_qry_tbl mgr_tbl = new Rndm_qry_tbl(trg_conn); mgr_tbl.Create_tbl();
	}
	public void Split__trg__nth__new(Split_ctx ctx, Db_conn trg_conn) {
		if (cur_ns_id != ctx.Trg_ns()) {
			if (cur_ns_id != -1)
				bldr.Exec_qry_end();
			cur_ns_id = ctx.Trg_ns();
			bldr.Exec_qry_bgn(Rndm_qry_itm.New_by_ns(ctx.Wiki(), cur_ns_id));
		}
		bldr.Exec_rng_bgn();
		bldr.Conn().Txn_bgn("rndm");
	}
	public void Split__trg__nth__rls(Split_ctx ctx, Db_conn trg_conn) {
		// make rndm_rng and add
		Rndm_rng_itm rng_itm = bldr.Exec_rng_end_or_null(); if (rng_itm == null) return;
		Rndm_rng_tbl rng_tbl = new Rndm_rng_tbl(trg_conn); rng_tbl.Create_tbl();
		Db_stmt rng_stmt = rng_tbl.Insert_stmt();
		rng_tbl.Insert(rng_stmt, rng_itm.Mgr_idx(), rng_itm.Rng_idx(), rng_itm.Seq_bgn(), rng_itm.Seq_end());
		rng_stmt.Rls();
		bldr.Conn().Txn_end();

		// make rndm_seq and bulk copy
		Rndm_seq_tbl seq_tbl = new Rndm_seq_tbl(trg_conn); seq_tbl.Create_tbl();
		Split_tbl_.Bld_insert_by_select(tmp_bfr, seq_tbl.Tbl_name(), seq_tbl.Flds());
		tmp_bfr.Add_str_u8_fmt("WHERE {0} = {1} AND {2} = {3}", seq_tbl.Fld__qry_idx(), bldr.Qry_idx(), seq_tbl.Fld__rng_idx(), bldr.Rng_idx());
		attach_mgr.Conn_main_(trg_conn).Conn_links_(new Db_attach_itm("src_db", bldr.Conn()));
		attach_mgr.Exec_sql(tmp_bfr.To_str_and_clear());
//			bldr.Conn().Txn_bgn("rndm");
	}
	public void Split__exec(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Xowd_page_itm page, int page_id) {
		if (page.Ns_id() == gplx.xowa.wikis.nss.Xow_ns_.Tid__main && !page.Redirected()) {
			bldr.Exec_seq_itm(page.Id());
			rslt_wkr.On__nth__itm(Rndm_seq_tbl.Db_row_size_fixed, bldr.Qry_idx(), bldr.Rng_idx(), bldr.Seq_in_qry());
		}
	}
	public void Split__pages_loaded(Split_ctx ctx, int ns_id, int score_bgn, int score_end) {}		// rndm_wkr has no caching
}
