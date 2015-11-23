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
package gplx.xowa.htmls.core.wkrs.thms.divs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.thms.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
public class Xoh_thm_caption_parser {
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
	public int Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int Capt_end() {return capt_end;} private int capt_end;
	public int Alt_div_bgn() {return alt_div_bgn;} private int alt_div_bgn;
	public int Alt_div_end() {return alt_div_end;} private int alt_div_end;
	public boolean Alt_div_exists() {return alt_div_end > alt_div_bgn;}
	public Xoh_thm_magnify_parser Magnify_parser() {return magnify_parser;} private final Xoh_thm_magnify_parser magnify_parser = new Xoh_thm_magnify_parser();
	public int Parse(Xoh_hdoc_wkr hdoc_wkr, Html_tag_rdr tag_rdr, byte[] src, Html_tag capt_head) {
		this.rng_bgn = capt_head.Src_bgn();
		magnify_parser.Parse(hdoc_wkr, tag_rdr, src, capt_head);
		this.capt_bgn = magnify_parser.Magnify_tail_div().Src_end();
		Html_tag capt_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);
		this.capt_end = capt_tail.Src_bgn();
		capt_end = Bry_find_.Find_bwd_non_ws_or_end(src, capt_end - 1, -1) + 1;
		Alt_div_exists(tag_rdr, src);
		this.rng_end = tag_rdr.Pos();
		return rng_end;
	}
	private void Alt_div_exists(Html_tag_rdr tag_rdr, byte[] src) {
		this.alt_div_bgn = alt_div_end = -1;
		Html_tag nxt_tag = tag_rdr.Tag__peek_fwd_head(); 
		if (nxt_tag.Name_id() != Html_tag_.Id__hr) return;
		tag_rdr.Tag__move_fwd_head();										// <hr>
		nxt_tag = tag_rdr.Tag__move_fwd_head().Chk_id(Html_tag_.Id__div);	// <div>
		alt_div_bgn = Bry_find_.Find_fwd_while_ws(src, nxt_tag.Src_end(), src.length);
		nxt_tag = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);			// </div>
		alt_div_end = Bry_find_.Find_bwd_non_ws_or_not_found(src, nxt_tag.Src_bgn() - 1, -1) + 1;
	}
}