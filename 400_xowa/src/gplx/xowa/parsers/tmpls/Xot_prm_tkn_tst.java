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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xot_prm_tkn_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Idx_1()							{fxt.Test_parse_tmpl_str_test("{{{1}}}"							, "{{test|a|b}}"					, "a");}
	@Test  public void Idx_2()							{fxt.Test_parse_tmpl_str_test("{{{2}}}"							, "{{test|a|b}}"					, "b");}
	@Test  public void Idx_3_nil()						{fxt.Test_parse_tmpl_str_test("{{{3}}}"							, "{{test|a|b}}"					, "{{{3}}}");}
	@Test  public void Idx_3_dflt()						{fxt.Test_parse_tmpl_str_test("{{{3|c}}}"						, "{{test|a|b}}"					, "c");}
	@Test  public void Idx_3_dflt_len0()				{fxt.Test_parse_tmpl_str_test("{{{1|}}}"							, "{{test}}"						, "");}
	@Test  public void Idx_1_and_2()					{fxt.Test_parse_tmpl_str_test("({{{1}}} {{{2}}})"				, "{{test|a|b}}"					, "(a b)");}
	@Test  public void Idx_2_len0()						{fxt.Test_parse_tmpl_str_test("{{{1}}}"							, "{{test||b}}"						, "");}// should not fail
	@Test  public void Key()							{fxt.Test_parse_tmpl_str_test("{{{k1}}}"							, "{{test|k1=a|k2=b}}"				, "a");}
	@Test  public void Key_nil()						{fxt.Test_parse_tmpl_str_test("{{{k3|c}}}"						, "{{test|k1=a|k2=b}}"				, "c");}
	@Test  public void Key_exact()						{fxt.Test_parse_tmpl_str_test("{{{k|}}}{{{k2|}}}"				, "{{test|k=a}}"					, "a");}	// only {{{k}}} matched
	@Test  public void Var()							{fxt.Test_parse_tmpl_str_test("{{{1|-{{PAGENAME}}-}}}"			, "{{test}}"						, "-Test page-");}
	@Test  public void Newline_bgn()					{fxt.Test_parse_tmpl_str_test("{{{1}}} {{{2}}}"					, "{{test|a|\nb}}"					, "a \nb");}
	@Test  public void Newline_end()					{fxt.Test_parse_tmpl_str_test("{{{1}}} {{{2}}}"					, "{{test|a|b\n}}"					, "a b\n");}
	@Test  public void Exc_lkp_nil()					{fxt.Test_parse_tmpl_str_test("{{{}}}"							, "{{test|a|b}}"					, "{{{}}}");}
	@Test  public void Exc_lkp_and_args1()				{fxt.Test_parse_tmpl_str_test("{{{|}}}"							, "{{test|a|b}}"					, "");}
	@Test  public void Exc_lkp_nil_args1_txt()			{fxt.Test_parse_tmpl_str_test("{{{|a}}}"							, "{{test|a|b}}"					, "a");}
	@Test  public void Ws_idx()							{fxt.Test_parse_tmpl_str_test("{{{ 1 }}}"						, "{{test|a|b}}"					, "a");}
	@Test  public void Ws_idx_nil()						{fxt.Test_parse_tmpl_str_test("{{{ 1 }}}"						, "{{test}}"						, "{{{ 1 }}}");}
	@Test  public void Ws_key()							{fxt.Test_parse_tmpl_str_test("{{{ k1 }}}"						, "{{test|k1=a|k2=b}}"				, "a");}
	@Test  public void Ws_dflt()						{fxt.Test_parse_tmpl_str_test("{{{1| a }}}"						, "{{test}}"						, " a ");}
	@Test  public void Dflt_multiple()					{fxt.Test_parse_tmpl_str_test("{{{1|a|b}}}"						, "{{test}}"						, "a");}
	@Test  public void Keyd_not_idxd()					{fxt.Test_parse_tmpl_str_test("{{{1}}}{{{2}}}"					, "{{test|a|key=b}}"				, "a{{{2}}}");}
	@Test  public void Keyd_not_idxd_ints()				{fxt.Test_parse_tmpl_str_test("{{{1}}}{{{2}}}"					, "{{test|1=a|2=b}}"				, "ab");}
	@Test  public void Recurse_1()						{fxt.Test_parse_tmpl_str_test("{{{1{{{2|}}}|}}}"					, "{{test|a}}"						, "a");}	// used in {{See}} to test if argument 2 is last
	@Test  public void Recurse_2()						{fxt.Test_parse_tmpl_str_test("{{{1{{{2|}}}|}}}"					, "{{test|a|b}}"					, "");}
	@Test  public void Keyd_int()						{fxt.Test_parse_tmpl_str_test("{{{1}}}{{{2}}}"					, "{{test|2=a|b}}"					, "ba");}
	@Test  public void Keyd_int2()						{fxt.Test_parse_tmpl_str_test("{{{1}}}{{{2}}}"					, "{{test|2=a|1=b}}"				, "ba");}
	@Test  public void Keyd_int3()						{fxt.Test_parse_tmpl_str_test("{{{12}}}"							, "{{test|12=a}}"					, "a");}
	@Test  public void Equal_ignored()					{fxt.Test_parse_tmpl_str_test("{{{1|b=c}}}"						, "{{test}}"						, "b=c");}
	@Test  public void Unresolved()						{fxt.Test_parse_tmpl_str_test(""									, "{{{a|b}}}"						, "b");}
	@Test  public void Six_ltr()						{fxt.Test_parse_tmpl_str_test("{{{{{{1}}}}}}"					, "{{test|a}}"						, "{{{a}}}");}
	@Test  public void Six_num()						{fxt.Test_parse_tmpl_str_test("{{{{{{1}}}}}}"					, "{{test|1}}"						, "1");}
}
/*
*/
