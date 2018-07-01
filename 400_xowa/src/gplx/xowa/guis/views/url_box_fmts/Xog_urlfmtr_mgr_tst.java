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
package gplx.xowa.guis.views.url_box_fmts; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.apps.urls.*;
public class Xog_urlfmtr_mgr_tst {
	private Xog_urlfmtr_mgr_fxt fxt = new Xog_urlfmtr_mgr_fxt();
	@Test  public void Basic() {
		String fmt_suffix = "domain:~{wiki_domain}; page_unders:~{page_title_unders}; page_spaces:~{page_title_spaces}";
		fxt.Init__init_by_parse
		( "*|wild -- " + fmt_suffix
		, "en.wikipedia.org|en.w -- " + fmt_suffix
		, "de.wikibooks.org|de.b -- " + fmt_suffix
		);
		fxt.Test__gen_or_null("en.wikipedia.org/wiki/Page_1", "en.w -- domain:en.wikipedia.org; page_unders:Page_1; page_spaces:Page 1");
		fxt.Test__gen_or_null("de.wikibooks.org/wiki/Page_1", "de.b -- domain:de.wikibooks.org; page_unders:Page_1; page_spaces:Page 1");
		fxt.Test__gen_or_null("fr.wikibooks.org/wiki/Page_1", "wild -- domain:fr.wikibooks.org; page_unders:Page_1; page_spaces:Page 1");
	}
	@Test  public void Wildcard_default() {
		String fmt_suffix = "domain:~{wiki_domain}; page_unders:~{page_title_unders}; page_spaces:~{page_title_spaces}";
		fxt.Init__init_by_parse
		( "en.wikipedia.org|en.w -- " + fmt_suffix
		, "de.wikibooks.org|de.b -- " + fmt_suffix
		);
		fxt.Test__gen_or_null("en.wikipedia.org/wiki/Page_1", "en.w -- domain:en.wikipedia.org; page_unders:Page_1; page_spaces:Page 1");
		fxt.Test__gen_or_null("fr.wikibooks.org/wiki/Page_1", "fr.wikibooks.org/wiki/Page_1");
	}
}
class Xog_urlfmtr_mgr_fxt {
	private final    Xog_urlfmtr_mgr mgr = new Xog_urlfmtr_mgr();
	private final    Xow_url_parser url_parser;
	public Xog_urlfmtr_mgr_fxt() {
		// create url parser
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xow_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		this.url_parser = new Xow_url_parser(wiki);

		// reg xwikis
		wiki.App().User().Wikii().Xwiki_mgr().Add_by_atrs("de.wikibooks.org", "de.wikibooks.org");
		wiki.App().User().Wikii().Xwiki_mgr().Add_by_atrs("fr.wikibooks.org", "fr.wikibooks.org");
	}
	public void Init__init_by_parse(String... lines) {
		mgr.Parse(Bry_.new_u8(String_.Concat_lines_nl_skip_last(lines)));
	}
	public void Test__gen_or_null(String url_str, String expd) {
		Xoa_url url = url_parser.Parse(Bry_.new_u8(url_str));
		Gftest.Eq__str(expd, mgr.Gen(url));
	}
}
