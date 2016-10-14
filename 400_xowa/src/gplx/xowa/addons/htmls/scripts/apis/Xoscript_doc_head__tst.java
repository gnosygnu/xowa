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
package gplx.xowa.addons.htmls.scripts.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*; import gplx.xowa.addons.htmls.scripts.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoscript_doc_head__tst {
	private final    Xoscript_doc_head__fxt fxt = new Xoscript_doc_head__fxt();
	@Before public void init() {
		fxt.Init__sect("head");
		fxt.Exec__reg_marker("<!--top-->", "top", Xoscript_doc_head.Pos__default);
		fxt.Exec__reg_marker("<!--bot-->", "bot");
		fxt.Exec__doc__html("a<!--top-->b<!--bot-->c");
	}
	@Test  public void Add_html() {
		fxt.Exec__add_html("top", "<b>add_1</b>");
		fxt.Exec__add_html("top", "<b>add_2</b>");
		fxt.Test__html("a<b>add_1</b><b>add_2</b><!--top-->b<!--bot-->c");
	}
	@Test  public void Add_html__default() {
		fxt.Exec__add_html("<b>add_1</b>");
		fxt.Test__html("a<b>add_1</b><!--top-->b<!--bot-->c");
	}
	@Test  public void Add_tag() {
		fxt.Exec__add_tag("top", "div", "div_body", "k0", "v0", "k1", "v1");
		fxt.Test__html
		( "a<div k0=\"v0\" k1=\"v1\">div_body</div>"
		, "<!--top-->b<!--bot-->c");
	}
	@Test  public void Add_js_file() {
		fxt.Exec__add_js_file("top", "./a.js");
		fxt.Test__html
		( "a<script src=\"file:///mem/wiki/test_wiki/bin/script/a.js\" type=\"text/javascript\"></script>"
		, "<!--top-->b<!--bot-->c");
	}
}
class Xoscript_doc_head__fxt {
	private final    Xoscript_page spg;
	private Xoscript_doc_sect_base sect;
	public Xoscript_doc_head__fxt() {
		Bry_bfr rv = Bry_bfr_.New();
		Xoscript_env env = new Xoscript_env(Io_url_.new_any_("mem/wiki/test_wiki/bin/script/"));
		Xoscript_url url = new Xoscript_url("test_wiki", "test_page");
		spg = new Xoscript_page(rv, env, url);
	}
	public void Init__sect(String sect_name) {
		if		(String_.Eq(sect_name, "head"))
			sect = spg.Doc().Head();
		else if (String_.Eq(sect_name, "tail"))
			sect = spg.Doc().Tail();
	}
	public void Exec__doc__html(String html) {spg.Doc().Html_(html);}
	public void Exec__reg_marker(String marker, String... pos_ary) {sect.Reg_marker(marker, pos_ary);}
	public void Exec__add_js_file(String pos, String file) {sect.Add_js_file(pos, file);}
	public void Exec__add_html(String html)				{sect.Add_html(html);}
	public void Exec__add_html(String pos, String html) {sect.Add_html(pos, html);}
	public void Exec__add_tag(String pos, String tag, String body, String... head_atrs) {sect.Add_tag(pos, tag, body, head_atrs);}
	public void Test__html(String... expd) {
		Gftest.Eq__ary__lines(String_.Concat_lines_nl_skip_last(expd), spg.Doc().Html(), "html");
	}
}
