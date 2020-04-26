/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.template_styles;

import gplx.String_;
import gplx.core.tests.Gftest;
import gplx.xowa.Xop_fxt;
import org.junit.Before;
import org.junit.Test;
public class Template_styles_nde_tst {
	private final    Template_styles_nde_fxt fxt = new Template_styles_nde_fxt();
	@Before public void init() {
		fxt.Reset();
	}
	@Test  public void Implicit_template() { // PURPOSE: default to template
		String css = fxt.Make_css_color("red");
		fxt.Init_page("Template:Test.css", css);
		fxt.Test_parse
		( "<templatestyles src='Test.css'/>"
		, ""
		, fxt.Make_style(0, css)
		);
	}
	@Test public void Force_main() { // PURPOSE: ":" forces main
		String css = fxt.Make_css_color("red");
		fxt.Init_page("Test.css", css);
		fxt.Test_parse
		( "<templatestyles src=':Test.css'/>"
		, ""
		, fxt.Make_style(0, css)
		);
	}
	@Test public void Explicit() { // PURPOSE: explicit ns
		String css = fxt.Make_css_color("red");
		fxt.Init_page("Module:Test.css", css);
		fxt.Test_parse
		( "<templatestyles src='Module:Test.css'/>"
		, ""
		, fxt.Make_style(0, css)
		);
	}
	@Test public void Multiple() { // PURPOSE: multiple calls to diff page should output diff styles; DATE:2018-12-30
		String css_red = fxt.Make_css_color("red");
		String css_blue = fxt.Make_css_color("blue");
		fxt.Init_page("Module:Test1.css", css_red);
		fxt.Init_page("Module:Test2.css", css_blue);
		fxt.Test_parse
		( String_.Concat_lines_nl
		(   "<templatestyles src='Module:Test1.css'/>"
		,   "<templatestyles src='Module:Test2.css'/>"
		)
		, ""
		, fxt.Make_style(0, css_red)
		+ fxt.Make_style(1, css_blue)
		);
	}
	@Test public void Dedupe() { // PURPOSE: multiple calls to same page should output link; DATE:2018-12-30
		String css = fxt.Make_css_color("red");
		fxt.Init_page("Module:Test.css", css);
		fxt.Test_parse
		( String_.Concat_lines_nl
		(  "<templatestyles src='Module:Test.css'/>"
		, "<templatestyles src='Module:Test.css'/>"
		)
		, ""
		, String_.Concat_lines_nl
		( fxt.Make_style(0, css))
		);
	}
	@Test public void Tag() { // PURPOSE: {{#tag}}
		String css = fxt.Make_css_color("red");
		fxt.Init_page("Module:A/Test.css", css);
		fxt.Test_parse
		( "{{#tag:templatestyles||src='Module:A/Test.css'}}"
		, ""
		, fxt.Make_style(0, css)
		);
	}
	@Test  public void Fix_single_word_paragraph() { // PURPOSE:make sure block is closed, else single-word paragraphs; ISSUE#:616; DATE:2019-11-18
		String css = fxt.Make_css_color("red");
		fxt.Init_page("Template:Test.css", css);
		fxt.Parser_fxt().Init_para_y_();
		fxt.Test_parse
		( String_.Concat_lines_nl_skip_last("<templatestyles src='Test.css'/>", "a")
		, String_.Concat_lines_nl_skip_last("", "<p>a", "</p>")
		, fxt.Make_style(0, css)
		);
		fxt.Parser_fxt().Init_para_n_();
	}
	@Test public void Error__invalid_title() {
		fxt.Test_parse
		( "<templatestyles src='A|b.css'/>"
		, "<strong class=\"error\">Invalid title for TemplateStyles src attribute.</strong>"
		, ""
		);
	}
	@Test public void Error__missing_page() {
		fxt.Test_parse
		( "<templatestyles src='Missing.css'/>"
		, "<strong class=\"error\">Page Missing.css has no content.</strong>"
		, ""
		);
	}
	@Test public void Error__missing_src() {
		fxt.Test_parse
		( "<templatestyles src{{=}}'Missing.css'/>"// PAGE:en.w:Switzerland; ISSUE#:416; DATE:2019-03-31
		, "<strong class=\"error\">Invalid title for TemplateStyles src attribute.</strong>"
		, ""
		);
	}
	@Test public void Wrapper() {
		String css = fxt.Make_css_color("red");
		fxt.Init_page("Template:Test.css", css);
		fxt.Test_parse
		( "<templatestyles src='Test.css' wrapper='.wrapper_class' />"
		, ""
		, fxt.Make_style(0, ".wrapper_class", css)
		);
	}
	@Test public void Url() {
		fxt.Init_page("Template:Test.css", fxt.Make_css_url("//upload.wikimedia.org/A.png"));
		fxt.Test_parse
			( "<templatestyles src='Test.css' />"
			, ""
			, fxt.Make_style(0, fxt.Make_css_url("//www.xowa.org/xowa/fsys/bin/any/xowa/upload.wikimedia.org/A.png"))
			);
	}
	@Test public void Minify() {
		fxt.Init_page("Template:Test.css", fxt.Make_css_color("rgb (128,128,128)"));
		fxt.Test_parse
			( "<templatestyles src='Test.css' />"
			, ""
			, fxt.Make_style(0, fxt.Make_css_color("#808080"))
			);
	}
}
class Template_styles_nde_fxt {
	private final    Xop_fxt parser_fxt = new Xop_fxt();
	public Xop_fxt Parser_fxt() {return parser_fxt;}
	public void Reset() {
		parser_fxt.Reset();
		parser_fxt.Wiki().Xtn_mgr().Init_by_wiki(parser_fxt.Wiki());
	}
	public void Init_page(String page, String text) {
		parser_fxt.Init_page_create(page, text);
	}
	public String Make_css_color(String color) {
		return ".style0{color:" + color + "}";
	}
	public String Make_css_url(String url) {
		return ".style1{url(" + url + "}";
	}
	public String Make_style(int id, String css) {return Make_style(id, null, css);}
	public String Make_style(int id, String wrapper, String css) {
		wrapper = wrapper == null ? "" : wrapper + " ";
		return "\n/*TemplateStyles:r" + id + "*/\n" + ".mw-parser-output " + wrapper + css; // .mw-parser-output needs to be added to all TemplateStyles CSS, else TS ids called "portal" will affect sidebar; ISSUE#:426; PAGE:en.w:Poland DATE:2020-04-10
	}
	public void Test_parse(String src, String expd_html, String expd_head) {
		parser_fxt.Test__parse__tmpl_to_html(src, expd_html);
		Gftest.Eq__ary__lines(expd_head, parser_fxt.Page().Html_data().Head_mgr().Itm__css_dynamic().Get_and_clear());
	}
}
