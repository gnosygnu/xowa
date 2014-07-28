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
import gplx.xowa.files.*; import gplx.xowa.html.*;
class Imap_map implements Bry_fmtr_arg, Xoh_lnki_file_wkr {
	public Imap_xtn_mgr Xtn_mgr() {return xtn_mgr;} private Imap_xtn_mgr xtn_mgr;
	public void Init(Imap_xtn_mgr xtn_mgr, int id, byte[] img_src, Imap_itm_img img, Imap_itm_dflt dflt, Imap_itm_desc desc, Imap_itm_shape[] shapes, Imap_err[] errs) {
		this.xtn_mgr = xtn_mgr;
		this.id = id; this.img_src = img_src; this.img = img; this.dflt = dflt; this.desc = desc; this.shapes = shapes; this.errs = errs;
	}
	public int Id() {return id;} private int id;
	public byte[] Img_src() {return img_src;} private byte[] img_src;
	public Imap_itm_img Img() {return img;} private Imap_itm_img img;
	public Imap_itm_dflt Dflt() {return dflt;} private Imap_itm_dflt dflt;
	public Imap_itm_desc Desc() {return desc;} private Imap_itm_desc desc;
	public Imap_itm_shape[] Shapes() {return shapes;} private Imap_itm_shape[] shapes;
	public Imap_err[] Errs() {return errs;} private Imap_err[] errs;
	public void Write_img_full(Bry_bfr bfr, Xof_xfer_itm xfer_itm, int elem_id, byte[] lnki_href, byte[] html_view_src, int html_w, int html_h, byte[] lnki_alt_text, byte[] lnki_ttl, byte[] anchor_cls, byte[] anchor_rel, byte[] anchor_title, byte[] itm_cls) {
		pts_fmtr_arg.Scale_(Calc_scale(xfer_itm.Orig_w(), xfer_itm.Orig_h(), html_w, html_h));
		img_fmtr_arg.Init(this, elem_id, lnki_alt_text, html_view_src, html_w, html_h, itm_cls, lnki_href);
		byte[] desc_style = Calc_desc_style(html_w, html_h);
		Imap_html_fmtrs.Map.Bld_bfr_many(bfr, id, desc_style, img_fmtr_arg, this);
	}
	private byte[] Calc_desc_style(int html_w, int html_h) {
		if (desc == null) return Bry_.Empty;
		Bry_bfr tmp_bfr = xtn_mgr.Wiki().Utl_bry_bfr_mkr().Get_b128().Mkr_rls();
		return Imap_html_fmtrs.Desc_style.Bld_bry_many(tmp_bfr, html_w, html_h);
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		int shapes_len = shapes.length;
		Bry_fmtr fmtr = Imap_html_fmtrs.Area;
		for (int i = 0; i < shapes_len; ++i) {
			Imap_itm_shape shape = shapes[i];
			Fmt_shape(bfr, fmtr, pts_fmtr_arg, shape);
		}
	}
	public static void Fmt_shape(Bry_bfr bfr, Bry_fmtr fmtr, Imap_pts_fmtr_arg pts_fmtr, Imap_itm_shape shape) {
		pts_fmtr_arg.Pts_(shape.Shape_pts());
		fmtr.Bld_bfr_many(bfr
		, shape.Link_href()
		, Imap_itm_.Xto_key(shape.Itm_tid())
		, pts_fmtr_arg
		, shape.Link_text()
		);
	}
	private static final Imap_pts_fmtr_arg pts_fmtr_arg = new Imap_pts_fmtr_arg();
	private static final Imap_img_fmtr_arg img_fmtr_arg = new Imap_img_fmtr_arg();
	private static double Calc_scale(int orig_w, int orig_h, int html_w, int html_h) {
		int denominator = orig_w + orig_h;
		int numerator   = html_w + html_h;
		if (denominator <= 0 || numerator <= 0) return 1;	// return identity; note that MW does "return self::error( 'imagemap_invalid_image' );"
		return (double)numerator / (double)denominator;
	}
}
