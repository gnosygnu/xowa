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
import gplx.core.intls.*;
public class Pf_formatnum_en_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test   public void Len_4()				{fxt.Test_parse_tmpl_str_test("{{formatnum:1234}}"						, "{{test}}"	, "1,234");}
	@Test   public void Len_7()				{fxt.Test_parse_tmpl_str_test("{{formatnum:1234567}}"					, "{{test}}"	, "1,234,567");}
	@Test   public void Len_2()				{fxt.Test_parse_tmpl_str_test("{{formatnum:12}}"						, "{{test}}"	, "12");}
	@Test   public void Len_10()			{fxt.Test_parse_tmpl_str_test("{{formatnum:1234567890}}"				, "{{test}}"	, "1,234,567,890");}
	@Test   public void Neg()				{fxt.Test_parse_tmpl_str_test("{{formatnum:-1234}}"						, "{{test}}"	, "-1,234");}
	@Test   public void Decimal()			{fxt.Test_parse_tmpl_str_test("{{formatnum:1234.5678}}"					, "{{test}}"	, "1,234.5678");}
	@Test   public void Mixed()				{fxt.Test_parse_tmpl_str_test("{{formatnum:1234abc5678}}"				, "{{test}}"	, "1,234abc5,678");}
	@Test   public void Zeros()				{fxt.Test_parse_tmpl_str_test("{{formatnum:0000000}}"					, "{{test}}"	, "0,000,000");}
	@Test   public void Raw__grp_dlm()		{fxt.Test_parse_tmpl_str_test("{{formatnum:1,234.56|R}}"				, "{{test}}"	, "1234.56");}
	@Test   public void Raw__plain()		{fxt.Test_parse_tmpl_str_test("{{formatnum:1234.56|R}}"					, "{{test}}"	, "1234.56");}
	@Test   public void Nosep__plain()		{fxt.Test_parse_tmpl_str_test("{{formatnum:1234.56|NOSEP}}"				, "{{test}}"	, "1234.56");}
	@Test   public void Nosep__grp_dlm()	{fxt.Test_parse_tmpl_str_test("{{formatnum:1,234.56|NOSEP}}"			, "{{test}}"	, "1,234.56");}
	@Test   public void Cs()				{fxt.Test_parse_tmpl_str_test("{{FORMATNUM:1234}}"						, "{{test}}"	, "1,234");}
	@Test   public void Exc_ws()	{ // PURPOSE: EX: {{rnd|122835.3|(-3)}}
		fxt.Test_parse_tmpl_str_test(String_.Concat_lines_nl_skip_last
			(	"{{formatnum:"
			,	"   {{#expr:2}}"
			,	"}}"
			)
			,	"{{test}}"
			,	"2"
			);
	}
}
