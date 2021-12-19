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
package gplx.xowa.xtns.scribunto.libs;
import gplx.frameworks.tests.GfoTstr;
import org.junit.*; import gplx.langs.regxs.*;
public class Scrib_regx_converter_tst {
	@Before public void init() {fxt.Clear();} private Scrib_regx_converter_fxt fxt = new Scrib_regx_converter_fxt();
	@Test public void Basic()				{fxt.Test_parse("abc012ABC"				, "abc012ABC");}
	@Test public void Pow_0()				{fxt.Test_parse("^a"					, "\\Ga");}
	@Test public void Pow_1()				{fxt.Test_parse("a^b"					, "a\\^b");}
	@Test public void Dollar_n()			{fxt.Test_parse("$a"					, "\\$a");}
	@Test public void Dollar_last()		{fxt.Test_parse("a$"					, "a$");}
	@Test public void Dot()				{fxt.Test_parse("a."					, "a.");}
//		@Test public void Paren()				{fxt.Test_parse("(a)"					, "(?<m1>a)");}
	@Test public void Percent_has()		{fxt.Test_parse("%a"					, "\\p{L}");}
	@Test public void Percent_na()		{fxt.Test_parse("%y"					, "y");}
	@Test public void Percent_b00()		{fxt.Test_parse("%b00"					, "{0}[^0]*0");}
	@Test public void Percent_b01()		{fxt.Test_parse("%b01"					, "(?=\\0)(?:(?=.*?\\0(?!.*?\\1)(.*\\1(?!.*\\2).*))(?=.*?\\1(?!.*?\\2)(.*)).)+?.*?(?=\\1)[^\\0]*(?=\\2$)");}
//		@Test public void Percent_num()		{fxt.Test_parse("()%1"					, "(?<m1>)\\g{m1}");}
	@Test public void Percent_text()		{fxt.Test_parse("%e"					, "e");}
	@Test public void Brack_pow()			{fxt.Test_parse("[^a]"					, "[^a]");}
	@Test public void Brack_percent_a()	{fxt.Test_parse("[%a]"					, "[\\p{L}]");}	// NOTE: was previously [a]; DATE:2013-11-08
	@Test public void Brack_dash()		{fxt.Test_parse("[a-z]"					, "[a-z]");}
	@Test public void Brack_num()			{fxt.Test_parse("[%d]"					, "[\\p{Nd}]");}
	@Test public void Brack_text()		{fxt.Test_parse("[abc]"					, "[abc]");}
	@Test public void Null()				{fxt.Test_parse("[%z]"					, "[\\x00]");}
	@Test public void Null_not()			{fxt.Test_parse("%Z"					, "[^\\x00]");}
	@Test public void Backslash()			{fxt.Test_parse("\\"					, "\\\\");}		// PURPOSE: make sure \ is preg_quote'd; DATE:2014-01-06
	@Test public void Ex_url()			{fxt.Test_parse("^%s*(.-)%s*$"			, "\\G\\s*(.*?)\\s*$");}
	@Test public void Balanced() {
		fxt.Test_replace("a(1)c"				, "%b()", "b", "abc");
		fxt.Test_replace("a(2(1)2)c"			, "%b()", "b", "abc");
		fxt.Test_replace("a(3(2(1)2)3)c"		, "%b()", "b", "abc");
	}
	@Test public void Balanced_nested() { // handle nested; PAGE:en.w:Portal:Constructed_languages/Intro DATE:2018-07-02
		fxt.Test_replace("[[a|b[[c]]d]]  p1"	, "%b[]", "z", "z  p1");
	}
	@Test public void Mbcs() {	// PURPOSE: handle regex for multi-byte chars; PAGE:en.d:どう; DATE:2016-01-22; .NET.REGX:fails
		fxt.Test_replace("𠀀"					, "[𠀀-𯨟]"	, "a", "a");
	}
	@Test public void Invalid_range() {// PURPOSE: if range is invalid, take 1st char only; note range is multi-byte; ISSUE#:383; PAGE:en.d:dictionary DATE:2019-03-16
		fxt.Test_parse("[ড়-য়]"	, "[ড়]"); // 2492-2479
	}
//		@Test public void Brack_empty_all()	{fxt.Test_parse("[]"					, "(?:(*FAIL))");}
//		@Test public void Brack_empty_not()	{fxt.Test_parse("[^]"					, ".");}
}
class Scrib_regx_converter_fxt {
	private Scrib_regx_converter under;
	public void Clear() {
		if (under == null) {
			under = new Scrib_regx_converter();
		}
	}
	public void Test_parse(String raw, String expd) {
		under.patternToRegex(raw, Scrib_regx_converter.Anchor_G, true);
		GfoTstr.EqObj(expd, under.Regx());
	}
	public void Test_replace(String text, String find, String replace, String expd) {
		String regex_str = under.patternToRegex(find, Scrib_regx_converter.Anchor_G, true);
		String actl = Regx_adp_.Replace(text, regex_str, replace);
		GfoTstr.EqObj(expd, actl);
	}
}
