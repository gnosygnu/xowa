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
import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.parsers.*;
class Imap_img_fmtr_arg extends gplx.core.brys.Bfr_arg_base {
	private Xoh_wtr_ctx hctx; private Imap_map map; private Imap_xtn_mgr xtn_mgr; // private byte[] src;
	private int img_elem_id, img_w, img_h;
	private byte[] img_alt, img_src, img_cls, img_href;
	private Int_2_ref margin_calc = new Int_2_ref();
	public void Init(Xoh_wtr_ctx hctx, Imap_xtn_mgr xtn_mgr, Imap_map map, byte[] src, int img_elem_id, byte[] img_alt, byte[] img_src, int img_w, int img_h, byte[] img_cls, byte[] img_href) {
		this.hctx = hctx; this.map = map; this.xtn_mgr = xtn_mgr; // this.src = src;
		this.img_elem_id = img_elem_id; this.img_w = img_w; this.img_h = img_h;
		this.img_alt = img_alt;
		this.img_src = img_src;
		this.img_cls = img_cls;
		this.img_href = img_href;
	}
	@Override public void Bfr_arg__add(Bry_bfr bfr) {
		Bry_fmtr fmtr = Imap_html_fmtrs.Img_anchor_none;
		byte[] anchor_href = Bry_.Empty, anchor_text = Bry_.Empty;
		Imap_itm_dflt itm_dflt = map.Dflt();
		boolean hctx_is_hdump = hctx.Mode_is_hdump();
		Xoh_arg_img_core img_core_fmtr = xtn_mgr.Img_core_fmtr(hctx_is_hdump);
		img_core_fmtr.Init(img_elem_id, img_src, img_w, img_h);			
		if (itm_dflt != null) {
//				Xowe_wiki wiki = map.Xtn_mgr().Wiki();
//				if (src.length != 0)	// imap update will pass 0 src
//					Imap_link_owner_.Write(itm_dflt, wiki.Appe(), wiki, hctx, src);
			fmtr = itm_dflt.Link_tid() == Xop_tkn_itm_.Tid_lnki ? Imap_html_fmtrs.Img_anchor_lnki : Imap_html_fmtrs.Img_anchor_lnke;
			anchor_href = itm_dflt.Link_href();
			anchor_text = itm_dflt.Link_text();
		}
		fmtr.Bld_bfr_many(bfr, map.Id(), img_elem_id, img_alt, img_core_fmtr, img_cls, anchor_href, anchor_text);
		Imap_itm_desc itm_desc = map.Desc();
		if (itm_desc != null) {
			Imap_desc_tid.Calc_desc_margins(margin_calc, itm_desc.Desc_tid(), img_w, img_h);
			Imap_html_fmtrs.Desc_main.Bld_bfr_many(bfr, margin_calc.Val_0(), margin_calc.Val_1(), img_href, xtn_mgr.Desc_msg(), xtn_mgr.Desc_icon_url());
		}
	}
}
