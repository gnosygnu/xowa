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
package gplx.xowa.xtns.scribunto.lib; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
public class Scrib_lib_ustring__lib_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_ustring().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test  public void Find() {			
		Exec_find("abcd"	, "b"				, 1, Bool_.N, "2;2");				// basic
		Exec_find("abac"	, "a"				, 2, Bool_.N, "3;3");				// bgn
		Exec_find("()()"	, "("				, 2, Bool_.Y, "3;3");				// plain; note that ( would "break" regx
		Exec_find("a bcd e"	, "(b(c)d)"			, 2, Bool_.N, "3;5;bcd;c");			// groups
		Exec_find("a bcd e"	, "()(b)"			, 2, Bool_.N, "3;3;3;b");			// groups; empty capture
		Exec_find("abcd"	, "x"				, 1, Bool_.N, "");					// empty
		Exec_find("abcd"	, ""				, 2, Bool_.Y, "2;1");				// empty regx should return values; plain; EX:w:Fool's_mate; DATE:2014-03-04
		Exec_find("abcd"	, ""				, 2, Bool_.N, "2;1");				// empty regx should return values; regx; EX:w:Fool's_mate; DATE:2014-03-04
		Exec_find("abcd"	, "^(c)"			, 3, Bool_.N, "3;3;c");				// ^ should be converted to \G; regx; EX:cs.n:Category:1._září_2008; DATE:2014-05-07
	}
	@Test  public void Find_surrogate() {	// PURPOSE: handle surrogates in Find PAGE:zh.w:南北鐵路_(越南); DATE:2014-08-28
		Exec_find("aé𡼾\nbî𡼾\n"	, "\n"		, 1, Bool_.N, "4;4");				// 4 b/c \n starts at pos 4 (super 1)
		Exec_find("aé𡼾\nbî𡼾\n"	, "\n"		, 5, Bool_.N, "8;8");				// 8 b/c \n starts at pos 8 (super 1)
	}
	@Test  public void Match() {
		Exec_match("abcd"	, "bc"				, 1, "bc");							// basic
		Exec_match("abcd"	, "x"				, 1, String_.Null_mark);			// empty
		Exec_match("abcd"	, "a"				, 2, String_.Null_mark);			// bgn
		Exec_match("abcd"	, "b(c)"			, 1, "c");							// group
		Exec_match(" a b "	, "^%s*(.-)%s*$"	, 1, "a b");						// trim
		Exec_match("abcd"	, "a"				, 0, "a");							// handle 0; note that php/lua is super-1, but some modules pass in 0; ru.w:Module:Infocards; DATE:2013-11-08
		Exec_match("abcd"	, "."				, -1, "d");							// -1
		Exec_match("aaa"	, "a"				, 1, "a");							// should return 1st match not many
		Exec_match("aaa"	, "(a)"				, 1, "a;a;a");						// should return all matches
		Exec_match("a b"	, "%S"				, 1, "a");							// %S was returning every match instead of 1st; EX:en.w:Bertrand_Russell; DATE:2014-04-02
	}
	@Test  public void Match_args_out_of_order() {
		fxt.Test_scrib_proc_empty(lib, Scrib_lib_ustring.Invk_match, KeyVal_.Ary(KeyVal_.int_(2, "[a]")));
	}
	@Test  public void Gsub() {
		Exec_gsub_regx("abcd", "[a]"		, -1, "A"		, "Abcd;1");
		Exec_gsub_regx("aaaa", "[a]"		, 2, "A"		, "AAaa;2");
		Exec_gsub_regx("a"	, "(a)"			, 1, "%%%1"		, "%a;1");
		Exec_gsub_regx("à{b}c", "{b}"		, 1, "b"		, "àbc;1");		// utf8
		Exec_gsub_regx("àbc", "^%s*(.-)%s*$", 1, "%1"		, "àbc;1");		// utf8; regx is for trim line
		Exec_gsub_regx("a"	, "[^]"			, 1, "b"		, "a;0");		// invalid regx should not fail; should return self; DATE:2013-10-20
	}
	@Test  public void Gsub_table() {
		Exec_gsub_regx("abcd", "[ac]"		, -1, Scrib_kv_utl_.flat_many_("a", "A", "c", "C")	, "AbCd;2");
		Exec_gsub_regx("abc" , "[ab]"		, -1, Scrib_kv_utl_.flat_many_("a", "A")			, "Abc;2");	// PURPOSE: match not in regex should still print itself; in this case [c] is not in tbl regex; DATE:2014-03-31
	}
	@Test  public void Gsub_table_match() {	// PURPOSE: replace using group, not found term; EX:"b" not "%b%" PAGE:en.w:Bannered_routes_of_U.S._Route_60; DATE:2014-08-15
		Exec_gsub_regx("a%b%c", "%%(%w+)%%"	, -1, Scrib_kv_utl_.flat_many_("b", "B")			, "aBc;1");
	}
	@Test  public void Gsub_capture() {
		Exec_gsub_regx("aa"			, "(a)%1"				, 1, "%1z", "az;1");	// capture
		Exec_gsub_regx("a\"b'c\"d"	, "([\"'])(.-)%1"		, 1, "%1z", "a\"zd;1");	// capture; http://www.lua.org/pil/20.3.html; {{#invoke:test|gsub_string|a"b'c"d|(["'])(.-)%1|%1z}}
	}
	@Test  public void Gsub_no_replace() {// PURPOSE: gsub with no replace argument should not fail; EX:d:'orse; DATE:2013-10-14
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_ustring.Invk_gsub, Object_.Ary("text", "regx")						, "1=text");	// NOTE: repl, limit deliberately omitted
	}
	@Test  public void Gmatch_init() {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_ustring.Invk_gmatch_init, Object_.Ary("abcabc", "a(b)")						, "1=a(b)\n2=\n  1=false");
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_ustring.Invk_gmatch_init, Object_.Ary("abcabc", "a()(b)")					, "1=a()(b)\n2=\n  1=true\n  2=false");
	}
	@Test  public void Gmatch_callback() {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("abcabc", "a(b)", Scrib_kv_utl_.base1_many_(false), 0)	, "1=2\n2=\n  1=b");
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("abcabc", "a(b)", Scrib_kv_utl_.base1_many_(false), 2)	, "1=5\n2=\n  1=b");
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("abcabc", "a(b)", Scrib_kv_utl_.base1_many_(false), 8)	, "1=8\n2=");
	}
	@Test  public void Gmatch_callback_nomatch() {// PURPOSE.fix: was originally returning "" instead of original String; EX:vi.d:trở_thành; DATE:2014-04-23
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("a", "a"			, KeyVal_.Ary_empty, 0)	, "1=1\n2=\n  1=a");
	}
	@Test  public void Gmatch_callback_anypos() {// PURPOSE.fix: was not handling $capt argument; EX:vi.d:trở_thành; DATE:2014-04-23
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("a bcd e", "()(b)"	, Scrib_kv_utl_.base1_many_(true, false), 0), String_.Concat_lines_nl_skip_last
		( "1=3"
		, "2="
		, "  1=3"
		, "  2=b"
		));
	}
	@Test  public void Gsub_balanced_group() {	// PURPOSE: handle balanced group regex; EX:"%b()"; NOTE:test will fail if run in 1.6 environment; DATE:2013-12-20
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		Exec_gsub_regx("(a)", "%b()", 1, "c", "c;1");
	}
	@Test  public void Gmatch_callback__text_as_number() { // PURPOSE: Gmatch_callback must be able to take non String value; DATE:2013-12-20
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary(1234, "1(2)", Scrib_kv_utl_.base1_many_(false), 0), String_.Concat_lines_nl_skip_last
		( "1=2"
		, "2="
		, "  1=2"
		));
	}
//		@Test  public void Match_viwiktionary() {
//			fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_match);
//			Exec_match("tr"	, "()(r)", 1, ";");						// should return all matches
//			Exec_match("tr"	, "^([b]*).-([c]*)$", 1, ";");						// should return all matches
//		}
	private void Exec_find(String text, String regx, int bgn, boolean plain, String expd) {
		fxt.Test_scrib_proc_kv_vals(lib, Scrib_lib_ustring.Invk_find, Scrib_kv_utl_.base1_many_(text, regx, bgn, plain), expd);
	}
	private void Exec_match(String text, String regx, int bgn, String expd) {
		fxt.Test_scrib_proc_kv_vals(lib, Scrib_lib_ustring.Invk_match, Scrib_kv_utl_.base1_many_(text, regx, bgn), expd);
	}
	private void Exec_gsub_regx(String text, String regx, int limit, Object repl, String expd) {Exec_gsub(text, regx, limit, repl, expd);}
	private void Exec_gsub(String text, String regx, int limit, Object repl, String expd) {
		fxt.Test_scrib_proc_kv_vals(lib, Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(text, regx, repl, limit), expd);
	}
}
