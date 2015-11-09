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
public class Xoh_hdr_parser {
	public int Parse(Xoh_hdoc_wkr wkr, Html_tag_rdr rdr, byte[] src, int hdr_name_id, int rng_bgn, Html_tag span) {// <h2><span class='mw-headline' id='A_1'>A 1</span></h2>
		Html_atr anch_atr = span.Atrs__get_by(Html_atr_.Bry__id);
		int anch_bgn = anch_atr.Val_bgn(), anch_end = anch_atr.Val_end();
		int capt_bgn = span.Src_end();
		rdr.Tag__move_fwd_tail(hdr_name_id);											// find </h2> not </span> since <span> can be nested, but <h2> cannot
		int capt_end = rdr.Tag__peek_bwd_tail(Html_tag_.Id__span).Src_bgn();			// get </span> before </h2>
		byte[] anch = null;
		if (!Bry_.Match_w_swap(src, capt_bgn, capt_end, src, anch_bgn, anch_end, Byte_ascii.Space, Byte_ascii.Underline))
			anch = Bry_.Mid(src, anch_bgn, anch_end);									// anch is different than capt; occurs with html and dupe-anchors; EX: "==<i>A</i>==" -> id='A'
		int rng_end = rdr.Pos();
		wkr.On_hdr(rng_bgn, rng_end, hdr_name_id, capt_bgn, capt_end, anch);
		return rng_end;
	}
}
