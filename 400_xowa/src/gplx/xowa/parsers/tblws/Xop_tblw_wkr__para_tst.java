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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_tblw_wkr__para_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final    Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Para() {	// PURPOSE: para causing strange breaks; SEE:[[John F. Kennedy]] and "two Supreme Court appointments"
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"<p></p>"
			,	"|a"
			,	"<p></p>"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table><p></p>"
			,	"  <tr>"
			,	"    <td>a"
			,	"<p></p>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Nl() {	// PURPOSE: para causing strange breaks; SEE:[[John F. Kennedy]] and "two Supreme Court appointments"
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"!a"
			,	""
			,	"|-"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th>a"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Unnecessary_para() {	// PURPOSE: tblw causes unnecessary <p>; home/wiki/Dashboard/Image_databases; DATE:2014-02-20
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"|"
			,	"a<br/>"
			,	"b"
			,	"|"
			,	"c<br/>"
			,	"d"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	""
			,	"<p>a<br/>"
			,	"b"
			,	"</p>"
			,	"    </td>"
			,	"    <td>"
			,	""
			,	"<p>c<br/>"
			,	"d"
			,	"</p>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Ws_leading() {	// PAGE:en.w:AGPLv3
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	" !a"
			,	" !b"
			,	"|}"
			)
			, String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th>a"
			,	"    </th>"
			,	"    <th>b"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Ws_th_2() {	// "\n\s!" should still be interpreted as tblw; s.w:Manchester; DATE:2014-02-14
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"|!style='color:red'|a"
			,	" !style=\"color:blue\"|b"
			,	"|}"
			)
			, String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"    <th style=\"color:blue\">b"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Ws_th_3() {	// "\n\s!" and "!!" breaks tblw; ru.w:Храмы_Санкт-Петербурга (List of churches in St Petersburg); DATE:2014-02-20
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	" ! id='1' | a !! id='2' | b"
			,	"|}"
			)
			, String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th id='1'> a "
			,	"    </th>"
			,	"    <th id='2'> b"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Tblw_td2_should_not_create_ws() { // PURPOSE: a||b -> a\n||b; EX:none;discovered during luaj test; DATE:2014-04-14
		fxt.Test_parse_page_wiki_str("a||b", "<p>a||b\n</p>");
	}
	@Test  public void Para_on_tblw() {	// PURPOSE:table following link should automatically add para around link; PAGE:en.w:Template_engine_(web) DATE:2017-04-08
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"[[A]] b"
		,	"{|"
		,	"|-"
		,	"|c"
		,	"|}"
		)
		, String_.Concat_lines_nl_skip_last
		(	"<p><a href=\"/wiki/A\">A</a> b" // NOTE: previously, <p> was not included; now added for TRAILING_TBLW fix; DATE:2017-04-08
		,	"</p>"
		,	"<table>"
		,	"  <tr>"
		,	"    <td>c"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	""
		)
		);
	}
}
