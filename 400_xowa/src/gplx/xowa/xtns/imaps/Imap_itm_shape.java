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
import gplx.core.primitives.*; import gplx.core.net.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.lnkes.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.wkrs.lnkes.*;
interface Imap_link_owner {
	int Link_tid();
	Xop_tkn_itm Link_tkn();
	void Link_tid_(int v, Xop_tkn_itm tkn);
	void Link_href_(byte[] v);
	void Link_text_(byte[] v);
}
class Imap_link_owner_ {
	public static void Init(Imap_link_owner link_owner, Xoae_app app, Xowe_wiki wiki, byte[] src, Xop_tkn_itm tkn) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		try {
			int tkn_tid = tkn.Tkn_tid();
			link_owner.Link_tid_(tkn_tid, tkn);
			switch (tkn_tid) {
				case Xop_tkn_itm_.Tid_lnki: {
					Xop_lnki_tkn lnki_tkn = (Xop_lnki_tkn)tkn;
					link_owner.Link_href_(app.Html__href_wtr().Build_to_bry(wiki, lnki_tkn.Ttl()));
					wiki.Html_mgr().Html_wtr().Lnki_wtr().Write_caption(bfr, Xoh_wtr_ctx.Alt, src, lnki_tkn, lnki_tkn.Ttl());
					link_owner.Link_text_(bfr.To_bry_and_clear());
					break;
				}
				case Xop_tkn_itm_.Tid_lnke: {
					Xop_lnke_tkn lnke = (Xop_lnke_tkn)tkn;
					Xop_ctx ctx = wiki.Parser_mgr().Ctx();
					int lnke_bgn = lnke.Lnke_href_bgn(), lnke_end = lnke.Lnke_href_end(); boolean proto_is_xowa = lnke.Proto_tid() == Gfo_protocol_itm.Tid_xowa;
					Xoh_lnke_html lnke_wtr = wiki.Html_mgr().Html_wtr().Wkr__lnke();
					lnke_wtr.Write_href(bfr, Xoh_wtr_ctx.Basic, ctx, src, lnke, lnke_bgn, lnke_end, proto_is_xowa);
					link_owner.Link_href_(bfr.To_bry_and_clear());
					lnke_wtr.Write_caption(bfr, wiki.Html_mgr().Html_wtr(), Xoh_wtr_ctx.Basic, ctx, src, lnke, lnke_bgn, lnke_end, proto_is_xowa);
					link_owner.Link_text_(bfr.To_bry_and_clear());
					break;
				}
			}
		}
		finally {bfr.Mkr_rls();}	// release buffer in case of null error; PAGE:de.u:PPA/Raster/TK25/51/18/12/20; DATE:2015-02-02
	}
	public static void Write_todo(Imap_link_owner link_owner, Xoae_app app, Xowe_wiki wiki, Xoh_wtr_ctx hctx, byte[] src) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		try {
			switch (link_owner.Link_tid()) {
				case Xop_tkn_itm_.Tid_lnki: {
					Xop_lnki_tkn lnki_tkn = (Xop_lnki_tkn)link_owner.Link_tkn();
					link_owner.Link_href_(app.Html__href_wtr().Build_to_bry(wiki, lnki_tkn.Ttl()));
					wiki.Html_mgr().Html_wtr().Lnki_wtr().Write_caption(bfr, hctx, src, lnki_tkn, lnki_tkn.Ttl());
					link_owner.Link_text_(bfr.To_bry_and_clear());
					break;
				}
				case Xop_tkn_itm_.Tid_lnke: {
					Xop_lnke_tkn lnke = (Xop_lnke_tkn)link_owner.Link_tkn();
					Xop_ctx ctx = wiki.Parser_mgr().Ctx();
					int lnke_bgn = lnke.Lnke_href_bgn(), lnke_end = lnke.Lnke_href_end(); boolean proto_is_xowa = lnke.Proto_tid() == Gfo_protocol_itm.Tid_xowa;
					Xoh_lnke_html lnke_wtr = wiki.Html_mgr().Html_wtr().Wkr__lnke();
					lnke_wtr.Write_href(bfr, Xoh_wtr_ctx.Basic, ctx, src, lnke, lnke_bgn, lnke_end, proto_is_xowa);
					link_owner.Link_href_(bfr.To_bry_and_clear());
					lnke_wtr.Write_caption(bfr, wiki.Html_mgr().Html_wtr(), hctx, ctx, src, lnke, lnke_bgn, lnke_end, proto_is_xowa);
					link_owner.Link_text_(bfr.To_bry_and_clear());
					break;
				}
			}
		}
		finally {bfr.Mkr_rls();}	// release buffer in case of null error; PAGE:de.u:PPA/Raster/TK25/51/18/12/20; DATE:2015-02-02
	}
}
class Imap_itm_shape implements Imap_itm, Imap_link_owner {
	public Imap_itm_shape(byte shape_tid, Double_obj_val[] shape_pts) {
		this.shape_tid = shape_tid;
		this.shape_pts = shape_pts;
	}
	public byte Itm_tid() {return shape_tid;} private byte shape_tid;
	public Double_obj_val[] Shape_pts() {return shape_pts;} private Double_obj_val[] shape_pts;
	public int Link_tid() {return link_tid;} private int link_tid;
	public Xop_tkn_itm Link_tkn() {return link_tkn;} private Xop_tkn_itm link_tkn;
	public void Link_tid_(int tid, Xop_tkn_itm tkn) {link_tid = tid; link_tkn = tkn;} 
	public byte[] Link_href() {return link_href;} public void Link_href_(byte[] v) {this.link_href = v;} private byte[] link_href;
	public byte[] Link_text() {return link_text;} public void Link_text_(byte[] v) {this.link_text = v;} private byte[] link_text;
	public static final byte Tid_default = 0, Tid_rect = 4, Tid_circle = 3, Tid_poly = 5;
}
