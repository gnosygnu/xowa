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
package gplx.xowa.mediawiki.includes.parsers.tables; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*;
public class Xomw_table_wkr__tst {
	private final    Xomw_table_wkr__fxt fxt = new Xomw_table_wkr__fxt();
	@Test  public void Basic() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "|a"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<table>"
		, ""
		, "<tr>"
		, "<td>a"
		, "</td></tr></table>"
		));
	}		
	@Test  public void Tb__atrs() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "{|id='1'"
		, "|-"
		, "|a"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<table id=\"1\">"
		, ""
		, "<tr>"
		, "<td>a"
		, "</td></tr></table>"
		));
	}		
	@Test  public void Tc__atrs() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|+id='1'|a"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<table>"
		, "<caption id=\"1\">a"
		, "</caption><tr><td></td></tr></table>"
		));
	}
	@Test  public void Th__double() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "{|"
		, "!a!!b"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<table>"
		, "<tr>"
		, "<th>a</th>"
		, "<th>b"
		, "</th></tr></table>"
		));
	}		
	@Test  public void Blank() {	// COVERS: "empty line, go to next line"
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "   "
		), String_.Concat_lines_nl_skip_last
		( "   "
		));
	}
	@Test  public void Tb__indent() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "::{|"
		, "|-"
		, "|a"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<dl><dd><dl><dd><table>"
		, ""
		, "<tr>"
		, "<td>a"
		, "</td></tr></table></dd></dl></dd></dl>"
		));
	}
	@Test  public void Tb__empty() {	// COVERS: "if (has_opened_tr.Len() == 0) {"
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<table>"
		, "<tr><td></td></tr></table>"
		));
	}
	@Test  public void Td__empty() {	// PURPOSE: handles (a) failure due to "first_2" array not handling "\n|\n"; (b) missing <tr><td></td></tr>
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "|"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<table>"
		, ""
		, "<tr>"
		, "<td>"
		, "</td></tr></table>"
		));
	}
}
class Xomw_table_wkr__fxt {
	private final    XomwParserBfr parser_bfr = new XomwParserBfr();
	private final    XomwParserCtx pctx = new XomwParserCtx();
	private final    Xomw_table_wkr wkr;
	public Xomw_table_wkr__fxt() {
		XomwParser parser = new XomwParser(XomwEnv.NewTest());
		this.wkr = new Xomw_table_wkr(Bry_bfr_.New(), parser.Sanitizer(), parser.Strip_state());
	}

	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		parser_bfr.Init(src_bry);
		wkr.doTableStuff(pctx, parser_bfr);
		Tfds.Eq_str_lines(expd, parser_bfr.Rslt().To_str_and_clear(), src_str);
	}
}
