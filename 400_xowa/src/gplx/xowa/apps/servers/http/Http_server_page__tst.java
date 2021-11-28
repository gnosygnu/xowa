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
import org.junit.*; import gplx.core.tests.*;
public class Http_server_page__tst {
	private final Http_server_page__fxt fxt = new Http_server_page__fxt();
	@Test  public void Xwiki() {
		fxt.Init__xwiki("en.wikipedia.org", "it", "it.wikipedia.org");
		fxt.Test__make_url(false, "en.wikipedia.org", "it:Roma", "");
		fxt.Test__redirect("it.wikipedia.org/wiki/Roma");
	}
}
class Http_server_page__fxt {
	private final Xop_fxt fxt;
	private final Http_server_page page;
	public Http_server_page__fxt() {
		this.fxt = new Xop_fxt();
		this.page = new Http_server_page(fxt.App());
	}
	public void Init__xwiki(String wiki, String abrv, String domain) {
		Xowe_wiki xwiki_wiki = fxt.App().Wiki_mgr().Get_by_or_make(Bry_.new_u8(wiki));
		xwiki_wiki.Xwiki_mgr().Add_by_atrs(abrv, domain);
		xwiki_wiki.Installed_by_test_(Bool_.Y);
	}
	public void Test__make_url(boolean expd, String wiki_domain, String ttl_bry_arg, String qarg) {
		boolean actl = page.Make_url(Bry_.new_u8(wiki_domain), Bry_.new_u8(ttl_bry_arg), Bry_.new_u8(qarg));
		Gftest.Eq__bool(expd, actl);
	}
	public void Test__redirect(String expd) {
		Gftest.Eq__str(expd, page.Redirect());
	}
}
