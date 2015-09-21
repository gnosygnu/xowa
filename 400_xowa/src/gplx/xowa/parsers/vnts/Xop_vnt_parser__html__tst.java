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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_vnt_parser__html__tst {	// NOTE: cur_vnt is zh-cn
	private final Xop_vnt_parser_fxt fxt = new Xop_vnt_parser_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Literal()			{fxt.Test_parse("-{A}-", "A");}
	@Test  public void Bidi_fwd()			{fxt.Test_parse("-{zh-cn:B;zh-hans:A}-", "B");}
	@Test  public void Bidi_bwd()			{fxt.Test_parse("-{zh-hans:A;zh-cn:B}-", "B");}
	@Test  public void Empty()				{fxt.Test_parse("a-{}-b", "ab");}
	@Test  public void Unknown_empty()		{fxt.Test_parse("a-{|}-c", "ac");}
	@Test  public void Unknown_text()		{fxt.Test_parse("a-{|b}-c", "abc");}
	@Test  public void Unknown_flag()		{fxt.Test_parse("a-{x|b}-c", "abc");}
	@Test  public void Lang_y()				{fxt.Test_parse("-{zh-hant|A}-", "A");}
	@Test  public void Lang_n()				{fxt.Test_parse("-{zh-sg|A}-", "");}
	@Test  public void Raw()				{fxt.Test_parse("-{R|zh-hans:A;}-", "zh-hans:A;");}
//		@Test  public void Descrip()			{fxt.Test_parse("-{D|zh-hans:A;}-", "zh-hans:A");}
	@Test  public void Tmpl() {
		fxt.Parser_fxt().Init_page_create("Template:A", "B");
		fxt.Test_parse("-{{{A}}}-", "B");
	}
	@Test  public void Tmpl_arg_4() {	// PURPOSE: handle "-{" + "{{{"
		fxt.Parser_fxt().Init_page_create("Template:A", "-{{{{1}}}}-");
		fxt.Test_parse("{{A|B}}", "B");	//  -{ {{{1}}} }- -> -{B}- -> B
	}
	@Test  public void Tmpl_arg_3() {	// PURPOSE: handle "-" + "{{{"; PAGE:sr.w:ДНК; EX:<span id="interwiki-{{{1}}}-fa"></span> DATE:2014-07-03
		fxt.Parser_fxt().Init_page_create("Template:A", "-{{{1}}}-");
		fxt.Test_parse("{{A|B}}", "-B-");
	}
	@Test  public void Parser_function() {
		fxt.Test_parse("-{{{#expr:1}}}-", "1");
	}
	@Test  public void Ignore() {
		fxt.Test_parse("-{{#expr:1}}-", "-1-");
	}
	@Test  public void Expr() {
		fxt.Parser_fxt().Init_page_create("Template:A", "{{#expr: 0-{{{1|2}}}}}");
		fxt.Test_parse("{{A}}", "-2");
	}
	@Test  public void Invalid() {	// PURPOSE: invalid flags should cause vnt to render text only; DATE:2014-04-10
		fxt.Test_parse("-{:a|b}-", "b");
	}
	@Test  public void Macro_ignore() {	// PURPOSE: ignore macro (implement later); EX:zh.v:西安; Template:pagebanner; DATE:2014-05-03
		fxt.Test_parse("-{H|zh-cn:亚琛; zh-tw:阿亨;}-", "");
	}
	@Test  public void Title() {	// PURPOSE: implement title; PAGE:zh.w:Help:進階字詞轉換處理 DATE:2014-08-29
		fxt.Test_parse("-{T|zh-hant:A;zh-hans:B}-", "");
		Tfds.Eq("A", String_.new_u8(fxt.Parser_fxt().Page().Html_data().Display_ttl_vnt()));
	}
//		@Test  public void Disabled() {
//			Xop_fxt fxt = new Xop_fxt();
//			fxt.Wiki().Vnt_mgr().Set(null, null);
//			fxt.Test_parse_page_all_str("a-{b}-c", "a-{b}-c");
//		}
//		@Test  public void Enabled() {
//			Xoae_app app = Xoa_app_fxt.app_();
//			Xol_lang lang = new Xol_lang(app, Bry_.new_a7("zh"));
//			Xowe_wiki wiki = Xoa_app_fxt.wiki_(app, "zh.wikipedia.org", lang);
//			Xop_fxt fxt = new Xop_fxt(app, wiki);
//			fxt.Test_parse_page_all_str("a-{b}-c", "ac");
//			fxt.Wiki().Vnt_mgr().Set(null, null);	// set it back to null for other tests
//		}
}
