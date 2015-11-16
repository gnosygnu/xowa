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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;	
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.langs.htmls.parsers.styles.*;
public class Xoh_gly_itm_parser {
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
	// xoimg_parser
	public boolean Img_exists() {return img_exists;} private boolean img_exists;
	public int Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int Capt_end() {return capt_end;} private int capt_end;
	public int Parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, byte[] src, Html_tag_rdr tag_rdr, Html_tag li_tag) {
		this.rng_bgn = li_tag.Src_bgn();
		this.rng_end = li_tag.Src_end();
		
		tag_rdr.Tag__move_fwd_head().Chk_id(Html_tag_.Id__div);	// <div style="width: 175px">
		Html_tag div_1_head = tag_rdr.Tag__move_fwd_head().Chk_id(Html_tag_.Id__div);	// <div class="thumb" style="height: 170px;">
		int file_text_bgn = div_1_head.Src_end();
		Html_tag div_1_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);
		int file_text_end = div_1_tail.Src_bgn();
		Html_tag img_tag = tag_rdr.Tag__find_fwd_head(file_text_bgn, file_text_end, Html_tag_.Id__div);	//	search <div style="margin:15px auto;">
		if (img_tag.Src_exists()) {	// img_exists
			img_exists = true;
			// div_nxt.Atrs__cls_has(Bry__gallery_text); // assert cls = Bry_gallery_text
		}
		else {
			img_exists = false;
		}
		tag_rdr.Tag__move_fwd_head().Chk_id(Html_tag_.Id__div); // <div class="gallerytext"> 
		Html_tag para_head = tag_rdr.Tag__move_fwd_head().Chk_id(Html_tag_.Id__p);
		capt_bgn = para_head.Src_end();
		Html_tag para_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__p);
		capt_end = para_tail.Src_bgn();
		tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);
		tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);
		this.rng_end = tag_rdr.Pos();
		return rng_end;
	}
	// private static final byte[] Bry__gallery_text = Bry_.new_a7("gallerytext");
}
