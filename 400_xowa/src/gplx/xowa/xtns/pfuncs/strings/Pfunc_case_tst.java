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
package gplx.xowa.xtns.pfuncs.strings; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Pfunc_case_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test  public void Lc()					{fxt.Test_parse_tmpl_str_test("{{lc:ABC}}"					, "{{test}}", "abc");}
	@Test  public void Lc_first()			{fxt.Test_parse_tmpl_str_test("{{lcfirst:ABC}}"				, "{{test}}", "aBC");}
	@Test  public void Uc()					{fxt.Test_parse_tmpl_str_test("{{uc:abc}}"					, "{{test}}", "ABC");}
	@Test  public void Uc_first()			{fxt.Test_parse_tmpl_str_test("{{ucfirst:abc}}"				, "{{test}}", "Abc");}
	@Test  public void Multi_byte()			{// NOTE: separate test b/c will sometimes fail in suite
		fxt.Wiki().Lang().Case_mgr_u8_();
		fxt.Test_parse_tmpl_str_test("{{uc:ĉ}}"						, "{{test}}", "Ĉ");					// upper all
	}
	@Test  public void Multi_byte_asymmetric() {
		fxt.Wiki().Lang().Case_mgr_u8_();
		fxt.Test_parse_tmpl_str_test("{{uc:ⱥ}}"						, "{{test}}", "Ⱥ");					// handle multi-byte asymmetry (lc is 3 bytes; uc is 2 bytes)
	}
	@Test  public void Multi_byte_first() {
		fxt.Wiki().Lang().Case_mgr_u8_();
		fxt.Test_parse_tmpl_str_test("{{ucfirst:провинция}}"		, "{{test}}", "Провинция");			// upper first; DATE:2014-02-04
	}
}
