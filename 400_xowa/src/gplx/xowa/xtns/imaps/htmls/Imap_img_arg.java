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
package gplx.xowa.xtns.imaps.htmls;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.BryBfrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.xtns.imaps.*;
import gplx.core.primitives.*; import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.xowa.files.*; import gplx.xowa.parsers.lnkis.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.parsers.*;
import gplx.xowa.xtns.imaps.itms.*;
public class Imap_img_arg implements BryBfrArg {
	private final Xoh_wtr_ctx hctx; private final Imap_map map; private final Imap_xtn_mgr xtn_mgr;
	private final int img_elem_id, img_w, img_h;
	private final byte[] img_alt, img_src, img_cls, img_href, lnki_ttl;
	private final Int_2_ref margin_calc = new Int_2_ref();
	public Imap_img_arg(Xoh_wtr_ctx hctx, Imap_xtn_mgr xtn_mgr, Imap_map map
		, int img_elem_id, byte[] img_alt, byte[] img_src, int img_w, int img_h, byte[] img_cls, byte[] img_href, byte[] lnki_ttl) {
		this.hctx = hctx; this.map = map; this.xtn_mgr = xtn_mgr;
		this.img_elem_id = img_elem_id; this.img_w = img_w; this.img_h = img_h;
		this.img_alt = img_alt; this.img_src = img_src; this.img_cls = img_cls; this.img_href = img_href;
		this.lnki_ttl = lnki_ttl;
	}
	public void AddToBfr(BryWtr bfr) {
		BryFmtr fmtr = Imap_html_fmtrs.Img_anchor_none;
		byte[] anchor_href = BryUtl.Empty, anchor_text = BryUtl.Empty;
		Imap_part_dflt itm_dflt = map.Dflt();
		byte[] img_href_tmp = img_href;
		if (hctx.Mode_is_hdump()) {
			BryWtr tmp_bfr = BryBfrUtl.Get();
			try {
				byte[] data_xowa_image = Xoh_file_fmtr__hdump.Bld_xowa_image_data(tmp_bfr, Xop_lnki_type.Id_none, img_w, img_h, Xop_lnki_tkn.Upright_null, Xof_lnki_time.Null, Xof_lnki_page.Null);
				byte[] data_xowa_title = Gfh_atr_.Make(tmp_bfr, Xoh_img_xoimg_data.Bry__data_xowa_title, lnki_ttl);
				byte[] usemap = tmp_bfr.Add(Imap_xtn_mgr.Bry__usemap__html).AddIntVariable(map.Id()).AddByteQuote().ToBryAndClear();
				int img_w_tmp = img_w; int img_h_tmp = img_h;
				byte[] img_src_tmp = BryUtl.Empty; // ISSUE#:553; DATE:2019-09-25
				img_w_tmp = img_h_tmp = 0;
				Xoh_file_fmtr__hdump.Add_anch_n(tmp_bfr, data_xowa_title, data_xowa_image, img_src_tmp, img_w_tmp, img_h_tmp, Xoh_img_cls_.Tid__none, BryUtl.Empty, img_alt, usemap);
				bfr.AddByteNl().AddByteRepeat(AsciiByte.Space, 6);
				bfr.AddBfrAndClear(tmp_bfr);
			} finally {tmp_bfr.MkrRls();}
		}
		else {
			Xoh_arg_img_core img_core_fmtr = xtn_mgr.Img_core_arg();
			img_core_fmtr.Init(img_elem_id, img_src, img_w, img_h);
			if (itm_dflt != null) {
				fmtr = itm_dflt.Link_tid() == Xop_tkn_itm_.Tid_lnki ? Imap_html_fmtrs.Img_anchor_lnki : Imap_html_fmtrs.Img_anchor_lnke;
				anchor_href = itm_dflt.Link_href();
				anchor_text = itm_dflt.Link_text();
			}
			fmtr.BldToBfrMany(bfr, map.Id(), img_elem_id, img_alt, img_core_fmtr, img_cls, anchor_href, anchor_text);
		}
		Imap_part_desc itm_desc = map.Desc();
		if (itm_desc != null) {
			byte[] desc_icon_url = xtn_mgr.Desc_icon_url();
			byte[] data_xowa_hdump = BryUtl.Empty;
			if (hctx.Mode_is_hdump()) {// ISSUE#:553 DATE:2019-09-07
				desc_icon_url = BryUtl.Empty;
				data_xowa_hdump = Imap_desc_hdump_wkr.HDUMP_ATR;
			}
			Imap_desc_tid.Calc_margins(margin_calc, itm_desc.Desc_tid(), img_w, img_h);
			Imap_html_fmtrs.Desc_main.BldToBfrMany(bfr, margin_calc.Val_0(), margin_calc.Val_1(), img_href_tmp, xtn_mgr.Desc_msg(), desc_icon_url, data_xowa_hdump);
		}
	}
}
