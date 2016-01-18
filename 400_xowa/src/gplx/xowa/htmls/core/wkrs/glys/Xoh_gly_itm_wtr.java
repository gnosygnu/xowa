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
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_gly_itm_wtr implements Bfr_arg {
	private final Bfr_arg_clearable[] arg_ary;
	private final Bfr_arg__hatr_id li_id = Bfr_arg__hatr_id.New_id("xogly_li_"), img_id = Bfr_arg__hatr_id.New_id(gplx.xowa.htmls.Xoh_img_mgr.Bry__html_uid)
	, div_1_id = Bfr_arg__hatr_id.New_id("xogly_div1_"), div_2_id = Bfr_arg__hatr_id.New_id("xogly_div2_"), div_3_id = Bfr_arg__hatr_id.New_id("xogly_div3_");
	private final Bfr_arg__itm_caption itm_caption_fmtr = new Bfr_arg__itm_caption();
	private int li_w, div_1_w, div_2_margin;
	public Xoh_gly_itm_wtr() {
		arg_ary = new Bfr_arg_clearable[] {li_id, div_1_id, div_2_id, div_3_id, img_id};
	}
	public Xoh_img_wtr Img_wtr() {return img_wtr;} private final Xoh_img_wtr img_wtr = new Xoh_img_wtr();
	public void Init(boolean mode_is_diff, int img_id, int li_w, int div_1_w, int div_2_margin, byte capt_tid, byte[] itm_caption) {
		this.Clear();
		this.li_w = li_w; this.div_1_w = div_1_w; this.div_2_margin = div_2_margin;
		itm_caption_fmtr.Set(capt_tid, itm_caption);
		if (!mode_is_diff) {
			li_id.Set(img_id); div_1_id.Set(img_id); div_2_id.Set(img_id); div_3_id.Set(img_id); img_wtr.Img_id_(img_id);
		}
		img_wtr.Anch_cls_(Xoh_img_data.Bry__cls__anch__image);
	}
	public Xoh_gly_itm_wtr Clear() {
		for (Bfr_arg_clearable arg : arg_ary)
			arg.Bfr_arg__clear();
		// img_wtr.Clear(); // TOMBSTONE: do not clear; clear will be called by Xoh_hzip_img.Decode1
		return this;
	}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return false;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		fmtr.Bld_bfr_many(bfr, li_id, div_1_id, div_2_id, div_3_id, li_w, div_1_w, div_2_margin, img_wtr, itm_caption_fmtr);
	}
	public static final Xoh_gly_itm_wtr[] Ary_empty = new Xoh_gly_itm_wtr[0];
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<li~{li_id} class=\"gallerybox\" style=\"width:~{li_w}px;\">"
	,   "<div~{div_1_id} style=\"width:~{li_w}px;\">"
	,     "<div~{div_2_id} class=\"thumb\" style=\"width:~{div_1_w}px;\">"
	,       "<div~{div_3_id} style=\"margin:~{div_2_margin}px auto;\">~{img_itm}</div>"
	,     "</div>"
	,     "<div class=\"gallerytext\">~{itm_caption}</div>"
	,   "</div>"
	, "</li>"
	), "li_id", "div_1_id", "div_2_id", "div_3_id", "li_w", "div_1_w", "div_2_margin", "img_itm", "itm_caption");
}
class Bfr_arg__itm_caption implements Bfr_arg {
	private byte capt_tid;
	private byte[] capt_bry;
	public void Set(byte tid, byte[] bry)	{this.capt_tid = tid; this.capt_bry = bry;}
	public void Bfr_arg__clear()			{capt_bry = null;}
	public boolean Bfr_arg__missing()			{return capt_bry == null;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		switch (capt_tid) {
			case Xoh_gly_itm_data.Capt_tid__empty:	return;												// <div class='gallerytext'></div>
			case Xoh_gly_itm_data.Capt_tid__p:		bfr.Add_byte_nl().Add(Gfh_tag_.P_lhs); break;		// <div class='gallerytext'><p>
			case Xoh_gly_itm_data.Capt_tid__br:		bfr.Add(Gfh_tag_.Br_lhs).Add_byte_nl(); break;		// <div class='gallerytext'><br>
		}
		bfr.Add(capt_bry);
		int itm_caption_len = capt_bry.length;
		if (capt_tid == Xoh_gly_itm_data.Capt_tid__p) {
			if (itm_caption_len > 3 && Bry_.Match(capt_bry, itm_caption_len - 4, Gfh_tag_.Br_lhs)) bfr.Add_byte_nl();
			bfr.Add(Gfh_tag_.P_rhs);
			bfr.Add_byte_nl();
		}
	}
}
