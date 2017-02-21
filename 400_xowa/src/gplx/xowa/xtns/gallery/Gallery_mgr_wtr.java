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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.lnkis.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.parsers.htmls.*;
import gplx.xowa.files.*;
public class Gallery_mgr_wtr {
	public static void Write_mgr(Bry_bfr bfr, Gallery_mgr_base mgr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Gallery_xnde xnde) {
		// init
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();			
		boolean hctx_is_hdump = hctx.Mode_is_hdump();
		int itm_default_w = mgr.Itm_default_w();
		int itms_per_row = mgr.Itms_per_row();

		// write <ul> lhs
		int	ul_uid = page.Html_data().Xtn_gallery_next_id();
		byte[] ul_id = tmp_bfr.Add(Gallery_mgr_wtr_.Id__ul).Add_int_variable(ul_uid).To_bry_and_clear();
		byte[] ul_style = xnde.Atr_style();
		if (itms_per_row > 0) {	// "perrow" specified; add "_maxwidth" to div1 style
			int max_width = itms_per_row * (itm_default_w + mgr.Get_all_padding());
			ul_style = Fmt_and_add(tmp_bfr, Gallery_mgr_wtr_.Fmtr__ul__style, ul_style, max_width);
		}
		byte[] ul_cls = Fmt_and_add(tmp_bfr, Gallery_mgr_wtr_.Fmtr__ul__cls, xnde.Atr_cls(), Gallery_mgr_base_.To_bry(mgr.Tid()));
		Write_ul_lhs(bfr, tmp_bfr, hctx_is_hdump, wiki.Html_mgr().Whitelist_mgr(), src, xnde, ul_id, ul_cls, ul_style, xnde.Atrs_other());

		// write gallery_caption; EX: <li class='gallerycaption'>caption</li>
		byte[] mgr_caption = xnde.Mgr_caption();
		if (Bry_.Len_gt_0(mgr_caption)) Gallery_mgr_wtr_.Fmtr__mgr_caption.Bld_bfr_many(bfr, mgr_caption);

		// write <li> items
		Xoae_app app = wiki.Appe(); Xoh_html_wtr html_wtr = wiki.Html_mgr().Html_wtr();
		int itm_len = xnde.Itms_len();
		for (int i = 0; i < itm_len; i++)
			Write_itm(bfr, tmp_bfr, app, wiki, page, ctx, html_wtr, hctx, src, mgr, xnde, i, null);
		bfr.Add_byte_nl().Add(Gfh_tag_.Ul_rhs);	// "\n</ul>"

		tmp_bfr.Mkr_rls();
	}
	public static void Write_itm(Bry_bfr bfr, Bry_bfr tmp_bfr, Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx
		, byte[] src, Gallery_mgr_base mgr, Gallery_xnde xnde, int i, Xof_file_itm xfer_itm) {
		boolean hctx_is_hdump = hctx.Mode_is_hdump();

		// get itm ttl / caption
		Gallery_itm itm = (Gallery_itm)xnde.Itms_get_at(i);
		Xoa_ttl itm_ttl = itm.Ttl(); if (Bry_.Len_eq_0(itm_ttl.Page_db())) return;	// if file ttl is invalid, do not write; EX:File:#A.png; DATE:2016-01-12
		byte[] itm_caption = itm.Caption_bry(); if (itm_caption == null) itm_caption = Bry_.Empty;

		// get lnki, xfer, html_w_expand, html_h_expand
		Xop_lnki_tkn lnki = itm.Lnki_tkn();
		int lnki_w_orig = lnki.W(), lnki_h_orig = lnki.H(); // capture orig lnki_w / lnki_h for later
		mgr.Get_thumb_size(lnki, itm.Ext());				// noop if traditional; expand by 1.5 if packed
		if (xfer_itm == null) {								// will be null on 1st pass
			xfer_itm = html_wtr.Lnki_wtr().File_wtr().Lnki_eval(Xof_exec_tid.Tid_wiki_page, ctx, page, lnki);
			xfer_itm.Html_gallery_mgr_h_(xnde.Itm_h_actl());
			xfer_itm.Html_elem_tid_(Xof_html_elem.Tid_gallery_v2);
		}
		int html_w_expand = xfer_itm.Html_w();
		int html_h_expand = xfer_itm.Html_h();

		// get id
		int img_uid = xfer_itm.Html_uid();
		byte[] li_id = Gallery_mgr_wtr_.Bld_id(tmp_bfr, Gallery_mgr_wtr_.Id__li, img_uid);
		byte[] li_id_atr = Gallery_mgr_wtr_.Bld_id_atr(tmp_bfr, hctx_is_hdump, li_id);

		// get file_found
		boolean file_found = xfer_itm.File_exists();
		if (File_found_mode != Bool_.__byte) file_found = File_found_mode == Bool_.Y_byte;	// TEST: override File_found

		// make itm_html; 1st clause is for "missing" itms; 2nd clause is for "found" itms
		byte[] itm_html = Bry_.Empty;
		int div_2_w = -1, div_3_margin = -1;
		if (	!hctx_is_hdump					// not hdump; hdump should always write img regardless of whether or not img is missing; DATE:2015-04-27
			&&	(	!itm_ttl.Ns().Id_is_file()	// not a file
				||	!file_found					// file is missing
				)
			) {									// write text
			itm_html = Gallery_mgr_wtr_.Fmtr__div1__missing.Bld_bry_many(tmp_bfr, mgr.Get_thumb_padding() + mgr.Itm_default_h(), itm_ttl.Page_txt());
			itm.Html_prepare(wiki, ctx, src, xnde, xfer_itm, li_id, i);
			xfer_itm.Html_img_wkr_(itm);
			lnki.W_(lnki_w_orig).H_(lnki_h_orig);
			html_w_expand = lnki_w_orig;		// reset lnki_w_orig / lnki_h_orig else large captions
			html_h_expand = lnki_h_orig;		
		}
		else {
			// get alt
			byte[] alt = itm.Alt_bgn() == Bry_find_.Not_found && Bry_.Len_eq_0(itm_caption)	//	if ( $alt == '' && $text == '' )  $imageParameters['alt'] = $nt->getText();
				? itm.Ttl().Page_txt()
				: Bry_.Mid(src, itm.Alt_bgn(), itm.Alt_end());
			alt = Xoh_html_wtr_escaper.Escape(app.Parser_amp_mgr(), tmp_bfr, alt);	// NOTE: need to handle situations wherein alt has quotes; EX:File:A"b.png; PAGE:en.w:Alexandria,_Romania DATE:2015-12-27

			// get href_ttl
			Xoa_ttl href_ttl = itm.Link_bgn() == Bry_find_.Not_found
				? itm_ttl
				: Xoa_ttl.Parse(wiki, Bry_.Mid(src, itm.Link_bgn(), itm.Link_end()));
			if (href_ttl == null) href_ttl = itm_ttl;	// occurs when link is invalid; EX: A.png|link=<invalid>

			// get w, h
			mgr.Adjust_image_parameters(xfer_itm);		// noop if traditional; reduce by 1.5 if packed
			int html_w_normal = xfer_itm.Html_w();
			int html_h_normal = xfer_itm.Html_h();
			xfer_itm.Init_at_gallery_bgn(html_w_normal, html_h_normal, html_w_expand);// NOTE: file_w should be set to expanded width so js can resize if gallery

			// write div_2
			div_2_w = mgr.Get_thumb_div_width(html_w_expand);
			Gallery_mgr_wtr_.Fmtr__div1__img.Bld_bfr_many(tmp_bfr, div_2_w);

			// write div_3
			div_3_margin = mgr.Get_vpad(mgr.Itm_default_h(), html_h_expand);
			Gallery_mgr_wtr_.Fmtr__div1__vpad.Bld_bfr_many(tmp_bfr, new Gallery_img_pad_fmtr_arg(div_3_margin));	// <div style="margin:~{vpad}px auto;">

			// write <img>
			wiki.Html_mgr().Html_wtr().Lnki_wtr().Write_file(tmp_bfr, ctx, hctx, src, lnki, xfer_itm, alt);
			tmp_bfr.Add(itm_divs_end_bry);
			itm_html = tmp_bfr.To_bry_and_clear();
		}

		// write <li>
		int div_1_w = mgr.Get_gb_width(html_w_expand, html_h_expand);
		Gallery_mgr_wtr_.Fmtr__li__lhs.Bld_bfr_many(bfr, li_id_atr, new Gallery_box_w_fmtr_arg(div_1_w));
		bfr.Add(itm_html);

		// get show_filenames_link
		byte[] show_filenames_link = Bry_.Empty;
		if (xnde.Show_filename()) {
			wiki.Html_mgr().Html_wtr().Lnki_wtr().Write_plain_by_bry
			( tmp_bfr, src, lnki
			, Bry_.Limit(itm_ttl.Page_txt(), 25)	// 25 is defined by captionLength in DefaultSettings.php
			);										// MW:passes know,noclasses which isn't relevant to XO
		}

		// write caption
		wiki.Parser_mgr().Main().Parse_text_to_html(tmp_bfr, ctx, page, hctx, true, itm_caption);
		itm_caption = tmp_bfr.To_bry_and_clear();
		itm_caption = tmp_bfr.Add(show_filenames_link).Add(itm_caption).To_bry_and_clear();
		mgr.Wrap_gallery_text(bfr, itm_caption, html_w_expand, html_h_expand);
		bfr.Add(itm_li_end_bry);
	}

	public static byte File_found_mode = Bool_.__byte;
	private static byte[] Fmt_and_add(Bry_bfr tmp_bfr, Bry_fmtr fmtr, byte[] trailer, Object... fmtr_args) {
		fmtr.Bld_bfr_many(tmp_bfr, fmtr_args);
		if (Bry_.Len_gt_0(trailer)) {
			tmp_bfr.Add_byte_space();
			tmp_bfr.Add(trailer);
		}
		return tmp_bfr.To_bry_and_clear();
	}
	private static void Write_ul_lhs(Bry_bfr bfr, Bry_bfr tmp_bfr, boolean hctx_is_hdump, Xop_xatr_whitelist_mgr whitelist_mgr, byte[] src, Gallery_xnde xnde, byte[] ul_id, byte[] ul_cls, byte[] ul_style, List_adp xatr_list) {
		bfr.Add_byte(Byte_ascii.Lt).Add(Gfh_tag_.Bry__ul);
		if (hctx_is_hdump) {	// if hdump, write extra data;
			tmp_bfr.Add_int_variable(xnde.Itm_w_orig()).Add_byte_pipe();
			tmp_bfr.Add_int_variable(xnde.Itm_h_orig()).Add_byte_pipe();
			tmp_bfr.Add_int_variable(xnde.Itms_per_row());
			Gfh_wtr.Write_atr_bry(bfr, Bry__data_xogly, tmp_bfr.To_bry_and_clear());
		}
		else					// not hdump; write id attribute
			Gfh_wtr.Write_atr_bry(bfr, Gfh_atr_.Bry__id, ul_id);
		Gfh_wtr.Write_atr_bry(bfr, Gfh_atr_.Bry__class, ul_cls);
		Gfh_wtr.Write_atr_bry(bfr, Gfh_atr_.Bry__style, ul_style);
		if (xatr_list != null) {
			int len = xatr_list.Count();
			for (int i = 0; i < len; i++) {
				Mwh_atr_itm xatr = (Mwh_atr_itm)xatr_list.Get_at(i);
				if (!whitelist_mgr.Chk(Xop_xnde_tag_.Tid__ul, src, xatr)) continue;
				byte[] key = xatr.Key_bry();
				byte[] val = xatr.Val_as_bry();
				Gfh_wtr.Write_atr_bry(bfr, key, val);
			}
		}
		bfr.Add_byte(Byte_ascii.Gt);
	}
	private static final    byte[] 
	  itm_li_end_bry	= Bry_.new_a7		  ( "\n    </div>"
											  + "\n  </li>")
	, itm_divs_end_bry	= Bry_.new_a7		  ( "\n        </div>\n      </div>");
	public static final    byte[] Bry__data_xogly = Bry_.new_a7 ("data-xogly");
}
