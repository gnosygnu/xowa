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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import org.junit.*; import gplx.core.tests.*; import gplx.xowa.apps.urls.*;
public class Xoctg_catpage_url__tst {
	@Before public void init() {fxt.Clear();} private Xoctg_catpage_url__fxt fxt = new Xoctg_catpage_url__fxt();
	@Test   public void Specific() {
		fxt.Exec__parse("A?subcatfrom=B&filefrom=C&pagefrom=D"		).Test__keys("B", "C", "D").Test__fwds(Bool_.Y, Bool_.Y, Bool_.Y);
		fxt.Exec__parse("A?subcatuntil=B&fileuntil=C&pageuntil=D"	).Test__keys("B", "C", "D").Test__fwds(Bool_.N, Bool_.N, Bool_.N);
	}
	@Test   public void General() {
		fxt.Exec__parse("A?from=B"	).Test__keys("B", "B", "B").Test__fwds(Bool_.Y, Bool_.Y, Bool_.Y);
		fxt.Exec__parse("A?until=B"	).Test__keys("B", "B", "B").Test__fwds(Bool_.N, Bool_.N, Bool_.N);
	}
	@Test   public void Url_encoded() {
		fxt.Exec__parse("A?from=B+C").Test__keys("B C", "B C", "B C").Test__fwds(Bool_.Y, Bool_.Y, Bool_.Y);
	}
}
class Xoctg_catpage_url__fxt {
	private Xow_url_parser xo_url_parser; private Xoctg_catpage_url ctg_url;
	public void Clear() {
		Xoa_app app = Xoa_app_fxt.Make__app__edit();
		this.xo_url_parser = app.User().Wikii().Utl__url_parser();
	}
	public Xoctg_catpage_url__fxt Exec__parse(String url_str) {
		Xoa_url page_url = xo_url_parser.Parse(Bry_.new_u8(url_str));
		this.ctg_url = Xoctg_catpage_url_parser.Parse(page_url);
		return this;
	}
	public Xoctg_catpage_url__fxt Test__keys(String... expd)		{Gftest.Eq__ary(Bry_.Ary(expd), ctg_url.Grp_keys(), "keys"); return this;}
	public Xoctg_catpage_url__fxt Test__fwds(boolean... expd)		{Gftest.Eq__ary(expd, ctg_url.Grp_fwds(), "fwds"); return this;}
}
