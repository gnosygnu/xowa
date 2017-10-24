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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.addons.htmls.tocs.*;
public class Xoh_hdr_html {
	private final    Bry_bfr hdr_text_bfr = Bry_bfr_.New();
	private final    Xoh_toc_itm invalid_toc_itm = new Xoh_toc_itm().Set__txt(Bry_.Empty, Bry_.Empty);

	public void Write_html(Bry_bfr bfr, Xoh_html_wtr wtr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoh_html_wtr_cfg cfg, Xop_tkn_grp grp, int sub_idx, byte[] src, Xop_hdr_tkn hdr) {
		// init
		int hdr_num = hdr.Num();
		boolean hdr_is_valid = hdr_num > 0;	// hdr_num == 0 when dangling

		// register hdr with TOC
		byte[] hdr_text_bry = Bld_hdr_html(hdr_text_bfr, wtr, page, ctx, hctx, src, hdr);
		hdr_text_bry = wiki.Parser_mgr().Uniq_mgr().Convert(hdr_text_bry);	// need for math; DATE:2016-12-09
		Xoh_toc_itm toc_itm = hdr_is_valid && hdr_text_bry.length > 0
			? page.Html_data().Toc_mgr().Add(hdr_num, hdr_text_bry)
			: invalid_toc_itm;

		// write TOC tag if (a) TOC enabled and (b) 1st hdr
		if (	hdr.First_in_doc() 
			&&	cfg.Toc__show() 
			&&	page.Wtxt().Toc().Enabled()
			&&	!page.Wtxt().Toc().Flag__toc())	// __TOC__ not specified; place at top; NOTE: if __TOC__ was specified, then it would be placed wherever __TOC__ appears
			gplx.xowa.htmls.core.wkrs.tocs.Xoh_toc_wtr.Write_placeholder(page, bfr);

		// write <h#><span>
		if (hdr_is_valid) {													// NOTE: need to check hdr_num b/c it could be dangling
			Xoh_html_wtr_.Para__assert_tag_starts_on_nl(bfr, hdr.Src_bgn()); 
			bfr.Add(Bry__hdr_lhs_bgn).Add_int(hdr_num, 1, 1);				// '<h', '2'
			bfr.Add_byte(Byte_ascii.Angle_end);								// '>'
			if (cfg.Toc__show()) {
				bfr.Add(Bry__span_lhs_bgn);									// "<span class='mw-headline' id='"
				bfr.Add(toc_itm.Anch());									// 'Name_and_Etymology'
				bfr.Add(Bry__span_lhs_end);									// "'>"
			}
		}	
		if (hdr.Manual_bgn() > 0) bfr.Add_byte_repeat(Byte_ascii.Eq, hdr.Manual_bgn());	// '='

		// write text
		bfr.Add(hdr_text_bry);
		
		// write </span></h#>
		if (hdr_is_valid) {													// NOTE: need to check hdr_num b/c it could be dangling
			if (hdr.Manual_end() > 0) bfr.Add_byte_repeat(Byte_ascii.Eq, hdr.Manual_end());	// '='
			if (cfg.Toc__show())
				bfr.Add(Gfh_tag_.Span_rhs);									// '</span>'

			// write section editable
			if (wiki.Parser_mgr().Hdr__section_editable__mgr().Enabled()) {
				byte[] section_key = toc_itm.Anch();
				wiki.Parser_mgr().Hdr__section_editable__mgr().Write_html(bfr, page.Ttl().Full_db(), section_key, section_key);
			}

			bfr.Add(Bry__hdr_rhs_bgn).Add_int(hdr_num, 1, 1);				// '</h', '2'
			bfr.Add(Bry__hdr_rhs_end);										// '>\n'
		}
	}

	private static final    byte[] Bry__hdr_lhs_bgn = Bry_.new_a7("<h"), Bry__hdr_rhs_bgn = Bry_.new_a7("</h"), Bry__hdr_rhs_end = Bry_.new_a7(">\n")
	, Bry__span_lhs_bgn = Bry_.new_a7("<span class=\"mw-headline\" id=\""), Bry__span_lhs_end = Bry_.new_a7("\">")
	;
	public static byte[] Bld_hdr_html(Bry_bfr rv, Xoh_html_wtr wtr, Xoa_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_hdr_tkn hdr) {
		page.Html_data().Writing_hdr_for_toc_y_();
		int subs_len = hdr.Subs_len();										// write header; note that this can have embedded html; EX: ==<b>A</b>==
		for (int i = 0; i < subs_len; ++i)
			wtr.Write_tkn_to_html(rv, ctx, hctx, src, hdr, i, hdr.Subs_get(i));
		page.Html_data().Writing_hdr_for_toc_n_();
		return rv.To_bry_and_clear();
	}
}
