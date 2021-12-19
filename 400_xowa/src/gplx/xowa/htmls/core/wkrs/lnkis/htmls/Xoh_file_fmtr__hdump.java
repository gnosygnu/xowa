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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.xowa.*;
import gplx.xowa.files.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.parsers.lnkis.*;
public class Xoh_file_fmtr__hdump extends Xoh_file_fmtr__basic {		private final BryWtr tmp_bfr = BryWtr.NewAndReset(128);
	@Override public void Add_full_img(BryWtr bfr, Xoh_wtr_ctx hctx, Xoae_page page, byte[] src, Xof_file_itm xfer_itm
		, int uid, byte[] a_href, boolean a_href_is_file, byte a_cls, byte a_rel, byte[] a_title, byte[] a_xowa_title
		, int img_w, int img_h, byte[] img_src, byte[] img_alt, byte img_cls, byte[] img_cls_other) {

		// init data_xowa_title / data_xowa_image; EX: "xowa_title='A.png'"; "xowa_image='1|220|440|-1|-1|-1'"
		byte[] data_xowa_title = Gfh_atr_.Make(tmp_bfr, Xoh_img_xoimg_data.Bry__data_xowa_title, a_xowa_title);
		byte[] data_xowa_image = Bld_xowa_image_data(tmp_bfr, xfer_itm.Lnki_type(), xfer_itm.Lnki_w(), xfer_itm.Lnki_h(), xfer_itm.Lnki_upright(), xfer_itm.Lnki_time(), xfer_itm.Lnki_page());

		// always null out w, h, src; Hdb__hzip and Hdb__htxt should never write src; Hdb__page_sync will never come here; ISSUE#:553; DATE:2019-09-25
		img_w = img_h = 0;
		img_src = BryUtl.Empty;

		// bld bfr
		if (BryUtl.IsNullOrEmpty(a_href))
			Add_anch_n(bfr, data_xowa_title, data_xowa_image, img_src, img_w, img_h, img_cls, img_cls_other, img_alt, BryUtl.Empty);
		else {
			if (a_href_is_file) a_href = BryUtl.Empty;
			fmt__anch_y.Bld_many(bfr
			, a_href, Xoh_lnki_consts.A_cls_to_bry(a_cls), Xoh_lnki_consts.A_rel_to_bry(a_rel), a_title, a_xowa_title
			, data_xowa_title, data_xowa_image, Gfh_utl.Escape_html_as_bry(img_alt), img_src, img_w, img_h, Xoh_img_cls_.To_html(img_cls, img_cls_other)
			);
		}
	}
	public static void Add_anch_n(BryWtr bfr, byte[] data_xowa_title, byte[] data_xowa_image, byte[] img_src, int img_w, int img_h, byte img_cls, byte[] img_cls_other, byte[] img_alt, byte[] img_xtra_atrs) {
		fmt__anch_n.Bld_many(bfr, data_xowa_title, data_xowa_image, Gfh_utl.Escape_html_as_bry(img_alt), img_src, img_w, img_h, Xoh_img_cls_.To_html(img_cls, img_cls_other), img_xtra_atrs);
	} 
	public static byte[] Bld_xowa_image_data(BryWtr bfr, byte tid, int w, int h, double upright, double time, int page) {
		bfr.AddByteSpace().Add(Xoh_img_xoimg_data.Bry__data_xowa_image).AddByteEq().AddByteQuote();
		bfr.AddIntDigits(1, Xop_lnki_type.To_tid(tid)).AddBytePipe();
		bfr.AddIntVariable(w).AddBytePipe();
		bfr.AddIntVariable(h).AddBytePipe();
		bfr.AddDouble(upright).AddBytePipe();
		bfr.AddDouble(time).AddBytePipe();
		bfr.AddIntVariable(page).AddByteQuote();
		return bfr.ToBryAndClear();
	}
	private static final BryFmt
	  fmt__anch_n = BryFmt.Auto
	( "<img~{data_xowa_title}~{data_xowa_image} alt=\"~{img_alt}\" src=\"~{img_src}\" width=\"~{img_w}\" height=\"~{img_h}\"~{img_cls}~{img_xtra_atrs}/>"
	)
	, fmt__anch_y = BryFmt.Auto
	( "<a href=\"~{a_href}\"~{a_class}~{a_rel}~{a_title} xowa_title=\"~{a_xowa_title}\">"
	+ "<img~{data_xowa_title}~{data_xowa_image} alt=\"~{img_alt}\" src=\"~{img_src}\" width=\"~{img_w}\" height=\"~{img_h}\"~{img_cls}/>"
	+ "</a>"
	);
}
