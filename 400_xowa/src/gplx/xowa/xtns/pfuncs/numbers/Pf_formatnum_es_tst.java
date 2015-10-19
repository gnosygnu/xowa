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
import org.junit.*; import gplx.xowa.langs.*;
import gplx.core.intls.*; import gplx.xowa.langs.numbers.*;
public class Pf_formatnum_es_tst {
	private Xop_fxt fxt;
	@Before public void init() {
		Xoae_app app = Xoa_app_fxt.app_();
		Xol_lang_itm lang = new Xol_lang_itm(app.Lang_mgr(), Bry_.new_a7("es")).Init_by_load_assert();
		Xowe_wiki wiki = Xoa_app_fxt.wiki_(app, "es.wikipedia.org", lang);
		fxt = new Xop_fxt(app, wiki);
	}
	@Test  public void Basic()	{
		fxt.Test_parse_tmpl_str_test("{{formatnum:1234.56}}"		, "{{test}}",	"1234.56");		// fmt.n;
		fxt.Test_parse_tmpl_str_test("{{formatnum:1234}}"			, "{{test}}",	"1234");		// fmt.n; decimal
		fxt.Test_parse_tmpl_str_test("{{formatnum:-1234.56}}"		, "{{test}}",	"-1234.56");	// fmt.n; neg
		fxt.Test_parse_tmpl_str_test("{{formatnum:12345.90}}"		, "{{test}}",	"12,345.90");	// fmt.y; 5
		fxt.Test_parse_tmpl_str_test("{{formatnum:123456.90}}"		, "{{test}}",	"123,456.90");	// fmt.y; 6
		fxt.Test_parse_tmpl_str_test("{{formatnum:1234.}}"			, "{{test}}",	"1,234.");		// stress; decimal at end
		fxt.Test_parse_tmpl_str_test("{{formatnum:123456a}}"		, "{{test}}",	"123,456a");	// stress; letters
	}
}
