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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.styles.*;
import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.*;
public class Xoh_gly_itm_data {
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public Xoh_img_data Img_data() {return img_data;} private Xoh_img_data img_data = new Xoh_img_data();
	public boolean Img_exists() {return img_exists;} private boolean img_exists;
	public int Li_w() {return li_w;} private int li_w;
	public int Div_1_w() {return div_1_w;} private int div_1_w;
	public int Div_2_margin() {return div_2_margin;} private int div_2_margin;
	public byte Capt_tid() {return capt_tid;} private byte capt_tid;
	public int Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int Capt_end() {return capt_end;} private int capt_end;
	private void Clear() {
		img_exists = false;
		capt_bgn = capt_end = li_w = div_1_w = div_2_margin = -1;
		capt_tid = Capt_tid__null;
		img_data.Clear();
	}
	public boolean Parse1(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, byte[] src, Gfh_tag_rdr tag_rdr, Gfh_tag li_tag) {
		this.Clear();
		this.src_bgn = li_tag.Src_bgn();
		this.li_w = li_tag.Atrs__style_get_as_int(Gfh_style_key_.Bry__width);
		Gfh_tag div_0_head = tag_rdr.Tag__move_fwd_head();
		if (!div_0_head.Chk_name(Gfh_tag_.Id__div)) return false;									// <div style="width: 175px">
		Gfh_tag div_1_head = tag_rdr.Tag__move_fwd_head();
		if (!div_1_head.Chk(Gfh_tag_.Id__div, Xoh_thm_data.Atr__class__thumb)) return false;		// chk for <div class="thumb" style="height: 170px;">
		this.div_1_w = div_1_head.Atrs__style_get_as_int(Gfh_style_key_.Bry__width);
		int div_2_bgn = div_1_head.Src_end();
		Gfh_tag div_1_tail = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);							// </div> for class="thumb"
		int div_2_end = div_1_tail.Src_bgn();
		Gfh_tag div_2_head = tag_rdr.Tag__find_fwd_head(div_2_bgn, div_2_end, Gfh_tag_.Id__div);	// find <div style="margin:15px auto;">
		if (div_2_head.Src_exists()) {	// img_exists
			img_exists = true;
			this.div_2_margin = div_2_head.Atrs__style_get_as_int(Gfh_style_key_.Bry__margin);
			tag_rdr.Pos_(div_2_head.Src_end());
			Gfh_tag anch_tag = tag_rdr.Tag__move_fwd_head();
			if (!anch_tag.Chk(Gfh_tag_.Id__a, Xoh_img_data.Bry__cls__anch__image)) return false;	
			if (!img_data.Init_by_parse(hdoc_wkr, hctx, tag_rdr, src, anch_tag, null)) return false;
		}
		else {
			img_exists = false;
		}
		if (!Parse_gallerytext(src, tag_rdr)) return false;
		tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
		this.src_end = tag_rdr.Pos();
		return true;
	}
	private boolean Parse_gallerytext(byte[] src, Gfh_tag_rdr tag_rdr) {
		Gfh_tag capt_head = tag_rdr.Tag__move_fwd_head();
		if (!capt_head.Chk(Gfh_tag_.Id__div, Atr__cls__gallerytext)) return false;			// chk for <div class='gallerytext'>
		int capt_head_end = capt_head.Src_end();
		Gfh_tag capt_tail = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
		int capt_tail_bgn = capt_tail.Src_bgn();
		int capt_tail_end = capt_tail.Src_end();
		capt_bgn = Bry_find_.Find_fwd_while_not_ws(src, capt_head_end, capt_tail_bgn);	// skip any \n, \s
		if (capt_tail_bgn - capt_bgn == 0) {
			capt_tid = Capt_tid__empty;
			capt_end = capt_bgn;
			return true;										// EX: <div class='gallerytext'></div>
		}
		Object capt_tid_obj = Capt_tid__trie.Match_bgn(src, capt_bgn, capt_tail_bgn);
		if (capt_tid_obj == null) return false;	// something wrong;
		this.capt_tid = ((Byte_obj_val)capt_tid_obj).Val();
		boolean capt_head_is_p = false;
		switch (capt_tid) {
			case Capt_tid__p:		capt_bgn += 3; capt_head_is_p = true; break;		// EX: <div class='gallerytext'><p>
			case Capt_tid__br:		capt_bgn += 4; break;								// EX: <div class='gallerytext'><br>
		}
		if (capt_head_is_p)
			tag_rdr.Pos_(capt_bgn);
		capt_bgn = Bry_find_.Find_fwd_while_not_ws(src, capt_bgn, capt_tail_bgn);
		capt_end = capt_tail_bgn;
		if (capt_head_is_p) {
			Gfh_tag div_tail = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);	// search for closing </div>				
			capt_end = div_tail.Src_bgn();
			if (Bry_.Match(src, capt_end - 5, capt_end, Bry__p__rhs))
				capt_end -= 5; 													// set capt_end to before </p>; 5 = "\n</p>";
			else																// no </p>; occurs when <hr> in middle; PAGE:fr.w:Forfry DATE:2016-06-24
				capt_tid = Capt_tid__p_wo_rhs;
		}
		tag_rdr.Pos_(capt_tail_end);
		return true;
	}
	private static final    byte[] Atr__cls__gallerytext = Bry_.new_a7("gallerytext"), Bry__p__rhs = Bry_.new_a7("</p>\n");
	public static final byte Capt_tid__p = 0, Capt_tid__br = 1, Capt_tid__empty = 2, Capt_tid__null = 3, Capt_tid__p_wo_rhs = 4;
	private static final    Btrie_slim_mgr Capt_tid__trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(Gfh_tag_.P_lhs	, Capt_tid__p)
	.Add_bry_byte(Gfh_tag_.Br_lhs	, Capt_tid__br)
	;
}
