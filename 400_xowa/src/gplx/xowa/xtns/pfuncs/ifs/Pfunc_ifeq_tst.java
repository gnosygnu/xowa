/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_ifeq_tst {
	private Xop_fxt fxt = new Xop_fxt();
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
	@Test  public void Ifeq_plus_minus()	{fxt.Test_parse_tmpl_str_test("{{#ifeq:+|-|y}}"					, "{{test}}"		, "");}	// PURPOSE: was evaluating to y; EX.WP:Permian-Triassic extinction
	@Test  public void Tab_ent() {	// PURPOSE: hack; tabs are materialized as "&#09;" which causes trimming problems; EX.WP: Template:Cretaceous_graphical_timeline and "|period11=    Campanian\s\t"
		fxt.Test_parse_page_all_str("{{#ifeq:a|a &#09;|y|n}}", "y");	// note that "|a\s\t" gets trimmed to "a"
	}
	@Test  public void Ifeq_hex()			{fxt.Test_parse_tmpl_str_test("{{#ifeq:44|0X002C|y|n}}"			, "{{test}}"		, "y");}	// PURPOSE: hex compares to int; EX:w:Comma 
	@Test  public void Colon_2() {	// PURPOSE: 2nd colon causes error b/c of bad whitespace evaluation; EX.WP:de.wiktionary.org/wiki/glitschig; DATE:2013-12-10
		fxt.Test_parse_tmpl_str_test("{{#ifeq: :|a|b|c}}"			, "{{test}}"		, "c");
	}
}
