/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;	
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.styles.*;
import gplx.xowa.htmls.core.wkrs.thms.divs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_thm_data implements Gfh_style_wkr {
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public boolean Rng_valid() {return rng_valid;} private boolean rng_valid;
	public byte Div_0_align() {return div_0_align;} private byte div_0_align;
	public int Div_1_width() {return div_1_width;} private int div_1_width;
	public Xoh_img_data Img_data() {return img_data;} private final    Xoh_img_data img_data = new Xoh_img_data();
	public Xoh_thm_caption_data Capt_data() {return capt_data;} private final    Xoh_thm_caption_data capt_data = new Xoh_thm_caption_data();
	public void Clear() {
		rng_valid = false;
		capt_data.Clear();
		img_data.Clear();
	}
	public boolean Parse1(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, byte[] src, Gfh_tag_rdr tag_rdr, Gfh_tag div_0_head) {
		this.Clear();
		tag_rdr.Err_wkr().Init_by_sect("thm", div_0_head.Src_bgn());
		this.src_bgn = div_0_head.Src_bgn();
		this.div_0_align = div_0_head.Atrs__cls_find_or(gplx.xowa.parsers.lnkis.Xop_lnki_align_h_.Hash, Byte_.Zero);
		Gfh_tag div_0_tail = tag_rdr.Tag__peek_fwd_tail(Gfh_tag_.Id__div);					// </div>
		Gfh_tag div_1_head = tag_rdr.Tag__find_fwd_head(div_0_head.Src_end(), div_0_tail.Src_bgn(), Gfh_tag_.Id__div); // <div class='thumbinner'>
		if (div_1_head.Name_id() != Gfh_tag_.Id__div) return false;
		if (!tag_rdr.Tag__move_fwd_head().Chk_name(Gfh_tag_.Id__div)) return false;			// exit if not div; PAGE:en.w:Chess; DATE:2015-12-27
		this.div_1_width = -1;
		Gfh_style_parser_.Parse(div_1_head, this);											// " style='120px'"
		if (div_1_width == -1) return false; // handle invalid styles from en.w:Template:CSS_image_crop; PAGE:en.w:Carlisle_United_F.C.; DATE:2016-01-01
		if (!img_data.Init_by_parse(hdoc_wkr, hctx, tag_rdr, src, tag_rdr.Tag__move_fwd_head(), null)) return false;	// <a>
		if (!capt_data.Parse1(hdoc_wkr, tag_rdr, src, tag_rdr.Tag__move_fwd_head())) return false;						// <div>
		rng_valid = true;
		tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
		int tag_rdr_pos = tag_rdr.Pos();
		if (!Bry_.Match(src, tag_rdr_pos, tag_rdr_pos + 7, Xoh_thm_caption_data.Bry__div_1_tail_bgn)) {	// TIDY:handle tidy relocating xowa-alt-div between div2 and div3; PAGE:en.w:Non-helical_models_of_DNA_structure; DATE:2016-01-11
			tag_rdr.Pos_(tag_rdr_pos + 6);	// also move tag_rdr forward one
			Gfh_tag nxt_div_tail = tag_rdr.Tag__peek_fwd_tail(Gfh_tag_.Id__div);
			int capt_3_bgn = tag_rdr_pos;
			int capt_3_end = nxt_div_tail.Src_bgn();
			capt_data.Capt_3_(capt_3_bgn, capt_3_end);
		}
		tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
		this.src_end = tag_rdr.Pos();
		hdoc_wkr.On_thm(this);
		return true;
	}
	public boolean On_atr(byte[] src, int atr_idx, int atr_val_bgn, int atr_val_end, int itm_bgn, int itm_End, int key_bgn, int key_end, int val_bgn, int val_end) {
		if (	Bry_.Match(src, key_bgn, key_end, Gfh_style_key_.Bry__width)
			&&	val_bgn - key_end == 1) {	// handle invalid styles from en.w:Template:CSS_image_crop which have "width: 123px"; PAGE:en.w:Abraham_Lincoln; DATE:2016-01-02
			this.div_1_width = Bry_.To_int_or__lax(src, val_bgn, val_end, -1);
		}
		else	// if there are any other attribs, invalidate; EX:style='width:123px;color:blue;'; PAGE:en.w:Wikipedia:New_CSS_framework; DATE:2016-01-11
			this.div_1_width = -1;
		return true;
	} 
	public static final    byte[] 
	  Atr__class__thumb				= Bry_.new_a7("thumb")
	, Atr__class__thumbinner		= Bry_.new_a7("thumbinner")
	, Atr__id__xowa_media_div		= Bry_.new_a7("xowa_media_div")
	;
}
