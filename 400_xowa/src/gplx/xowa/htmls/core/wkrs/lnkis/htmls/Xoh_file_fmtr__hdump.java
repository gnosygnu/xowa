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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.brys.fmtrs.*;	
import gplx.xowa.files.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.makes.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.parsers.lnkis.*;
public class Xoh_file_fmtr__hdump extends Xoh_file_fmtr__basic {		private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(128);
	@Override public void Add_full_img(Bry_bfr bfr, Xoh_wtr_ctx hctx, Xoae_page page, byte[] src, Xof_file_itm xfer_itm
		, int uid, byte[] a_href, boolean a_href_is_file, byte a_cls, byte a_rel, byte[] a_title, byte[] a_xowa_title
		, int img_w, int img_h, byte[] img_src, byte[] img_alt, byte img_cls, byte[] img_cls_other) {

		// init data_xowa_title / data_xowa_image; EX: "xowa_title='A.png'"; "xowa_image='1|220|440|-1|-1|-1'"
		byte[] data_xowa_title = Gfh_atr_.Make(tmp_bfr, Xoh_img_xoimg_data.Bry__data_xowa_title, a_xowa_title);
		byte[] data_xowa_image = Bld_xowa_image_data(tmp_bfr, xfer_itm.Lnki_type(), xfer_itm.Lnki_w(), xfer_itm.Lnki_h(), xfer_itm.Lnki_upright(), xfer_itm.Lnki_time(), xfer_itm.Lnki_page());

		// only write src="..." width="..." height="..." if orig is present
		if (!xfer_itm.Orig_exists()) {
			img_w = img_h = 0;
			img_src = Bry_.Empty;
		}
		else {
			data_xowa_title = Bry_.Empty;
			data_xowa_image = Bry_.Empty;
		}

		// bld bfr
		if (Bry_.Len_eq_0(a_href))
			Add_anch_n(bfr, data_xowa_title, data_xowa_image, img_src, img_w, img_h, img_cls, img_cls_other, img_alt, Bry_.Empty);
		else {
			if (a_href_is_file) a_href = Bry_.Empty;
			fmt__anch_y.Bld_many(bfr
			, a_href, Xoh_lnki_consts.A_cls_to_bry(a_cls), Xoh_lnki_consts.A_rel_to_bry(a_rel), a_title, a_xowa_title
			, data_xowa_title, data_xowa_image, img_src, img_w, img_h, Xoh_img_cls_.To_html(img_cls, img_cls_other), Gfh_utl.Escape_html_as_bry(img_alt)
			);
		}
	}
	public static void Add_anch_n(Bry_bfr bfr, byte[] data_xowa_title, byte[] data_xowa_image, byte[] img_src, int img_w, int img_h, byte img_cls, byte[] img_cls_other, byte[] img_alt, byte[] img_xtra_atrs) {
		fmt__anch_n.Bld_many(bfr, data_xowa_title, data_xowa_image, img_src, img_w, img_h, Xoh_img_cls_.To_html(img_cls, img_cls_other), Gfh_utl.Escape_html_as_bry(img_alt), img_xtra_atrs);
	} 
	public static byte[] Bld_xowa_image_data(Bry_bfr bfr, byte tid, int w, int h, double upright, double time, int page) {
		bfr.Add_byte_space().Add(Xoh_img_xoimg_data.Bry__data_xowa_image).Add_byte_eq().Add_byte_quote();
		bfr.Add_int_digits(1, Xop_lnki_type.To_tid(tid)).Add_byte_pipe();
		bfr.Add_int_variable(w).Add_byte_pipe();
		bfr.Add_int_variable(h).Add_byte_pipe();
		bfr.Add_double(upright).Add_byte_pipe();
		bfr.Add_double(time).Add_byte_pipe();
		bfr.Add_int_variable(page).Add_byte_quote();
		return bfr.To_bry_and_clear();
	}
	private static final    Bry_fmt
	  fmt__anch_n = Bry_fmt.Auto
	( "<img~{data_xowa_title}~{data_xowa_image} src=\"~{img_src}\" width=\"~{img_w}\" height=\"~{img_h}\"~{img_cls} alt=\"~{img_alt}\"~{img_xtra_atrs}/>"
	)
	, fmt__anch_y = Bry_fmt.Auto
	( "<a href=\"~{a_href}\"~{a_class}~{a_rel}~{a_title} xowa_title=\"~{a_xowa_title}\">"
	+ "<img~{data_xowa_title}~{data_xowa_image} src=\"~{img_src}\" width=\"~{img_w}\" height=\"~{img_h}\"~{img_cls} alt=\"~{img_alt}\"/>"
	+ "</a>"
	);
}
