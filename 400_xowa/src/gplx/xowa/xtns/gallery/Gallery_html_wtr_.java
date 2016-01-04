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
import gplx.core.brys.fmtrs.*;
import gplx.xowa.parsers.*; import gplx.xowa.htmls.core.htmls.*;
public class Gallery_html_wtr_ {
	public static final byte[] Cls_packed = Bry_.new_a7(" mw-gallery-packed");
	public static final Bry_fmtr
	  Fmtr__ul__nde = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<ul~{id} class=\"gallery~{cls}\" style=\"~{style}\">~{itm_list}"
	, "</ul>"
	), "id", "cls", "style", "itm_list"
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
	, "            <img id=\"xoimg_~{img_id}\" alt=\"~{img_alt}\"~{img_core} />"
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
	public static final Bry_fmtr
	  Fmtr__ul__style				= Bry_fmtr.new_(	"max-width:~{max_width}px; _width:~{max_width}px;", "max_width")
	, Fmtr__ul__cls					= Bry_fmtr.new_(	"gallery mw-gallery-~{mode}", "mode")
	, Fmtr__li__txt					= Bry_fmtr.new_(	"\n  <li class='gallerycaption'>~{caption}</li>", "caption")
	, Fmtr__li__img					= Bry_fmtr.new_(	"\n  <li~{li_id} class=\"gallerybox\" ~{itm_box_w}>"
													  + "\n    <div ~{itm_box_w}>", "li_id", "itm_box_w")
	, Fmtr__div1__img				= Bry_fmtr.new_(	"\n      <div class=\"thumb\" style=\"width:~{width}px;\">", "width")
	, Fmtr__div1__missing			= Bry_fmtr.new_(	"\n      <div class=\"thumb\" style=\"height:~{height}px;\">~{ttl_text}</div>", "height", "ttl_text")
	, itm_div1_fmtr					= Bry_fmtr.new_(	"\n        <div ~{vpad}>\n          ", "vpad")
	, hdump_box_w_fmtr				= Bry_fmtr.new_(	"width:~{width}px;", "width")
	, hdump_img_pad_fmtr			= Bry_fmtr.new_(	"margin:~{width}px auto;", "width")
	;
	public static final byte[] 
	  Id__ul	= Bry_.new_a7("xowa_gallery_ul_")
	, Id__li	= Bry_.new_a7("xowa_gallery_li_")
	;
	private static final byte[] Id__atr = Bry_.new_a7(" id=\"");
	public static byte[] Bld_id(Bry_bfr bfr, byte[] prefix, int id)			{return bfr.Add(prefix).Add_int_variable(id).To_bry_and_clear();}
	public static byte[] Bld_id_atr(Bry_bfr bfr, boolean hdump, byte[] li_id)	{return hdump ? Bry_.Empty : bfr.Add(Id__atr).Add(li_id).Add_byte_quote().To_bry_and_clear();}
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
