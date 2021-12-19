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
package gplx.xowa.htmls.core.wkrs.tocs;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_toc_wtr implements BryBfrArg, Xoh_wtr_itm {
	private byte toc_mode;
	public void Init_by_parse(BryWtr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_toc_data data) {
		Init_by_decode(hpg, hctx, src, data);
		this.AddToBfr(bfr);
	}
	public boolean Init_by_decode(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm) {
		Xoh_toc_data data = (Xoh_toc_data)data_itm;
		this.toc_mode = data.Toc_mode();
		return true;
	}
	public void AddToBfr(BryWtr bfr) {
		Write_tag(bfr, toc_mode == Xoh_toc_data.Toc_mode__pgbnr);
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_toc_wtr rv = new Xoh_toc_wtr(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}

	public static final byte[]
	  Atr__class__toc				= BryUtl.NewA7("xo-toc")
	, Atr__data__toc__mode			= BryUtl.NewA7("data-toc-mode")
	;
	public static void Write_tag(BryWtr bfr, boolean pgbnr_enabled) {
		bfr.Add(Gfh_tag_.Div_lhs_bgn);
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__class, Atr__class__toc);
		if (pgbnr_enabled)
			Gfh_atr_.Add(bfr, Atr__data__toc__mode, Xoh_toc_data.Toc_mode__pgbnr);
		Gfh_tag_.Bld_lhs_end_nde(bfr);
		bfr.Add(Gfh_tag_.Div_rhs);
	}
	public static void Write_placeholder(Xoa_page pg, BryWtr bfr) {
		pg.Html_data().Toc_mgr().Toc_bgn_(bfr.Len());
	}
	public static void Write_toc(BryWtr rv, Xoa_page pg, Xoh_wtr_ctx hctx) {
		int toc_bgn = pg.Html_data().Toc_mgr().Toc_bgn(); if (toc_bgn == BryFind.NotFound) return; // no TOC

		// build body
		byte[] bry = rv.ToBryAndClear();	// NOTE: create bry to free bfr
		rv.AddMid(bry, 0, toc_bgn);
		if (hctx.Mode_is_hdump())	// NOTE: only write TOC tag in hdump; DATE:2016-08-20
			Xoh_toc_wtr.Write_tag(rv, false);
		else
			pg.Html_data().Toc_mgr().To_html(rv, hctx, pg.Html_data().Xtn_pgbnr() != null);
		rv.AddMid(bry, toc_bgn, bry.length);
	}
}
