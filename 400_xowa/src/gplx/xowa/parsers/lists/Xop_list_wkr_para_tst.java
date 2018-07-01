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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_list_wkr_para_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"*a"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>a"
			,	"  </li>"
			,	"</ul>"
			,	""
			)
			);
	}
	@Test  public void Multiple() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"*a"
			,	"*b"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>a"
			,	"  </li>"
			,	"  <li>b"
			,	"  </li>"
			,	"</ul>"
			)
			);
	}
	@Test  public void Multiple_w_1_nl() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"*a"
			,	""
			,	"*b"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>a"
			,	"  </li>"
			,	"</ul>"
			,	""
			,	"<ul>"
			,	"  <li>b"
			,	"  </li>"
			,	"</ul>"
			)
			);
	}
	@Test  public void Pre_between_lists() {	// PURPOSE: list should close pre; EX:en.b:Knowing Knoppix/Other applications; DATE:2014-02-18
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"#a"
			,	" b"
			,	"#c"	// should close <pre> opened by b
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ol>"
			,	"  <li>a"
			,	"  </li>"
			,	"</ol>"
			,	""
			,	"<pre>b"
			,	"</pre>"
			,	""
			,	"<ol>"
			,	"  <li>c"
			,	"  </li>"
			,	"</ol>"
			)
			);
	}
}
