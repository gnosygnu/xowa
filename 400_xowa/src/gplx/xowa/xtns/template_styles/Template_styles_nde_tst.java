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
import org.junit.*; import gplx.core.tests.*;
public class Template_styles_nde_tst {
	private final    Template_styles_nde_fxt fxt = new Template_styles_nde_fxt();
	@Before public void init() {
		fxt.Reset();
	}
	@Test   public void Implicit_template() { // PURPOSE: default to template
		String css = fxt.Make__css_color("red");
		fxt.Init__page("Template:Test.css", css);
		fxt.Test__parse
		( "<templatestyles src='Test.css'/>"
		, ""
		, fxt.Make__style(0, css)
		);
	}
	@Test  public void Force_main() { // PURPOSE: ":" forces main
		String css = fxt.Make__css_color("red");
		fxt.Init__page("Test.css", css);
		fxt.Test__parse
		( "<templatestyles src=':Test.css'/>"
		, ""
		, fxt.Make__style(0, css)
		);
	}
	@Test  public void Explicit() { // PURPOSE: explicit ns
		String css = fxt.Make__css_color("red");
		fxt.Init__page("Module:Test.css", css);
		fxt.Test__parse
		( "<templatestyles src='Module:Test.css'/>"
		, ""
		, fxt.Make__style(0, css)
		);
	}
	@Test  public void Multiple() { // PURPOSE: multiple calls to diff page should output diff styles; DATE:2018-12-30
		String css_red = fxt.Make__css_color("red");
		String css_blue = fxt.Make__css_color("blue");
		fxt.Init__page("Module:Test1.css", css_red);
		fxt.Init__page("Module:Test2.css", css_blue);
		fxt.Test__parse
		( String_.Concat_lines_nl
		(   "<templatestyles src='Module:Test1.css'/>"
		,   "<templatestyles src='Module:Test2.css'/>"
		)
		, ""
		, fxt.Make__style(0, css_red)
		+ fxt.Make__style(1, css_blue)			
		);
	}
	@Test  public void Dedupe() { // PURPOSE: multiple calls to same page should output link; DATE:2018-12-30
		String css = fxt.Make__css_color("red");
		fxt.Init__page("Module:Test.css", css);
		fxt.Test__parse
		( String_.Concat_lines_nl
		(   "<templatestyles src='Module:Test.css'/>"
		,   "<templatestyles src='Module:Test.css'/>"
		)
		, ""
		, String_.Concat_lines_nl
		(   fxt.Make__style(0, css))
		);
	}
	@Test  public void Tag() { // PURPOSE: {{#tag}}
		String css = fxt.Make__css_color("red");
		fxt.Init__page("Module:A/Test.css", css);
		fxt.Test__parse
		( "{{#tag:templatestyles||src='Module:A/Test.css'}}"
		, ""
		, fxt.Make__style(0, css)
		);
	}
	@Test  public void Error__invalid_title() {
		fxt.Test__parse
		( "<templatestyles src='A|b.css'/>"
		, "<strong class=\"error\">Invalid title for TemplateStyles src attribute.</strong>"
		, ""
		);
	}
	@Test  public void Error__missing_page() {
		fxt.Test__parse
		( "<templatestyles src='Missing.css'/>"
		, "<strong class=\"error\">Page Missing.css has no content.</strong>"
		, ""
		);
	}
	@Test  public void Error__missing_src() {
		fxt.Test__parse
		( "<templatestyles src{{=}}'Missing.css'/>"// PAGE:en.w:Switzerland; ISSUE#:416; DATE:2019-03-31
		, "<strong class=\"error\">Invalid title for TemplateStyles src attribute.</strong>"
		, ""
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
	public String Make__css_color(String color) {
		return ".style0{color:" + color + ";}";
	}
	public String Make__style(int id, String css) {
		return "\n/*TemplateStyles:r" + id + "*/\n" + css;
	}
	public void Test__parse(String src, String expd_html, String expd_head) {
		parser_fxt.Test__parse__tmpl_to_html(src, expd_html);
		Gftest.Eq__ary__lines(expd_head, parser_fxt.Page().Html_data().Head_mgr().Itm__css_dynamic().Get_and_clear());
	}
}
