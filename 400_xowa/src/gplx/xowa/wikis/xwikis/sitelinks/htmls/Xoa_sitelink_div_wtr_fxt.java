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
package gplx.xowa.wikis.xwikis.sitelinks.htmls;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import gplx.xowa.parsers.*;
class Xoa_sitelink_div_wtr_fxt {
	public void Clear() {
		app = Xoa_app_fxt.Make__app__edit();
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Init_langs(wiki);
	}
	public static void Init_langs(Xowe_wiki wiki) {
		Xoae_app app = wiki.Appe();
		app.Xwiki_mgr__sitelink_mgr().Parse(BryUtl.NewU8(StringUtl.ConcatLinesNl
		( "0|grp1"
		, "1|simple|Simple"
		, "1|es|Spanish"
		, "1|it|Italian"
		, "1|zh|Chinese"
		, "0|grp2"
		, "1|fr|French"
		, "1|de|German"
		)));
		wiki.Xwiki_mgr().Add_by_sitelink_mgr();
		wiki.Appe().Usere().Wiki().Xwiki_mgr().Add_by_csv(BryUtl.NewA7(StringUtl.ConcatLinesNl
		( "1|simple.wikipedia.org|simple.wikipedia.org"
		, "1|fr.wikipedia.org|fr.wikipedia.org"
		, "1|es.wikipedia.org|es.wikipedia.org"
		, "1|de.wikipedia.org|de.wikipedia.org"
		, "1|it.wikipedia.org|it.wikipedia.org"
		)));
	}
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	Xoae_app app;
	public void tst(String raw, String expd) {
		Xop_ctx ctx = wiki.Parser_mgr().Ctx();
		ctx.Page().Ttl_(Xoa_ttl.Parse(wiki, BryUtl.NewA7("test_page")));
		byte[] raw_bry = BryUtl.NewU8(raw);
		BryWtr bfr = BryWtr.New();
		Xop_root_tkn root = ctx.Tkn_mkr().Root(raw_bry);
		wiki.Parser_mgr().Main().Parse_page_all_clear(root, ctx, ctx.Tkn_mkr(), raw_bry);
		wiki.Html_mgr().Html_wtr().Write_doc(bfr, ctx, raw_bry, root);

		BryWtr html_bfr = BryWtr.New();
		wiki.App().Xwiki_mgr__sitelink_mgr().Write_html(html_bfr, wiki, ctx.Page().Slink_list(), gplx.xowa.xtns.wbases.Wdata_xwiki_link_wtr.Qid_null);
	    GfoTstr.EqLines(expd, html_bfr.ToStrAndClear());
	}
}
