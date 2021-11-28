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
package gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.Store; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.Wikibase.*; import gplx.xowa.mediawiki.extensions.Wikibase.lib.*; import gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.mediawiki.includes.xohtml.*;
public class XomwWikiTextPropertyOrderProvider_tst {
	private final XomwWikiTextPropertyOrderProvider_fxt fxt = new XomwWikiTextPropertyOrderProvider_fxt();
	@Test  public void Basic()	{
		fxt.Test__getPropertyOrder(String_.Concat_lines_nl
			( "* [[Property:P1]]"
			, "* [[Property:P2]]"
			), XophpArray.New()
			.Add("P1", "0")
			.Add("P2", "1")
			);
	}
	@Test  public void Comments() {
		fxt.Test__getPropertyOrder(String_.Concat_lines_nl
			( "<!--* [[Property:P0]]-->"
			, "* [[Property:P1]]"
			, "* [[Property:P2]]"
			), XophpArray.New()
			.Add("P1", "0")
			.Add("P2", "1")
			);
	}
	@Test  public void Invalid_properties() {
		fxt.Test__getPropertyOrder(String_.Concat_lines_nl
			( "* [[Property:P0a]]"
			, "* [[Property:P1]]"
			, "* [[Property:P2]]"
			), XophpArray.New()
			.Add("P1", "0")
			.Add("P2", "1")
			);
	}
}
class XomwWikiTextPropertyOrderProvider_fxt {
	public void Test__getPropertyOrder(String page, XophpArray expd) {
		MockXomwWikiTextPropertyOrderProvider provider = new MockXomwWikiTextPropertyOrderProvider(page);
		XophpArray actl = provider.getPropertyOrder();
		Gftest.Eq__str(expd.To_str(), actl.To_str());
	}
}
class MockXomwWikiTextPropertyOrderProvider extends XomwWikiTextPropertyOrderProvider {	private final String text;
	public MockXomwWikiTextPropertyOrderProvider(String text) {this.text = text;}
	@Override protected String getPropertyOrderWikitext() {
		return text;
	}
}
