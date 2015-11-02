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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.btries.*; import gplx.core.net.*; import gplx.langs.htmls.encoders.*; import gplx.xowa.apps.urls.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkes.*;	
import gplx.xowa.htmls.core.htmls.*;
public class Xoh_lnke_html {
	public void Write_html(Bry_bfr bfr, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_ctx ctx, byte[] src, Xop_lnke_tkn lnke) {
		int href_bgn = lnke.Lnke_href_bgn(), href_end = lnke.Lnke_href_end(); boolean proto_is_xowa = lnke.Proto_tid() == Gfo_protocol_itm.Tid_xowa;
		byte lnke_type = Calc_type(lnke);
		if (!hctx.Mode_is_alt()) {					// do not write "<a ...>" if mode is alt
			if (hctx.Mode_is_hdump()) {
				bfr.Add(Xoh_html_dict_.Hook__lnke);	// "<a data-xotype='lnke"
				bfr.Add_byte(lnke_type).Add(Bry__href);
			}
			else
				bfr.Add(Xoh_consts.A_bgn);
			if (Write_href(bfr, ctx, src, lnke, href_bgn, href_end, proto_is_xowa))
				bfr.Add(Xoh_lnke_dict_.Html__atr__0).Add(Xoh_lnke_dict_.To_html_class(lnke_type));
			bfr.Add(Xoh_lnke_dict_.Html__rhs_end);
		}
		Write_caption(bfr, html_wtr, hctx, ctx, src, lnke, href_bgn, href_end, proto_is_xowa);
		if (!hctx.Mode_is_alt()) {
			if (proto_is_xowa)	// add <img />
				bfr.Add(Xoh_consts.Img_bgn).Add(html_wtr.Html_mgr().Img_xowa_protocol()).Add(Xoh_consts.__inline_quote);
			bfr.Add(Xoh_consts.A_end);
		}
	}
	public boolean Write_href(Bry_bfr bfr, Xop_ctx ctx, byte[] src, Xop_lnke_tkn lnke, int href_bgn, int href_end, boolean proto_is_xowa) {
		byte[] lnke_xwiki_wiki = lnke.Lnke_xwiki_wiki();
		if (lnke_xwiki_wiki == null) {
			if (lnke.Lnke_relative()) {		// relative; EX: //a.org
				bfr.Add(ctx.Wiki().Utl__url_parser().Url_parser().Relative_url_protocol_bry()).Add_mid(src, href_bgn, href_end);
				return true;
			}
			else {							// xowa or regular; EX: http://a.org
				if (proto_is_xowa) {
					bfr.Add(Xop_lnke_wkr.Bry_xowa_protocol);
					Xoa_app_.Utl__encoder_mgr().Gfs().Encode(bfr, src, href_bgn, href_end);
					return false;
				}
				else {						// regular; add href
					bfr.Add_mid(src, href_bgn, href_end);
					return true;
				}
			}
		}
		else {	// xwiki
			Url_encoder href_encoder = Xoa_app_.Utl__encoder_mgr().Href_quotes();
			bfr.Add(Xoh_href_.Bry__site).Add(lnke_xwiki_wiki).Add(Xoh_href_.Bry__wiki)
				.Add(href_encoder.Encode(lnke.Lnke_xwiki_page()));	// NOTE: must encode page; EX:%22%3D -> '">' which will end attribute; PAGE:en.w:List_of_Category_A_listed_buildings_in_West_Lothian DATE:2014-07-15
			if (lnke.Lnke_xwiki_qargs() != null)
				Gfo_qarg_mgr.Concat_bfr(bfr, href_encoder, lnke.Lnke_xwiki_qargs()); // NOTE: must encode args
			return false;
		}
	}
	public void Write_caption(Bry_bfr bfr, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_ctx ctx, byte[] src, Xop_lnke_tkn lnke, int href_bgn, int href_end, boolean proto_is_xowa) {
		int subs_len = lnke.Subs_len();
		if (subs_len == 0) {
			if (lnke.Lnke_typ() == Xop_lnke_tkn.Lnke_typ_text)											// EX: 'http://a.org' -> 'http://a.org'
				bfr.Add_mid(src, href_bgn, href_end);
			else																						// EX: '[http://a.org]' -> '[1]'
				bfr.Add_byte(Byte_ascii.Brack_bgn).Add_int_variable(ctx.Cur_page().Html_data().Lnke_autonumber_next()).Add_byte(Byte_ascii.Brack_end);
		}
		else {																							// EX: '[http://a.org a]' -> 'a'
			for (int i = 0; i < subs_len; i++)
				html_wtr.Write_tkn(bfr, ctx, hctx, src, lnke, i, lnke.Subs_get(i));
		}
	}
	private static final byte[]
	  Bry__href				= Bry_.new_a7("' href=\"")
	;
	private static byte Calc_type(Xop_lnke_tkn lnke) {
		if (lnke.Lnke_typ() == Xop_lnke_tkn.Lnke_typ_text)
			return Xoh_lnke_dict_.Type__free;
		else
			return lnke.Subs_len() == 0  ? Xoh_lnke_dict_.Type__auto : Xoh_lnke_dict_.Type__text;			
	}
}
