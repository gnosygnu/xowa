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
package gplx.xowa.parsers.paras; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_para_wkr_para_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final Xop_fxt fxt = new Xop_fxt();
	@After public void teardown() {fxt.Init_para_n_();}
	@Test  public void Pre_then_xnde_pre() {	// PURPOSE: if ws_pre is in effect, xnde_pre should end it; EX: b:Knowing Knoppix/Other applications
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	" a"
		,	"b<pre>c"
		,	"d</pre>"
		,	"e"
		), String_.Concat_lines_nl_skip_last
		( 	"<pre>a"
		,	"</pre>"
		,	"b<pre>c"
		,	"d</pre>"
		,	""
		,	"<p>e"
		,	"</p>"
		,	""
		));
	}
	@Test  public void List_ignore_pre_lines() {	// PURPOSE: "\s\n" should create new list; was continuing previous list; DATE:2013-07-12
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	": a"
		,	":* b"
		,	" "
		,	": c"
		,	":* d"
		)
		,	String_.Concat_lines_nl_skip_last
		(	"<dl>"
		,	"  <dd> a"
		,	""
		,	"    <ul>"
		,	"      <li> b"
		,	"      </li>"
		,	"    </ul>"
		,	"  </dd>"
		,	"</dl>"
		,	""
		,	"<dl>"
		,	"  <dd> c"
		,	""
		,	"    <ul>"
		,	"      <li> d"
		,	"      </li>"
		,	"    </ul>"
		,	"  </dd>"
		,	"</dl>"
		,	""
		));		
	}
	@Test  public void Multiple_nl_in_tblx() {	// PURPOSE: "\n\n\n" was causing multiple breaks; EX:fr.w:Portail:G�nie m�canique; DATE:2014-02-17
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"<table><tr><td>a"
		,	"</td>"
		,	""
		,	""
		,	""
		,	""
		,	""
		,	"</tr></table>"
		)
		,	String_.Concat_lines_nl_skip_last
		(	"<table>"
		,	"  <tr>"
		,	"    <td>a"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	""
		)
		);
	}
	@Test  public void Ignore_cr() {	// PURPOSE: handle "\r\n"; EX: Special:MovePage; DATE:2014-03-02
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"a\r"
		,	"\r"
		,	"b\r"
		)
		,	String_.Concat_lines_nl_skip_last
		(	"<p>a"
		,	"</p>"
		,	""
		,	"<p>b"
		,	"</p>"
		,	""
		)
		);
	}
}
