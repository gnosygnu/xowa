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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.hzips.stats.*;
public class Xoh_hdr_hzip implements Xoh_hzip_wkr {
	public String Key() {return Xoh_hzip_dict_.Key__hdr;}
	public void Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, byte[] src, int rng_bgn, int rng_end, int level, int capt_bgn, int capt_end, byte[] anch) {
		stat_itm.Hdr_add(level);
		bfr.Add(Xoh_hzip_dict_.Bry__hdr);											// add hook
		bfr.Add_int_digits(1, level);												// add level; EX: 2 in <h2>
		bfr.Add_mid(src, capt_bgn, capt_end).Add_byte(Xoh_hzip_dict_.Escape);		// add caption
		if (anch != null) bfr.Add(anch);											// add anchor
		bfr.Add_byte(Xoh_hzip_dict_.Escape);										// add escape
	}
	public int Decode(Bry_bfr bfr, Xoh_decode_ctx ctx, Bry_rdr rdr, byte[] src, int hook_bgn) {
		byte level = rdr.Read_byte();
		int capt_bgn = rdr.Pos();
		int capt_end = rdr.Find_fwd_lr(Xoh_hzip_dict_.Escape);
		int anch_bgn = rdr.Pos();
		int anch_end = rdr.Find_fwd_lr(Xoh_hzip_dict_.Escape);

		bfr.Add(Bry__hdr__0).Add_byte(level);
		bfr.Add(Bry__hdr__1);
		if (anch_end > anch_bgn) 
			bfr.Add_mid			(src, anch_bgn, anch_end);
		else
			bfr.Add_mid_w_swap	(src, capt_bgn, capt_end, Byte_ascii.Space, Byte_ascii.Underline);
		bfr.Add(Bry__hdr__2);
		bfr.Add_mid(src, capt_bgn, capt_end);
		bfr.Add(Bry__hdr__3).Add_byte(level);
		bfr.Add_byte(Byte_ascii.Angle_end);
		return rdr.Pos();
	}
	private static final byte[]
	  Bry__hdr__0 = Bry_.new_a7("<h")
	, Bry__hdr__1 = Bry_.new_a7(">\n  <span class=\"mw-headline\" id=\"")
	, Bry__hdr__2 = Bry_.new_a7("\">")
	, Bry__hdr__3 = Bry_.new_a7("</span>\n</h")
	;
}
