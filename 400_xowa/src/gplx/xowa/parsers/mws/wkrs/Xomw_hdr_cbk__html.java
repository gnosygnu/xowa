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
package gplx.xowa.parsers.mws.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
public class Xomw_hdr_cbk__html implements Xomw_hdr_cbk {
	public Bry_bfr Bfr() {return bfr;} private final    Bry_bfr bfr = Bry_bfr_.New();
	public void On_hdr_seen(Xomw_parser_ctx pctx, Xomw_hdr_wkr wkr) {
		// add from txt_bgn to hdr_bgn; EX: "abc\n==A==\n"; "\n==" seen -> add "abc"
		byte[] src = wkr.Src();
		int hdr_bgn = wkr.Hdr_bgn(), txt_bgn = wkr.Txt_bgn();
		if (hdr_bgn > txt_bgn)
			bfr.Add_mid(src, txt_bgn, hdr_bgn);

		// add "\n" unless BOS
		if (hdr_bgn != Xomw_parser_ctx.Pos__bos) bfr.Add_byte_nl();

		// add <h2>...</h2>
		int hdr_num = wkr.Hdr_num();
		bfr.Add(Tag__lhs).Add_int_digits(1, hdr_num).Add(Byte_ascii.Angle_end_bry);	// <h2>
		bfr.Add_mid(wkr.Src(), wkr.Hdr_lhs_end(), wkr.Hdr_rhs_bgn());
		bfr.Add(Tag__rhs).Add_int_digits(1, hdr_num).Add(Byte_ascii.Angle_end_bry);	// </h2>
	}
	public void On_src_done(Xomw_parser_ctx pctx, Xomw_hdr_wkr wkr) {
		// add from txt_bgn to EOS;
		byte[] src = wkr.Src();
		int txt_bgn = wkr.Txt_bgn(), src_end = wkr.Src_end();
		if (txt_bgn != src_end)	// PERF: don't call Add_mid() if hdr is at end of EOS
			bfr.Add_mid(src, txt_bgn, src_end);
	}
	private static final    byte[] 
	  Tag__lhs = Bry_.new_a7("<h")
	, Tag__rhs = Bry_.new_a7("</h")
	;
}
