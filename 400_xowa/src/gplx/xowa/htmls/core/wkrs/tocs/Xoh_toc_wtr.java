/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.htmls.core.wkrs.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*; import gplx.core.threads.poolables.*; import gplx.core.brys.args.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.bfr_args.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.sections.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_toc_wtr implements gplx.core.brys.Bfr_arg, Xoh_wtr_itm {
	private byte toc_mode;
	public void Init_by_parse(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_toc_data data) {
		Init_by_decode(hpg, hctx, src, data);
		this.Bfr_arg__add(bfr);
	}
	public boolean Init_by_decode(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm) {
		Xoh_toc_data data = (Xoh_toc_data)data_itm;
		this.toc_mode = data.Toc_mode();
		return true;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Write_tag(bfr, toc_mode == Xoh_toc_data.Toc_mode__pgbnr);
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_toc_wtr rv = new Xoh_toc_wtr(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}

	public static final    byte[] 
	  Atr__class__toc				= Bry_.new_a7("xo-toc")
	, Atr__data__toc__mode			= Bry_.new_a7("data-toc-mode")
	;
	public static void Write_tag(Bry_bfr bfr, boolean pgbnr_enabled) {
		bfr.Add(Gfh_tag_.Div_lhs_bgn);
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__class, Atr__class__toc);
		if (pgbnr_enabled)
			Gfh_atr_.Add(bfr, Atr__data__toc__mode, Xoh_toc_data.Toc_mode__pgbnr);
		Gfh_tag_.Bld_lhs_end_nde(bfr);
		bfr.Add(Gfh_tag_.Div_rhs);
	}
	public static void Write_placeholder(Xoa_page pg, Bry_bfr bfr) {
		pg.Html_data().Toc_mgr().Toc_bgn_(bfr.Len());
	}
	public static void Write_toc(Bry_bfr rv, Xoa_page pg, Xoh_wtr_ctx hctx) {
		int toc_bgn = pg.Html_data().Toc_mgr().Toc_bgn(); if (toc_bgn == Bry_find_.Not_found) return; // no TOC

		// build body
		byte[] bry = rv.To_bry_and_clear();	// NOTE: create bry to free bfr
		rv.Add_mid(bry, 0, toc_bgn);
		if (hctx.Mode_is_hdump())	// NOTE: only write TOC tag in hdump; DATE:2016-08-20
			Xoh_toc_wtr.Write_tag(rv, false);
		else
			pg.Html_data().Toc_mgr().To_html(rv, hctx, pg.Html_data().Xtn_pgbnr() != null);
		rv.Add_mid(bry, toc_bgn, bry.length);
	}
}
