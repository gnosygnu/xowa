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
package gplx.xowa.addons.wikis.searchs.searchers.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import org.junit.*; import gplx.xowa.addons.wikis.searchs.parsers.*;
public class Srch_crt_scanner_tst {
	private final    Srch_crt_scanner_fxt fxt = new Srch_crt_scanner_fxt();
	@Test   public void Word() 						{fxt.Test__scan("abc"				, "abc");}
	@Test   public void Word__many() 				{fxt.Test__scan("abc d ef"			, "abc", "d", "ef");}
	@Test   public void Word__symbol() 				{fxt.Test__scan("a; b"				, "a;", "b");}
	@Test   public void And() 						{fxt.Test__scan("a + b"				, "a", "+", "b");}
	@Test   public void And__parens() 				{fxt.Test__scan("a +(b)"			, "a", "+", "(", "b", ")");}		// check that ( causes and to be treated as separate word
	@Test   public void Or() 						{fxt.Test__scan("a , b"				, "a", ",", "b");}
	@Test   public void Or__no_space() 				{fxt.Test__scan("a, b"				, "a", ",", "b");}
	@Test   public void Not() 						{fxt.Test__scan("-abc"				, "-", "abc");}
	@Test   public void Not__mid__1()				{fxt.Test__scan("a-b"				, "a-b");}							// fails if "a", "-", "b"
	@Test   public void Not__mid__2() 				{fxt.Test__scan("a b-c"				, "a", "b-c");}						// ignore - if in middle of word
	@Test   public void Not__and() 					{fxt.Test__scan("a -bc"				, "a", "-", "bc");}					// auto-add AND for -
	@Test   public void Not__dangling() 			{fxt.Test__scan("a -"				, "a", "-");}
	@Test   public void Space() 					{fxt.Test__scan(" a   b "			, "a", "b");}						// spaces should not generate tkns
	@Test   public void Quote() 					{fxt.Test__scan("\"a b\""			, "a b");}
	@Test   public void Quote__mid() 				{fxt.Test__scan("a\"b"				, "a", "b");}
	@Test   public void Quote__double() 			{fxt.Test__scan("\"a\"\"b\""		, "a\"b");}
	@Test   public void Quote__missing__one() 		{fxt.Test__scan("\"abc"				, "abc");}
	@Test   public void Quote__missing__many() 		{fxt.Test__scan("\"abc a"			, "abc", "a");}
	@Test   public void Escape__bgn() 				{fxt.Test__scan("\\-a"				, "-a");}							// fails if "-", "a"
	@Test   public void Escape__and__bgn() 			{fxt.Test__scan("\\+"				, "+");}							// fails if "a", "&", "b"
	@Test   public void Escape__and__mid() 			{fxt.Test__scan("a\\+b"				, "a+b");}							// fails if "a", "&", "b"
	@Test   public void Escape__eos__1() 			{fxt.Test__scan("\\"				, String_.Ary_empty);}
	@Test   public void Escape__eos__2() 			{fxt.Test__scan("a \\"				, "a");}
	@Test   public void Escape__eos__3() 			{fxt.Test__scan("a\\"				, "a");}
	@Test   public void Escape__many() 				{fxt.Test__scan("c\\+\\+"			, "c++");}
	@Test   public void Escape__end() 				{fxt.Test__scan("a\\\\"				, "a\\");}
	@Test   public void Complicated() 				{fxt.Test__scan("(a + \"b\") , -c", "(", "a", "+", "b", ")", ",", "-", "c");}
}
class Srch_crt_scanner_fxt {
	private final    Srch_crt_scanner scanner;
	public Srch_crt_scanner_fxt() {
		this.scanner = new Srch_crt_scanner(Srch_crt_scanner_syms.Dflt);
		Srch_text_parser text_parser = new Srch_text_parser();
		text_parser.Init_for_ttl(gplx.xowa.langs.cases.Xol_case_mgr_.A7());
	}
	public void Test__scan(String src_str, String... expd) {
		byte[] src_bry = Bry_.new_a7(src_str);
		Srch_crt_tkn[] actl_itms = scanner.Scan(src_bry);
		Tfds.Eq_ary(expd, To_vals(src_bry, actl_itms));
	}
	public void Test__scan_tids(String src_str, byte... expd) {
		byte[] src_bry = Bry_.new_a7(src_str);
		Srch_crt_tkn[] actl_itms = scanner.Scan(src_bry);
		Tfds.Eq_ary(expd, To_tids(actl_itms));
	}
	private String[] To_vals(byte[] src, Srch_crt_tkn[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Srch_crt_tkn tkn = ary[i];
			rv[i] = String_.new_a7(tkn.Val);
		}
		return rv;
	}
	private byte[] To_tids(Srch_crt_tkn[] ary) {
		int len = ary.length;
		byte[] rv = new byte[len];
		for (int i = 0; i < len; i++) {
			Srch_crt_tkn tkn = ary[i];
			rv[i] = tkn.Tid;
		}
		return rv;
	}
}
