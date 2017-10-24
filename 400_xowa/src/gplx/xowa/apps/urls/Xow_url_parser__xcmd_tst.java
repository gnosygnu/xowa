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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Xow_url_parser__xcmd_tst {
	private final    Xow_url_parser_fxt tstr = new Xow_url_parser_fxt();
	@Test  public void Basic() {
		tstr.Exec__parse("xowa-cmd:xowa.app.version").Test__tid(Xoa_url_.Tid_xcmd).Test__page("xowa.app.version");
	}
	@Test  public void Encoded() {
		tstr.Exec__parse("xowa-cmd:a%22b*c").Test__tid(Xoa_url_.Tid_xcmd).Test__page("a\"b*c");
	}
	@Test  public void Ignore_anchor_and_qargs() {
		tstr.Exec__parse("xowa-cmd:a/b/c?d=e#f").Test__tid(Xoa_url_.Tid_xcmd).Test__page("a/b/c?d=e#f");
	}
}
