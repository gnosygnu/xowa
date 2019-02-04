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
import org.junit.*;
public class Xoh_lnki_htxt__tst {
	private final    Xoh_htxt_fxt fxt = new Xoh_htxt_fxt();
	@Test   public void Redlink__basic() {
		fxt.Test__decode
		( "<a href='/wiki/A'>a</a> <a href='/wiki/B'>b</a>"
		, "<a id='xolnki_2' href='/wiki/A'>a</a> <a id='xolnki_3' href='/wiki/B'>b</a>");
		fxt.Test__hpg__redlinks("A", "B");
	}
	@Test   public void Redlink__anchor() {
		fxt.Test__decode
		( "<a href='#A'>a</a>"
		, "<a href='#A'>a</a>");
		fxt.Test__hpg__redlinks();
	}
}
