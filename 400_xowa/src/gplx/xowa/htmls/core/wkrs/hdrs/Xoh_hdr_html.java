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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
import gplx.xowa.htmls.core.htmls.*;
public class Xoh_hdr_html {
	public void Write_html(Bry_bfr bfr, Xoh_html_wtr wtr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoh_html_wtr_cfg cfg, Xop_tkn_grp grp, int sub_idx, byte[] src, Xop_hdr_tkn hdr) {
		if (hdr.Hdr_html_first() && cfg.Toc__show() && !page.Hdr_mgr().Toc_manual())	// __TOC__ not specified; place at top; NOTE: if __TOC__ was specified, then it would be placed wherever __TOC__ appears
			wiki.Html_mgr().Toc_mgr().Html(ctx.Cur_page(), hctx, src, bfr);

		int hdr_len = hdr.Hdr_level();
		if (hdr_len > 0) {													// NOTE: need to check hdr_len b/c it could be dangling
			Xoh_html_wtr_.Para__assert_tag_starts_on_nl(bfr, hdr.Src_bgn()); 
			bfr.Add(Bry__hdr_lhs_bgn).Add_int(hdr_len, 1, 1);				// '<h', '2'
			bfr.Add_byte(Byte_ascii.Angle_end);								// '>'
			if (cfg.Toc__show()) {
				bfr.Add(Bry__span_lhs_bgn);									// "<span class='mw-headline' id='"
				bfr.Add(hdr.Hdr_html_id());									// '1'
				bfr.Add(Bry__span_lhs_end);									// "'>"
			}
		}	
		if (hdr.Hdr_bgn_manual() > 0) bfr.Add_byte_repeat(Byte_ascii.Eq, hdr.Hdr_bgn_manual());	// '='
		int subs_len = hdr.Subs_len();										// write header; note that this can have embedded html; EX: ==<b>A</b>==
		for (int i = 0; i < subs_len; ++i)
			wtr.Write_tkn(bfr, ctx, hctx, src, hdr, i, hdr.Subs_get(i));
		if (hdr_len > 0) {													// NOTE: need to check hdr_len b/c it could be dangling
			if (hdr.Hdr_end_manual() > 0) bfr.Add_byte_repeat(Byte_ascii.Eq, hdr.Hdr_end_manual());	// '='
			if (cfg.Toc__show())
				bfr.Add(Gfh_tag_.Span_rhs);									// '</span>'
			bfr.Add(Bry__hdr_rhs_bgn).Add_int(hdr_len, 1, 1);				// '</h', '2'
			bfr.Add(Bry__hdr_rhs_end);										// '>\n'
		}
	}
	private static final byte[] Bry__hdr_lhs_bgn = Bry_.new_a7("<h"), Bry__hdr_rhs_bgn = Bry_.new_a7("</h"), Bry__hdr_rhs_end = Bry_.new_a7(">\n")
	, Bry__span_lhs_bgn = Bry_.new_a7("<span class=\"mw-headline\" id=\""), Bry__span_lhs_end = Bry_.new_a7("\">")
	;
}
