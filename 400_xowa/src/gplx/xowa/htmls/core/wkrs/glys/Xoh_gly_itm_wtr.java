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
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import gplx.xowa.parsers.lnkis.*; import gplx.xowa.xtns.gallery.*;
import gplx.xowa.files.*; import gplx.xowa.guis.cbks.js.*;
public class Xoh_gly_itm_wtr implements Bfr_arg, Js_img_wkr {
	private final    Bfr_arg_clearable[] arg_ary;
	private final    Bfr_arg__hatr_id 
	  li_id		= Bfr_arg__hatr_id.New_id("xogly_li_")
	, img_id	= Bfr_arg__hatr_id.New_id(gplx.xowa.htmls.Xoh_img_mgr.Bry__html_uid)
	, div_1_id	= Bfr_arg__hatr_id.New_id("xowa_gallery_div1_")
	, div_2_id	= Bfr_arg__hatr_id.New_id("xowa_gallery_div2_")
	, div_3_id	= Bfr_arg__hatr_id.New_id("xowa_gallery_div3_");
	private final    Bfr_arg__itm_caption itm_caption_fmtr = new Bfr_arg__itm_caption();
	private byte mode;
	private int xnde_w_orig, xnde_h_orig, xnde_per_row, div_1_w, div_2_w, div_3_margin, li_idx, li_nth;
	public Xoh_gly_itm_wtr() {
		arg_ary = new Bfr_arg_clearable[] {li_id, div_1_id, div_2_id, div_3_id, img_id};
	}
	public Xoh_img_wtr Img_wtr() {return img_wtr;} private final    Xoh_img_wtr img_wtr = new Xoh_img_wtr();
	public void Init(boolean mode_is_diff, byte mode, int xnde_w, int xnde_h, int xnde_per_row, int img_id, int li_idx, int li_nth, int div_1_w, int div_2_w, int div_3_margin, byte capt_tid, byte[] itm_caption) {
		this.Clear();
		this.mode = mode; this.xnde_w_orig = xnde_w; this.xnde_h_orig = xnde_h; this.xnde_per_row = xnde_per_row;
		this.li_idx = li_idx; this.li_nth = li_nth;
		this.div_1_w = div_1_w; this.div_2_w = div_2_w; this.div_3_margin = div_3_margin;
		itm_caption_fmtr.Set(capt_tid, itm_caption);
		if (!mode_is_diff) {
			li_id.Set(img_id); div_1_id.Set(img_id); div_2_id.Set(img_id); div_3_id.Set(img_id); img_wtr.Img_id_(img_id);
		}
		img_wtr.Anch_cls_(Xoh_img_data.Bry__cls__anch__image);
	}
	public Xoh_gly_itm_wtr Clear() {
		this.mode = 0;
		this.xnde_w_orig = xnde_h_orig = xnde_per_row = li_idx = li_nth = -1;
		this.div_1_w = div_2_w = div_3_margin = 0;			
		for (Bfr_arg_clearable arg : arg_ary)
			arg.Bfr_arg__clear();
		// img_wtr.Clear(); // TOMBSTONE: do not clear; clear will be called by Xoh_hzip_img.Decode1
		return this;
	}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return false;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		fmtr.Bld_bfr_many(bfr, li_id, div_1_id, div_2_id, div_3_id, div_1_w, div_2_w, div_3_margin, img_wtr, itm_caption_fmtr);
	}

	public void Js_wkr__update_hdoc(Xoa_page page, Xog_js_wkr js_wkr, int html_uid, int html_w, int html_h, Io_url html_view_url, int orig_w, int orig_h, Xof_ext orig_ext, Io_url html_orig_url, byte[] lnki_ttl) {
		Bry_bfr bfr = Bry_bfr_.New();

		// get mgr
		Gallery_mgr_base mgr = Gallery_mgr_base_.New(mode);
		int xnde_w_actl = xnde_w_orig == -1 ? Gallery_xnde.Default : xnde_w_orig;
		int xnde_h_actl = xnde_h_orig == -1 ? Gallery_xnde.Default : xnde_h_orig;
		mgr.Init(xnde_w_actl, xnde_h_actl, xnde_per_row);

		// get lnki and calculate expanded dimensions; note that packed will generate large widths; EX: 220 -> 2200+
		gplx.xowa.parsers.lnkis.Xop_lnki_tkn lnki = new gplx.xowa.parsers.lnkis.Xop_lnki_tkn();
		lnki.Ttl_(page.Wiki().Ttl_parse(lnki_ttl)).W_(html_w).H_(html_h);	// NOTE: gallery_parser also does ad-hoc creation of lnki_tkn
		mgr.Get_thumb_size(lnki, orig_ext);						// noop if traditional; expand by 1.5 if packed
		Xof_img_size img_size = new Xof_img_size();
		img_size.Html_size_calc(Xof_exec_tid.Tid_wiki_page, lnki.W(), lnki.H(), lnki.Lnki_type(), Xof_patch_upright_tid_.Tid_all, lnki.Upright(), orig_ext.Id(), orig_w, orig_h, Xof_img_size.Thumb_width_img);
		int html_w_expand = img_size.Html_w();
		int html_h_expand = img_size.Html_h();
		Xof_fsdb_itm fsdb_itm = img_wtr.Fsdb_itm(); 
		fsdb_itm.Html_size_(html_w_expand, html_h_expand);

		// update div_1, div_2, div_3 vars
		div_1_w = mgr.Get_gb_width(html_w_expand, html_h_expand);
		div_2_w = mgr.Get_thumb_div_width(html_w_expand);
		div_3_margin = mgr.Get_vpad(xnde_h_actl, html_h_expand);

		// adjust params
		mgr.Adjust_image_parameters(fsdb_itm);					// noop if traditional; reduce by 1.5 if packed
		int html_w_normal = fsdb_itm.Html_w();
		int html_h_normal = fsdb_itm.Html_h();
		//	fsdb_itm.Init_at_gallery_bgn(html_w_normal, html_h_normal, html_w_expand);		// NOTE: gallery_htmlr updates fsdb_itm, but only need to update img_wtr; NOTE: file_w should be set to expanded width so js can resize if gallery
		img_wtr.Init_html(html_w_normal, html_h_normal, html_view_url.To_http_file_bry());	// NOTE: html_view_url uses url generated by Xof_file_wkr; theoretically, packed could generate different file, but for now ignore

		// generate html; call json
		fmtr.Bld_bfr_many(bfr, li_id, div_1_id, div_2_id, div_3_id, div_1_w, div_2_w, div_3_margin, img_wtr, itm_caption_fmtr);
		js_wkr.Html_elem_replace_html(String_.new_u8(li_id.Get_id_val()), bfr.To_str_and_clear());
		if (	Gallery_mgr_base_.Mode_is_packed(mode)	// packed gallery
			&&	li_idx == li_nth) {						// last itm
			js_wkr.Html_gallery_packed_exec();			// call packed js
		}
	}

	public static final    Xoh_gly_itm_wtr[] Ary_empty = new Xoh_gly_itm_wtr[0];
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<li~{li_id} class=\"gallerybox\" style=\"width:~{div_1_w}px;\">"
	,   "<div~{div_1_id} style=\"width:~{div_1_w}px;\">"
	,     "<div~{div_2_id} class=\"thumb\" style=\"width:~{div_2_w}px;\">"
	,       "<div~{div_3_id} style=\"margin:~{div_3_margin}px auto;\">~{img_itm}</div>"
	,     "</div>"
	,     "<div class=\"gallerytext\">~{itm_caption}</div>"
	,   "</div>"
	, "</li>"
	), "li_id", "div_1_id", "div_2_id", "div_3_id", "div_1_w", "div_2_w", "div_3_margin", "img_itm", "itm_caption");
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
			case Xoh_gly_itm_data.Capt_tid__empty:		return;												// <div class='gallerytext'></div>
			case Xoh_gly_itm_data.Capt_tid__p:			bfr.Add_byte_nl().Add(Gfh_tag_.P_lhs); break;		// <div class='gallerytext'><p>
			case Xoh_gly_itm_data.Capt_tid__br:			bfr.Add(Gfh_tag_.Br_lhs).Add_byte_nl(); break;		// <div class='gallerytext'><br>
			case Xoh_gly_itm_data.Capt_tid__p_wo_rhs:	bfr.Add_byte_nl().Add(Gfh_tag_.P_lhs); break;		// <div class='gallerytext'><br>
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
