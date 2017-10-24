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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import org.junit.*;
public class XomwParserTest {
	private final    XomwParserFxt fxt = new XomwParserFxt();
	@Test  public void Basic() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "== heading_1 =="
		, "para_1"
		, "== heading_2 =="
		, "para_2"
		, "-----"
		, "{|"
		, "|-"
		, "|a"
		, "|}"
		, "''italics''"
		, "__TOC__"
		, "[https://a.org b]"
		, "[[A|abc]]"
		, "https://c.org"
		, "a »b« &#160;!important c"
		), String_.Concat_lines_nl_skip_last
		( "<h2> heading_1 </h2>"
		, "<p>para_1"
		, "</p>"
		, "<h2> heading_2 </h2>"
		, "<p>para_2"
		, "</p>"
		, "<hr />"
		, "<table>"
		, ""
		, "<tr>"
		, "<td>a"
		, "</td></tr></table>"
		, "<p><i>italics</i>"
		, "<!--MWTOC-->"
		, "<a rel=\"nofollow\" class=\"external text\" href=\"https://a.org\">b</a>"
		, "<a href=\"/wiki/A\" title=\"A\">abc</a>"
		, "<a rel=\"nofollow\" class=\"external free\" href=\"https://c.org\">https://c.org</a>"
		, "a&#160;»b«&#160; !important c"
		, "</p>"
		));
	}		
}
class XomwParserFxt {
	private final    XomwParser parser;
	private final    XomwParserCtx pctx = new XomwParserCtx();
	private final    XomwParserBfr pbfr = new XomwParserBfr();
	public XomwParserFxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		this.parser = new XomwParser(XomwEnv.NewTestByApp(app));
		parser.Init_by_wiki(wiki);
		parser.Init_by_page(XomwTitle.newFromText(parser.Env(), Bry_.new_a7("Page_1")));
		pctx.Init_by_page(XomwTitle.newFromText(parser.Env(), Bry_.new_a7("Page_1")));
	}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		parser.internalParse(pbfr, pctx, src_bry);
		parser.internalParseHalfParsed(pbfr, pctx, true, true);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
