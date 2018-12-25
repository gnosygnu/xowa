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
package gplx.xowa.xtns.template_styles; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Template_styles_nde_tst {
	private final    Template_styles_nde_fxt fxt = new Template_styles_nde_fxt();
	@Before public void init() {
		fxt.Reset();
	}
	private static final String Css_red = ".red{color:red;}";
	private static final String Style_red = "<style>" + Css_red + "</style>";
	@Test   public void Implicit_template() { // PURPOSE: default to template
		fxt.Init__page("Template:Test.css", Css_red);
		fxt.Test__parse
		( "<templatestyles src='Test.css'/>"
		, Style_red
		);
	}
	@Test  public void Force_main() { // PURPOSE: ":" forces main
		fxt.Init__page("Test.css", Css_red);
		fxt.Test__parse
		( "<templatestyles src=':Test.css'/>"
		, Style_red
		);
	}
	@Test  public void Explicit() { // PURPOSE: explicit ns
		fxt.Init__page("Module:Test.css", Css_red);
		fxt.Test__parse
		( "<templatestyles src='Module:Test.css'/>"
		, Style_red
		);
	}
	@Test  public void Tag() { // PURPOSE: {{#tag}}
		fxt.Init__page("Module:A/Test.css", Css_red);
		fxt.Test__parse
		( "{{#tag:templatestyles||src='Module:A/Test.css'}}"
		, Style_red
		);
	}
	@Test  public void Error__invalid_title() {
		fxt.Test__parse
		( "<templatestyles src='A|b.css'/>"
		, "<strong class=\"error\">Invalid title for TemplateStyles src attribute.</strong>"
		);
	}
	@Test  public void Error__missing_page() {
		fxt.Test__parse
		( "<templatestyles src='Missing.css'/>"
		, "<strong class=\"error\">Page Missing.css has no content.</strong>"
		);
	}
}
class Template_styles_nde_fxt {
	private final    Xop_fxt parser_fxt = new Xop_fxt();
	public void Reset() {
		parser_fxt.Reset();
		parser_fxt.Wiki().Xtn_mgr().Init_by_wiki(parser_fxt.Wiki());
	}
	public void Init__page(String page, String text) {
		parser_fxt.Init_page_create(page, text);
	}
	public void Test__parse(String src, String expd) {
		parser_fxt.Test__parse__tmpl_to_html(src, expd);
	}
}
