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
package gplx.xowa.xtns.wikias; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Tabview_xnde_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test   public void Basic() {
		fxt.Init_page_create("A", "''a''");
		fxt.Init_page_create("B", "''b''");
		fxt.Test__parse__tmpl_to_html(String_.Concat_lines_nl_skip_last
		( "<tabview id='test'>"
		, "A|tab1"
		, "B|tab2||true"
		, "</tabview>"
		), String_.Concat_lines_nl_skip_last
		( "<div id=\"tabber-test\" class=\"tabber\">"
		,   "<div class=\"tabbertab\" title=\"tab1\">"
		,     "<p><i>a</i></p>"
		,   "</div>"
		,   "<div class=\"tabbertab\" title=\"tab2\" data-active=\"true\">"
		,     "<p><i>b</i></p>"
		,   "</div>"
		, "</div>"
		));
	}
}
