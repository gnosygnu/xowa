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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*;
public class Xoh_hdr_html_tst {
	private final    Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Test   public void Basic() {
		fxt.Test__html(String_.Concat_lines_nl_skip_last
		( "z"
		, "==A 1=="			
		, "a 1"
		, "==B=="
		, "b"
		), String_.Concat_lines_nl_skip_last
		( "z"
		, ""
		, "<h2><span class='mw-headline' id='A_1'>A 1</span></h2>"
		, "a 1"
		, ""
		, "<h2><span class='mw-headline' id='B'>B</span></h2>"
		, "b"
		));
	}
	@Test   public void Uniq() {
		fxt.Test__html(String_.Concat_lines_nl_skip_last
		( "== a <math>c</math> e =="
		, "text"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class=\"mw-headline\" id=\"a_c_e\"> a <span id='xowa_math_txt_0'>c</span> e </span></h2>"
		, "text"
		)
		, false);
	}
}
