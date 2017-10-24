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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.files.*; import gplx.xowa.guis.cbks.js.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.xtns.imaps.itms.*; import gplx.xowa.xtns.imaps.htmls.*;
public class Imap_map implements Xoh_file_fmtr, Js_img_wkr {
	private byte img_cls_tid; private Imap_xtn_mgr xtn_mgr;
	private byte[] a_href, img_alt, img_cls_other;
	public Imap_map(int id) {this.id = id;}
	public void Init(Imap_xtn_mgr xtn_mgr, byte[] img_src, Imap_part_img img, Imap_part_dflt dflt, Imap_part_desc desc, Imap_part_shape[] shapes, Imap_err[] errs) {
		this.xtn_mgr = xtn_mgr; this.img_src = img_src; this.img = img; this.dflt = dflt; this.desc = desc; this.shapes = shapes; this.errs = errs;
	}
	public int					Id()			{return id;}			private final    int id;
	public byte[]				Img_src()		{return img_src;}		private byte[] img_src;
	public Imap_part_img		Img()			{return img;}			private Imap_part_img img;
	public Imap_part_dflt		Dflt()			{return dflt;}			private Imap_part_dflt dflt;
	public Imap_part_desc		Desc()			{return desc;}			private Imap_part_desc desc;
	public Imap_part_shape[]	Shapes()		{return shapes;}		private Imap_part_shape[] shapes;
	public Imap_err[]			Errs()			{return errs;}			private Imap_err[] errs;
	public boolean					Invalid()		{return img == null;}	// invalid if missing image; PAGE:en.w:Wikipedia:WikiProject_Games/Advert EX: <imagemap>|thumb;</imagemap>; DATE:2014-08-12

	public void Add_full_img(Bry_bfr tmp_bfr, Xoh_wtr_ctx hctx, Xoae_page page, byte[] src, Xof_file_itm xfer_itm, int uid
		, byte[] a_href, boolean a_href_is_file, byte a_cls, byte a_rel, byte[] a_title, byte[] a_xowa_title
		, int img_w, int img_h, byte[] img_src, byte[] img_alt, byte img_cls, byte[] img_cls_other
		) {
		this.a_href = a_href; this.img_alt = img_alt; this.img_cls_tid = img_cls; this.img_cls_other = img_cls_other;
		xfer_itm.Html_img_wkr_(this);
		xfer_itm.Html_elem_tid_(Xof_html_elem.Tid_imap);
		if (hctx.Mode_is_hdump()) {
			if (xfer_itm.Orig_exists()) {
				img_w = xfer_itm.Html_w();
				img_h = xfer_itm.Html_h();
			}
			else {
				img_w = xfer_itm.Lnki_w();	// NOTE: hdump must dump lnki_w, not img_w; GUI will either (a) write -1 and update later thru js_wkr; (b) get correct img_w from cache; hdump can do neither (a) nor (b); DATE:2016-06-17
				img_h = xfer_itm.Lnki_h();
			}
		}
		Write_imap_div(tmp_bfr, hctx, uid, img_w, img_h, img_src, xfer_itm.Orig_exists(), xfer_itm.Orig_w(), xfer_itm.Orig_h(), a_xowa_title);
	}
	public void Js_wkr__update_hdoc(Xoa_page page, Xog_js_wkr js_wkr, int html_uid
		, int html_w, int html_h, Io_url html_view_url
		, int orig_w, int orig_h, Xof_ext orig_ext, Io_url html_orig_url, byte[] lnki_ttl) {
		Bry_bfr tmp_bfr = Bry_bfr_.Get();
		try {
			Write_imap_div(tmp_bfr, Xoh_wtr_ctx.Basic, html_uid, html_w, html_h, html_view_url.To_http_file_bry(), orig_w > 0, orig_w, orig_h, lnki_ttl);
			js_wkr.Html_elem_replace_html("imap_div_" + Int_.To_str(html_uid), tmp_bfr.To_str_and_rls());
		} finally {tmp_bfr.Mkr_rls();}
	}
	private void Write_imap_div(Bry_bfr bfr, Xoh_wtr_ctx hctx, int html_uid, int html_w, int html_h, byte[] img_src, boolean orig_exists, int orig_w, int orig_h, byte[] lnki_ttl) {
		Imap_map_arg map_arg = new Imap_map_arg(id, shapes, Calc_scale(orig_w, orig_h, html_w, html_h));
		Imap_img_arg img_arg = new Imap_img_arg(hctx, xtn_mgr, this, html_uid, img_alt, img_src, html_w, html_h, Xoh_img_cls_.To_html(img_cls_tid, img_cls_other), a_href, lnki_ttl, orig_exists);
		Imap_html_fmtrs.All.Bld_bfr_many(bfr, html_uid, Calc_desc_style(desc, html_w, html_h), map_arg, img_arg);
	}
	private static byte[] Calc_desc_style(Imap_part_desc desc, int html_w, int html_h) {
		if (desc == null) return Bry_.Empty;
		Bry_bfr tmp_bfr = Bry_bfr_.Get();
		try		{return Imap_html_fmtrs.Desc_style.Bld_bry_many(tmp_bfr, html_w, html_h);}
		finally {tmp_bfr.Mkr_rls();}
	}
	private static double Calc_scale(int orig_w, int orig_h, int html_w, int html_h) {
		int denominator = orig_w + orig_h;
		int numerator   = html_w + html_h;
		if (denominator <= 0 || numerator <= 0) return 1;	// return identity; note that MW does "return self::error( 'imagemap_invalid_image' );"
		return (double)numerator / (double)denominator;
	}
}
