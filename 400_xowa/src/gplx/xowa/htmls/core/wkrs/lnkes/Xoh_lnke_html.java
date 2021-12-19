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
package gplx.xowa.htmls.core.wkrs.lnkes;
import gplx.langs.html.HtmlEntityCodes;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.htmls.*;
import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.langs.htmls.encoders.*;
import gplx.langs.htmls.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkes.*;	
import gplx.xowa.htmls.core.htmls.*;
public class Xoh_lnke_html {
	private static final byte[] Disabled_button = BryUtl.NewA7("&#x2297;");
	private final Gfo_url_encoder href_encoder = Gfo_url_encoder_.New__html_href_quotes().Make();
	private final Gfo_url_encoder gfs_encoder = Gfo_url_encoder_.New__gfs().Make();
	public void Write_html(BryWtr bfr, Xow_html_mgr html_mgr, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_ctx ctx, byte[] src, Xop_lnke_tkn lnke) {
		int href_bgn = lnke.Lnke_href_bgn(), href_end = lnke.Lnke_href_end(); boolean proto_is_xowa = lnke.Proto_tid() == Gfo_protocol_itm.Tid_xowa;
		byte lnke_type = Calc_type(lnke);
		if (proto_is_xowa && hctx.Mode_is_file_dump()) {	// if protocol and file-dump, then don't write link; DATE:2016-04-12
			bfr.Add(Gfh_tag_.Div_lhs);
			Write_caption(bfr, html_wtr, hctx, ctx, src, lnke, href_bgn, href_end, proto_is_xowa);
			bfr.Add(HtmlEntityCodes.NbspNumBry).Add(Disabled_button);
			bfr.Add(Gfh_tag_.Div_rhs);
			return;
		}
		ctx.Page().Stat_itm().Lnke_count++;
		if (!hctx.Mode_is_alt()) {					// do not write "<a ...>" if mode is alt
			bfr.Add(Gfh_bldr_.Bry__a_lhs_w_href);
			if (Write_href(bfr, hctx, ctx, src, lnke, href_bgn, href_end, proto_is_xowa))
				bfr.Add(Xoh_lnke_dict_.Html__atr__0).Add(Xoh_lnke_dict_.To_html_class(lnke_type));
			bfr.Add(Xoh_lnke_dict_.Html__rhs_end);
		}
		Write_caption(bfr, html_wtr, hctx, ctx, src, lnke, href_bgn, href_end, proto_is_xowa);
		if (!hctx.Mode_is_alt()) {
			if (proto_is_xowa)	// add <img />
				bfr.Add(Xoh_consts.Img_bgn).Add(html_mgr.Img_xowa_protocol()).Add(Xoh_consts.__inline_quote);
			bfr.Add(Gfh_bldr_.Bry__a_rhs);
		}
	}
	public boolean Write_href(BryWtr bfr, Xoh_wtr_ctx hctx, Xop_ctx ctx, byte[] src, Xop_lnke_tkn lnke, int href_bgn, int href_end, boolean proto_is_xowa) {
		byte[] lnke_xwiki_wiki = lnke.Lnke_xwiki_wiki();
		if (	lnke_xwiki_wiki == null 
			||	hctx.Mode_is_hdump()		// if hdump, never write xwiki format (/site/); always write in url format (https:); note that xwiki is set when wiki is installed locally
			||	hctx.Mode_is_file_dump()
			) {
			// xowa; EX: "xowa:some_cmd"
			if (proto_is_xowa) {
				bfr.Add(Xop_lnke_wkr.Bry_xowa_protocol);
				gfs_encoder.Encode(bfr, src, href_bgn, href_end);
				return false;
			}

			// either relative (EX: "//a.org") or regular (EX: "http://a.org")
			if (lnke.Lnke_relative()) {
				bfr.Add(ctx.Wiki().Utl__url_parser().Url_parser().Relative_url_protocol_bry());
			}
			Xoh_html_wtr_escaper.Escape(ctx.App().Parser_amp_mgr(), bfr, src, href_bgn, href_end, true, false); // escape "&" ISSUE#:371; DATE:2019-03-03
			return true;
		}
		else {	// xwiki
			byte[] xwiki_page_enc = href_encoder.Encode(lnke.Lnke_xwiki_page());
			bfr.Add(Xoh_href_.Bry__site).Add(lnke_xwiki_wiki).Add(Xoh_href_.Bry__wiki)
				.Add(xwiki_page_enc);					// NOTE: must encode page; EX:%22%3D -> '">' which will end attribute; PAGE:en.w:List_of_Category_A_listed_buildings_in_West_Lothian DATE:2014-07-15
			if (lnke.Lnke_xwiki_qargs() != null)
				Gfo_qarg_mgr_old.Concat_bfr(bfr, href_encoder, lnke.Lnke_xwiki_qargs()); // NOTE: must encode args
			return ctx.Wiki().App().Xwiki_mgr__missing(lnke_xwiki_wiki);	// write "external" if hdump or xwiki is missing
		}
	}
	public void Write_caption(BryWtr bfr, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_ctx ctx, byte[] src, Xop_lnke_tkn lnke, int href_bgn, int href_end, boolean proto_is_xowa) {
		int subs_len = lnke.Subs_len();
		if (subs_len == 0) {
			if (lnke.Lnke_typ() == Xop_lnke_tkn.Lnke_typ_text)											// EX: 'http://a.org' -> 'http://a.org'
				bfr.AddMid(src, href_bgn, href_end);
			else																						// EX: '[http://a.org]' -> '[1]'
				bfr.AddByte(AsciiByte.BrackBgn).AddIntVariable(ctx.Page().Html_data().Lnke_autonumber_next()).AddByte(AsciiByte.BrackEnd);
		}
		else {																							// EX: '[http://a.org a]' -> 'a'
			for (int i = 0; i < subs_len; i++)
				html_wtr.Write_tkn_to_html(bfr, ctx, hctx, src, lnke, i, lnke.Subs_get(i));
		}
	}
	private static byte Calc_type(Xop_lnke_tkn lnke) {
		if (lnke.Lnke_typ() == Xop_lnke_tkn.Lnke_typ_text)
			return Xoh_lnke_dict_.Type__free;
		else
			return lnke.Subs_len() == 0  ? Xoh_lnke_dict_.Type__auto : Xoh_lnke_dict_.Type__text;			
	}
}
