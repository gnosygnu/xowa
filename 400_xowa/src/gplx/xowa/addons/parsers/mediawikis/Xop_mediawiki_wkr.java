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
package gplx.xowa.addons.parsers.mediawikis;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.String_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.Xoctg_pagebox_itm;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xow_parser_mgr;
import gplx.xowa.wikis.Xow_page_tid;
import gplx.xowa.wikis.pages.Xopg_view_mode_;

public class Xop_mediawiki_wkr {
	private final Xowe_wiki wiki;
	private final Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xop_mediawiki_wkr(Xowe_wiki wiki, Xop_mediawiki_loader loader) {
		this.wiki = wiki;
		if (loader != null)
			wiki.Cache_mgr().Load_wkr_(new Xow_page_cache_wkr__embeddable(wiki, loader));
	}
	public void Free_memory() {
		wiki.Cache_mgr().Free_mem__page();
		wiki.Parser_mgr().Scrib().Core_term();
		wiki.Appe().Wiki_mgr().Wdata_mgr().Clear();
	}
	public String Parse(String page, String wikitext) {
		Xoa_ttl ttl = wiki.Ttl_parse(Bry_.new_u8(page));

		byte[] wtxt = Bry_.new_u8(wikitext);
		Xoae_page wpg = Xoae_page.New(wiki, ttl);
		wpg.Db().Text().Text_bry_(wtxt);

		Xow_parser_mgr parser_mgr = wiki.Parser_mgr();

		// parse page
		Xop_ctx pctx = parser_mgr.Ctx();
		pctx.Clear_all();
		parser_mgr.Parse(wpg, true);

		// write to html
		boolean is_wikitext = Xow_page_tid.Identify(wpg.Wiki().Domain_tid(), ttl.Ns().Id(), ttl.Page_db()) == Xow_page_tid.Tid_wikitext;
		byte[] orig_bry = Bry_.Empty;
		if (is_wikitext) {
			wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_view_mode_.Tid__read).Write_hdump(tmp_bfr, pctx, Xoh_wtr_ctx.Embeddable, wpg);

			// write categories
			int ctgs_len = wpg.Wtxt().Ctgs__len();
			if (	ctgs_len > 0						// skip if no categories found while parsing wikitext
				) {
				Xoctg_pagebox_itm[] pagebox_itms = new Xoctg_pagebox_itm[ctgs_len];
				for (int i = 0; i < ctgs_len; i++) {
					pagebox_itms[i] = new Xoctg_pagebox_itm(wpg.Wtxt().Ctgs__get_at(i));
				}
				wiki.Ctg__pagebox_wtr().Write_pagebox(tmp_bfr, wpg, pagebox_itms);
			}

			orig_bry = tmp_bfr.To_bry_and_clear();
			wpg.Db().Html().Html_bry_(orig_bry);
		}
		else {	// not wikitext; EX: pages in MediaWiki: ns; DATE:2016-09-12
			wpg.Db().Html().Html_bry_(wpg.Db().Text().Text_bry());
		}

		return String_.new_u8(orig_bry);
	}
}
