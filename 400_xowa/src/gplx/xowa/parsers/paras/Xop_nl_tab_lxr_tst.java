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
public class Xop_nl_tab_lxr_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final Xop_fxt fxt = new Xop_fxt();
	@After public void teardown() {fxt.Init_para_n_();}
	@Test  public void Basic() {		// PURPOSE: \n\t|- should be recognized as tblw; EX:zh.v:西安; DATE:2014-05-06
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
		( "{|"
		, "\t|-"
		, "|a"
		, "|}"
		), String_.Concat_lines_nl
		( "<table>"
		, "  <tr>"
		, "    <td>a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test  public void Ws() {			// PURPOSE: \n\t|- should be recognized as tblw; EX:zh.v:西安; DATE:2014-05-06
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
		( "{|"
		, "\t  |-"	// \t  
		, "|a"
		, "|}"
		), String_.Concat_lines_nl
		( "<table>"
		, "  <tr>"
		, "    <td>a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test  public void Ignore() {// PURPOSE: \n\t should not be pre; EX:pl.w:Main_Page; DATE:2014-05-06
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a"
		, "\t b"
		, "c"
		),	String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "\t b"
		, "c"
		, "</p>"
		));
	}
}
