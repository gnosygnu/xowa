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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_lnki_make__basic__tst {
	private final Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Before public void Init() {fxt.Clear();}
	@Test public void Wiki() {
		fxt.Test__make("<a href='/wiki/Help:A' title='Help:A'>Help:A</a>", fxt.Page_chkr().Body_("<a id='xolnki_2' href='/wiki/Help:A' title='Help:A'>Help:A</a>"));
	}
	@Test public void Anch() {
		String orig = "<a href='#a'>#a</a>";
		fxt.Test__make(orig, fxt.Page_chkr().Body_(orig));
	}
	@Test public void Site() {
		String orig = "<a href='/site/en.wiktionary.org/wiki/A' title='wikt:A'>wikt:A</a>";
		fxt.Test__make(orig, fxt.Page_chkr().Body_(orig));
	}
	@Test public void Inet() {
		String orig = "<a href='https://simple.wikisource.org/wiki/A' title='A'>b</a>";
		fxt.Test__make(orig, fxt.Page_chkr().Body_(orig));
	}
	@Test public void Redlinks() {// PURPOSE: redlink should have ns and ttl, not just ns; ISSUE#:568 DATE:2019-09-29
		fxt.Expd__redlinks("Help:A_\"_b");
		fxt.Test__make("<a href='/wiki/Help:A_%22_b' title='Help:A_\"_b'>Help:A \" b</a>");
	}
}
