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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_hdr_data implements Xoh_data_itm {
	public int Tid() {return Xoh_hzip_dict_.Tid__hdr;}
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public int Hdr_level() {return hdr_level;} private int hdr_level;
	public int Anch_bgn() {return anch_bgn;} private int anch_bgn;
	public int Anch_end() {return anch_end;} private int anch_end;
	public boolean Anch_is_diff() {return anch_is_diff;} private boolean anch_is_diff;
	public int Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int Capt_end() {return capt_end;} private int capt_end;
	public int Capt_rhs_bgn() {return capt_rhs_bgn;} private int capt_rhs_bgn;
	public int Capt_rhs_end() {return capt_rhs_end;} private int capt_rhs_end;
	public boolean Capt_rhs_exists() {return capt_rhs_end > capt_rhs_bgn;}
	public void Clear() {
		this.anch_bgn = anch_end = capt_bgn = capt_end = capt_rhs_bgn = capt_rhs_end = -1;
		this.anch_is_diff = false;
	}
	public boolean Init_by_parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag hdr_head, Gfh_tag span_head) {
		this.Clear();
		this.src_bgn = hdr_head.Src_bgn(); this.hdr_level = hdr_head.Name_id();
		if (hdr_head.Atrs__len() > 0) return false;								// skip manual <h2> with atrs; PAGE:fr.w:Wikipédia:LiveRC/ToDo; DATE:2016-07-02
		Gfh_atr anch_atr = span_head.Atrs__get_by_or_empty(Gfh_atr_.Bry__id);
		if (anch_atr.Val_dat_missing()) return false;							// skip manual <h2> without id; PAGE:fr.w:Portail:Nord-Amérindiens/Image_sélectionnée; DATE:2016-07-01
		this.anch_bgn = anch_atr.Val_bgn(); this.anch_end = anch_atr.Val_end();
		this.capt_bgn = span_head.Src_end();
		Gfh_tag hdr_tail = tag_rdr.Tag__move_fwd_tail(hdr_level);				// find </h2> not </span> since <span> can be nested, but <h2> cannot
		Gfh_tag span_tail = tag_rdr.Tag__peek_bwd_tail(Gfh_tag_.Id__span);		// get </span> before </h2>
		this.capt_end = span_tail.Src_bgn();
		if (capt_end < src_bgn) return false;									// </span> is before <h#>; occurs b/c TIDY will relocate <span headline> out of <h#> if <h#> has center; PAGE:en.s:On_the_Vital_Principle/Book_2/Prelude_to_Chapter_2; DATE:2016-01-21
		if (span_tail.Src_end() != hdr_tail.Src_bgn()) {
			capt_rhs_bgn = span_tail.Src_end(); capt_rhs_end = hdr_tail.Src_bgn();
		}
		this.anch_is_diff = !Bry_.Match_w_swap(src, capt_bgn, capt_end, src, anch_bgn, anch_end, Byte_ascii.Space, Byte_ascii.Underline);	// anch is different than capt; occurs with html and dupe-anchors; EX: "==<i>A</i>==" -> id='A'
		this.src_end = tag_rdr.Pos();
		return true;
	}
	public void Init_by_decode(int hdr_level, boolean anch_is_diff, int anch_bgn, int anch_end, int capt_bgn, int capt_end, int capt_rhs_bgn, int capt_rhs_end) {
		this.hdr_level = hdr_level;
		this.anch_is_diff = anch_is_diff;
		this.anch_bgn = anch_bgn; this.anch_end = anch_end; this.capt_bgn = capt_bgn; this.capt_end = capt_end;
		this.capt_rhs_bgn = capt_rhs_bgn; this.capt_rhs_end = capt_rhs_end;
	}
	public static final    byte[] Bry__class__mw_headline	= Bry_.new_a7("mw-headline");
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_hdr_data rv = new Xoh_hdr_data(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
