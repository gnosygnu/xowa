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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Php_parser_tst {		
	@Before public void init() {fxt.Clear();} private final    Php_parser_fxt fxt = new Php_parser_fxt();
	@Test  public void Text()				{fxt.tst_tkns("text", fxt.tkn_txt(0, 4));		}
	@Test  public void Declaration_pass()	{fxt.tst_tkns("<?php", fxt.tkn_declaration());}
	@Test  public void Declaration_fail()	{fxt.tst_tkns("<?phpx", fxt.tkn_txt(0, 6));}
	@Test  public void Ws_basic()			{fxt.tst_tkns(" ", fxt.tkn_ws(0, 1));}
	@Test  public void Ws_mix()				{fxt.tst_tkns(" a\n", fxt.tkn_ws(0, 1), fxt.tkn_txt(1, 2), fxt.tkn_ws(2, 3));}
	@Test  public void Comment_mult()		{fxt.tst_tkns("/*a*/", fxt.tkn_comment_mult(0, 5));}
	@Test  public void Comment_slash()		{fxt.tst_tkns("//a\n", fxt.tkn_comment_slash(0, 4));}
	@Test  public void Comment_hash()		{fxt.tst_tkns("#a\n", fxt.tkn_comment_hash(0, 3));}
	@Test  public void Comment_mult_fail()	{fxt.Msg(Php_lxr_comment.Dangling_comment, 0, 2).tst_tkns("/*a", fxt.tkn_comment_mult(0, 3));}
	@Test  public void Var()				{fxt.tst_tkns("$abc", fxt.tkn_var(0, 4, "abc"));}
	@Test  public void Sym()				{fxt.tst_tkns(";==>,()", fxt.tkn_generic(0, 1, Php_tkn_.Tid_semic), fxt.tkn_generic(1, 2, Php_tkn_.Tid_eq), fxt.tkn_generic(2, 4, Php_tkn_.Tid_eq_kv), fxt.tkn_generic(4, 5, Php_tkn_.Tid_comma), fxt.tkn_generic(5, 6, Php_tkn_.Tid_paren_bgn), fxt.tkn_generic(6, 7, Php_tkn_.Tid_paren_end));}
	@Test  public void Keyword()			{fxt.tst_tkns("null=nulla", fxt.tkn_generic(0, 4, Php_tkn_.Tid_null), fxt.tkn_generic(4, 5, Php_tkn_.Tid_eq), fxt.tkn_txt(5, 10));}
	@Test  public void Num()				{fxt.tst_tkns("0=123", fxt.tkn_num(0, 1, 0), fxt.tkn_generic(1, 2, Php_tkn_.Tid_eq), fxt.tkn_num(2, 5, 123));}
	@Test  public void Quote_apos()			{fxt.tst_tkns("'a\"b'", fxt.tkn_quote_apos(0, 5));}
	@Test  public void Quote_quote()		{fxt.tst_tkns("\"a'b\"", fxt.tkn_quote_quote(0, 5));}
	@Test  public void Quote_escape()		{fxt.tst_tkns("'a\\'b'", fxt.tkn_quote_apos(0, 6));}
	@Test  public void Brack()				{fxt.tst_tkns("['a']", fxt.tkn_generic(0, 1, Php_tkn_.Tid_brack_bgn), fxt.tkn_quote_apos(1, 4), fxt.tkn_generic(4, 5, Php_tkn_.Tid_brack_end));}
	@Test  public void Line_ws()			{fxt.tst_lines("\r\n$a = false;", fxt.line_assign("a", fxt.itm_bool_false()));}
	@Test  public void Line_brack()			{fxt.tst_lines("$a['b'] = 'c';", fxt.line_assign_subs("a", String_.Ary("b"), fxt.itm_quote("c")));}
	@Test  public void Line_assign_false()	{fxt.tst_lines("$a = false;", fxt.line_assign("a", fxt.itm_bool_false()));}
	@Test  public void Line_assign_quote_charcode() {fxt.tst_lines("$a = 'bc';", fxt.line_assign("a", fxt.itm_quote("bc")));}
	@Test  public void Line_assign_mult()	{fxt.tst_lines("$a = 'b';\n$c='d';", fxt.line_assign("a", fxt.itm_quote("b")), fxt.line_assign("c", fxt.itm_quote("d")));}
	@Test  public void Empty_usr_array() {
		fxt.tst_lines("$a = array();\n$b = array();"
				, fxt.line_assign("a", fxt.itm_ary())
				, fxt.line_assign("b", fxt.itm_ary())
				);
	}
	@Test  public void Ary_flat()			{fxt.tst_lines("$a = array('b', 'c', 'd');"	, fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_quote("b"), fxt.itm_quote("c"), fxt.itm_quote("d"))));}
	@Test  public void Brack_flat()			{fxt.tst_lines("$a = ['b', 'c', 'd'];"		, fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_quote("b"), fxt.itm_quote("c"), fxt.itm_quote("d"))));}
	@Test  public void Ary_flat_escape() {	// PURPOSE.fix: \\' was being interpreted incorrectly; \\ should escape \, but somehow \' was being escaped
		fxt.tst_lines("$a = array('b\\\\', 'c');", fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_quote("b\\\\"), fxt.itm_quote("c"))));
	}
	@Test  public void Ary_flat_escape2() {	// PURPOSE.fix: \\' was being interpreted incorrectly; \\ should escape \, but somehow \' was being escaped
		fxt.tst_lines("$a = array('b\\\\\\'c', 'd');", fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_quote("b\\\\\\'c"), fxt.itm_quote("d"))));
	}
	@Test  public void Ary_kv()				{fxt.tst_lines("$a = array(k0 => 'v0', k1 => 'v1', k2 => 'v2');", fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_kv_quote("k0", "v0"), fxt.itm_kv_quote("k1", "v1"), fxt.itm_kv_quote("k2", "v2"))));}
	@Test  public void Brack_kv()			{fxt.tst_lines("$a = [k0 => 'v0', k1 => 'v1', k2 => 'v2'];"		, fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_kv_quote("k0", "v0"), fxt.itm_kv_quote("k1", "v1"), fxt.itm_kv_quote("k2", "v2"))));}
	@Test  public void Ary_kv_num()			{fxt.tst_lines("$a = array(k0 => 0, k1 => 1);", fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_kv_int("k0", 0), fxt.itm_kv_int("k1", 1))));}
	@Test  public void Ary_kv_txt()			{fxt.tst_lines("$a = array('k0' => a, 'k1' => b);", fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_kv_txt("k0", "a"), fxt.itm_kv_txt("k1", "b"))));}
	@Test  public void Ary_nest()			{fxt.tst_lines("$a = array('b', array('c', 'd'), 'e');"	, fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_quote("b"), fxt.itm_ary().Subs_(fxt.itm_quote("c"), fxt.itm_quote("d")), fxt.itm_quote("e"))));}
	@Test  public void Brack_nest()			{fxt.tst_lines("$a = ['b', ['c', 'd'], 'e'];"			, fxt.line_assign("a", fxt.itm_ary().Subs_(fxt.itm_quote("b"), fxt.itm_ary().Subs_(fxt.itm_quote("c"), fxt.itm_quote("d")), fxt.itm_quote("e"))));}
	@Test  public void Ary_nest_kv() {
		fxt.tst_lines("$a = array('i00' => array('01', '02'), 'i10' => array('11', '12'), 'i20' => array('21', '22'));"
				, fxt.line_assign
				(	"a"
				,	fxt.itm_ary().Subs_
				(		fxt.itm_kv_itm("i00", fxt.itm_ary().Subs_(fxt.itm_quote("01"), fxt.itm_quote("02")))	
				,		fxt.itm_kv_itm("i10", fxt.itm_ary().Subs_(fxt.itm_quote("11"), fxt.itm_quote("12")))
				,		fxt.itm_kv_itm("i20", fxt.itm_ary().Subs_(fxt.itm_quote("21"), fxt.itm_quote("22")))
				)));
	}
	@Test  public void Brack_nest_kv() {
		fxt.tst_lines("$a = ['i00' => ['01', '02'], 'i10' => ['11', '12'], 'i20' => ['21', '22']];"
				, fxt.line_assign
				(	"a"
				,	fxt.itm_ary().Subs_
				(		fxt.itm_kv_itm("i00", fxt.itm_ary().Subs_(fxt.itm_quote("01"), fxt.itm_quote("02")))	
				,		fxt.itm_kv_itm("i10", fxt.itm_ary().Subs_(fxt.itm_quote("11"), fxt.itm_quote("12")))
				,		fxt.itm_kv_itm("i20", fxt.itm_ary().Subs_(fxt.itm_quote("21"), fxt.itm_quote("22")))
				)));
	}
}
