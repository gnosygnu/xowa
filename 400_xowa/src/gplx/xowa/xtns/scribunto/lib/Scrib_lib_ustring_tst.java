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
public class Scrib_lib_ustring_tst {
	@Before public void init() {
		fxt.Clear();
		fxt.Init_page("{{#invoke:Mod_0|Func_0}}");
		lib = fxt.Core().Lib_ustring().Init();
	}	Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); Scrib_lib lib;
	@Test  public void Find() {			
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_find);
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
	@Test  public void Match() {
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_match);
		Exec_match("abcd"	, "bc"				, 1, "bc");							// basic
		Exec_match("abcd"	, "x"				, 1, "null");						// empty
		Exec_match("abcd"	, "a"				, 2, "null");						// bgn
		Exec_match("abcd"	, "b(c)"			, 1, "c");							// group
		Exec_match(" a b "	, "^%s*(.-)%s*$"	, 1, "a b");						// trim
		Exec_match("abcd"	, "a"				, 0, "a");							// handle 0; note that php/lua is super-1, but some modules pass in 0; ru.w:Module:Infocards; DATE:2013-11-08
		Exec_match("abcd"	, "."				, -1, "d");							// -1
		Exec_match("aaa"	, "a"				, 1, "a");							// should return 1st match not many
		Exec_match("aaa"	, "(a)"				, 1, "a;a;a");						// should return all matches
		Exec_match("a b"	, "%S"				, 1, "a");							// %S was returning every match instead of 1st; EX:en.w:Bertrand_Russell; DATE:2014-04-02
	}
	@Test  public void Match_args_out_of_order() {
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_match);
		fxt.Test_lib_proc_kv(lib, Scrib_lib_ustring.Invk_match, new KeyVal[] {KeyVal_.int_(2, "[a]")}, "");
	}
//		@Test  public void Match_viwiktionary() {
//			fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_match);
//			Exec_match("tr"	, "()(r)", 1, ";");						// should return all matches
//			Exec_match("tr"	, "^([b]*).-([c]*)$", 1, ";");						// should return all matches
//		}
	@Test  public void Gsub() {
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		Exec_gsub_regx("abcd", "[a]"		, -1, "A", "Abcd;1");
		Exec_gsub_regx("aaaa", "[a]"		, 2, "A", "AAaa;2");
		Exec_gsub_regx("a"	, "(a)"			, 1, "%%%1", "%a;1");
		Exec_gsub_regx("à{b}c", "{b}"		, 1, "b", "àbc;1");		// utf8
		Exec_gsub_regx("àbc", "^%s*(.-)%s*$", 1, "%1", "àbc;1");	// utf8; regx is for trim line
		Exec_gsub_regx("a"	, "[^]"			, 1, "b", "a;0");		// invalid regx should not fail; should return self; DATE:2013-10-20
	}
	@Test  public void Gsub_table() {
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		Exec_gsub_regx("abcd", "[ac]"		, -1, Scrib_kv_utl_.flat_many_("a", "A", "c", "C")	, "AbCd;2");
		Exec_gsub_regx("abc" , "[ab]"		, -1, Scrib_kv_utl_.flat_many_("a", "A")			, "Abc;2");	// PURPOSE: match not in regex should still print itself; in this case [c] is not in tbl regex; DATE:2014-03-31
	}
	@Test  public void Gsub_capture() {
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		Exec_gsub_regx("aa"			, "(a)%1"				, 1, "%1z", "az;1");	// capture
		Exec_gsub_regx("a\"b'c\"d"	, "([\"'])(.-)%1"		, 1, "%1z", "a\"zd;1");	// capture; http://www.lua.org/pil/20.3.html; {{#invoke:test|gsub_string|a"b'c"d|(["'])(.-)%1|%1z}}
	}
	@Test  public void Gsub_proc() {
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		Exec_gsub_regx_func_0("abcd", "([a])", "Abcd;1");
	}
	@Test  public void Gsub_proc_w_grouped() {	// PURPOSE: gsub_proc should pass matched String, not entire String; DATE:2013-12-01
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		Exec_gsub_regx_func_1("[[a]]", "%[%[([^#|%]]-)%]%]"	, "A;1");
		fxt.Test_log_rcvd(3, "000000370000006D{[\"op\"]=\"call\",[\"id\"]=1,[\"nargs\"]=1,[\"args\"]={[1]=\"a\"}}");	// should be "a", not "[[a]]"
	}
	@Test  public void Gsub_proc_w_grouped_2() {// PURPOSE: gsub_proc failed when passing multiple matches; DATE:2013-12-01
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		Exec_gsub_regx_func_2("[[a]] [[b]]", "%[%[([^#|%]]-)%]%]"	, "A B;2");
		fxt.Test_log_rcvd(3, "000000370000006D{[\"op\"]=\"call\",[\"id\"]=1,[\"nargs\"]=1,[\"args\"]={[1]=\"a\"}}");	// should be "a", not "[[a]]"
		fxt.Test_log_rcvd(4, "000000370000006D{[\"op\"]=\"call\",[\"id\"]=1,[\"nargs\"]=1,[\"args\"]={[1]=\"b\"}}");	// should be "b", not "[[b]]"
	}
	@Test  public void Gsub_no_replace() {// PURPOSE: gsub with no replace argument should not fail; EX:d:'orse; DATE:2013-10-14
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		fxt.Init_lua_module();
		fxt.Init_lua_rcvd(Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_("text", "regx"));	// NOTE: repl, limit deliberately omitted
		fxt.Init_lua_rcvd_rv();
		fxt.Test_invoke("text");
	}
	@Test  public void Gsub_int() {	// PURPOSE: gsub with integer arg should not fail; DATE:2013-11-06
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		fxt.Init_lua_module();
		fxt.Init_lua_rcvd(Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(1, "[1]", "2", 1));	// NOTE: text is integer (lua / php are type-less)
		fxt.Init_lua_rcvd_rv();
		fxt.Test_invoke("2;1");
	}
	@Test  public void Gmatch_init() {
		fxt.Test_lib_proc(lib, Scrib_lib_ustring.Invk_gmatch_init, Object_.Ary("abcabc", "a(b)")					, "a(b);\n  false");
		fxt.Test_lib_proc(lib, Scrib_lib_ustring.Invk_gmatch_init, Object_.Ary("abcabc", "a()(b)")					, "a()(b);\n  true;false");
	}
	@Test  public void Gmatch_callback() {
		fxt.Test_lib_proc(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("abcabc", "a(b)", Scrib_kv_utl_.base1_many_(false), 0)	, "2;\n  b");
		fxt.Test_lib_proc(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("abcabc", "a(b)", Scrib_kv_utl_.base1_many_(false), 2)	, "5;\n  b");
		fxt.Test_lib_proc(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("abcabc", "a(b)", Scrib_kv_utl_.base1_many_(false), 8)	, "8;{}");
	}
	@Test  public void Gmatch_callback_nomatch() {// PURPOSE.fix: was originally returning "" instead of original String; EX:vi.d:trở_thành; DATE:2014-04-23
		fxt.Test_lib_proc(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("a", "a"			, KeyVal_.Ary_empty, 0)	, "1;\n  a");
	}
	@Test  public void Gmatch_callback_anypos() {// PURPOSE.fix: was not handling $capt argument; EX:vi.d:trở_thành; DATE:2014-04-23
		fxt.Test_lib_proc(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("a bcd e", "()(b)"	, Scrib_kv_utl_.base1_many_(true, false), 0)	, "3;\n  3;b");
	}
	@Test  public void Gsub_balanced_group() {	// PURPOSE: handle balanced group regex; EX:"%b()"; NOTE:test will fail if run in 1.6 environment; DATE:2013-12-20
		fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_gsub);
		Exec_gsub_regx("(a)", "%b()", 1, "c", "c;1");
	}
	@Test  public void Gmatch_callback__text_as_number() { // PURPOSE: Gmatch_callback must be able to take non String value; DATE:2013-12-20
		fxt.Test_lib_proc(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary(1234, "1(2)", Scrib_kv_utl_.base1_many_(false), 0)	, "2;\n  2");
	}
	private void Exec_find(String text, String regx, int bgn, boolean plain, String expd) {
		fxt.Init_lua_module();
		fxt.Init_lua_rcvd(Scrib_lib_ustring.Invk_find, Scrib_kv_utl_.base1_many_(text, regx, bgn, plain));
		fxt.Init_lua_rcvd_rv();
		fxt.Test_invoke(expd);
	}
	private void Exec_match(String text, String regx, int bgn, String expd) {
		fxt.Init_lua_module();
		fxt.Init_lua_rcvd(Scrib_lib_ustring.Invk_match, Scrib_kv_utl_.base1_many_(text, regx, bgn));
		fxt.Init_lua_rcvd_rv();
		fxt.Test_invoke(expd);
	}
	private void Exec_gsub_regx(String text, String regx, int limit, Object repl, String expd) {Exec_gsub(text, regx, limit, repl, expd);}
	private void Exec_gsub(String text, String regx, int limit, Object repl, String expd) {
		fxt.Init_lua_module();
		fxt.Init_lua_rcvd(Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(text, regx, repl, limit));
		fxt.Init_lua_rcvd_rv();
		fxt.Test_invoke(expd);
	}
	private void Exec_gsub_regx_func_0(String text, String regx, String expd) {
		fxt.Init_lua_module();
		fxt.Init_lua_rcvd(Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(text, regx, new Scrib_lua_proc("ignore_key", 1)));
		fxt.Init_lua_rcvd_raw("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;s:1:\"A\";}}");
		fxt.Init_lua_rcvd_rv();
		fxt.Test_invoke(expd);
	}
	private void Exec_gsub_regx_func_1(String text, String regx, String expd) {
		fxt.Init_lua_module();
		fxt.Init_lua_rcvd(Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(text, regx, new Scrib_lua_proc("ignore_key", 1)));
		fxt.Init_lua_rcvd_raw("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;s:1:\"A\";}}");
		fxt.Init_lua_rcvd_rv();
		fxt.Test_invoke(expd);
	}
	private void Exec_gsub_regx_func_2(String text, String regx, String expd) {
		fxt.Init_lua_module();
		fxt.Init_lua_rcvd(Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(text, regx, new Scrib_lua_proc("ignore_key", 1)));
		fxt.Init_lua_rcvd_raw("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;s:1:\"A\";}}");
		fxt.Init_lua_rcvd_raw("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;s:1:\"B\";}}");
		fxt.Init_lua_rcvd_rv();
		fxt.Init_lua_rcvd_rv();
		fxt.Test_invoke(expd);
	}
}
