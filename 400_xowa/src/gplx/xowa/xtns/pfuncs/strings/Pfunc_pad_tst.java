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
public class Pfunc_pad_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test   public void L_len_3()			{fxt.Test_parse_tmpl_str_test("{{padleft: a|4|0}}"				, "{{test}}"	, "000a");}
	@Test   public void L_str_ab()			{fxt.Test_parse_tmpl_str_test("{{padleft: a|4|01}}"				, "{{test}}"	, "010a");}
	@Test   public void L_len_neg1()		{fxt.Test_parse_tmpl_str_test("{{padleft: a|-1|01}}"			, "{{test}}"	, "a");}
	@Test   public void L_val_null()		{fxt.Test_parse_tmpl_str_test("{{padleft: |4|0}}"				, "{{test}}"	, "0000");}
	@Test   public void L_word_3()			{fxt.Test_parse_tmpl_str_test("{{padleft: abc|4}}"				, "{{test}}"	, "0abc");}
	@Test   public void L_word_3_u8()		{fxt.Test_parse_tmpl_str_test("{{padleft: niǎo|5}}"				, "{{test}}"	, "0niǎo");}	// PURPOSE:use length of String in chars, not bytes; PAGE:zh.d:不 DATE:2014-08-27
	@Test   public void L_exc_len_bad1()	{fxt.Test_parse_tmpl_str_test("{{padleft:a|bad|01}}"			, "{{test}}"	, "a");}
	@Test   public void L_exc_pad_ws()		{fxt.Test_parse_tmpl_str_test("{{padleft:a|4|\n \t}}"			, "{{test}}"	, "a");}
	@Test   public void R_len_3()			{fxt.Test_parse_tmpl_str_test("{{padright:a|4|0}}"				, "{{test}}"	, "a000");}
	@Test   public void R_str_ab()			{fxt.Test_parse_tmpl_str_test("{{padright:a|4|01}}"				, "{{test}}"	, "a010");}
	@Test   public void R_str_intl()		{fxt.Test_parse_tmpl_str_test("{{padright:|6|devanā}}"			, "{{test}}"	, "devanā");}
}
