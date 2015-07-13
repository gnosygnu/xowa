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
class Xow_popup_anchor_finder {
	private byte[] src, find;
	private int src_len, nl_lhs;
	public int Find(byte[] src, int src_len, byte[] find, int bgn) {
		this.src = src; this.src_len = src_len; this.find = find; this.nl_lhs = bgn;
		if (bgn == Xop_parser_.Doc_bgn_bos && Find_hdr(bgn)) return Xop_parser_.Doc_bgn_bos;// handle BOS separately which won't fit "\n=" search below; EX: "BOS==A==\n"
		int lhs_bgn = bgn;
		while (true) {
			lhs_bgn = Bry_finder.Find_fwd(src, Hdr_bgn, nl_lhs, src_len);
			if (lhs_bgn == Bry_.NotFound) break;	// "\n=" not found; exit;
			if (Find_hdr(lhs_bgn)) return lhs_bgn;
		}
		return Find_id(bgn);
	}
	private boolean Find_hdr(int lhs_bgn) {
		int nl_rhs = Bry_finder.Find_fwd(src, Byte_ascii.Nl, nl_lhs + 1, src_len);		// look for \n
		if (nl_rhs == Bry_finder.Not_found) nl_rhs = src_len - 1;							// no more \n; set to last idx
		nl_lhs = nl_rhs;																	// update nl_lhs for loop
		int lhs_end = Bry_finder.Find_fwd_while(src, lhs_bgn + 1, nl_rhs, Byte_ascii.Eq);	// skip eq; EX: "\n==="; +1 to skip eq
		int rhs_end = Bry_finder.Trim_bwd_space_tab(src, nl_rhs, lhs_end);					// skip ws bwd; EX: "==   \n"
		int rhs_bgn = Bry_finder.Find_bwd_while(src, rhs_end, lhs_end, Byte_ascii.Eq);		// skip eq; EX: "==\n" -> pos before =
		if (rhs_bgn < lhs_end) return false;												// eq found, but is actually lhs_eq; no rhs_eq, so exit; EX: "\n== \n"
		++rhs_bgn;																			// rhs_bgn is 1st char before eq; position at eq; neede for txt_end below
		int txt_bgn = Bry_finder.Trim_fwd_space_tab(src, lhs_end, nl_rhs);					// trim ws
		int txt_end = Bry_finder.Trim_bwd_space_tab(src, rhs_bgn, lhs_end);					// trim ws
		return Bry_.Eq(find, src, txt_bgn, txt_end);										// check for strict match
	}
	private int Find_id(int bgn) {
		byte[] quoted = Bry_.Add(Byte_ascii.Quote_bry, find, Byte_ascii.Quote_bry);
		int rv = Find_id_by_quoted(bgn, quoted);
		if (rv == Bry_finder.Not_found) {
			quoted[0] = Byte_ascii.Apos; quoted[quoted.length - 1] = Byte_ascii.Apos;
			rv = Find_id_by_quoted(bgn, quoted);
		}
		return rv;
	}
	private int Find_id_by_quoted(int bgn, byte[] quoted) {
		int rv = Bry_finder.Not_found;
		int pos = Bry_finder.Find_fwd(src, quoted, bgn);
		if (pos == Bry_finder.Not_found) return rv;
		pos = Bry_finder.Trim_bwd_space_tab(src, pos, bgn);
		if (src[pos - 1] != Byte_ascii.Eq) return rv;
		int id_end = Bry_finder.Trim_bwd_space_tab(src, pos - 1, bgn);
		int id_bgn = Bry_finder.Find_bwd_ws(src, id_end - 1, bgn);
		boolean id_match = Int_.Bounds_chk(id_bgn, id_end, src_len) && Bry_.Eq(Id_bry, src, id_bgn + 1, id_end);
		if (!id_match) return rv;
		rv = Bry_finder.Find_bwd(src, Byte_ascii.Nl, id_bgn);
		return rv == Bry_finder.Not_found ? 0 : rv;
	}
	private static final byte[] Hdr_bgn = Bry_.new_a7("\n="), Id_bry = Bry_.new_a7("id");
}
