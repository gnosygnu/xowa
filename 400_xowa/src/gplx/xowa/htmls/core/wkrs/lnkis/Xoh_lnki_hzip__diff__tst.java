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
public class Xoh_lnki_hzip__diff__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Diff__basic() {				// EX: [[A|b]]
		fxt.Test__bicode("~$\"A~b~"	, "<a href='/wiki/A' title='A'>b</a>");
	}
	@Test   public void Diff__cs__lo() {			// EX: [[A|a]]
		fxt.Test__bicode("~$%A~"	, "<a href='/wiki/A' title='A'>a</a>");
	}
	@Test   public void Diff__page_w_anch() {		// EX: [[A#b|c]]
		fxt.Test__bicode("~${'$A#b~b~A~", "<a href='/wiki/A#b' title='A'>b</a>");
	}
	@Test   public void Capt__nest() {				// EX: [[A|B[[C|C1]]D]]
		fxt.Test__bicode
		( "~$\"A~B<a href=\"/wiki/C\" title=\"C\">C1</a>D~"
		, "<a href='/wiki/A' title='A'>B<a href='/wiki/C' title='C'>C1</a>D</a>"
		);
	}
}
