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
public class Pfunc_rel2abs_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test   public void Slash_lvl3()			{fxt.Test_parse_tmpl_str_test("{{#rel2abs:/d|a/b/c}}"				, "{{test}}"			, "a/b/c/d");}
	@Test   public void Cur_lvl1()				{fxt.Test_parse_tmpl_str_test("{{#rel2abs:./d|a}}"					, "{{test}}"			, "a/d");}
	@Test   public void Cur_lvl2()				{fxt.Test_parse_tmpl_str_test("{{#rel2abs:./d|a/b}}"				, "{{test}}"			, "a/b/d");}
	@Test   public void Cur_lvl3()				{fxt.Test_parse_tmpl_str_test("{{#rel2abs:./d|a/b/c}}"				, "{{test}}"			, "a/b/c/d");}
	@Test   public void Owner_lvl3()			{fxt.Test_parse_tmpl_str_test("{{#rel2abs:../d|a/b/c}}"				, "{{test}}"			, "a/b/d");}
	@Test   public void Owner_cur_lvl3()		{fxt.Test_parse_tmpl_str_test("{{#rel2abs:../.|a/b/c}}"				, "{{test}}"			, "a/b");}
	@Test   public void Text_lvl3()				{fxt.Test_parse_tmpl_str_test("{{#rel2abs:d|a/b/c}}"				, "{{test}}"			, "d");}
	@Test   public void Slash_mult()			{fxt.Test_parse_tmpl_str_test("{{#rel2abs:/d//e|a/b/c}}"			, "{{test}}"			, "a/b/c/d/e");}
	@Test   public void Slash_cur_mult()		{fxt.Test_parse_tmpl_str_test("{{#rel2abs:/d/./e|a/b/c}}"			, "{{test}}"			, "a/b/c/d/e");}
	@Test   public void Qry_ends_w_slash()		{fxt.Test_parse_tmpl_str_test("{{#rel2abs:/d/|a/b/c}}"				, "{{test}}"			, "a/b/c/d");}
	@Test   public void Qry_is_empty()			{fxt.Test_parse_tmpl_str_test("{{#rel2abs:|a/b/c}}"					, "{{test}}"			, "a/b/c");}
	@Test   public void Qry_is_dot()			{fxt.Test_parse_tmpl_str_test("{{#rel2abs:.|a/b/c}}"				, "{{test}}"			, "a/b/c");}
	@Test   public void DotDot_mult2()			{fxt.Test_parse_tmpl_str_test("{{#rel2abs:/../..|a/b/c}}"			, "{{test}}"			, "a");}
	@Test   public void DotDot_mult3()			{fxt.Test_parse_tmpl_str_test("{{#rel2abs:/../../..|a/b/c}}"		, "{{test}}"			, "");}
	@Test   public void Src_is_empty()			{fxt.Test_parse_tmpl_str_test("{{#rel2abs:/d|}}"					, "{{test}}"			, "Test page/d");}
	@Test   public void Err_owner()				{fxt.Test_parse_tmpl_str_test("{{#rel2abs:..}}"						, "{{test}}"			, "");}	// PURPOSE.fix: should not fail
	@Test   public void Err_owner_2()			{fxt.Test_parse_tmpl_str_test("{{#rel2abs:/../../b|a}}"				, "{{test}}"			, "");}	// PURPOSE.fix: should not fail
	@Test   public void Ns_should_be_included_for_cur_page()	{// PURPOSE.fix: current title was not returning ns; EX: de.wikipedia.org/wiki/Hilfe:Vorlagenprogrammierung#Funktion_rel2abs 
		fxt.Page_ttl_("Help:A");	// set page to title with Srch_rslt_cbk
		fxt.Test_parse_tmpl_str_test("{{#rel2abs:.}}"				, "{{test}}"			, "Help:A");
	}
	@Test   public void Owner_lvl0()	{// PURPOSE.fix: old rel2abs was producing "/c"; EX: de.wikipedia.org/wiki/Hilfe:Vorlagenprogrammierung#Funktion_rel2abs
		fxt.Test_parse_tmpl_str_test("{{#rel2abs:../c|a}}"	, "{{test}}"			, "c");
	}	
	@Test   public void Rel2abs_slash() {
		fxt.Page_ttl_("Page_1");
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test"				, "{{/B}}");
		fxt.Init_page_create("Page_1/B"		, "Page_1/B text");
		fxt.Test_parse_tmpl_str("{{test}}"	, "Page_1/B text");
		fxt.Init_defn_clear();
	}
	@Test   public void Rel2abs_dot() {
		fxt.Page_ttl_("Page_1/A");
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test"				, "{{../C}}");
		fxt.Init_page_create("Page_1/C"		, "Page_1/C text");
		fxt.Test_parse_tmpl_str("{{test}}"	, "Page_1/C text");
		fxt.Init_defn_clear();
	}
	@Test   public void Rel2abs_ttl()		{
		Tst_rel2abs_ttl("a../b", true);
		Tst_rel2abs_ttl("a../[b", false);
	}
	private void Tst_rel2abs_ttl(String raw, boolean expd) {Tfds.Eq(expd, Pfunc_rel2abs.Rel2abs_ttl(Bry_.new_a7(raw), 0, String_.Len(raw)));}
}
