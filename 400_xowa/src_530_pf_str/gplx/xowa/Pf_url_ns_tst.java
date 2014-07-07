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
public class Pf_url_ns_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test  public void Ns_0()				{fxt.Test_parse_tmpl_str_test("{{ns:0}}"						, "{{test}}", "");}
	@Test  public void Ns_10()				{fxt.Test_parse_tmpl_str_test("{{ns:10}}"					, "{{test}}", "Template");}
	@Test  public void Ns_11()				{fxt.Test_parse_tmpl_str_test("{{ns:11}}"					, "{{test}}", "Template talk");}
	@Test  public void Ns_11_ws()			{fxt.Test_parse_tmpl_str_test("{{ns: 11 }}"					, "{{test}}", "Template talk");}
	@Test  public void Ns_Template()		{fxt.Test_parse_tmpl_str_test("{{ns:Template}}"				, "{{test}}", "Template");}
	@Test  public void Ns_invalid()			{fxt.Test_parse_tmpl_str_test("{{ns:252}}"					, "{{test}}", "");}
	@Test  public void Nse_10()				{fxt.Test_parse_tmpl_str_test("{{nse:10}}"					, "{{test}}", "Template");}
	@Test  public void Nse_11()				{fxt.Test_parse_tmpl_str_test("{{nse:11}}"					, "{{test}}", "Template_talk");}
	@Test  public void Ns_Image()			{fxt.Test_parse_tmpl_str_test("{{ns:Image}}"					, "{{test}}", "File");}
	@Test  public void Ns_Templatex()		{fxt.Test_parse_tmpl_str_test("{{ns:Templatex}}"				, "{{test}}", "");}
	@Test  public void Ns_Talk() {	// PURPOSE: non-English wikis may have parameterized Project Talk ($1 talk); swap out with ns:4; REF.MW: Language.php!fixVariableInNamespace
		Xow_ns_mgr ns_mgr = new Xow_ns_mgr(gplx.xowa.langs.cases.Xol_case_mgr_.Ascii());
		ns_mgr.Add_new(4, "wiki").Add_new(5, "$1 talk").Add_new(10, "Template").Init();
		fxt.Wiki().Ns_mgr_(ns_mgr);
		fxt.Test_parse_tmpl_str_test("{{ns:5}}"					, "{{test}}", "wiki talk");
	}
}
