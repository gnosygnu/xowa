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
import org.junit.*;
public class Xow_url_parser__wiki_tst {
	private final    Xow_url_parser_fxt tstr = new Xow_url_parser_fxt();
	@Test  public void Basic() {
		tstr.Exec__parse("en.wikipedia.org/wiki/A").Test__tid(Xoa_url_.Tid_page).Test__wiki("en.wikipedia.org").Test__page("A");
	}
	@Test  public void No_wiki() {	// PURPOSE: no "/wiki/"
		tstr.Exec__parse("en.wikipedia.org/A").Test__wiki("en.wikipedia.org").Test__page("A");
	}
	@Test  public void Nested() {
		tstr.Exec__parse("en.wikipedia.org/wiki/A/b").Test__wiki("en.wikipedia.org").Test__page("A/b");
	}
	@Test  public void Slash() {
		tstr.Exec__parse("en.wikipedia.org/wiki//A").Test__wiki("en.wikipedia.org").Test__page("/A");
		tstr.Exec__parse("en.wikipedia.org/wiki/A//b").Test__wiki("en.wikipedia.org").Test__page("A//b");
		tstr.Exec__parse("en.wikipedia.org/wiki///A").Test__wiki("en.wikipedia.org").Test__page("//A");
	}
	@Test  public void Vnt() {
		Xowe_wiki wiki = tstr.Wiki();
		gplx.xowa.langs.vnts.Xol_vnt_regy_fxt.Init__vnt_mgr(wiki.Lang().Vnt_mgr(), 0, String_.Ary("zh-hans", "zh-hant"));
		tstr.Exec__parse("en.wikipedia.org/zh-hans/A").Test__wiki("en.wikipedia.org").Test__page("A").Test__vnt("zh-hans");
	}
}
