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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoh_toc_wkr__txt__dupe__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_wkr__txt__fxt fxt = new Xoh_toc_wkr__txt__fxt();
	@Test   public void Basic() {
		fxt.Test__anch("a"		, "a");
		fxt.Test__anch("a"		, "a_2");
	}
	@Test   public void Case_insensitive() {
		fxt.Test__anch("a"		, "a");
		fxt.Test__anch("A"		, "A_2");
	}
	@Test   public void Autonumber_exists() {	// PAGE:fr.w:Itanium; EX: Itanium,Itanium_2,Itanium
		fxt.Test__anch("a"		, "a");
		fxt.Test__anch("a_2"	, "a_2");
		fxt.Test__anch("a"		, "a_3");
	}
	@Test   public void Autonumber_exists_2() {
		fxt.Test__anch("a_2"	, "a_2");
		fxt.Test__anch("a"		, "a");
		fxt.Test__anch("a"		, "a_3");
		fxt.Test__anch("a"		, "a_4");
		fxt.Test__anch("a_2"	, "a_2_2");
	}
}
