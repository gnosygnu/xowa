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
package gplx.xowa.apps.apis.xowa.addons.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.addons.*;
import org.junit.*; import gplx.core.tests.*;
public class Xopg_match_mgr__tst {
	private final    Xopg_match_mgr__fxt fxt = new Xopg_match_mgr__fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Specific() {
		fxt.Init__set("en.w:A");
		fxt.Test__match_y("A");
		fxt.Test__match_n("AB");
	}
	@Test  public void Other_wiki() {
		fxt.Init__set("fr.w:A");
		fxt.Test__match_n("A");	// note that test defaults to "en.w" as primary wiki
	}
	@Test  public void Wildcard__app() {
		fxt.Init__set("*");
		fxt.Test__match_y("A", "B");
	}
	@Test  public void Wildcard__page() {
		fxt.Init__set("en.w:*");
		fxt.Test__match_y("A", "B");
	}
	@Test  public void Wildcard__page__other() {
		fxt.Init__set("fr.w:*");
		fxt.Test__match_n("A", "B");
	}
	@Test  public void Wildcard__wiki() {
		fxt.Init__set("*:A");
		fxt.Test__match_y("A");
		fxt.Test__match_n("B");
	}
}
class Xopg_match_mgr__fxt {
	private final    Xopg_match_mgr match_mgr = new Xopg_match_mgr();
	private Xowe_wiki wiki;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		app.User().Wikii().Xwiki_mgr().Add_by_atrs(wiki.Domain_bry(), wiki.Domain_bry());
	}
	public void Init__set(String url) {
		match_mgr.Set(url);
	}
	public void Test__match_y(String... urls) {Test__match(Bool_.Y, urls);}
	public void Test__match_n(String... urls) {Test__match(Bool_.N, urls);}
	private void Test__match(boolean expd, String... urls) {
		for (int i = 0; i < urls.length; i++) {
			String url = urls[i];				
			boolean actl = match_mgr.Match(wiki, Bry_.new_u8(url));
			Gftest.Eq__bool(expd, actl, "match failed", "expd", expd, "url", url);
		}
	}
}
