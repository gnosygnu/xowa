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
package gplx.xowa.htmls.core.wkrs.thms.divs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.thms.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
public class Xoh_thm_caption_data {
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public int Capt_1_bgn() {return capt_1_bgn;} private int capt_1_bgn;
	public int Capt_1_end() {return capt_1_end;} private int capt_1_end;
	public boolean Capt_1_exists() {return capt_1_end > capt_1_bgn;}
	public int Capt_2_bgn() {return capt_2_bgn;} private int capt_2_bgn;
	public int Capt_2_end() {return capt_2_end;} private int capt_2_end;
	public boolean Capt_2_exists() {return capt_2_end > capt_2_bgn;}
	public boolean Capt_2_is_tidy() {return capt_2_is_tidy;} private boolean capt_2_is_tidy;
	public int Capt_3_bgn() {return capt_3_bgn;} private int capt_3_bgn;
	public int Capt_3_end() {return capt_3_end;} private int capt_3_end;
	public boolean Capt_3_exists() {return capt_3_end > capt_3_bgn;}
	public boolean Xowa_alt_text_exists() {return xowa_alt_text_exists;} private boolean xowa_alt_text_exists;
	public Xoh_thm_magnify_data Magnify_data() {return magnify_data;} private final    Xoh_thm_magnify_data magnify_data = new Xoh_thm_magnify_data();
	public void Clear() {
		this.capt_2_is_tidy = this.xowa_alt_text_exists = false;
		this.src_bgn = src_end = capt_1_bgn = capt_1_end = capt_2_bgn = capt_2_end = capt_3_bgn = capt_3_end = -1;
		magnify_data.Clear();
	}
	public boolean Parse1(Xoh_hdoc_wkr hdoc_wkr, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag capt_head) {
		this.src_bgn = capt_head.Src_bgn();
		if (!magnify_data.Parse(hdoc_wkr, tag_rdr, src, capt_head)) return false;
		this.capt_1_bgn = magnify_data.Magnify_tail_div().Src_end();
		if (src[capt_1_bgn] == Byte_ascii.Cr) ++capt_1_bgn;	// wikitext sometimes has \r\n instead of \n; PAGE:en.w:List_of_Saint_Petersburg_Metro_stations; DATE:2016-01-04
		if (src[capt_1_bgn] != Byte_ascii.Nl) tag_rdr.Err_wkr().Fail("expected newline before caption");
		++capt_1_bgn;	// skip \n
		tag_rdr.Pos_(magnify_data.Magnify_tail_div().Src_end() + 1);	// also move tag_rdr forward one
		Gfh_tag capt_tail = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
		this.capt_1_end = capt_tail.Src_bgn();
		Capt_2_chk(tag_rdr, capt_tail, src);
		this.src_end = tag_rdr.Pos();
		return true;
	}
	private void Capt_2_chk(Gfh_tag_rdr tag_rdr, Gfh_tag capt_tail, byte[] src) {
		Gfh_tag nxt_div_head = tag_rdr.Tag__peek_fwd_head(Gfh_tag_.Id__div); 
		if (nxt_div_head.Atrs__cls_has(gplx.xowa.htmls.core.wkrs.lnkis.htmls.Xoh_file_fmtr__basic.Bry__xowa_alt_text)) {
                tag_rdr.Tag__move_fwd_head();
			xowa_alt_text_exists = true;
		}
		int capt_tail_end = capt_tail.Src_end();
		Gfh_tag nxt_div_tail = tag_rdr.Tag__peek_fwd_tail(Gfh_tag_.Id__div); 
		int nxt_div_tail_bgn = nxt_div_tail.Src_bgn();
		Gfh_tag nxt_tag = tag_rdr.Tag__find_fwd_head(tag_rdr.Pos(), nxt_div_tail_bgn, Gfh_tag_.Id__hr); 
		if (nxt_tag.Name_id() == Gfh_tag_.Id__hr) {	// "alt" text
			tag_rdr.Tag__move_fwd_head();												// <hr>
			nxt_tag = tag_rdr.Tag__move_fwd_head().Chk_name_or_fail(Gfh_tag_.Id__div);	// <div>
			capt_2_bgn = nxt_tag.Src_end();	// NOTE: do not try to trim ws; PAGE:en.w:Chimney_sweep; DATE:2016-01-05
			nxt_tag = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);						// </div>
			capt_2_end = nxt_tag.Src_bgn(); // NOTE: do not try to trim ws; PAGE:en.w:Chimney_sweep; DATE:2016-01-05
			if (xowa_alt_text_exists)
				tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
		}
		else {
			if (!Bry_.Match(src, capt_tail_end, capt_tail_end + 7, Bry__div_1_tail_bgn)) {	// next chars should be "\n</div>"
				capt_2_is_tidy = true;
				capt_2_bgn = capt_tail_end;
				capt_2_end = nxt_div_tail_bgn;
			}
		}
	}
	public void Capt_3_(int bgn, int end) {this.capt_3_bgn = bgn; this.capt_3_end = end;}
	public static final    byte[] Bry__div_1_tail_bgn = Bry_.new_a7("\n</div>");
}
