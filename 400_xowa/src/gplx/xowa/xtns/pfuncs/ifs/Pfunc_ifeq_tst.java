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
import org.junit.*;
public class Pfunc_ifeq_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}

	@Test  public void Ifeq_y()				{fxt.Test_parse_tmpl_str_test("{{#ifeq:1|1|a|b}}"				, "{{test}}"		, "a");}
	@Test  public void Ifeq_n()				{fxt.Test_parse_tmpl_str_test("{{#ifeq:1|2|a|b}}"				, "{{test}}"		, "b");}
	@Test  public void Ifeq_prm_arg()		{fxt.Test_parse_tmpl_str_test("{{#ifeq:{{{1}}}|1|a|b}}"			, "{{test|1}}"		, "a");}
	@Test  public void Ifeq_prm_arg_n()		{fxt.Test_parse_tmpl_str_test("{{#ifeq:{{{1}}}|1|a|b}}"			, "{{test|2}}"		, "b");}
	@Test  public void Ifeq_prm_blank_y()	{fxt.Test_parse_tmpl_str_test("{{#ifeq:||a|b}}"					, "{{test}}"		, "a");}
	@Test  public void Ifeq_prm_blank_n()	{fxt.Test_parse_tmpl_str_test("{{#ifeq:|1|a|b}}"					, "{{test}}"		, "b");}
	@Test  public void Ifeq_numeric()		{fxt.Test_parse_tmpl_str_test("{{#ifeq:003|3.0|y|n}}"			, "{{test}}"		, "y");}
	@Test  public void Ifeq_numeric_neg()	{fxt.Test_parse_tmpl_str_test("{{#ifeq:-1.0|-1|y|n}}"			, "{{test}}"		, "y");}
	@Test  public void Ifeq_prm_arg0()		{fxt.Test_parse_tmpl_str_test("{{#ifeq:1|{{{1}}}|a|b}}"			, "{{test|1}}"		, "a");}
	@Test  public void Ifeq_expr_err()		{fxt.Test_parse_tmpl_str_test("{{#ifeq:{{#expr:a}}|0|y|n}}"		, "{{test}}"		, "n");}
	@Test  public void Ifeq_blank()			{fxt.Test_parse_tmpl_str_test("{{#ifeq:0||y|n}}"					, "{{test}}"		, "n");}

	@Test  public void Ifeq_exc_args_0()	{fxt.Test_parse_tmpl_str_test("{{#ifeq:}}"						, "{{test}}"		, "");}
	@Test  public void Ifeq_exc_args_1()	{fxt.Test_parse_tmpl_str_test("{{#ifeq:1|1}}"					, "{{test}}"		, "");}
	@Test  public void Ifeq_exc_args_2()	{fxt.Test_parse_tmpl_str_test("{{#ifeq:1|1|a}}"					, "{{test}}"		, "a");}
	@Test  public void Ifeq_exp()			{fxt.Test_parse_tmpl_str_test("{{#ifeq:0.006|+6.0E-3|y|n}}"		, "{{test}}"		, "y");}
	@Test  public void Ifeq_plus_minus()	{fxt.Test_parse_tmpl_str_test("{{#ifeq:+|-|y}}"					, "{{test}}"		, "");}	// PURPOSE: was evaluating to y; PAGE:en.w:Permian-Triassic extinction
	@Test  public void Tab_ent() {	// PURPOSE: hack; tabs are materialized as "&#09;" which causes trimming problems; PAGE:en.w:Template:Cretaceous_graphical_timeline and "|period11=    Campanian\s\t"
		fxt.Test_parse_page_all_str("{{#ifeq:a|a &#09;|y|n}}", "y");	// note that "|a\s\t" gets trimmed to "a"
	}
	@Test  public void Ifeq_hex()			{fxt.Test_parse_tmpl_str_test("{{#ifeq:44|0X002C|y|n}}"			, "{{test}}"		, "y");}	// PURPOSE: hex compares to int; EX:w:Comma 
	@Test  public void Colon_2() {	// PURPOSE: 2nd colon causes error b/c of bad whitespace evaluation; PAGE:en.w:de.wiktionary.org/wiki/glitschig; DATE:2013-12-10
		fxt.Test_parse_tmpl_str_test("{{#ifeq: :|a|b|c}}"			, "{{test}}"		, "c");
	}
}
