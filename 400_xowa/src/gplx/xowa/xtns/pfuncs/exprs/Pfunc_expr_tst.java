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
package gplx.xowa.xtns.pfuncs.exprs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_expr_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test  public void Null()				{fxt.Test_parse_tmpl_str_test("{{#expr:}}"										, "{{test}}"	, "");}
	@Test  public void Num_len1()			{fxt.Test_parse_tmpl_str_test("{{#expr:1}}"										, "{{test}}"	, "1");}
	@Test  public void Num_len3()			{fxt.Test_parse_tmpl_str_test("{{#expr:123}}"									, "{{test}}"	, "123");}
	@Test  public void Num_decimal()		{fxt.Test_parse_tmpl_str_test("{{#expr:1.2}}"									, "{{test}}"	, "1.2");}
	@Test  public void Num_decimal_lead()	{fxt.Test_parse_tmpl_str_test("{{#expr:.12}}"									, "{{test}}"	, "0.12");}
	@Test  public void Num_decimal_lax()	{fxt.Test_parse_tmpl_str_test("{{#expr:1.2.3}}"									, "{{test}}"	, "1.2");}			// PURPOSE: PHP allows 1.2.3 to be 1.2
	@Test  public void Num_neg()			{fxt.Test_parse_tmpl_str_test("{{#expr:-1}}"									, "{{test}}"	, "-1");}
	@Test  public void Num_neg_double()		{fxt.Test_parse_tmpl_str_test("{{#expr:--1}}"									, "{{test}}"	, "1");}
	@Test  public void Ws()					{fxt.Test_parse_tmpl_str_test("{{#expr: 123\t\n }}"								, "{{test}}"	, "123");}
	@Test  public void Plus_op()			{fxt.Test_parse_tmpl_str_test("{{#expr:1 + 2}}"									, "{{test}}"	, "3");}
	@Test  public void Minus_op()			{fxt.Test_parse_tmpl_str_test("{{#expr:3 - 2}}"									, "{{test}}"	, "1");}
	@Test  public void Mult()				{fxt.Test_parse_tmpl_str_test("{{#expr:3 * 2}}"									, "{{test}}"	, "6");}
	@Test  public void Div_sym()			{fxt.Test_parse_tmpl_str_test("{{#expr:6 / 3}}"									, "{{test}}"	, "2");}
	@Test  public void Div_word()			{fxt.Test_parse_tmpl_str_test("{{#expr:6 div 3}}"								, "{{test}}"	, "2");}
	@Test  public void Plus_sign()			{fxt.Test_parse_tmpl_str_test("{{#expr:1 + + 2}}"								, "{{test}}"	, "3");}
	@Test  public void Minus_sign()			{fxt.Test_parse_tmpl_str_test("{{#expr:3 + - 2}}"								, "{{test}}"	, "1");}
	@Test  public void Paren_1()			{fxt.Test_parse_tmpl_str_test("{{#expr:(1 + 2) * 3}}"							, "{{test}}"	, "9");}
	@Test  public void Paren_2()			{fxt.Test_parse_tmpl_str_test("{{#expr:((1 + 2) * 3) * 4}}"						, "{{test}}"	, "36");}
	@Test  public void Pow()				{fxt.Test_parse_tmpl_str_test("{{#expr:2 ^ 4}}"									, "{{test}}"	, "16");}
	@Test  public void Eq_y()				{fxt.Test_parse_tmpl_str_test("{{#expr:2 = 2}}"									, "{{test}}"	, "1");}
	@Test  public void Eq_n()				{fxt.Test_parse_tmpl_str_test("{{#expr:2 = 3}}"									, "{{test}}"	, "0");}
	@Test  public void Neq_1()				{fxt.Test_parse_tmpl_str_test("{{#expr:2 != 3}}"								, "{{test}}"	, "1");}
	@Test  public void Neq_2()				{fxt.Test_parse_tmpl_str_test("{{#expr:2 <> 3}}"								, "{{test}}"	, "1");}
	@Test  public void Gt()					{fxt.Test_parse_tmpl_str_test("{{#expr:3 > 2}}"									, "{{test}}"	, "1");}
	@Test  public void Lt()					{fxt.Test_parse_tmpl_str_test("{{#expr:2 < 3}}"									, "{{test}}"	, "1");}
	@Test  public void Gte()				{fxt.Test_parse_tmpl_str_test("{{#expr:2 >= 2}}"								, "{{test}}"	, "1");}
	@Test  public void Lte()				{fxt.Test_parse_tmpl_str_test("{{#expr:2 <= 2}}"								, "{{test}}"	, "1");}
	@Test  public void Mod()				{fxt.Test_parse_tmpl_str_test("{{#expr:3 mod 2}}"								, "{{test}}"	, "1");}
	@Test  public void And_1()				{fxt.Test_parse_tmpl_str_test("{{#expr:1 and -1}}"								, "{{test}}"	, "1");}
	@Test  public void And_0()				{fxt.Test_parse_tmpl_str_test("{{#expr:1 and  0}}"								, "{{test}}"	, "0");}
	@Test  public void Or_0()				{fxt.Test_parse_tmpl_str_test("{{#expr:1 or  0}}"								, "{{test}}"	, "1");}
	@Test  public void Not_y()				{fxt.Test_parse_tmpl_str_test("{{#expr:not 0}}"									, "{{test}}"	, "1");}
	@Test  public void Not_n()				{fxt.Test_parse_tmpl_str_test("{{#expr:not 1}}"									, "{{test}}"	, "0");}
	@Test  public void Minus_op_neg()		{fxt.Test_parse_tmpl_str_test("{{#expr:2 - 3}}"									, "{{test}}"	, "-1");}
	@Test  public void E_num()				{fxt.Test_parse_tmpl_str_test("{{#expr:e}}"										, "{{test}}"	, "2.71828182845904");}
	@Test  public void Pi_num()				{fxt.Test_parse_tmpl_str_test("{{#expr:pi}}"									, "{{test}}"	, "3.14159265358979");}
	@Test  public void Pi_mult()			{fxt.Test_parse_tmpl_str_test("{{#expr:pi*1}}"									, "{{test}}"	, "3.14159265358979");}
	@Test  public void E_op_pos()			{fxt.Test_parse_tmpl_str_test("{{#expr:1.2 e 2}}"								, "{{test}}"	, "120");}
	@Test  public void E_op_neg()			{fxt.Test_parse_tmpl_str_test("{{#expr:1.2 e -2}}"								, "{{test}}"	, "0.012");}
	@Test  public void E_op_inf()			{fxt.Test_parse_tmpl_str_test("{{#expr:1.2 e 309}}"								, "{{test}}"	, "INF");}		// PURPOSE:constrain to PHP double (e308); PAGE:en.w:Factorial; en.w:Astatine; DATE:2015-04-09
	@Test  public void E_op_inf_2()			{fxt.Test_parse_tmpl_str_test("{{#expr:1.8 e 308}}"								, "{{test}}"	, "INF");}		// PURPOSE:constrain to PHP double (1.8 e308); PAGE:en.w:Mathematics_of_Sudoku DATE:2015-04-21
	@Test  public void E_op_large()			{fxt.Test_parse_tmpl_str_test("{{#expr:1E28}}"									, "{{test}}"	, "1E+28");}	// PURPOSE:number should print in exponent notation (1E307), not full literal String (10000000...); DATE:2015-04-09;
	@Test  public void Ceil_neg()			{fxt.Test_parse_tmpl_str_test("{{#expr:ceil(-1.2)}}"							, "{{test}}"	, "-1");}
	@Test  public void Trunc_neg()			{fxt.Test_parse_tmpl_str_test("{{#expr:trunc(-1.2)}}"							, "{{test}}"	, "-1");}
	@Test  public void Floor_neg()			{fxt.Test_parse_tmpl_str_test("{{#expr:floor(-1.2)}}"							, "{{test}}"	, "-2");}
	@Test  public void Ceil_pos()			{fxt.Test_parse_tmpl_str_test("{{#expr:ceil(1.2)}}"								, "{{test}}"	, "2");}
	@Test  public void Trunc_pos()			{fxt.Test_parse_tmpl_str_test("{{#expr:trunc(1.2)}}"							, "{{test}}"	, "1");}
	@Test  public void Floor_pos()			{fxt.Test_parse_tmpl_str_test("{{#expr:floor(1.2)}}"							, "{{test}}"	, "1");}
	@Test  public void Abs_pos()			{fxt.Test_parse_tmpl_str_test("{{#expr:abs(1)}}"								, "{{test}}"	, "1");}
	@Test  public void Abs_neg()			{fxt.Test_parse_tmpl_str_test("{{#expr:abs(-1)}}"								, "{{test}}"	, "1");}
	@Test  public void Exp()				{fxt.Test_parse_tmpl_str_test("{{#expr:exp(10)}}"								, "{{test}}"	, "22026.46579480671789");}	// NOTE: MW returns 4807, not 480671789;
	@Test  public void Ln()					{fxt.Test_parse_tmpl_str_test("{{#expr:ln(22026.4657948067)}}"					, "{{test}}"	, "10");}
	@Test  public void Ln_mult()			{fxt.Test_parse_tmpl_str_test("{{#expr:ln4/ln2}}"								, "{{test}}"	, "2");}	// PAGE:en.w:Fieldbus; DATE:2015-04-09
	@Test  public void Sin()				{fxt.Test_parse_tmpl_str_test("{{#expr:sin(1.5707963267949)}}"					, "{{test}}"	, "1");}
	@Test  public void Cos()				{fxt.Test_parse_tmpl_str_test("{{#expr:cos(0)}}"								, "{{test}}"	, "1");}
	@Test  public void Tan()				{fxt.Test_parse_tmpl_str_test("{{#expr:tan(45)}}"								, "{{test}}"	, "1.61977519054386");}
	@Test  public void Asin()				{fxt.Test_parse_tmpl_str_test("{{#expr:asin(0)}}"								, "{{test}}"	, "0");}
	@Test  public void Acos()				{fxt.Test_parse_tmpl_str_test("{{#expr:acos(0)}}"								, "{{test}}"	, "1.57079632679489");} // NOTE: MW (and C#) returns 49, not 489 
	@Test  public void Atan()				{fxt.Test_parse_tmpl_str_test("{{#expr:atan(0)}}"								, "{{test}}"	, "0");}
	@Test  public void Round()				{fxt.Test_parse_tmpl_str_test("{{#expr:1.5 round 0}}"							, "{{test}}"	, "2");}
	@Test  public void Round_0()			{fxt.Test_parse_tmpl_str_test("{{#expr:0 round 1}}"								, "{{test}}"	, "0");}		// PURPOSE: 0 round 1 should be 0, not 0.0; DATE:2013-11-09
	@Test  public void Round_ex_1()			{fxt.Test_parse_tmpl_str_test("{{#expr:(0.03937007874015)round(3)}}"			, "{{test}}"	, "0.039");}	// PURPOSE: rounding results in excessive decimal places; PAGE:en.w:Milky Way (light year conversions)
	@Test  public void Mod_frac()			{fxt.Test_parse_tmpl_str_test("{{#expr:0.00999999mod10}}"						, "{{test}}"	, "0");}
	@Test  public void Mod_large()			{fxt.Test_parse_tmpl_str_test("{{#expr:39052000900mod100}}"						, "{{test}}"	, "0");}		// PURPOSE: JAVA was failing in converting to int and converted to Int_.Max_value instead; DATE:2013-01-26
	@Test  public void Fmod()				{fxt.Test_parse_tmpl_str_test("{{#expr:1.25 fmod .5}}"							, "{{test}}"	, "0.25");}
	@Test  public void Sqrt()				{fxt.Test_parse_tmpl_str_test("{{#expr:sqrt 4}}"								, "{{test}}"	, "2");}
	@Test  public void Sqrt_frac()			{fxt.Test_parse_tmpl_str_test("{{#expr:sqrt 2}}"								, "{{test}}"	, "1.41421356237309");}	// NOTE: MW (and C#) returns 31, not 309 
	@Test  public void Esc_xml_entRef()		{fxt.Test_parse_tmpl_str_test("{{#expr:&minus;1 &lt; 5}}"						, "{{test}}"	, "1");}
	@Test  public void Ex_1()				{fxt.Test_parse_tmpl_str_test("{{#expr:1e2round0}}"								, "{{test}}"	, "100");}		// PURPOSE: used in Convert
	@Test  public void Floating()			{fxt.Test_parse_tmpl_str_test("{{#expr:27.321582}}"								, "{{test}}"	, "27.321582");}
	@Test  public void Floating_2()			{fxt.Test_parse_tmpl_str_test("{{#expr:0.1*41}}"								, "{{test}}"	, "4.1");}		// PURPOSE: division results in expanded floating-point; PAGE:en.w:Wikipedia
	@Test  public void Floating_3()			{fxt.Test_parse_tmpl_str_test("{{#expr:111/10^(-1)}}"							, "{{test}}"	, "1110");}		// PURPOSE: division by pow; PAGE:en.w:Wikipedia:Featured articles
	@Test  public void Floating_4()			{fxt.Test_parse_tmpl_str_test("{{#expr:abs(-73.9023)}}"							, "{{test}}"	, "73.9023");}	// PURPOSE: Abs;
	@Test  public void Unicode_8722()		{fxt.Test_parse_tmpl_str_test("{{#expr:2−1}}"									, "{{test}}"	, "1");}		// PURPOSE: handle alternate minus; PAGE:en.w:Australian krill
	@Test  public void Exp_large_neg()		{fxt.Test_parse_tmpl_str_test("{{#expr:418400000000000000000000E-23}}"			, "{{test}}"	, "4.184");}	// PURPOSE: handle large neg; EX: w:Chicxulub_crater; {{convert|100|TtonTNT|J|lk=on}}
	@Test  public void Exp_large_neg2()		{fxt.Test_parse_tmpl_str_test("{{#expr:210000000000000000E-17}}"				, "{{test}}"	, "2.1");}		// PURPOSE: handle large neg2; EX: w:Chicxulub_crater; {{convert|50|MtonTNT|J|lk=on}}
	@Test  public void Fix_transclusion()	{fxt.Test_parse_tmpl_str_test("{{#expr:{{#if:||1}}/.2}}"						, "{{test}}"	, "5");}		// PURPOSE: /. was invoking transclusion; DATE:2013-04-26
	@Test  public void Exc_unrecognised_word()					{fxt.Test_parse_tmpl_str_test("{{#expr:abc}}"				, "{{test}}"	, "<strong class=\"error\">Expression error: Unrecognised word \"abc\"</strong>");}
	@Test  public void Exc_division_by_zero()					{fxt.Test_parse_tmpl_str_test("{{#expr:1/0}}"				, "{{test}}"	, "<strong class=\"error\">Division by zero</strong>");}
	@Test  public void Exc_division_by_zero_mod()				{fxt.Test_parse_tmpl_str_test("{{#expr:1 mod 0}}"			, "{{test}}"	, "<strong class=\"error\">Division by zero</strong>");}
	@Test  public void Exc_unexpected_number()					{fxt.Test_parse_tmpl_str_test("{{#expr:0 1}}"				, "{{test}}"	, "<strong class=\"error\">Expression error: Unexpected number</strong>");}
	@Test  public void Exc_unexpected_closing_bracket()			{fxt.Test_parse_tmpl_str_test("{{#expr:5 + 1)}}"			, "{{test}}"	, "<strong class=\"error\">Expression error: Unexpected closing bracket</strong>");}
	@Test  public void Exc_unclosed_bracket()					{fxt.Test_parse_tmpl_str_test("{{#expr:(5 + 1}}"			, "{{test}}"	, "<strong class=\"error\">Expression error: Unclosed bracket</strong>");}
	@Test  public void Exc_unexpected_operator_paren_end()		{fxt.Test_parse_tmpl_str_test("{{#expr:5 ( 1}}"				, "{{test}}"	, "<strong class=\"error\">Expression error: Unexpected ( operator</strong>");}
	@Test  public void Exc_unexpected_number_pi()				{fxt.Test_parse_tmpl_str_test("{{#expr:5 pi}}"				, "{{test}}"	, "<strong class=\"error\">Expression error: Unexpected number</strong>");}
	@Test  public void Exc_missing_operand()					{fxt.Test_parse_tmpl_str_test("{{#expr:5 *}}"				, "{{test}}"	, "<strong class=\"error\">Expression error: Missing operand for *</strong>");}
	@Test  public void Exc_invalid_argument_asin()				{fxt.Test_parse_tmpl_str_test("{{#expr:asin(2)}}"			, "{{test}}"	, "<strong class=\"error\">Invalid argument for asin: < -1 or > 1</strong>");}
	@Test  public void Exc_invalid_argument_acos()				{fxt.Test_parse_tmpl_str_test("{{#expr:acos(2)}}"			, "{{test}}"	, "<strong class=\"error\">Invalid argument for acos: < -1 or > 1</strong>");}
	@Test  public void Exc_invalid_argument_ln()				{fxt.Test_parse_tmpl_str_test("{{#expr:ln(-1)}}"			, "{{test}}"	, "<strong class=\"error\">Invalid argument for ln: <= 0</strong>");}
	@Test  public void Exc_pow_nan()							{fxt.Test_parse_tmpl_str_test("{{#expr:(-2)^1.2}}"			, "{{test}}"	, "NaN");}	// PURPOSE: handle nan; EX: w:Help:Calculation
	@Test  public void Exc_unrecognized_word_ornot()			{fxt.Test_parse_tmpl_str_test("{{#expr:0ornot0}}"			, "{{test}}"	, "<strong class=\"error\">Expression error: Unrecognised word \"ornot\"</strong>");}	// PURPOSE: handle nan; EX: w:Help:Calculation
	@Test  public void Exc_unrecognized_word_notnot()			{fxt.Test_parse_tmpl_str_test("{{#expr:notnot0}}"			, "{{test}}"	, "<strong class=\"error\">Expression error: Unrecognised word \"notnot\"</strong>");}	// PURPOSE: handle nan; EX: w:Help:Calculation
	@Test  public void Exc_unrecognized_word_sinln()			{fxt.Test_parse_tmpl_str_test("{{#expr:sinln1.1}}"			, "{{test}}"	, "<strong class=\"error\">Expression error: Unrecognised word \"sinln\"</strong>");}	// PURPOSE: handle nan; EX: w:Help:Calculation
}
