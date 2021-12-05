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
package gplx.xowa.xtns.graphs;
import gplx.String_;
import gplx.objects.primitives.BoolUtl;
import org.junit.Before;
import org.junit.Test;
public class Graph_html_tst {
	private final Graph_html_fxt fxt = new Graph_html_fxt();
	@Before public void init() {
		fxt.Reset();
	}
	@Test  public void Basic() {
		fxt.Parser_fxt().Test_html_full_str(String_.Concat_lines_nl_skip_last
		( "<graph>"
		, "{"
		, "// COMMENT \n"
		, "   \"version\":2,"
		, "   \"width\":300"
		, "}"
		, "</graph>"
		), String_.Concat_lines_nl_skip_last
		( "<div class='mw-graph' xo-graph-version=2>"
		, "{"
		, ""
		, "   \"version\":2,"
		, "   \"width\":300"
		, "}"
		, "</div>"
		));
	}
	@Test  public void Wikirawupload() {
		String wtxt = fxt.Wikirawupload__wtxt();
		fxt.Test__hview(wtxt, fxt.Hdump_n_().Wikirawupload__html(BoolUtl.Y));
		fxt.Test__hdump(wtxt, fxt.Hdump_y_().Wikirawupload__html(BoolUtl.N), fxt.Wikirawupload__html(BoolUtl.Y));
	}
	@Test  public void Literal_XOWA_ROOT() {
		String wtxt = fxt.Literal_XOWA_ROOT__wtxt();
		fxt.Test__hview(wtxt, fxt.Hdump_n_().Literal_XOWA_ROOT__html(BoolUtl.Y));
		fxt.Test__hdump(wtxt, fxt.Hdump_y_().Literal_XOWA_ROOT__html(BoolUtl.N), fxt.Literal_XOWA_ROOT__html(BoolUtl.Y));
	}
	@Test  public void Error__missing_endquote() {
		fxt.Test__hload(String_.Concat_lines_nl_skip_last
		( "<div class='mw-graph' xo-graph-version=2 data-xowa-hdump='graph-json'>"
		, "{"
		, "   \"version\":2,"
		, "   \"path\":\"wikirawupload:{XOWA_ROOT}"
		, "}"
		, "</div>"
		));
	}
	@Test  public void Error__invalid() {
		fxt.Test__hload(String_.Concat_lines_nl_skip_last
		( "<div class='mw-graph' xo-graph-version=2 data-xowa-hdump='graph-json'>"
		, "{"
		, "   \"version\":2,"
		, "   \"path\":\"wikirawupload:{XOWA_ROOT}/invalid/commons.wikimedia.org/orig/7/0/1/c/A.png\","
		, "   \"width\":300"
		, "}"
		, "</div>"
		));
	}
}
