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
import gplx.xowa.files.*; import gplx.xowa.html.*; import gplx.xowa.files.gui.*; import gplx.xowa.gui.views.*;
public class Imap_map implements Xoh_lnki_file_wkr, Js_img_wkr {
	private static final Imap_map_fmtr map_fmtr_arg = new Imap_map_fmtr();
	public Imap_map(int id) {this.id = id;}
	public Imap_xtn_mgr Xtn_mgr() {return xtn_mgr;} private Imap_xtn_mgr xtn_mgr;
	@gplx.Internal protected void Init(Imap_xtn_mgr xtn_mgr, byte[] img_src, Imap_itm_img img, Imap_itm_dflt dflt, Imap_itm_desc desc, Imap_itm_shape[] shapes, Imap_err[] errs) {
		this.xtn_mgr = xtn_mgr; this.img_src = img_src; this.img = img; this.dflt = dflt; this.desc = desc; this.shapes = shapes; this.errs = errs;
	}
	public int Id() {return id;} private int id;
	public byte[] Img_src() {return img_src;} private byte[] img_src;
	@gplx.Internal protected Imap_itm_img Img() {return img;} private Imap_itm_img img;
	@gplx.Internal protected Imap_itm_dflt Dflt() {return dflt;} private Imap_itm_dflt dflt;
	@gplx.Internal protected Imap_itm_desc Desc() {return desc;} private Imap_itm_desc desc;
	@gplx.Internal protected Imap_itm_shape[] Shapes() {return shapes;} private Imap_itm_shape[] shapes;
	@gplx.Internal protected Imap_err[] Errs() {return errs;} private Imap_err[] errs;
	private byte[] lnki_alt_text, itm_cls, lnki_href;
	public void Write_img_full(Bry_bfr bfr, Xof_xfer_itm xfer_itm, int html_uid, byte[] lnki_href, byte[] html_src, int html_w, int html_h, byte[] lnki_alt_text, byte[] lnki_ttl, byte[] anchor_cls, byte[] anchor_rel, byte[] anchor_title, byte[] itm_cls) {
		xfer_itm.Html_img_wkr_(this);
		xfer_itm.Html_elem_tid_(Xof_html_elem.Tid_imap);
		this.lnki_alt_text = lnki_alt_text; this.itm_cls = itm_cls; this.lnki_href = lnki_href;
		Write_imap_div(bfr, html_uid, html_w, html_h, html_src, xfer_itm.Orig_w(), xfer_itm.Orig_h());
	}
	public void Html_update(Xoa_page page, Xog_html_itm html_itm, int html_uid, int html_w, int html_h, String html_src, int orig_w, int orig_h, String orig_src) {
		Xow_wiki wiki = xtn_mgr.Wiki();
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_k004();
		Write_imap_div(tmp_bfr, html_uid, html_w, html_h, Bry_.new_utf8_(html_src), orig_w, orig_h);
		html_itm.Html_elem_replace_html("imap_div_" + Int_.XtoStr(html_uid), tmp_bfr.Mkr_rls().XtoStrAndClear());
	}
	private void Write_imap_div(Bry_bfr bfr, int html_uid, int html_w, int html_h, byte[] html_src, int orig_w, int orig_h) {
		byte[] desc_style = Calc_desc_style(html_w, html_h);
		map_fmtr_arg.Init(id, shapes, Calc_scale(orig_w, orig_h, html_w, html_h));
		img_fmtr_arg.Init(this, html_uid, lnki_alt_text, html_src, html_w, html_h, itm_cls, lnki_href);
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
