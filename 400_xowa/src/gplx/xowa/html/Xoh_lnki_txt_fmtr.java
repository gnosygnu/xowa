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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
public class Xoh_lnki_txt_fmtr implements Bry_fmtr_arg {	// formats alt or caption
	private Bry_fmtr fmtr; private Xoh_html_wtr wtr; private Xop_ctx ctx; private Xoh_html_wtr_ctx hctx; private byte[] src; private Xop_tkn_itm tkn;
	public Xoh_lnki_txt_fmtr Set(Xoh_html_wtr wtr, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, byte[] src, Xop_tkn_itm tkn, Bry_fmtr fmtr) {
		this.wtr = wtr; this.ctx = ctx; this.hctx = hctx; this.src = src; this.tkn = tkn; this.fmtr = fmtr; 
		return this;
	}
	public void XferAry(Bry_bfr trg, int idx) {
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		wtr.Write_tkn(tmp_bfr, ctx, hctx, src, null, Xoh_html_wtr.Sub_idx_null, tkn);
		byte[] bry = tmp_bfr.XtoAryAndClear();
		if (bry.length == 0) return;
		fmtr.Bld_bfr_many(trg, bry);
	}
}
