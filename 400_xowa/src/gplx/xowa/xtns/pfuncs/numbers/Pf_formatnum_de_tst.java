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
