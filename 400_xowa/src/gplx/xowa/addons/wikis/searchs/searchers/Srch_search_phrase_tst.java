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
package gplx.xowa.addons.wikis.searchs.searchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import org.junit.*; import gplx.xowa.addons.wikis.searchs.parsers.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Srch_search_phrase_tst {
	private final    Srch_search_phrase_fxt fxt = new Srch_search_phrase_fxt();
	@Test   public void Word() 						{fxt.Test__auto_wildcard("a"		, "a*");}
	@Test   public void Paren_end() 				{fxt.Test__auto_wildcard("(a)"		, "(a*)");}
	@Test   public void Quoted() 					{fxt.Test__auto_wildcard("\"a\""	, "\"a\"");}
	@Test   public void Space() 					{fxt.Test__auto_wildcard(" "		, " ");}
	@Test   public void Not() 						{fxt.Test__auto_wildcard("-"		, "-");}
	@Test   public void And() 						{fxt.Test__auto_wildcard("+"		, "+");}
	@Test   public void Or() 						{fxt.Test__auto_wildcard(","		, ",");}
	@Test   public void Paren_bgn() 				{fxt.Test__auto_wildcard("("		, "(");}
	@Test   public void Star() 						{fxt.Test__auto_wildcard("*"		, "*");}
	@Test   public void Wildcard__exists__y() 		{fxt.Test__auto_wildcard("a*b"		, "a*b");}
	@Test   public void Wildcard__exists__escaped() {fxt.Test__auto_wildcard("a\\*b"	, "a\\*b*");}
	@Test   public void Wildcard__exists__n() 		{fxt.Test__auto_wildcard("a* bc"	, "a* bc*");}
	@Test   public void Escape() 					{fxt.Test__auto_wildcard("\\*"		, "\\**");}
	@Test   public void Escape__incomplete() 		{fxt.Test__auto_wildcard("a\\"		, "a\\");}
	@Test   public void Escape__escaped() 			{fxt.Test__auto_wildcard("a\\\\"	, "a\\\\*");}
	@Test   public void Auto_wildcard_n() 			{fxt.Init__auto_wildcard_n_().Test__auto_wildcard("a", "a");}
}
class Srch_search_phrase_fxt {
	private final    Srch_crt_scanner_syms syms = Srch_crt_scanner_syms.Dflt;
	private boolean auto_wildcard = true;
	public Srch_search_phrase_fxt Init__auto_wildcard_n_() {this.auto_wildcard = false; return this;}
	public void Test__auto_wildcard(String src_str, String expd) {
		byte[] src_raw = Bry_.new_u8(src_str);
		byte[] actl = Srch_search_phrase.Auto_wildcard(src_raw, auto_wildcard, syms);
		Tfds.Eq(expd, String_.new_u8(actl));
	}
}
