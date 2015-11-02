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
public class Xoh_hdr_hzip extends Xoh_hdr_parse_base implements Xoh_hzip_wkr {
	private Hzip_stat_itm stat_itm;
	public String Key() {return Xoh_hzip_dict_.Key__hdr;}
	public void Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, Bry_parser parser, byte[] src, int hook_bgn) {
		this.stat_itm = stat_itm;
		this.Parse(bfr, parser, src, hook_bgn);
	}
	@Override public void Parse_exec(Bry_bfr bfr, byte[] src, int hook_bgn, int rng_bgn, int rng_end, byte level, int anchor_bgn, int anchor_end, int display_bgn, int display_end) {
		stat_itm.Hdr_add(level);
		bfr.Del_by(3);										// delete "<h#"
		bfr.Add(Xoh_hzip_dict_.Bry__hdr);					// add hook
		bfr.Add_int_digits(1, level);						// add level; EX: 2 in <h2>
		bfr.Add_mid(src, display_bgn, display_end);			// add display;
		bfr.Add_byte(Xoh_hzip_dict_.Escape);				// add escape
		if (!Bry_.Match_w_swap(src, display_bgn, display_end, src, anchor_bgn, anchor_end, Byte_ascii.Underline, Byte_ascii.Space))	// check if anchor == display, while treating " " == "_"
			bfr.Add_mid(src, anchor_bgn, anchor_end);		// add anchor
		bfr.Add_byte(Xoh_hzip_dict_.Escape);				// add escape
	}
	public int Decode(Bry_bfr bfr, Bry_parser parser, byte[] src, int hook_bgn) {
		byte level = parser.Read_byte();
		int display_bgn = parser.Pos();
		int display_end = parser.Fwd_bgn(Xoh_hzip_dict_.Escape);
		int anchor_bgn = parser.Pos();
		int anchor_end = parser.Fwd_bgn(Xoh_hzip_dict_.Escape);

		bfr.Add(Bry__hdr__0).Add_byte(level);
		bfr.Add(Bry__hdr__1);
		if (anchor_end - anchor_bgn == 0) 
			bfr.Add_mid_w_swap	(src, display_bgn, display_end, Byte_ascii.Space, Byte_ascii.Underline);
		else
			bfr.Add_mid			(src, anchor_bgn, anchor_end);
		bfr.Add(Bry__hdr__2);
		bfr.Add_mid(src, display_bgn, display_end);
		bfr.Add(Bry__hdr__3).Add_byte(level);
		bfr.Add_byte(Byte_ascii.Angle_end);
		return parser.Pos();
	}
	private static final byte[]
	  Bry__hdr__0 = Bry_.new_a7("<h")
	, Bry__hdr__1 = Bry_.new_a7(" data-xotype='hdr_bgn'>\n  <span class='mw-headline' id='")
	, Bry__hdr__2 = Bry_.new_a7("'>")
	, Bry__hdr__3 = Bry_.new_a7("<!--xo.hdr--></span>\n</h")
	;
}
