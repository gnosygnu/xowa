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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.parsers.*;
import gplx.xowa.htmls.core.htmls.*;
public class Xoh_lnki_text_fmtr implements gplx.core.brys.Bfr_arg {	// formats alt or caption
	private final    Bry_bfr_mkr bfr_mkr; private final    Xoh_html_wtr html_wtr;
	private Xop_ctx ctx; private Xoh_wtr_ctx hctx; private byte[] src; private Xop_tkn_itm text_tkn; private Bry_fmtr fmtr; 
	private boolean called_by_alt_as_caption;
	public Xoh_lnki_text_fmtr(Bry_bfr_mkr bfr_mkr, Xoh_html_wtr html_wtr) {this.bfr_mkr = bfr_mkr; this.html_wtr = html_wtr;}
	public Xoh_lnki_text_fmtr Set(Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_tkn_itm text_tkn, Bry_fmtr fmtr, boolean called_by_alt_as_caption) {
		this.ctx = ctx; this.hctx = hctx; this.src = src; this.text_tkn = text_tkn; this.fmtr = fmtr; this.called_by_alt_as_caption = called_by_alt_as_caption;
		return this;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		html_wtr.Write_tkn_to_html(tmp_bfr, ctx, hctx, src, null, Xoh_html_wtr.Sub_idx_null, text_tkn);
		tmp_bfr.Mkr_rls();
		byte[] bry = called_by_alt_as_caption 
			? tmp_bfr.To_bry_and_clear_and_trim()	// NOTE: Trim to handle ws-only alt; EX:[[File:A.png|thumb|alt= ]]; en.w:Bird; DATE:2015-12-28
			: tmp_bfr.To_bry();
		if (bry.length == 0) return;
		if (fmtr == Null_fmtr)
			bfr.Add(bry);
		else
			fmtr.Bld_bfr_many(bfr, bry);
	}
	public static final    Bry_fmtr Null_fmtr = null;
}
