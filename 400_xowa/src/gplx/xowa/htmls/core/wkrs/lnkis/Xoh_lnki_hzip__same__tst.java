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
public class Xoh_lnki_hzip__same__tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Same__basic() {			// EX: [[A]]
		fxt.Test__decode("~$!A~", "<a href='/wiki/A' title='A'>A</a>");
	}
	@Test   public void Same__encoded() {		// EX: [[A's]]
		String html = "<a href=\"/wiki/A%27s\" title=\"A's\">A's</a>";
		fxt.Test__bicode_raw("~${$#A%27s~A's~", html, html);
	}
	@Test   public void Same__encoded__anch() {	// EX: [[A#90.51]]
		fxt.Test__bicode("~${$%A~#90.51~", "<a href='/wiki/A#90.51' title='A'>A</a>");
	}
	@Test   public void Same__encoded__anch__nbsp() {	// EX: [[A#&nbsp;B|abc]]
		fxt.Test__bicode("~${$#A#.C2.A0B~abc~", "<a href='/wiki/A#.C2.A0B' title='abc'>abc</a>");
	}
	@Test   public void Same__amp() {			// EX: [[A&b]]
		fxt.Test__bicode("~${$#A%26b~A&amp;b~", "<a href='/wiki/A%26b' title='A&amp;b'>A&amp;b</a>");
	}
	@Test   public void More__basic() {			// EX: [[A]]b
		fxt.Test__bicode("~$#A~b~", "<a href='/wiki/A' title='A'>Ab</a>");
	}
	@Test   public void Less__cs__eq() {		// EX: [[Ab|A]]
		fxt.Test__bicode("~$$A~b~", "<a href='/wiki/Ab' title='Ab'>A</a>");
	}
	@Test   public void Less__cs__lo() {		// EX: [[Ab|a]]
		fxt.Test__bicode("~$(A~b~", "<a href='/wiki/Ab' title='Ab'>a</a>");
	}
	@Test   public void Less__ns__cs() {		// EX: [[Help:A_b|a]]; make sure ns is added correctly, not "aHelp:b"
		fxt.Test__bicode("~$h/Ab~ c~", "<a href='/wiki/Help:Ab_c' title='Help:Ab c'>ab</a>");
	}
	@Test   public void Ignore__audio() {
		String html = "<a href=\"file:///\" xowa_title=\"A.ogg\">a</a>";
		fxt.Test__bicode(html, html);
	}
}
