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
import gplx.langs.htmls.encoders.*;
import gplx.xowa.wikis.pages.*;
public class Http_url_parser_tst {
	private final    Http_url_parser_fxt fxt = new Http_url_parser_fxt();
	@Test  public void Parse() {
		// wiki-only
		fxt.Test__parse("/en.wikipedia.org", fxt.Make().Wiki_("en.wikipedia.org"));

		// wiki + page
		fxt.Test__parse("/en.wikipedia.org/wiki/Page_1", fxt.Make().Wiki_("en.wikipedia.org").Page_("Page_1"));

		// url-decoded; "%20" -> " "
		fxt.Test__parse("/en.wikipedia.org/wiki/Page%201", fxt.Make().Wiki_("en.wikipedia.org").Page_("Page 1"));

		// page with multiple slashes
		fxt.Test__parse("/en.wikipedia.org/wiki/Page_1/A/B/C", fxt.Make().Wiki_("en.wikipedia.org").Page_("Page_1/A/B/C"));

		// action=edit
		fxt.Test__parse("/en.wikipedia.org/wiki/Page_1?action=edit", fxt.Make().Wiki_("en.wikipedia.org").Page_("Page_1").Action_(Xopg_view_mode_.Tid__edit));

		// action=html
		fxt.Test__parse("/en.wikipedia.org/wiki/Page_1?action=html", fxt.Make().Wiki_("en.wikipedia.org").Page_("Page_1").Action_(Xopg_view_mode_.Tid__html));

		// action=popup
		fxt.Test__parse("/en.wikipedia.org/wiki/Page_1?action=popup", fxt.Make().Wiki_("en.wikipedia.org").Page_("Page_1").Popup_(true));

		// action=N/A
		fxt.Test__parse("/en.wikipedia.org/wiki/Page_1?action=a", fxt.Make().Wiki_("en.wikipedia.org").Page_("Page_1"));

		// fail: null
		fxt.Test__parse(null, fxt.Make().Err_msg_("invalid url; url is null"));

		// fail: empty
		fxt.Test__parse("", fxt.Make().Err_msg_("invalid url; url is empty"));

		// fail: missing '/' at start
		fxt.Test__parse("en.wikipedia.org", fxt.Make().Err_msg_("invalid url; must start with '/'; url=en.wikipedia.org"));
/*
		// fail: missing '/wiki/'
		fxt.Test__parse("/en.wikipedia.org/Page_1", fxt.Make().Err_msg_("invalid url; must have '/wiki/' after wiki_domain; url=/en.wikipedia.org/Page_1"));
*/
	}
}
class Http_url_parser_fxt {
	public Http_url_parser Make() {return new Http_url_parser();}
	public void Test__parse(String url, Http_url_parser expd) {
		Http_url_parser actl = new Http_url_parser();
		actl.Parse(url == null ? null : Bry_.new_u8(url));
		Gftest.Eq__ary__lines(expd.To_str(), actl.To_str());
	}
}
