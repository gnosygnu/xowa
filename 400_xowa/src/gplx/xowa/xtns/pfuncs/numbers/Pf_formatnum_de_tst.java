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
package gplx.xowa.xtns.pfuncs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
import gplx.core.intls.*; import gplx.xowa.langs.numbers.*;
public class Pf_formatnum_de_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {
		fxt.Reset();
		fxt.Init_lang_numbers_separators(".", ",");
	}
	@After public void term() {
		fxt.Init_lang_numbers_separators_en();
	}
	@Test   public void Fmt__plain()		{fxt.Test_parse_tmpl_str_test("{{formatnum:1234,56}}"					, "{{test}}"	, "1.234.56");}	// NOTE: double "." looks strange, but matches MW; DATE:2013-10-24
	@Test   public void Fmt__grp_dlm()		{fxt.Test_parse_tmpl_str_test("{{formatnum:1.234,56}}"					, "{{test}}"	, "1,234.56");}
	@Test   public void Fmt__dec_dlm()		{fxt.Test_parse_tmpl_str_test("{{formatnum:1234.56}}"					, "{{test}}"	, "1.234,56");} // NOTE: "." should be treated as decimal separator, but replaced with ","; DATE:2013-10-21
	@Test   public void Raw__grp_dlm()		{fxt.Test_parse_tmpl_str_test("{{formatnum:1.234,56|R}}"				, "{{test}}"	, "1234.56");}
	@Test   public void Raw__plain()		{fxt.Test_parse_tmpl_str_test("{{formatnum:1234,56|R}}"					, "{{test}}"	, "1234.56");}
	@Test   public void Raw__dec_dlm()		{fxt.Test_parse_tmpl_str_test("{{formatnum:12,34|R}}"					, "{{test}}"	, "12.34");}	// NOTE: dec_dlm is always ".
	@Test   public void Nosep__plain()		{fxt.Test_parse_tmpl_str_test("{{formatnum:1234,56|NOSEP}}"				, "{{test}}"	, "1234,56");}
	@Test   public void Nosep__grp_dlm()	{fxt.Test_parse_tmpl_str_test("{{formatnum:1.234,56|NOSEP}}"			, "{{test}}"	, "1.234,56");}
}
