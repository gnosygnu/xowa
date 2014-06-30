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
package gplx.xowa; import gplx.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Pf_str_case_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test  public void Lc()					{fxt.Test_parse_tmpl_str_test("{{lc:ABC}}"					, "{{test}}", "abc");}
	@Test  public void Lc_first()			{fxt.Test_parse_tmpl_str_test("{{lcfirst:ABC}}"				, "{{test}}", "aBC");}
	@Test  public void Uc()					{fxt.Test_parse_tmpl_str_test("{{uc:abc}}"					, "{{test}}", "ABC");}
	@Test  public void Uc_first()			{fxt.Test_parse_tmpl_str_test("{{ucfirst:abc}}"				, "{{test}}", "Abc");}
	@Test  public void Multi_byte()			{// NOTE: separate test b/c will sometimes fail in suite
		Xol_lang lang = fxt.Wiki().Lang();
		lang.Case_mgr().Clear();
		lang.Case_mgr().Add_bulk(Xol_case_itm_.Universal);
		fxt.Test_parse_tmpl_str_test("{{uc:ĉ}}"						, "{{test}}", "Ĉ");					// upper all
	}
	@Test  public void Multi_byte_asymmetric() {
		Xol_lang lang = fxt.Wiki().Lang();
		lang.Case_mgr().Clear();
		lang.Case_mgr().Add_bulk(Xol_case_itm_.Universal);
		fxt.Test_parse_tmpl_str_test("{{uc:ⱥ}}"						, "{{test}}", "Ⱥ");					// handle multi-byte asymmetry (lc is 3 bytes; uc is 2 bytes)
	}
	@Test  public void Multi_byte_first()			{
		Xol_lang lang = fxt.Wiki().Lang();
		lang.Case_mgr().Clear();
		lang.Case_mgr().Add_bulk(Xol_case_itm_.Universal);
		fxt.Test_parse_tmpl_str_test("{{ucfirst:провинция}}"			, "{{test}}", "Провинция");			// upper first; DATE:2014-02-04
	}
}
