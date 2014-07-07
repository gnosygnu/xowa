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
package gplx.xowa.html.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*;
class Xow_popup_hdr_finder {
	private byte[] src, hdr;
	private int src_len;
	private int nl_pos;
	public int Find(byte[] src, int src_len, byte[] hdr, int bgn) {
		this.src = src; this.src_len = src_len; this.hdr = hdr;
		int lhs_bgn = bgn;
		while (true) {
			boolean found = Find_hdr(lhs_bgn);
			if (found) return lhs_bgn;
			lhs_bgn = Bry_finder.Find_fwd(src, Hdr_bgn, nl_pos, src_len);
			if (lhs_bgn == Bry_.NotFound) break;	// "\n=" not found; exit;
			++lhs_bgn; // skip \n
		}
		return Bry_finder.Not_found;
	}
	private boolean Find_hdr(int lhs_bgn) {
		nl_pos = Bry_finder.Find_fwd(src, Byte_ascii.NewLine, lhs_bgn, src_len);		// look for \n
		if (nl_pos == Bry_finder.Not_found) nl_pos = src_len - 1;						// no more \n; set to last idx
		int lhs_end = Bry_finder.Find_fwd_while(src, lhs_bgn, nl_pos, Byte_ascii.Eq);	// skip eq; EX: "\n==="
		int rhs_end = Bry_finder.Find_bwd_non_ws_or_end(src, nl_pos, lhs_end);			// skip ws bwd; EX: "==   \n"
		int rhs_bgn = Bry_finder.Find_bwd_while(src, rhs_end, lhs_end, Byte_ascii.Eq);	// skip eq
		if (rhs_bgn < lhs_end) return false;											// eq found, but < lhs_end; exit; EX: "\n== \n"			
		++rhs_bgn;																		// rhs_bgn is 1st char before eq; position at eq;
		if (rhs_end - rhs_bgn < 1) return false;										// no eq; exit; EX: "\n==abc  \n"
		int txt_end = Bry_finder.Find_bwd_non_ws_or_end(src, rhs_bgn, lhs_end);			// skip ws before ==; EX: "\n==a   ==\n"
		int txt_bgn = Bry_finder.Find_fwd_while_space_or_tab(src, lhs_end, nl_pos);		// skip spaces after eq
		return Bry_.Eq(hdr, src, txt_bgn, txt_end);										// check for strict match
	}
	private static final byte[] Hdr_bgn = Bry_.new_ascii_("\n=");
}
