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
package gplx.xowa.html.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public class Xoh_lnki_text_fmtr implements Bry_fmtr_arg {	// formats alt or caption
	private final Bry_bfr_mkr bfr_mkr; private final Xoh_html_wtr html_wtr;
	private Xop_ctx ctx; private Xoh_wtr_ctx hctx; private byte[] src; private Xop_tkn_itm text_tkn; private Bry_fmtr fmtr; 
	public Xoh_lnki_text_fmtr(Bry_bfr_mkr bfr_mkr, Xoh_html_wtr html_wtr) {this.bfr_mkr = bfr_mkr; this.html_wtr = html_wtr;}
	public Xoh_lnki_text_fmtr Set(Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_tkn_itm text_tkn, Bry_fmtr fmtr) {
		this.ctx = ctx; this.hctx = hctx; this.src = src; this.text_tkn = text_tkn; this.fmtr = fmtr; 
		return this;
	}
	public void XferAry(Bry_bfr trg, int idx) {
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		html_wtr.Write_tkn(tmp_bfr, ctx, hctx, src, null, Xoh_html_wtr.Sub_idx_null, text_tkn);
		tmp_bfr.Mkr_rls();
		if (tmp_bfr.Len() == 0) return;
		byte[] bry = tmp_bfr.Xto_bry_and_clear();
		if (fmtr == Null_fmtr)
			trg.Add(bry);
		else
			fmtr.Bld_bfr_many(trg, bry);
	}
	public static final Bry_fmtr Null_fmtr = null;
}
