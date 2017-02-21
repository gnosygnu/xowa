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
package gplx.xowa.xtns.imaps.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.imaps.*;
import gplx.core.primitives.*; import gplx.core.net.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.lnkes.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.wkrs.lnkes.*;
public class Imap_link_owner_ {
	public static void Init(Imap_link_owner link_owner, Xoae_app app, Xowe_wiki wiki, byte[] src, Xop_tkn_itm tkn) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		try {
			int tkn_tid = tkn.Tkn_tid();
			link_owner.Link_tid_(tkn_tid, tkn);
			switch (tkn_tid) {
				case Xop_tkn_itm_.Tid_lnki: {
					Xop_lnki_tkn lnki_tkn = (Xop_lnki_tkn)tkn;
					link_owner.Link_href_(wiki.Html__href_wtr().Build_to_bry(wiki, lnki_tkn.Ttl()));
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
					link_owner.Link_href_(wiki.Html__href_wtr().Build_to_bry(wiki, lnki_tkn.Ttl()));
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
