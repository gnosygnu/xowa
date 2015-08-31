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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xoh_lnke_wtr_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_(); fxt.Reset();}
	@Test  public void Basic()			{fxt.Test_parse_page_wiki_str("[irc://a]"				, "<a href=\"irc://a\" class=\"external text\" rel=\"nofollow\">[1]</a>");}
	@Test  public void Autonumber()		{fxt.Test_parse_page_wiki_str("[irc://a] [irc://b]"		, "<a href=\"irc://a\" class=\"external text\" rel=\"nofollow\">[1]</a> <a href=\"irc://b\" class=\"external text\" rel=\"nofollow\">[2]</a>");}
	@Test  public void Caption()		{fxt.Test_parse_page_wiki_str("[irc://a b]"				, "<a href=\"irc://a\" class=\"external text\" rel=\"nofollow\">b</a>");}
	@Test  public void Caption_wtxt()	{fxt.Test_parse_page_wiki_str("[irc://a ''b'']"			, "<a href=\"irc://a\" class=\"external text\" rel=\"nofollow\"><i>b</i></a>");}
	@Test  public void Xowa_protocol()	{
		String img = "<img src=\"file:///mem/xowa/bin/any/xowa/file/app.general/xowa_exec.png\"/>";
		fxt.Wiki().Sys_cfg().Xowa_proto_enabled_(true);
		fxt.Test_parse_page_wiki_str("[xowa-cmd:\"a\" z]"			, "<a href=\"xowa-cmd:a\">z" + img + "</a>");
		fxt.Test_parse_page_wiki_str("[xowa-cmd:\"a.b('c_d');\" z]"	, "<a href=\"xowa-cmd:a.b('c_d');\">z" + img + "</a>");
		fxt.Test_parse_page_wiki_str("[xowa-cmd:*\"a\"b*c\"* z]"		, "<a href=\"xowa-cmd:a%22b%2Ac\">z" + img + "</a>");
		fxt.Wiki().Sys_cfg().Xowa_proto_enabled_(false);
		fxt.Test_parse_page_wiki_str("[xowa-cmd:\"a\" b]"			, "[xowa-cmd:&quot;a&quot; b]");	// protocol is disabled: literalize String (i.e.: don't make it an anchor)
	}
}
