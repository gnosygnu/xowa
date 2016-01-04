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
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
public class Xoh_thm_caption_data {
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public int Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int Capt_end() {return capt_end;} private int capt_end;
	public boolean Capt_exists() {return capt_end > capt_bgn;}
	public boolean Capt_moved_by_tidy() {return capt_moved_by_tidy;} private boolean capt_moved_by_tidy;
	public int Alt_div_bgn() {return alt_div_bgn;} private int alt_div_bgn;
	public int Alt_div_end() {return alt_div_end;} private int alt_div_end;
	public boolean Alt_div_exists() {return alt_div_end > alt_div_bgn;}
	public Xoh_thm_magnify_data Magnify_parser() {return magnify_parser;} private final Xoh_thm_magnify_data magnify_parser = new Xoh_thm_magnify_data();
	public void Clear() {
		this.capt_moved_by_tidy = false;
		this.src_bgn = src_end = capt_bgn = capt_end = alt_div_bgn = alt_div_end = -1;
		magnify_parser.Clear();
	}
	public boolean Parse1(Xoh_hdoc_wkr hdoc_wkr, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag capt_head) {
		this.src_bgn = capt_head.Src_bgn();
		if (!magnify_parser.Parse(hdoc_wkr, tag_rdr, src, capt_head)) return false;
		this.capt_bgn = magnify_parser.Magnify_tail_div().Src_end();
		if (src[capt_bgn] != Byte_ascii.Nl) tag_rdr.Err_wkr().Fail("expected newline before caption");
		++capt_bgn;	// skip \n
		tag_rdr.Pos_(magnify_parser.Magnify_tail_div().Src_end() + 1);	// also move tag_rdr forward one
		Gfh_tag capt_tail = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
		this.capt_end = capt_tail.Src_bgn();
		Alt_div_exists(tag_rdr, src);
		this.src_end = tag_rdr.Pos();
		return true;
	}
	public void Chk_capt_moved_by_tidy(byte[] src, int div_1_tail_end, int div_0_tail_bgn) {
		int pos = Bry_find_.Find_fwd_while_ws(src, div_1_tail_end, div_0_tail_bgn);
		if (pos != div_0_tail_bgn) {
			capt_moved_by_tidy = true;
                capt_bgn = div_1_tail_end;
			capt_end = Bry_find_.Find_bwd__skip_ws(src, div_0_tail_bgn, div_1_tail_end);
		}
	}
	private void Alt_div_exists(Gfh_tag_rdr tag_rdr, byte[] src) {
		this.alt_div_bgn = alt_div_end = -1;
		Gfh_tag nxt_div_tail = tag_rdr.Tag__peek_fwd_tail(Gfh_tag_.Id__div); 
		Gfh_tag nxt_tag = tag_rdr.Tag__find_fwd_head(tag_rdr.Pos(), nxt_div_tail.Src_bgn(), Gfh_tag_.Id__hr); 
		if (nxt_tag.Name_id() != Gfh_tag_.Id__hr) return;
		tag_rdr.Tag__move_fwd_head();												// <hr>
		nxt_tag = tag_rdr.Tag__move_fwd_head().Chk_name_or_fail(Gfh_tag_.Id__div);	// <div>
		alt_div_bgn = Bry_find_.Find_fwd_while_ws(src, nxt_tag.Src_end(), src.length);
		nxt_tag = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);					// </div>
		alt_div_end = Bry_find_.Find_bwd_non_ws_or_not_found(src, nxt_tag.Src_bgn() - 1, -1) + 1;
	}
}