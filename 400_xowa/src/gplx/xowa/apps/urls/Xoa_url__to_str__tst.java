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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.nss.*;
public class Xoa_url__to_str__tst {
	private final    Xoa_url__to_str__fxt fxt = new Xoa_url__to_str__fxt();
	@Test   public void Http()				{fxt.Chk_to_str_href(Bool_.N, "http://a.org/b"						, "http://a.org/b");}
	@Test   public void File()				{fxt.Chk_to_str_href(Bool_.N, "file:///C/xowa/file/a.png"			, "file:///C/xowa/file/a.png");}
	@Test   public void Abrv__page()		{fxt.Chk_to_str_href(Bool_.N, "/wiki/A"								, "A");}
	@Test   public void Abrv__anch()		{fxt.Chk_to_str_href(Bool_.N, "#b"									, "#b");}
	@Test   public void Full__page()		{fxt.Chk_to_str_href(Bool_.Y, "/wiki/A"								, "en.wikipedia.org/wiki/A");}
	@Test   public void Full__anch()		{fxt.Chk_to_str_href(Bool_.Y, "#b"									, "en.wikipedia.org/wiki/Page_1#b");}
	@Test   public void Vnt() {
		Xowe_wiki zh_wiki = fxt.Prep_create_wiki("zh.wikipedia.org");
		gplx.xowa.langs.vnts.Xol_vnt_regy_fxt.Init__vnt_mgr(zh_wiki.Lang().Vnt_mgr(), 0, String_.Ary("zh-hans", "zh-hant"));
		fxt.Chk_to_str_href(zh_wiki, Bool_.Y, "/site/zh.wikipedia.org/zh-hans/A"	, "zh.wikipedia.org/zh-hans/A");
		fxt.Chk_to_str_href(zh_wiki, Bool_.Y, "/site/zh.wikipedia.org/zh-hant/A"	, "zh.wikipedia.org/zh-hant/A");
		fxt.Chk_to_str_href(zh_wiki, Bool_.Y, "/site/zh.wikipedia.org/zh-cn/A"		, "zh.wikipedia.org/wiki/A");
		fxt.Chk_to_str_href(zh_wiki, Bool_.Y, "/site/zh.wikipedia.org/wiki/A"		, "zh.wikipedia.org/wiki/A");
	}
	@Test   public void Xwiki() {
		fxt.Prep_add_xwiki_to_user("fr.wikipedia.org");
		fxt.Chk_to_str_href(Bool_.N, "/site/fr.wikipedia.org/wiki/Page", "fr.wikipedia.org/wiki/Page");
	}
	@Test   public void Alias() {
		fxt.Prep_add_xwiki_to_wiki("wikt", "en.wiktionary.org");
		Xow_wiki en_d = fxt.Prep_create_wiki("en.wiktionary.org");
		en_d.Ns_mgr().Ns_main().Case_match_(Xow_ns_case_.Tid__all);
		fxt.Chk_to_str_href(Bool_.N, "/wiki/wikt:a"	, "en.wiktionary.org/wiki/a");
	}
	@Test   public void Unknown()			{fxt.Chk_to_str_href(Bool_.N, "/wiki/{{{extlink}}}"					, "");}	// {{{extlink}}} not a valid title; return empty
}
class Xoa_url__to_str__fxt extends Xow_url_parser_fxt { 	private final    Xoh_href_parser href_parser = new Xoh_href_parser();
	public void Chk_to_str_href(boolean full, String raw, String expd) {Chk_to_str_href(cur_wiki, full, raw, expd);}
	public void Chk_to_str_href(Xowe_wiki wiki, boolean full, String raw, String expd) {
		href_parser.Parse_as_url(actl_url, Bry_.new_u8(raw), wiki, Bry__page);
		this.Test__to_str(full, expd);
	}
	private static final    byte[] Bry__page = Bry_.new_a7("Page_1");
}
