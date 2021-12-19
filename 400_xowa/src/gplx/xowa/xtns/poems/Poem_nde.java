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
package gplx.xowa.xtns.poems;
import gplx.langs.html.HtmlEntityCodes;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryBfrMkr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.htmls.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*;
public class Poem_nde implements Xox_xnde {
	private Xop_root_tkn xtn_root;
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {// REF.MW: Poem.php|wfRenderPoemTag
		int itm_bgn = xnde.Tag_open_end(), itm_end = xnde.Tag_close_bgn();
		if (itm_bgn == src.length)	return;  // NOTE: handle inline where there is no content to parse; EX: <poem/>
		if (itm_bgn >= itm_end)		return;  // NOTE: handle inline where there is no content to parse; EX: a<poem/>b
		if (src[itm_bgn] 		== AsciiByte.Nl)	++itm_bgn;	// ignore 1st \n;
		if (src[itm_end - 1] 	== AsciiByte.Nl				// ignore last \n;
			&& itm_end != itm_bgn)						--itm_end;	// ...if not same as 1st \n; EX: <poem>\n</poem>
		Poem_xtn_mgr xtn_mgr = (Poem_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Poem_xtn_mgr.XTN_KEY);
		byte[] poem_bry = Parse_lines(wiki.Utl__bfr_mkr(), src, itm_bgn, itm_end);
		// xtn_root = xtn_mgr.Parser().Parse_text_to_wdom_old_ctx(ctx, poem_bry, true); // NOTE: ignoring paragraph mode; technically MW enables para mode, but by replacing "\n" with "<br/>\n" all the logic with para/pre mode is skipped
		xtn_root = xtn_mgr.Parser().Parse_text_to_wdom(Xop_ctx.New__sub_and_page(wiki, ctx), poem_bry, true); // NOTE: ignoring paragraph mode; technically MW enables para mode, but by replacing "\n" with "<br/>\n" all the logic with para/pre mode is skipped
	}
	public void Xtn_write(BryWtr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (xtn_root == null) return;	// inline poem; write nothing; EX: <poem/>
		bfr.Add(Div_poem_bgn);
		html_wtr.Write_tkn_to_html(bfr, ctx, hctx, xtn_root.Root_src(), xnde, Xoh_html_wtr.Sub_idx_null, xtn_root);
		bfr.Add(Div_poem_end);			
	}
	private static byte[] Parse_lines(BryBfrMkr bfr_mkr, byte[] src, int src_bgn, int src_end) {
		BryWtr bfr = bfr_mkr.GetK004();
		try {
			int line_bgn = src_bgn; boolean line_is_1st = true;
			while (line_bgn < src_end) {																			// iterate over each \n
				boolean indent_enabled = false;
				if (line_is_1st)	line_is_1st = false;
				else {
					int line_end_w_br = line_bgn + Xowa_br_mark.length;
					if (	line_end_w_br < src_end																	// check for out of bounds; PAGE:en.s:The Hebrew Nation did not write it; DATE:2015-01-31
						&&	BryLni.Eq(src, line_bgn, line_end_w_br, Xowa_br_mark))									// "<br/>\n" already inserted by XOWA; do not insert again; DATE:2014-10-20
						bfr.AddByteNl();
					else
						bfr.Add(Gfh_tag_.Br_inl).AddByteNl().Add(Xowa_br_mark);									// add "<br/>\n" unless 1st line; EX: "<poem>\n\s" should not add leading "<br/>\n"
				}
				switch (src[line_bgn]) {
					case AsciiByte.Space:																			// "\n\s" -> "\n&#160;"
						int space_end = BryFind.FindFwdWhile(src, line_bgn, src_end, AsciiByte.Space);
						int space_count = space_end - line_bgn;
						line_bgn = space_end;
						for (int i = 0; i < space_count; ++i)
							bfr.Add(HtmlEntityCodes.NbspNumBry);
						break;
					case AsciiByte.Colon: {																		// "\n:" -> <span class='mw-poem-indented' style='display: inline-block; margin-left: #em;'>
						int colon_end = BryFind.FindFwdWhile(src, line_bgn, src_end, AsciiByte.Colon);
						int colon_count = colon_end - line_bgn;
						line_bgn = colon_end;
						bfr.Add(Indent_bgn).AddIntVariable(colon_count).Add(Indent_end);							// add <span class='mw-poem-indented' style='display: inline-block; margin-left: #em;'>
						indent_enabled = true;
						break;
					}
				}
				int line_end = BryFind.FindFwd(src, AsciiByte.Nl, line_bgn, src_end);						// find end "\n"
				if (line_end == BryFind.NotFound) line_end = src_end;											// no "\n"; use eos;
				bfr.AddMid(src, line_bgn, line_end);																// add everything from line_bgn to line_end
				if (indent_enabled) bfr.Add(Gfh_tag_.Span_rhs);													// if "\n:", add </span>
				line_bgn = line_end + 1;																			// +1 to skip over end "\n"
			}
			return bfr.ToBryAndClear();
		} finally {bfr.MkrRls();}
	}
	private static byte[]
	  Div_poem_bgn = BryUtl.NewA7("<div class=\"poem\">\n<p>\n")	// NOTE: always enclose in <p>; MW does this implicitly in its modified parse; DATE:2014-04-27
	, Div_poem_end = BryUtl.NewA7("\n</p>\n</div>")
	, Indent_bgn = BryUtl.NewA7("\n<span class='mw-poem-indented' style='display: inline-block; margin-left: ")
	, Indent_end = BryUtl.NewA7("em;'>")
	, Xowa_br_mark = BryUtl.NewA7("<!--xowa.br-->")
	;
}
