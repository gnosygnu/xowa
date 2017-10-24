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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_hr_lxr_para_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final Xop_fxt fxt = new Xop_fxt();
	@Test   public void Bos()	{	// PURPOSE: check that bos rendered correctly; DATE:2014-04-18
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "----"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<hr/>"
		, ""
		, "<p>a"
		, "</p>"
		));
	}
	@Test   public void Multiple()	{	// PURPOSE.fix: hr disables para for rest of page; ca.b:Xarxes; DATE:2014-04-18
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a"
		, "----"
		, "b"
		, ""
		, ""
		, "c"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, "<hr/>"
		, ""
		, "<p>b"
		, "</p>"
		, ""
		, "<p><br/>"
		, "c"
		, "</p>"
		));
	}
}
