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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.html.*; import gplx.xowa.net.*;
public class Xoh_lnke_wtr {
	private Xoae_app app;
	public Xoh_lnke_wtr(Xowe_wiki wiki) {this.app = wiki.Appe();}
	public void Write_all(Bry_bfr bfr, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_ctx ctx, byte[] src, Xop_lnke_tkn lnke) {
		int lnke_bgn = lnke.Lnke_bgn(), lnke_end = lnke.Lnke_end(); boolean proto_is_xowa = lnke.Proto_tid() == Xoo_protocol_itm.Tid_xowa;
		if (!hctx.Mode_is_alt()) {		// write href, unless mode is alt
			if (hctx.Mode_is_hdump()) {
				if (lnke.Lnke_typ() == Xop_lnke_tkn.Lnke_typ_text)
					bfr.Add_str_a7("<a xtid='a_lnke_txt' href=\"");
				else {
					if (lnke.Subs_len() == 0)
						bfr.Add_str_a7("<a xtid='a_lnke_brk_n' href=\"");
					else
						bfr.Add_str_a7("<a xtid='a_lnke_brk_y' href=\"");
				}
			}
			else
				bfr.Add(Xoh_consts.A_bgn);
			if (Write_href(bfr, ctx, src, lnke, lnke_bgn, lnke_end, proto_is_xowa))
				bfr.Add(A_lhs_end_external);
			else
				bfr.Add(A_lhs_end_internal);
		}
		Write_caption(bfr, html_wtr, hctx, ctx, src, lnke, lnke_bgn, lnke_end, proto_is_xowa);
		if (!hctx.Mode_is_alt()) {
			if (proto_is_xowa)	// add <img />
				bfr.Add(Xoh_consts.Img_bgn).Add(html_wtr.Html_mgr().Img_xowa_protocol()).Add(Xoh_consts.__inline_quote);
			bfr.Add(Xoh_consts.A_end);
		}
	}
	public boolean Write_href(Bry_bfr bfr, Xop_ctx ctx, byte[] src, Xop_lnke_tkn lnke, int lnke_bgn, int lnke_end, boolean proto_is_xowa) {
		byte[] lnke_xwiki_wiki = lnke.Lnke_xwiki_wiki();
		if (lnke_xwiki_wiki == null) {
			if (lnke.Lnke_relative()) {		// relative; EX: //a.org
				bfr.Add(app.Url_parser().Url_parser().Relative_url_protocol_bry()).Add_mid(src, lnke_bgn, lnke_end);
				return true;
			}
			else {							// xowa or regular; EX: http://a.org
				if (proto_is_xowa) {
					bfr.Add(Xop_lnke_wkr.Bry_xowa_protocol);
					Xoa_app_.Utl__encoder_mgr().Gfs().Encode(bfr, src, lnke_bgn, lnke_end);
					return false;
				}
				else {						// regular; add href
					bfr.Add_mid(src, lnke_bgn, lnke_end);
					return true;
				}
			}
		}
		else {	// xwiki
			Url_encoder href_encoder = Xoa_app_.Utl__encoder_mgr().Href_quotes();
			bfr.Add(Xoh_href_parser.Href_site_bry).Add(lnke_xwiki_wiki).Add(Xoh_href_parser.Href_wiki_bry)
				.Add(href_encoder.Encode(lnke.Lnke_xwiki_page()));	// NOTE: must encode page; EX:%22%3D -> '">' which will end attribute; PAGE:en.w:List_of_Category_A_listed_buildings_in_West_Lothian DATE:2014-07-15
			if (lnke.Lnke_xwiki_qargs() != null)
				Xoa_url_arg_hash.Concat_bfr(bfr, href_encoder, lnke.Lnke_xwiki_qargs()); // NOTE: must encode args
			return false;
		}
	}
	public void Write_caption(Bry_bfr bfr, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_ctx ctx, byte[] src, Xop_lnke_tkn lnke, int lnke_bgn, int lnke_end, boolean proto_is_xowa) {
		int subs_len = lnke.Subs_len();
		if (subs_len == 0) {																			// no text; auto-number; EX: "[1]"
			if (lnke.Lnke_typ() == Xop_lnke_tkn.Lnke_typ_text)
				bfr.Add_mid(src, lnke_bgn, lnke_end);
			else
				bfr.Add_byte(Byte_ascii.Brack_bgn).Add_int_variable(ctx.Cur_page().Html_data().Lnke_autonumber_next()).Add_byte(Byte_ascii.Brack_end);
		}
		else {																							// text available
			for (int i = 0; i < subs_len; i++)
				html_wtr.Write_tkn(bfr, ctx, hctx, src, lnke, i, lnke.Subs_get(i));
		}
	}
	private static final byte[]
	  A_lhs_end_external	= Bry_.new_a7("\" class=\"external text\" rel=\"nofollow\">")
	, A_lhs_end_internal	= Bry_.new_a7("\">")
	;
}
