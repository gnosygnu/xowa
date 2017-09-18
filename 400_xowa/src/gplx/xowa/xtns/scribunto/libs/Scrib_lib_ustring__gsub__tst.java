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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*; import gplx.langs.regxs.*; import gplx.xowa.xtns.scribunto.engines.mocks.*;
public class Scrib_lib_ustring__gsub__tst {
	private final    Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Scrib_lib lib;
	@Before public void init() {
		fxt.Clear();
		lib = fxt.Core().Lib_ustring().Init();
	}
	@Test  public void Replace__basic() {
		Exec_gsub("abcd", "[a]"			, -1, "A"		, "Abcd;1");
		Exec_gsub("aaaa", "[a]"			, 2, "A"		, "AAaa;2");
		Exec_gsub("a"	, "(a)"			, 1, "%%%1"		, "%a;1");
		Exec_gsub("à{b}c", "{b}"		, 1, "b"		, "àbc;1");		// utf8
		Exec_gsub("àbc", "^%s*(.-)%s*$"	, 1, "%1"		, "àbc;1");		// utf8; regx is for trim line
		Exec_gsub("a"	, "[^]"			, 1, "b"		, "a;0");		// invalid regx should not fail; should return self; DATE:2013-10-20
	}
	@Test  public void Replace__none() {// PURPOSE: gsub with no replace argument should not fail; EX:d:'orse; DATE:2013-10-14
		fxt.Test__proc__objs__flat(lib, Scrib_lib_ustring.Invk_gsub, Object_.Ary("text", "regx")						, "text");	// NOTE: repl, limit deliberately omitted
	}
	@Test  public void Replace__int() {	// PURPOSE: do not fail if integer is passed in for @replace; PAGE:en.d:λύω DATE:2014-09-02
		Exec_gsub("abcd", 1	 , -1, 1		, "abcd;0");
	}
	@Test  public void Replace__double() {	// PURPOSE: do not fail if double is passed in for @replace; PAGE:de.v:Wikivoyage:Wikidata/Test_Modul:Wikidata2 DATE:2016-04-21
		Exec_gsub("abcd", 1	 , -1, 1.23d	, "abcd;0");
	}
	@Test  public void Replace__table() {
		Exec_gsub("abcd", "[ac]"		, -1, Scrib_kv_utl_.flat_many_("a", "A", "c", "C")	, "AbCd;2");
		Exec_gsub("abc" , "[ab]"		, -1, Scrib_kv_utl_.flat_many_("a", "A")			, "Abc;2");	// PURPOSE: match not in regex should still print itself; in this case [c] is not in tbl regex; DATE:2014-03-31
	}
	@Test  public void Replace__table__match() {// PURPOSE: replace using group, not found term; EX:"b" not "%b%" PAGE:en.w:Bannered_routes_of_U.S._Route_60; DATE:2014-08-15
		Exec_gsub("a%b%c", "%%(%w+)%%"	, -1, Scrib_kv_utl_.flat_many_("b", "B")			, "aBc;1");
	}
	@Test  public void Replace__proc__recursive() {	// PURPOSE:handle recursive gsub calls; PAGE:en.d:כלב; DATE:2016-01-22
		Bry_bfr bfr = Bry_bfr_.New();
		Mock_proc__recursive proc_lvl2 = new Mock_proc__recursive(fxt, lib, bfr, 2, null);
		Mock_proc__recursive proc_lvl1 = new Mock_proc__recursive(fxt, lib, bfr, 1, proc_lvl2);
		Mock_proc__recursive proc_root = new Mock_proc__recursive(fxt, lib, bfr, 0, proc_lvl1);
		fxt.Init__cbk(proc_root, proc_lvl1, proc_lvl2);
		Exec_gsub("ab", ".", -1, proc_root.To_scrib_lua_proc(), "ab;2");	// fails if "ab;4"
		Tfds.Eq_str("0;1;2;0;1;2;", bfr.To_str_and_clear());				// fails if "0;1;1;1"
	}
	@Test  public void Replace__proc__number() {	// PURPOSE:handle replace-as-number in gproc; PAGE:en.d:seven; DATE:2016-04-27
		Mock_proc__number proc = new Mock_proc__number(0);
		fxt.Init__cbk(proc);
		Exec_gsub("ab", ".", -1, proc.To_scrib_lua_proc(), "12;2");	// fails if "ab;4"
	}
	@Test  public void Replace__proc__empty__once() {	// PURPOSE:if a proc returns null, do not replace anything; PAGE:en.d:tracer; DATE:2017-04-22
		Mock_proc__empty proc = new Mock_proc__empty(0, "z", "Z");
		fxt.Init__cbk(proc);
		Exec_gsub("ab", ".", -1, proc.To_scrib_lua_proc(), "ab;2");	// fails if ";2" whic means each letter (".") replaced with null
	}
	@Test  public void Replace__proc__empty__many() {	// PURPOSE:replace all matches, not just first; PAGE:en.d:שלום; DATE:2017-04-24
		Mock_proc__empty proc = new Mock_proc__empty(0, "a", "A");
		fxt.Init__cbk(proc);
		Exec_gsub("aba", ".", -1, proc.To_scrib_lua_proc(), "AbA;3");	// fails if "Aba;3" whic means each that A was only matched once
	}
	@Test  public void Regx__int() {	// PURPOSE: do not fail if integer is passed in for @regx; PAGE:en.d:λύω DATE:2014-09-02
		Exec_gsub("abcd", 1	 , -1, "A"		, "abcd;0");
	}
	@Test  public void Regx__dash() {	// PURPOSE: "-" at end of regx should be literal; EX:[A-]; PAGE:en.d:frei DATE:2016-01-23
		Exec_gsub("abc", "[a-]", -1, "d", "dbc;1");
	}
	@Test   public void Regx__word_class() {		// PURPOSE: handle %w in extended regex; PAGE:en.w:A♯_(musical_note) DATE:2015-06-10
		Exec_gsub("(a b)", "[^%w%p%s]", -1, "x", "(a b);0");	// was returning "(x x)" b/c ^%w was incorrectly matching "a" and "b"
	}
	@Test  public void Regx__balanced_group() {	// PURPOSE: handle balanced group regex; EX:"%b()"; NOTE:test will fail if run in 1.6 environment; DATE:2013-12-20
		Exec_gsub("(a)", "%b()", 1, "c", "c;1");
	}
	@Test  public void Regx__capture() {
		Exec_gsub("aa"			, "(a)%1"				, 1, "%0z", "aaz;1");	// capture; handle %0; PAGE:en.w:Wikipedia:Wikipedia_Signpost/Templates/Voter/testcases; DATE:2015-08-02
		Exec_gsub("aa"			, "(a)%1"				, 1, "%1z", "az;1");	// capture; handle %1+; PAGE:en.w:Wikipedia:Wikipedia_Signpost/Templates/Voter/testcases; DATE:2015-08-02
		Exec_gsub("a\"b'c\"d"	, "([\"'])(.-)%1"		, 1, "%1z", "a\"zd;1");	// capture; http://www.lua.org/pil/20.3.html; {{#invoke:test|gsub_string|a"b'c"d|(["'])(.-)%1|%1z}}
	}
	@Test  public void Regx__frontier_pattern() {	// PURPOSE: handle frontier pattern; EX:"%f[%a]"; DATE:2015-07-21
		Exec_gsub("a b c", "%f[%W]", 5, "()", "a() b() c();3");
		Exec_gsub("abC DEF gHI JKm NOP", "%f[%a]%u+%f[%A]", Int_.Max_value, "()", "abC () gHI JKm ();2");	// based on http://lua-users.org/wiki/FrontierPattern
	}
	@Test  public void Regx__frontier_pattern_utl() {// PURPOSE: standalone test for \0 logic in frontier pattern; note that verified against PHP: echo(preg_match( "/[\w]/us", "\0" )); DATE:2015-07-21
		Tfds.Eq(Bool_.N, Regx_adp_.Match("\0", "a"));		// \0 not matched by a
		Tfds.Eq(Bool_.N, Regx_adp_.Match("\0", "0"));		// \0 not matched by numeric 0
		Tfds.Eq(Bool_.N, Regx_adp_.Match("\0", "[\\w]"));	// \0 not matched by word_char
		Tfds.Eq(Bool_.Y, Regx_adp_.Match("\0", "[\\W]"));	// \0 matched by !word_char
		Tfds.Eq(Bool_.Y, Regx_adp_.Match("\0", "[\\x]"));	// \0 matched by any_char
		Tfds.Eq(Bool_.Y, Regx_adp_.Match("\0", "[\\X]"));	// \0 matched by !any_char
	}
	private void Exec_gsub(String text, Object regx, int limit, Object repl, String expd) {
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(text, regx, repl, limit), expd);
	}
}
class Mock_proc__recursive extends Mock_proc_fxt {	private final    Mock_scrib_fxt fxt; private final    Scrib_lib lib; private final    Mock_proc__recursive inner;
	private final    Bry_bfr bfr;
	public Mock_proc__recursive(Mock_scrib_fxt fxt, Scrib_lib lib, Bry_bfr bfr, int id, Mock_proc__recursive inner) {super(id, "recur");
		this.fxt = fxt; this.lib = lib; this.inner = inner;
		this.bfr = bfr;
	}
	@Override public Keyval[] Exec_by_scrib(Keyval[] args) {
		bfr.Add_int_variable(this.Id()).Add_byte_semic();
		if (inner != null)
			fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_("a", ".", inner.To_scrib_lua_proc(), -1), "a;1");
		return args;
	}
}
class Mock_proc__number extends Mock_proc_fxt {	private int counter = 0;
	public Mock_proc__number(int id) {super(id, "number");}
	@Override public Keyval[] Exec_by_scrib(Keyval[] args) {
		args[0].Val_(++counter);	// set replace-val to int
		return args;
	}
}
class Mock_proc__empty extends Mock_proc_fxt {	private final    String find, repl;
	public Mock_proc__empty(int id, String find, String repl) {super(id, "number");
		this.find = find;
		this.repl = repl;
	}
	@Override public Keyval[] Exec_by_scrib(Keyval[] args) {
		String text = args[0].Val_to_str_or_empty();
		return String_.Eq(text, find) ? Keyval_.Ary(Keyval_.new_("0", repl)) : Keyval_.Ary_empty;
	}
}
