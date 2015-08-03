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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.files.*; import gplx.xowa.html.*; import gplx.xowa.html.lnkis.*; import gplx.xowa.html.hdumps.core.*; import gplx.xowa.html.hdumps.pages.*;
public class Gallery_html_wtr {
	private final Xoh_arg_img_core img_core_fmtr_basic = new Xoh_arg_img_core__basic(), img_core_fmtr_hdump = new Xoh_arg_img_core__hdump();
	public void Write_html(Bry_bfr bfr, Xoae_app app, Xowe_wiki wiki, Xop_ctx ctx, Xoh_html_wtr wtr, Xoh_wtr_ctx hctx, Xoae_page page, Gallery_xnde mgr, byte[] src) {
		int itm_div_w = Gallery_html_wtr_utl.Calc_itm_div_len(mgr.Itm_w_or_default());
		int itm_div_h = Gallery_html_wtr_utl.Calc_itm_div_len(mgr.Itm_h_or_default());
		int itm_box_w = Gallery_html_wtr_utl.Calc_itm_box_w(itm_div_w);
		int itms_len = mgr.Itms_len();
		int itms_per_row = mgr.Itms_per_row();
		if (itms_per_row == Gallery_xnde.Null) itms_per_row = wiki.Cfg_gallery().Imgs_per_row();
		int mgr_box_width_row = Gallery_html_wtr_utl.Calc_itm_pad_w(itm_box_w) * itms_per_row;
		int row_multiplier; Bry_fmtr mgr_box_style;
		if (itms_per_row == Gallery_xnde.Null) {	// no "perrow" defined; default to total # of items;
			row_multiplier = itms_len;
			mgr_box_style = Gallery_html_wtr_.Mgr_box_style_none;
		}
		else {
			row_multiplier = itms_per_row;
			mgr_box_style = Gallery_html_wtr_.Mgr_box_style_max;
		}
		boolean mode_is_packed = Gallery_mgr_base_.Mode_is_packed(mgr.Mode());
		byte[] mgr_box_cls; int mgr_box_width_all;
		if (mode_is_packed) {
			mgr_box_cls = Gallery_html_wtr_.Cls_packed;
			mgr_box_width_all = 0;
		}
		else {
			mgr_box_cls = Bry_.Empty;
			mgr_box_width_all = Gallery_html_wtr_utl.Calc_itm_pad_w(itm_box_w) * row_multiplier; // 8=Gallery Box borders; REF.MW:ImageGallery.php|GB_BORDERS;
		}
		Bry_bfr itm_bfr = wiki.Utl__bfr_mkr().Get_k004(), tmp_bfr = wiki.Utl__bfr_mkr().Get_k004();
		int mgr_elem_id = -1; int gallery_w_count = 0;
		boolean hctx_is_hdump = hctx.Mode_is_hdump();
		Xoh_arg_img_core img_core_fmtr = hctx_is_hdump ? img_core_fmtr_hdump : img_core_fmtr_basic;
		Xopg_hdump_data hdump_imgs = page.Hdump_data();
		for (int i = 0; i < itms_len; i++) {
			Gallery_itm itm = mgr.Itms_get_at(i);
			byte[] itm_caption = Gallery_html_wtr_.Bld_caption(wiki, ctx, wtr, hctx, itm);
			Xoa_ttl itm_ttl = itm.Ttl();
			if (	itm_ttl != null				// ttl does not have invalid characters
				&&	itm_ttl.Ns().Id_file()		// ttl is in file ns;
				) {
				Xop_lnki_tkn lnki = ctx.Tkn_mkr().Lnki(itm.Ttl_bgn(), itm.Ttl_end()).Ttl_(itm_ttl).W_(mgr.Itm_w()).H_(mgr.Itm_h());
				Xof_file_itm xfer_itm = wtr.Lnki_wtr().File_wtr().Lnki_eval(Xof_exec_tid.Tid_wiki_page, ctx, page, lnki);
				xfer_itm.Html_gallery_mgr_h_(mgr.Itm_h_or_default());
				xfer_itm.Html_elem_tid_(Xof_html_elem.Tid_gallery);
				if (mode_is_packed) {
					if (gallery_w_count < itms_per_row) {
						mgr_box_width_all += Gallery_html_wtr_utl.Calc_itm_pad_w(itm_box_w);
						++gallery_w_count;
					}
					if (xfer_itm.Html_w() > 0) {
						itm_div_w = Gallery_html_wtr_utl.Calc_itm_div_len(xfer_itm.Html_w());
						itm_box_w = Gallery_html_wtr_utl.Calc_itm_box_w(itm_div_w);	// NOTE: redefine itm_box_w for rest of loop
					}
					if (xfer_itm.Html_h() > 0)
						itm_div_h = Gallery_html_wtr_utl.Calc_itm_div_len(xfer_itm.Html_h());
				}
				int itm_elem_id = xfer_itm.Html_uid();
				if (mgr_elem_id == -1)
					mgr_elem_id = itm_elem_id;	// HACK: set mgr_elem_id to first itm_elem_id
				int html_w = xfer_itm.Html_w();
				int html_h = xfer_itm.Html_h();
				byte[] html_src = xfer_itm.Html_view_url().To_http_file_bry();
				if (html_src.length == 0) {	// itm not found; use gallery defaults
					html_w = mgr.Itm_w_or_default();
					html_h = mgr.Itm_h_or_default();
				}
				byte[] lnki_ttl = lnki.Ttl().Page_txt();
				Xoa_ttl lnki_link_ttl = itm_ttl;					// default href to ttl
				if (	itm.Link_bgn() != Bry_.NotFound				// link is not -1; EX: "A.png" has no link specified
					&&	(itm.Link_end() - itm.Link_bgn()) > 0		// and link_end - link_bgn > 0; EX: "A.png|link="; DATE:2014-06-15
					)
					lnki_link_ttl = Xoa_ttl.parse_(wiki, Bry_.Mid(src, itm.Link_bgn(), itm.Link_end()));
				byte[] lnki_href = app.Html__href_wtr().Build_to_bry(wiki, lnki_link_ttl);
				byte[] lnki_alt = itm.Alt_bgn() == Bry_.NotFound ? lnki_ttl : Xoh_html_wtr_escaper.Escape(app.Parser_amp_mgr(), tmp_bfr, Bry_.Mid(src, itm.Alt_bgn(), itm.Alt_end())); 
				img_core_fmtr.Init(itm_elem_id, html_src, html_w, html_h);
				int itm_margin = Gallery_html_wtr_utl.Calc_vpad(mgr.Itm_h(), html_h);
				Gallery_html_wtr_.Itm_img_fmtr.Bld_bfr_many(itm_bfr
					, itm_box_w, itm_div_w, itm_margin
					, itm_elem_id
					, lnki_ttl
					, lnki_href
					, img_core_fmtr
					, itm_caption
					, lnki_alt
					);
				if (hctx_is_hdump)
					hdump_imgs.Imgs_add_img(new Xohd_data_itm__gallery_itm().Data_init_gallery(itm_div_w, itm_box_w, itm_margin), xfer_itm, Xohd_data_itm__gallery_itm.Tid_gallery);
			}
			else {
				Gallery_html_wtr_.Itm_txt_fmtr.Bld_bfr_many(itm_bfr
					, itm_box_w, itm_div_h
					, Bry_.Mid(src, itm.Ttl_bgn(), itm.Ttl_end())
					, itm_caption
					);
			}
		}
		itm_bfr.Mkr_rls();
		tmp_bfr.Mkr_rls();
		int mgr_box_width_max = mgr_box_width_all < mgr_box_width_row ? mgr_box_width_row : mgr_box_width_all;
		Gallery_html_wtr_.Mgr_all_fmtr.Bld_bfr_many(bfr, mgr_elem_id, mgr_box_cls, Bry_fmtr_arg_.fmtr_(mgr_box_style, Bry_fmtr_arg_.int_(mgr_box_width_max)), itm_bfr);
	}
}
class Gallery_html_wtr_ {
	public static final byte[] Cls_packed = Bry_.new_a7(" mw-gallery-packed");
	public static final Bry_fmtr
	  Mgr_all_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<ul id=\"xowa_gallery_ul_~{gallery_id}\" class=\"gallery~{gallery_cls}\" style=\"~{gallery_style}\">~{itm_list}"
	, "</ul>"
	), "gallery_id", "gallery_cls", "gallery_style", "itm_list"
	)
	, Mgr_box_style_none	= Bry_fmtr.new_()
	, Mgr_box_style_max		= Bry_fmtr.new_("max-width:~{gallery_width}px; _width:~{gallery_width}px;", "gallery_width")
	, Itm_img_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <li id=\"xowa_gallery_li_~{img_id}\" class=\"gallerybox\" style=\"width:~{itm_box_width}px;\">"
	, "    <div id=\"xowa_gallery_div1_~{img_id}\" style=\"width:~{itm_box_width}px;\">"
	, "      <div id=\"xowa_gallery_div2_~{img_id}\" class=\"thumb\" style=\"width:~{itm_div_width}px;\">"
	, "        <div id=\"xowa_gallery_div3_~{img_id}\" style=\"margin:~{itm_margin}px auto;\">"
	, "          <a href=\"~{img_href}\" class=\"image\">"
	, "            <img id=\"xowa_file_img_~{img_id}\" alt=\"~{img_alt}\"~{img_core} />"
	, "          </a>"
	, "        </div>"
	, "      </div>"
	, "      <div class=\"gallerytext\">~{itm_caption}"
	, "      </div>"
	, "    </div>"
	, "  </li>"
	), "itm_box_width", "itm_div_width", "itm_margin", "img_id", "img_ttl", "img_href", "img_core", "itm_caption", "img_alt"
	)
	, Itm_txt_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <li id=\"xowa_gallery_li_~{img_id}\" class=\"gallerybox\" style=\"width:~{itm_box_width};\">"
	, "    <div id=\"xowa_gallery_div1_~{img_id}\" style=\"width:~{itm_box_width};\">"
	, "      <div id=\"xowa_gallery_div2_~{img_id}\" style=\"~{itm_div_height}\">"
	, "        ~{itm_text}"
	, "      </div>"
	, "      <div class=\"gallerytext\">~{itm_caption}"
	, "      </div>"
	, "    </div>"
	, "  </li>"
	), "itm_box_width", "itm_div_height", "itm_text", "itm_caption"
	);
	public static byte[] Bld_caption(Xowe_wiki wiki, Xop_ctx ctx, Xoh_html_wtr wtr, Xoh_wtr_ctx hctx, Gallery_itm itm) {
		byte[] rv = itm.Caption_bry();
		if (Bry_.Len_gt_0(rv)) {
			Bry_bfr caption_bfr = wiki.Utl__bfr_mkr().Get_k004();
			Xop_root_tkn caption_root = itm.Caption_tkn();
			wtr.Write_tkn(caption_bfr, ctx, hctx, caption_root.Root_src(), caption_root, Xoh_html_wtr.Sub_idx_null, caption_root);
			rv = caption_bfr.To_bry_and_rls();
		}
		return rv;
	}
}
