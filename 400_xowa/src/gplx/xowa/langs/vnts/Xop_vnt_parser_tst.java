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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Xop_vnt_parser_tst {	// uses zh-hant as cur_vnt
	private Xop_vnt_parser_fxt fxt = new Xop_vnt_parser_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Literal()			{fxt.Test_parse("-{A}-", "A");}
	@Test  public void Bidi()				{fxt.Test_parse("-{zh-hans:A;zh-hant:B}-", "B");}
	@Test  public void Empty()				{fxt.Test_parse("a-{}-b", "ab");}
	@Test  public void Unknown_empty()		{fxt.Test_parse("a-{|}-c", "ac");}
	@Test  public void Unknown_text()		{fxt.Test_parse("a-{|b}-c", "abc");}
	@Test  public void Unknown_flag()		{fxt.Test_parse("a-{x|b}-c", "abc");}
	@Test  public void Lang_y()				{fxt.Test_parse("-{zh-hant|A}-", "A");}
	@Test  public void Lang_n()				{fxt.Test_parse("-{zh-hans|A}-", "");}
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
}
class Xop_vnt_parser_fxt {
	public Xop_fxt Parser_fxt() {return fxt;} private Xop_fxt fxt;
	public Xop_vnt_parser_fxt Clear() {
		Xoae_app app = Xoa_app_fxt.app_();
		Xowe_wiki wiki = Xoa_app_fxt.wiki_(app, "zh.wikipedia.org");
		fxt = new Xop_fxt(app, wiki);
		Init_vnt_mgr(wiki.Lang().Vnt_mgr(), "zh-hans", "zh-hant");
		Xop_vnt_lxr_.set_(wiki);
		wiki.Lang().Vnt_mgr().Cur_vnt_(Bry_.new_a7("zh-hant"));
		return this;
	}
	private static void Init_vnt_mgr(Xol_vnt_mgr vnt_mgr, String... vnts_str) {
		byte[][] vnts_bry = Bry_.Ary(vnts_str);
		int vnts_bry_len = vnts_bry.length;
		for (int i = 0; i < vnts_bry_len; i++)
			vnt_mgr.Get_or_new(vnts_bry[i]);
		vnt_mgr.Convert_ttl_init();
	}
	public Xop_vnt_parser_fxt Test_parse(String raw, String expd) {
		fxt.Test_parse_page_all_str(raw, expd);
		return this;
	}
}
