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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import org.junit.*;
public class Http_server_wkr__tst {
	@Before public void init() {fxt.Clear();} private Http_server_wkr__fxt fxt = new Http_server_wkr__fxt();
	@Test  public void Assert_main_page() {
		fxt.Init_wiki_main_page("fr.wikiversity.org", "Accueil");
		fxt.Test_assert_main_page("/fr.wikiversity.org/"		, "/fr.wikiversity.org/wiki/Accueil");
		fxt.Test_assert_main_page("/fr.wikiversity.org/wiki"	, "/fr.wikiversity.org/wiki/Accueil");
		fxt.Test_assert_main_page("/fr.wikiversity.org/wiki/"	, "/fr.wikiversity.org/wiki/Accueil");
		fxt.Test_assert_main_page("/fr.wikiversity.org/wiki/A"	, "/fr.wikiversity.org/wiki/A");
	}
}
class Http_server_wkr__fxt {
	private Xoae_app app;
	public void Clear() {
		this.app = Xoa_app_fxt.Make__app__edit();
	}
	public void Init_wiki_main_page(String domain, String main_page) {
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_or_make(Bry_.new_u8(domain));
		wiki.Props().Main_page_(Bry_.new_u8(main_page));
	}
	public void Test_assert_main_page(String url, String expd) {
		Tfds.Eq(expd, Http_server_wkr_.Assert_main_page(app, url));
	}
}
