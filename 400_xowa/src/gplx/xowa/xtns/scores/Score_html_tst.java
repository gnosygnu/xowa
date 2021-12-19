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
package gplx.xowa.xtns.scores;
import gplx.types.basics.utls.BoolUtl;
import org.junit.Before;
import org.junit.Test;
public class Score_html_tst {
	private final Score_html_fxt fxt = new Score_html_fxt();
	@Before public void init() {
		fxt.Clear();
	}
	@Test public void Basic() {
		String wtxt = fxt.Basic__wtxt();
		fxt.Test__hview(wtxt, fxt.Hdump_n_().Basic__html(BoolUtl.Y));
		fxt.Test__hdump(wtxt, fxt.Hdump_y_().Basic__html(BoolUtl.N), fxt.Basic__html(BoolUtl.Y));
		fxt.Exec__Fill_page();
	}
}
