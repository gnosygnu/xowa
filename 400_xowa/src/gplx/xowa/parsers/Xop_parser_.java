/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers;

import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;

public class Xop_parser_ {
	public static final int Doc_bgn_bos = -1, Doc_bgn_char_0 = 0;
	public static byte[] Parse_text_to_html(Xowe_wiki wiki, Xop_ctx owner_ctx, Xoae_page page, byte[] src, boolean para_enabled) {
		return Parse_text_to_html(wiki, owner_ctx, Xoh_wtr_ctx.Basic, page, src, para_enabled);
	}
	public static byte[] Parse_text_to_html(Xowe_wiki wiki, Xop_ctx owner_ctx, Xoh_wtr_ctx hctx, Xoae_page page, byte[] src, boolean para_enabled) {	// NOTE: must pass in same page instance; do not do Xoa_page_.new_(), else img_idx will get reset to 0; DATE:2015-02-08
		// init
		Xop_ctx ctx = Xop_ctx.New__sub(wiki, owner_ctx, page);
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(src);
		Xop_parser parser = wiki.Parser_mgr().Main();

		// expand template; EX: {{A}} -> wikitext
		byte[] wtxt = parser.Expand_tmpl(root, ctx, tkn_mkr, src);

		// parse wikitext
		root.Reset();
		ctx.Para().Enabled_(para_enabled);
		parser.Parse_wtxt_to_wdom(root, ctx, ctx.Tkn_mkr(), wtxt, Xop_parser_.Doc_bgn_bos);

		// write html
		BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
		wiki.Html_mgr().Html_wtr().Write_doc(bfr, ctx, hctx, wtxt, root);
		return bfr.ToBryAndRls();
	}
}
