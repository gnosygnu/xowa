/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*;
public class Xoh_lnke_html__basic__tst {
	@After public void term() {fxt.Init_para_n_(); fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Auto__one()		{fxt.Test_parse_page_wiki_str("[https://a]"					, "<a href=\"https://a\" rel=\"nofollow\" class=\"external autonumber\">[1]</a>");}
	@Test  public void Auto__many()		{fxt.Test_parse_page_wiki_str("[https://a] [https://b]"		, "<a href=\"https://a\" rel=\"nofollow\" class=\"external autonumber\">[1]</a> <a href=\"https://b\" rel=\"nofollow\" class=\"external autonumber\">[2]</a>");}
	@Test  public void Text__basic()	{fxt.Test_parse_page_wiki_str("[https://a b]"				, "<a href=\"https://a\" rel=\"nofollow\" class=\"external text\">b</a>");}
	@Test  public void Text__wtxt()		{fxt.Test_parse_page_wiki_str("[https://a ''b'']"			, "<a href=\"https://a\" rel=\"nofollow\" class=\"external text\"><i>b</i></a>");}
	@Test  public void Xowa_protocol()	{
		String img = "<img src=\"file:///mem/xowa/bin/any/xowa/file/app.general/xowa_exec.png\"/>";
		fxt.Wiki().Sys_cfg().Xowa_proto_enabled_(true);
		fxt.Test_parse_page_wiki_str("[xowa-cmd:\"a\" z]"			, "<a href=\"xowa-cmd:a\">z" + img + "</a>");
		fxt.Test_parse_page_wiki_str("[xowa-cmd:\"a.b('c_d');\" z]"	, "<a href=\"xowa-cmd:a.b('c_d');\">z" + img + "</a>");
		fxt.Test_parse_page_wiki_str("[xowa-cmd:*\"a\"b*c\"* z]"	, "<a href=\"xowa-cmd:a%22b%2Ac\">z" + img + "</a>");
		fxt.Wiki().Sys_cfg().Xowa_proto_enabled_(false);
		fxt.Test_parse_page_wiki_str("[xowa-cmd:\"a\" b]"			, "[xowa-cmd:&quot;a&quot; b]");	// protocol is disabled: literalize String (i.e.: don't make it an anchor)
	}
	@Test   public void Xwiki() {
		String wtxt = "[//en.wiktionary.org/wiki/A B]";
		String html_https = "<a href='https://en.wiktionary.org/wiki/A' rel='nofollow' class='external text'>B</a>";
		String html_xwiki = "<a href='/site/en.wiktionary.org/wiki/A'>B</a>";
		fxt.Test__parse__wtxt_to_html(wtxt, html_https);			// https b/c wiki not installed
		fxt.Init_xwiki_add_user_("en.wiktionary.org");
		fxt.Test__parse__wtxt_to_html(wtxt, html_xwiki);			// xwiki b/c wiki installed
		fxt.Hctx_(gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx.Hdump);
		fxt.Test__parse__wtxt_to_html(wtxt, html_https);			// https b/c hdump, even though wiki installed
		fxt.Hctx_(gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx.Basic);
	}
}
