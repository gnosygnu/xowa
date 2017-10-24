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
public class Random_selection_xnde_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test   public void Basic() {
		Random_selection_xnde.Rnd_test = 2;
		fxt.Test__parse__tmpl_to_html(String_.Concat_lines_nl_skip_last
		( "<choose before=\"bgn_''\" after=\"''_end\">"
		, "<option>A</option>"
		, "<option>B</option>"
		, "<option>C</option>"
		, "</choose>"
		), "bgn_<i>B</i>_end");
		Random_selection_xnde.Rnd_test = -1;
	}
	@Test   public void Choicetemplate() {
		Random_selection_xnde.Rnd_test = 2;
		fxt.Init_page_create("Template:Tmpl", "bgn_''{{{1}}}''_end");
		fxt.Test__parse__tmpl_to_html(String_.Concat_lines_nl_skip_last
		( "<choose>"
		, "<option>A</option>"
		, "<option>B</option>"
		, "<option>C</option>"
		, "<choicetemplate>Tmpl</choicetemplate>"
		, "</choose>"
		), "bgn_<i>B</i>_end");
		Random_selection_xnde.Rnd_test = -1;
	}
}
