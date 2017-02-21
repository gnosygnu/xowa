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
public class Xoh_lnki_hzip__anch__tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Basic() {			// EX: [[#a]]
		fxt.Test__bicode("~$Ba~#a~", "<a href='#a'>#a</a>");
	}
	@Test   public void Capt() {			// EX: [[#a|b]]
		fxt.Test__bicode("~$Ba~b~", "<a href='#a'>b</a>");
	}
	@Test   public void Capt_similar() {	// EX: [[#a|a]]
		fxt.Test__bicode("~$Ba~a~", "<a href='#a'>a</a>");
	}
	@Test   public void Error() {			// EX: [[#a|b]]; make sure bad title character does not cause error
		fxt.Test__bicode("~$Ba|b~#a|b~", "<a href='#a|b'>#a|b</a>");	// NOTE: the "|" should be url-encoded
	}
	@Test   public void Inet__file() {
		fxt.Test__bicode("~$Rfile:///C://A.png~b~", "<a href='file:///C://A.png' title='file:///C://A.png'>b</a>");
	}
	@Test   public void Inet__enc() {
		fxt.Test__bicode("~${'Thttps://simple.wikisource.org/wiki/A%C3%A6e~b~Aæe~", "<a href='https://simple.wikisource.org/wiki/A%C3%A6e' title='Aæe'>b</a>");
	}
}
