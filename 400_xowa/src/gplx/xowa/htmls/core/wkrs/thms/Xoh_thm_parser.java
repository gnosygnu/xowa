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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;	
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.langs.htmls.parsers.styles.*;
import gplx.xowa.htmls.core.wkrs.thms.divs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_thm_parser implements Html_atr_style_wkr {
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
	public boolean Rng_valid() {return rng_valid;} private boolean rng_valid;
	public byte Div_0_align() {return div_0_align;} private byte div_0_align;
	public int Div_1_width() {return div_1_width;} private int div_1_width;
	public Xoh_img_parser Img_parser() {return img_parser;} private final Xoh_img_parser img_parser = new Xoh_img_parser();
	public Xoh_thm_caption_parser Capt_parser() {return capt_parser;} private final Xoh_thm_caption_parser capt_parser = new Xoh_thm_caption_parser();
	public int Parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, byte[] src, Html_tag_rdr tag_rdr, Html_tag div_0) {
		tag_rdr.Rdr().Init_by_hook("thm", div_0.Src_bgn(), div_0.Src_bgn());
		this.rng_bgn = div_0.Src_bgn();
		this.div_0_align = div_0.Atrs__cls_find_or_fail(gplx.xowa.parsers.lnkis.Xop_lnki_align_h_.Hash);
		Html_tag div_1 = tag_rdr.Tag__move_fwd_head();											// <div class='thumbinner'>
		this.div_1_width = -1;
		Html_atr_style_parser_.Parse(div_1, this);												// " style='120px'"
		rng_valid = false;
		if (img_parser.Parse(hdoc_wkr, hctx, src, tag_rdr, tag_rdr.Tag__move_fwd_head()) != Xoh_hdoc_ctx.Invalid) {	// <a>
			capt_parser.Parse(hdoc_wkr, tag_rdr, src, tag_rdr.Tag__move_fwd_head());				// <div>
			rng_valid = true;
		}
		tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);											// </div> for div_1
		Html_tag div_0_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);					// </div> for div_0
		this.rng_end = div_0_tail.Src_end();
		hdoc_wkr.On_thm(this);
		return rng_end;
	}
	public boolean On_atr(byte[] src, int atr_idx, int atr_bgn, int atr_end, int key_bgn, int key_end, int val_bgn, int val_end) {
		if (Bry_.Match(src, key_bgn, key_end, Html_atr_style_.Bry__width))
			this.div_1_width = Bry_.To_int_or__lax(src, val_bgn, val_end, -1);
		return true;
	} 
	public static final byte[] 
	  Atr__class__thumb			= Bry_.new_a7("thumb")
	, Atr__class__thumbinner	= Bry_.new_a7("thumbinner")
	;
}
