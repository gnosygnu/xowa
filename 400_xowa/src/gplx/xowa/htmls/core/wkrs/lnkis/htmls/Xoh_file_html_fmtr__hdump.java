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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*;
import gplx.xowa.files.*; import gplx.xowa.htmls.core.makes.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.parsers.lnkis.*;
public class Xoh_file_html_fmtr__hdump extends Xoh_file_html_fmtr__base {
	private final Bry_bfr tmp_bfr = Bry_bfr.reset_(128);
	@Override public void Html_full_img(Bry_bfr bfr, gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx hctx, Xoae_page page, byte[] src, Xof_file_itm xfer_itm
		, int uid, byte[] a_href, boolean a_href_is_file, byte a_cls, byte a_rel, byte[] a_title, byte[] a_xowa_title
		, int img_w, int img_h, byte[] img_src, byte[] img_alt, byte img_cls, byte[] img_cls_other) {
		boolean link_is_empty = Bry_.Len_eq_0(a_href);	// NOTE: set link_is_empty before blanking a_href below
		if (a_href_is_file) a_href = Bry_.Empty;
		byte[] data_xowa_title = Gfh_atr_.Make(tmp_bfr, Xoh_img_xoimg_data.Bry__data_xowa_title, a_xowa_title);
		byte[] data_xowa_image = Bld_xowa_image_data(tmp_bfr, xfer_itm.Lnki_type(), xfer_itm.Lnki_w(), xfer_itm.Lnki_h(), xfer_itm.Lnki_upright(), xfer_itm.Lnki_time(), xfer_itm.Lnki_page());
		if (link_is_empty)
			Bld_anch_n(bfr, data_xowa_title, data_xowa_image, img_cls, img_cls_other, img_alt, Bry_.Empty);
		else
			fmtr_anch_y.Bld_bfr_many(bfr
			, a_href, Xoh_lnki_consts.A_cls_to_bry(a_cls), Xoh_lnki_consts.A_rel_to_bry(a_rel), a_title, a_xowa_title
			, Gfh_utl.Escape_html_as_bry(img_alt), data_xowa_title, data_xowa_image, arg_img_core.Init(uid, Bry_.Empty, 0, 0), Xoh_img_cls_.To_html(img_cls, img_cls_other));
	}
	public static void Bld_anch_n(Bry_bfr bfr, byte[] data_xowa_title, byte[] data_xowa_image, byte img_cls, byte[] img_cls_other, byte[] img_alt, byte[] img_xtra_atrs) {
		fmtr_anch_n.Bld_bfr_many(bfr, data_xowa_title, data_xowa_image, Xoh_img_cls_.To_html(img_cls, img_cls_other), Gfh_utl.Escape_html_as_bry(img_alt), img_xtra_atrs);
	} 
	public static byte[] Bld_xowa_image_data(Bry_bfr tmp_bfr, byte tid, int w, int h, double upright, double time, int page) {
		tmp_bfr.Add_byte_space().Add(Xoh_img_xoimg_data.Bry__data_xowa_image).Add_byte_eq().Add_byte_quote();
		tmp_bfr.Add_int_digits(1, Xop_lnki_type.To_tid(tid)).Add_byte_pipe();
		tmp_bfr.Add_int_variable(w).Add_byte_pipe();
		tmp_bfr.Add_int_variable(h).Add_byte_pipe();
		tmp_bfr.Add_double(upright).Add_byte_pipe();
		tmp_bfr.Add_double(time).Add_byte_pipe();
		tmp_bfr.Add_int_variable(page).Add_byte_quote();
		return tmp_bfr.To_bry_and_clear();
	}
	private 
	  Bry_fmtr fmtr_anch_y = Bry_fmtr.new_
	( "<a href=\"~{a_href}\"~{a_class}~{a_rel}~{a_title} xowa_title=\"~{a_xowa_title}\">"
	+   "<img~{img_xoimg}~{data_xowa_title}~{data_xowa_image}~{img_class} alt=\"~{img_alt}\"/>"
	+ "</a>"
	, "a_href", "a_class", "a_rel", "a_title", "a_xowa_title", "img_alt", "img_xoimg", "data_xowa_title", "data_xowa_image", "img_class"
	)
	;
	private static final Bry_fmt fmtr_anch_n = Bry_fmt.New
	( "<img~{data_xowa_title}~{data_xowa_image} src=\"\" width=\"0\" height=\"0\"~{img_class} alt=\"~{img_alt}\"~{img_xtra_atrs}/>"
	, "data_xowa_title", "data_xowa_image", "img_class", "img_alt", "img_xtra_atrs"
	);
//		public override void Html_full_media(Bry_bfr tmp_bfr, byte[] a_href, byte[] a_title, Bfr_arg html) {
//			fmtr_full_media.Bld_bfr_many(tmp_bfr, a_href, a_title, html);
//		}
	public static final Xoh_file_html_fmtr__hdump Hdump = new Xoh_file_html_fmtr__hdump(); Xoh_file_html_fmtr__hdump() {}
}
