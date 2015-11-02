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
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.parsers.*;
public class Xoh_hdr_parse {
	public int Parse(Xoh_wkr wkr, Html_tag_rdr rdr, byte[] src, int hdr_tag_id, int hdr_tag_bgn, Html_tag span) {		// <a rel="nofollow" class="external autonumber_id" href="http://a.org">[1]</a>
		Html_atr id_atr = span.Atrs__get_by(Html_atr_.Bry__id);
		int id_bgn = id_atr.Val_bgn(), id_end = id_atr.Val_end();
		int capt_bgn = span.Src_end();
		rdr.Tag__move_fwd_tail(hdr_tag_id);												// find </h2> not </span> since <span> can be nested, but <h2> cannot
		int capt_end = rdr.Tag__peek_bwd_tail(Html_tag_.Id__span).Src_bgn();			// get </span> before </h2>
		boolean id_capt_related = false;
		byte[] capt = Bry_.Mid(src, capt_bgn, capt_end);
		byte[] id = null;
		switch (Match(src, capt_bgn, capt_end, src, id_bgn, id_end)) {
			case Bool_.Y_byte:	break;
			case Bool_.N_byte:	id = Bry_.Mid(src, id_bgn, id_end); break;
			case Bool_.__byte:	id_capt_related = true; break;
		}
		int tag_end = rdr.Pos();
		wkr.On_hdr(hdr_tag_bgn, tag_end, hdr_tag_id, id, capt, id_capt_related);
		return tag_end;
	}
	private static byte Match(byte[] lhs, int lhs_bgn, int lhs_end, byte[] rhs, int rhs_bgn, int rhs_end) {
		int lhs_len = lhs.length;
		if (lhs_end > lhs_len) lhs_end = lhs_len;			// must limit lhs_end to lhs_len, else ArrayIndexOutOfBounds below; DATE:2015-01-31
		int rhs_len = rhs_end - rhs_bgn;
		if (rhs_len != lhs_end - lhs_bgn) return Bool_.N_byte;
		if (rhs_len == 0) return lhs_end - lhs_bgn == 0 ? Bool_.Y_byte : Bool_.N_byte;	// "" only matches ""
		byte rv = Bool_.Y_byte;
		for (int i = 0; i < rhs_len; ++i) {
			int pos = lhs_bgn + i;
			if (pos >= lhs_end) return Bool_.N_byte;		// ran out of lhs; exit; EX: lhs=ab; rhs=abc
			byte lhs_byte = lhs[pos];
			byte rhs_byte = rhs[i + rhs_bgn];
			if (lhs_byte != rhs_byte) {
				if (	lhs_byte == Byte_ascii.Underline
					&&	rhs_byte == Byte_ascii.Space
					)
					continue;
				return Bool_.N_byte;
			}
		}
		return rv;
	}
}
