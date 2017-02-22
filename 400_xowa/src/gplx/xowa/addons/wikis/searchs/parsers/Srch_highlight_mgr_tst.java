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
package gplx.xowa.addons.wikis.searchs.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import org.junit.*; import gplx.xowa.langs.cases.*;
public class Srch_highlight_mgr_tst {
	private final    Srch_highlight_mgr_tstr tstr = new Srch_highlight_mgr_tstr();
	@Test   public void Full__one()				{tstr.Test("a"			, "A"					, "<strong>A</strong>");}
	@Test   public void Full__many()			{tstr.Test("a b"		, "A B"					, "<strong>A</strong> <strong>B</strong>");}
	@Test   public void Part__one()				{tstr.Test("a"			, "A1"					, "<strong>A</strong>1");}
	@Test   public void Part__many()			{tstr.Test("a b"		, "A1 B1"				, "<strong>A</strong>1 <strong>B</strong>1");}
	@Test   public void Unordered()				{tstr.Test("b a"		, "A1 B1"				, "<strong>A</strong>1 <strong>B</strong>1");}
	@Test   public void Repeat__part()			{tstr.Test("a ab"		, "Ab A"				, "<strong>Ab</strong> <strong>A</strong>");}
	@Test   public void Repeat__full()			{tstr.Test("a"			, "A A"					, "<strong>A</strong> <strong>A</strong>");}
	@Test   public void Close()					{tstr.Test("a"			, "Ba Aa"				, "Ba <strong>A</strong>a");}
	@Test   public void Comma()					{tstr.Test("a"			, "A, b"				, "<strong>A</strong>, b");}
	@Test   public void Dash()					{tstr.Test("b"			, "A-B c"				, "A-<strong>B</strong> c");}
	@Test   public void Parens()				{tstr.Test("a"			, "(A)"					, "(<strong>A</strong>)");}
	@Test   public void Strong()				{tstr.Test("strong"		, "strong strong"		, "<strong>strong</strong> <strong>strong</strong>");}
	@Test   public void Dash_w_mixed_cases()	{tstr.Test("b"			, "A-a B"				, "A-a <strong>B</strong>");}	// search_parser treats A-a separately from a-a
	@Test   public void Acronymn()				{tstr.Test("ab"			, "A.B."				, "A.B.");}
	// @Test   public void Slash()					{tstr.Test("b"			, "A/B/C"				, "A/<strong>B</strong>/C");}
}
class Srch_highlight_mgr_tstr {
	private final    Srch_highlight_mgr mgr;
	private final    Xol_case_mgr case_mgr = Xol_case_mgr_.A7();
	public Srch_highlight_mgr_tstr() {
		mgr = new Srch_highlight_mgr(case_mgr);
	}
	public void Test(String search, String title, String expd) {
		Tfds.Eq(expd, String_.new_u8(mgr.Search_(Bry_.new_u8(search)).Highlight(Bry_.new_u8(title))));
	}
}
