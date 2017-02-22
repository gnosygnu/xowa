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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_ns_tst {		
	@Before public void init()				{fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Ns_0()				{fxt.Test_parse_tmpl_str_test("{{ns:0}}"					, "{{test}}", "");}
	@Test  public void Ns_10()				{fxt.Test_parse_tmpl_str_test("{{ns:10}}"					, "{{test}}", "Template");}
	@Test  public void Ns_11()				{fxt.Test_parse_tmpl_str_test("{{ns:11}}"					, "{{test}}", "Template talk");}
	@Test  public void Ns_11_ws()			{fxt.Test_parse_tmpl_str_test("{{ns: 11 }}"					, "{{test}}", "Template talk");}
	@Test  public void Ns_Template()		{fxt.Test_parse_tmpl_str_test("{{ns:Template}}"				, "{{test}}", "Template");}
	@Test  public void Ns_invalid()			{fxt.Test_parse_tmpl_str_test("{{ns:252}}"					, "{{test}}", "");}
	@Test  public void Nse_10()				{fxt.Test_parse_tmpl_str_test("{{nse:10}}"					, "{{test}}", "Template");}
	@Test  public void Nse_11()				{fxt.Test_parse_tmpl_str_test("{{nse:11}}"					, "{{test}}", "Template_talk");}
	@Test  public void Ns_Image()			{fxt.Test_parse_tmpl_str_test("{{ns:Image}}"				, "{{test}}", "File");}
	@Test  public void Ns_Templatex()		{fxt.Test_parse_tmpl_str_test("{{ns:Templatex}}"			, "{{test}}", "");}
	@Test  public void Ns_Talk() {	// PURPOSE: non-English wikis may have parameterized Project Talk ($1 talk); swap out with ns:4; REF.MW: Language.php!fixVariableInNamespace
		fxt.Wiki().Ns_mgr().Clear().Add_new(4, "wiki").Add_new(5, "$1 talk").Add_new(10, "Template").Init();
		fxt.Test_parse_tmpl_str_test("{{ns:5}}"					, "{{test}}", "wiki talk");
	}
	@Test  public void Ns_neg1()			{fxt.Test_parse_tmpl_str_test("{{ns:-1}}"					, "{{test}}", "Special");}	// PURPOSE.FIX:handle special; DATE:2015-05-18
}
