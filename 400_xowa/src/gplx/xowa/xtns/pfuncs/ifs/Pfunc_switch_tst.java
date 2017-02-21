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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
public class Pfunc_switch_tst {		
	@Before public void init()				{fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic_a()			{fxt.Test_parse_tmpl_str_test("{{#switch:a|a=1|b=2|3}}"					, "{{test}}"			, "1");}
	@Test  public void Basic_b()			{fxt.Test_parse_tmpl_str_test("{{#switch:b|a=1|b=2|3}}"					, "{{test}}"			, "2");}
	@Test  public void Basic_dflt()			{fxt.Test_parse_tmpl_str_test("{{#switch:z|a=1|b=2|3}}"					, "{{test}}"			, "3");}
	@Test  public void FallThru_a()			{fxt.Test_parse_tmpl_str_test("{{#switch:a|a|b|c=1|d=2|3}}"				, "{{test}}"			, "1");}
	@Test  public void FallThru_b()			{fxt.Test_parse_tmpl_str_test("{{#switch:b|a|b|c=1|d=2|3}}"				, "{{test}}"			, "1");}
	@Test  public void FallThru_c()			{fxt.Test_parse_tmpl_str_test("{{#switch:c|a|b|c=1|d=2|3}}"				, "{{test}}"			, "1");}
	@Test  public void FallThru_d()			{fxt.Test_parse_tmpl_str_test("{{#switch:d|a|b|c=1|d=2|3}}"				, "{{test}}"			, "2");}
	@Test  public void FallThru_dflt()		{fxt.Test_parse_tmpl_str_test("{{#switch:z|a|b|c=1|d=2|3}}"				, "{{test}}"			, "3");}
	@Test  public void Dflt_named()			{fxt.Test_parse_tmpl_str_test("{{#switch:z|b=2|#default=3|a=1}}"			, "{{test}}"			, "3");}
	@Test  public void Dflt_last_idx_wins()		// even if there is a named default, if last arg is un-keyd, then use it as default
											{fxt.Test_parse_tmpl_str_test("{{#switch:z|#default=3|9}}"				, "{{test}}"			, "9");}
	@Test  public void Dflt_last_named_wins()	// last named default wins
											{fxt.Test_parse_tmpl_str_test("{{#switch:z|#default=3|#default=4}}"		, "{{test}}"			, "4");}
	@Test  public void Numeric()			{fxt.Test_parse_tmpl_str_test("{{#switch:003|3.0=y|n}}"					, "{{test}}"			, "y");} //{{#switch:{{CURRENTMONTH}}|03=y|n}}
	@Test  public void NoKeys()				{fxt.Test_parse_tmpl_str_test("{{#switch:a|a|b|c|d}}"					, "{{test}}"			, "d");}// d wins b/c it is default
	@Test  public void Prm_val()			{fxt.Test_parse_tmpl_str_test("{{#switch:{{{1}}}|a=1|b=2|3}}"			, "{{test|b}}"			, "2");}
	@Test  public void Prm_case1v()			{fxt.Test_parse_tmpl_str_test("{{#switch:{{{1}}}|a={{{1}}}|b=2|3}}"		, "{{test|a}}"			, "a");}
	@Test  public void Prm_case1k()			{fxt.Test_parse_tmpl_str_test("{{#switch:{{{1}}}|{{{1}}}=1|b=2|3}}"		, "{{test|a}}"			, "1");}
	@Test  public void Null_x()				{fxt.Test_parse_tmpl_str_test("{{#switch:|a=1|b=2|3}}"					, "{{test|b}}"			, "3");}
	@Test  public void Exc_no_cases()		{fxt.Test_parse_tmpl_str_test("{{#switch:a}}"							, "{{test}}"			, "");}
	@Test  public void Exc_brace()			{fxt.Test_parse_tmpl_str_test("{{#switch:a|{{{1}}}}=y|n}}"				, "{{test|a}}"			, "n");}// NOTE: deliberate 4th } brace
	@Test  public void Ex_1()				{fxt.Test_parse_tmpl_str_test("{{#switch:{{{1}}}|off=none|def=off|{{{1|off}}}}}", "{{test|b}}"			, "b");}
	@Test  public void Ex_2() {
		fxt.Test_parse_tmpl_str_test(String_.Concat_lines_nl_skip_last
			(	"{{#switch:{{{{{|safesubst:}}}NAMESPACE:Category:Foo}}"
			,	"|{{ns:0}}"
			,	"|{{ns:Category}}=yes"
			,	"|no"
			,	"}}"
			)
			,	"{{test}}"
			,	"yes");
	}
	@Test  public void Ws()					{
		fxt.Test_parse_tmpl_str_test(String_.Concat_lines_nl_skip_last
			(	"{{#switch: | {{ns:0}}"
			,	"|{{ns:2}} = yes"
			,	"|no"
			,	"}}"
			)
			,	"{{test}}"
			,	"yes");
	}
	@Test  public void Do_not_call_val_unless_needed() {
		fxt.Init_defn_clear();
		Xop_xowa_dbg.Argx_list.Clear();
		fxt.Init_defn_add("fail", "{{#xowa_dbg:Fail}}");
		fxt.Init_defn_add("pass", "{{#xowa_dbg:Pass}}");
		fxt.Init_defn_add("dflt", "{{#xowa_dbg:Dflt}}");
		fxt.Test_parse_tmpl_str_test("{{#switch:{{{1}}}|a={{fail}}|#default={{dflt}}|b={{pass}}}}", "{{test|b}}", "Pass");
		Tfds.Eq(1, Xop_xowa_dbg.Argx_list.Count());
	}
	@Test  public void Dflt_empty()	{	// PURPOSE: empty default should return "" not "#default"; PAGE:de.v:M�nchen/Sehensw�rdigkeiten; DATE:2014-05-29
		fxt.Test_parse_tmpl_str_test("{{#switch:z|b=1|#default}}"			, "{{test}}"			, "");
		fxt.Test_parse_tmpl_str_test("{{#switch:z|b=1|#defaultabc}}"		, "{{test}}"			, "abc");	// chop off "#default"
		fxt.Test_parse_tmpl_str_test("{{#switch:a|#default|#default=1}}"	, "{{test}}"			, "1");		// override "|#default|" with "|#default=2|"
		fxt.Test_parse_tmpl_str_test("{{#switch:b|#default|1}}"				, "{{test}}"			, "1");		// override "|#default|" with "|2|"
		fxt.Test_parse_tmpl_str_test("{{#switch:b|#defaultabc=1}}"			, "{{test}}"			, "1");		// this is also supported by MW
	}
	@Test  public void Multiple() {
		fxt.Wiki().Lang().Kwd_mgr().Kwd_default_match_reset();
		Xol_kwd_grp kwd_grp = fxt.Wiki().Lang().Kwd_mgr().Get_or_new(Xol_kwd_grp_.Id_xtn_default);
		kwd_grp.Srl_load(Bool_.Y, new byte[][] {Bry_.new_a7("#default1"), Bry_.new_a7("#default2")});
		fxt.Test_parse_tmpl_str_test("{{#switch:|n=n|#default1=y}}"			, "{{test}}"			, "y");
		fxt.Test_parse_tmpl_str_test("{{#switch:|n=n|#default2=y}}"			, "{{test}}"			, "y");
		fxt.Test_parse_tmpl_str_test("{{#switch:a|n=n|#default=y}}"			, "{{test}}"			, "");	// #default is just a case
		fxt.Test_parse_tmpl_str_test("{{#switch:|n=n|#default=y}}"			, "{{test}}"			, "");	// make sure empty String doesn't throw out of bounds
		fxt.Wiki().Lang().Kwd_mgr().Kwd_default_match_reset();
	}
}
