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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_lnke_wkr_relative_tst {
	@Before public void init() {fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Relative_obj() {
		fxt.Test_parse_page_wiki("[//a b]"
			, fxt.tkn_lnke_(0, 7).Lnke_rng_(1, 4).Subs_(fxt.tkn_txt_(5, 6))
			);
	}
	@Test  public void Relative_external() {
		fxt.Test__parse__wtxt_to_html("[//www.a.org a]", "<a href='https://www.a.org' rel='nofollow' class='external text'>a</a>");
	}
	@Test  public void Relative_internal() {
		fxt.Init_xwiki_add_user_("en.wikipedia.org");
		fxt.Test__parse__wtxt_to_html("[//en.wikipedia.org/wiki Wikipedia]", "<a href='/site/en.wikipedia.org/wiki/'>Wikipedia</a>");
	}
	@Test  public void Relative_w_category() {	// EX: [//commons.wikimedia.org/wiki/Category:Diomedeidae A]
		fxt.Init_xwiki_add_user_("en.wikipedia.org");
		fxt.Test__parse__wtxt_to_html("[//en.wikipedia.org/wiki/Category:A A]", "<a href='/site/en.wikipedia.org/wiki/Category:A'>A</a>");
	}
	@Test   public void Relurl() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Test__parse__wtxt_to_html("[[//en.wikipedia.org/ a]]", "[<a href='/site/en.wikipedia.org/wiki/'>a</a>]");
	}
}
