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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.langs.vnts.*;
public class Xop_parser_ {
	public static final int Doc_bgn_bos = -1, Doc_bgn_char_0 = 0;
	public static byte[] Parse_text_to_html(Xowe_wiki wiki, Xop_ctx owner_ctx, Xoae_page page, Xoa_ttl ttl, byte[] src, boolean para_enabled) {	// NOTE: must pass in same page instance; do not do Xoa_page_.new_(), else img_idx will get reset to 0; DATE:2015-02-08
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
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		wiki.Html_mgr().Html_wtr().Write_doc(bfr, ctx, Xoh_wtr_ctx.Basic, wtxt, root);
		return bfr.To_bry_and_rls();
	}
}
