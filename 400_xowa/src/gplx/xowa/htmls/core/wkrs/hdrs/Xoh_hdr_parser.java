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
	// gplx.xowa.htmls.core.wkrs.hdrs.Xoh_hdr_parser
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
	public int Hdr_level() {return hdr_level;} private int hdr_level;
	public int Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int Capt_end() {return capt_end;} private int capt_end;
	public byte[] Anch_bry() {return anch_bry;} private byte[] anch_bry;
	public int Parse(Xoh_hdoc_wkr wkr, byte[] src, Html_tag_rdr rdr, int hdr_level, int rng_bgn, Html_tag span) {// <h2><span class='mw-headline' id='A_1'>A 1</span></h2>
		this.rng_bgn = rng_bgn; this.hdr_level = hdr_level;
		Html_atr anch_atr = span.Atrs__get_by_or_fail(Html_atr_.Bry__id);
		int anch_bgn = anch_atr.Val_bgn(), anch_end = anch_atr.Val_end();
		this.capt_bgn = span.Src_end();
		rdr.Tag__move_fwd_tail(hdr_level);												// find </h2> not </span> since <span> can be nested, but <h2> cannot
		this.capt_end = rdr.Tag__peek_bwd_tail(Html_tag_.Id__span).Src_bgn();			// get </span> before </h2>
		this.anch_bry = null;
		if (!Bry_.Match_w_swap(src, capt_bgn, capt_end, src, anch_bgn, anch_end, Byte_ascii.Space, Byte_ascii.Underline))
			this.anch_bry = Bry_.Mid(src, anch_bgn, anch_end);							// anch is different than capt; occurs with html and dupe-anchors; EX: "==<i>A</i>==" -> id='A'
		this.rng_end = rdr.Pos();
		wkr.On_hdr(this);
		return rng_end;
	}
	public static final byte[] Bry__class__mw_headline	= Bry_.new_a7("mw-headline");
}
