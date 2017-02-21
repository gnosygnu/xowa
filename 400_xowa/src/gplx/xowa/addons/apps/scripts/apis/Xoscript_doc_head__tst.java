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
package gplx.xowa.addons.apps.scripts.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.scripts.*;
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
		Xoscript_env env = new Xoscript_env(new gplx.core.scripts.Gfo_script_engine__noop(), Io_url_.new_any_("mem/wiki/test_wiki/bin/script/"));
		Xoscript_url url = new Xoscript_url("test_wiki", "test_page");
		spg = new Xoscript_page(rv, env, url);
	}
	public void Init__sect(String sect_name) {
		if		(String_.Eq(sect_name, "head"))
			sect = spg.doc().head();
		else if (String_.Eq(sect_name, "tail"))
			sect = spg.doc().tail();
	}
	public void Exec__doc__html(String html) {spg.doc().html(html);}
	public void Exec__reg_marker(String marker, String... pos_ary) {sect.reg_marker(marker, pos_ary);}
	public void Exec__add_js_file(String pos, String file) {sect.add_js_file(pos, file);}
	public void Exec__add_html(String html)				{sect.add_html(html);}
	public void Exec__add_html(String pos, String html) {sect.add_html(pos, html);}
	public void Exec__add_tag(String pos, String tag, String body, Object... head_atrs) {sect.add_tag(pos, tag, body, head_atrs);}
	public void Test__html(String... expd) {
		Gftest.Eq__ary__lines(String_.Concat_lines_nl_skip_last(expd), spg.doc().html(), "html");
	}
}
