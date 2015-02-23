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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.files.*; import gplx.xowa.html.*; import gplx.xowa.files.gui.*; import gplx.xowa.gui.views.*; import gplx.xowa.html.lnkis.*;
public class Imap_map implements Xoh_file_img_wkr, Js_img_wkr {
	private static final Imap_map_fmtr map_fmtr_arg = new Imap_map_fmtr();
	public Imap_map(int id) {this.id = id;}
	public Imap_xtn_mgr Xtn_mgr() {return xtn_mgr;} private Imap_xtn_mgr xtn_mgr;
	@gplx.Internal protected void Init(Imap_xtn_mgr xtn_mgr, byte[] img_src, Imap_itm_img img, Imap_itm_dflt dflt, Imap_itm_desc desc, Imap_itm_shape[] shapes, Imap_err[] errs) {
		this.xtn_mgr = xtn_mgr; this.img_src = img_src; this.img = img; this.dflt = dflt; this.desc = desc; this.shapes = shapes; this.errs = errs;
	}
	public boolean Invalid() {return img == null;}	// invalid if missing image; PAGE:en.w:Wikipedia:WikiProject_Games/Advert EX: <imagemap>|thumb;</imagemap>; DATE:2014-08-12
	public int Id() {return id;} private int id;
	public byte[] Img_src() {return img_src;} private byte[] img_src;
	@gplx.Internal protected Imap_itm_img Img() {return img;} private Imap_itm_img img;
	@gplx.Internal protected Imap_itm_dflt Dflt() {return dflt;} private Imap_itm_dflt dflt;
	@gplx.Internal protected Imap_itm_desc Desc() {return desc;} private Imap_itm_desc desc;
	@gplx.Internal protected Imap_itm_shape[] Shapes() {return shapes;} private Imap_itm_shape[] shapes;
	@gplx.Internal protected Imap_err[] Errs() {return errs;} private Imap_err[] errs;
	private byte img_cls_tid;
	private byte[] a_href, img_alt, img_cls_other;
	public void Html_full_img(Bry_bfr tmp_bfr, Xoh_wtr_ctx hctx, Xoae_page page, Xof_xfer_itm xfer_itm, int uid
		, byte[] a_href, byte a_cls, byte a_rel, byte[] a_title, byte[] a_xowa_title
		, int img_w, int img_h, byte[] img_src, byte[] img_alt, byte img_cls, byte[] img_cls_other
		) {
		xfer_itm.Html_img_wkr_(this);
		xfer_itm.Html_elem_tid_(Xof_html_elem.Tid_imap);
		this.a_href = a_href; this.img_alt = img_alt; this.img_cls_tid = img_cls; this.img_cls_other = img_cls_other;
		Write_imap_div(tmp_bfr, page, hctx, uid, img_w, img_h, img_src, xfer_itm.Orig_w(), xfer_itm.Orig_h(), xfer_itm.Lnki_ttl());
	}
	public void Html_update(Xoae_page page, Xog_html_itm html_itm, int html_uid, int html_w, int html_h, String html_src, int orig_w, int orig_h, String orig_src, byte[] lnki_ttl) {
		Xowe_wiki wiki = xtn_mgr.Wiki();
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_k004();
		Write_imap_div(tmp_bfr, page, Xoh_wtr_ctx.Basic, html_uid, html_w, html_h, Bry_.new_utf8_(html_src), orig_w, orig_h, lnki_ttl);
		html_itm.Html_elem_replace_html("imap_div_" + Int_.Xto_str(html_uid), tmp_bfr.Mkr_rls().Xto_str_and_clear());
	}
	private void Write_imap_div(Bry_bfr bfr, Xoae_page page, Xoh_wtr_ctx hctx, int html_uid, int html_w, int html_h, byte[] html_src, int orig_w, int orig_h, byte[] lnki_ttl) {
		byte[] desc_style = Calc_desc_style(html_w, html_h);
		map_fmtr_arg.Init(id, shapes, Calc_scale(orig_w, orig_h, html_w, html_h));
		img_fmtr_arg.Init(page, hctx, xtn_mgr, this, html_uid, img_alt, html_src, html_w, html_h, Xoh_lnki_consts.Img_cls_to_bry(img_cls_tid, img_cls_other), a_href, lnki_ttl);
		Imap_html_fmtrs.All.Bld_bfr_many(bfr, html_uid, desc_style, map_fmtr_arg, img_fmtr_arg);
	}
	private byte[] Calc_desc_style(int html_w, int html_h) {
		if (desc == null) return Bry_.Empty;
		Bry_bfr tmp_bfr = xtn_mgr.Wiki().Utl_bry_bfr_mkr().Get_b128().Mkr_rls();
		return Imap_html_fmtrs.Desc_style.Bld_bry_many(tmp_bfr, html_w, html_h);
	}
	private static final Imap_img_fmtr_arg img_fmtr_arg = new Imap_img_fmtr_arg();
	private static double Calc_scale(int orig_w, int orig_h, int html_w, int html_h) {
		int denominator = orig_w + orig_h;
		int numerator   = html_w + html_h;
		if (denominator <= 0 || numerator <= 0) return 1;	// return identity; note that MW does "return self::error( 'imagemap_invalid_image' );"
		return (double)numerator / (double)denominator;
	}
}
