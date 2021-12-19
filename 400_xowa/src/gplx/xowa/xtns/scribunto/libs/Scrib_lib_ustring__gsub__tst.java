/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.scribunto.libs;
import gplx.langs.regxs.Regx_adp_;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.commons.XoKeyvalUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.xtns.scribunto.Scrib_kv_utl_;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.engines.mocks.Mock_proc_stub;
import gplx.xowa.xtns.scribunto.engines.mocks.Mock_scrib_fxt;
import org.junit.Before;
import org.junit.Test;
public class Scrib_lib_ustring__gsub__tst {
	private final Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Scrib_lib lib;
	@Before public void init() {
		fxt.Clear();
		lib = fxt.Core().Lib_ustring().Init();
	}
	@Test public void Replace__basic() {
		Exec_gsub("abcd", "[a]"			, -1, "A"		, "Abcd;1");
		Exec_gsub("aaaa", "[a]"			, 2, "A"		, "AAaa;2");
		Exec_gsub("a"	, "(a)"			, 1, "%%%1"		, "%a;1");
		Exec_gsub("à{b}c", "{b}"		, 1, "b"		, "àbc;1");		// utf8
		Exec_gsub("àbc", "^%s*(.-)%s*$"	, 1, "%1"		, "àbc;1");		// utf8; regx is for trim line
		// TOMBSTONE: tested with local MW and {{#invoke:Test|test16|a|[^]|b}} -> Lua error: Missing close-bracket for character set beginning at pattern character 1.; DATE:2018-07-02
		// Exec_gsub("a"	, "[^]"			, 1, "b"		, "a;0");		// invalid regx should not fail; should return self; DATE:2013-10-20
	}
	@Test public void ReplaceEmptyWithPattern() {
		Exec_gsub("", "a", -1, "A", ";0");
	}
	@Test public void ReplaceEmptyWithFlag() {
		Exec_gsub("", "$", -1, "A", "A;1");
	}
	@Test public void Find__int() {// PURPOSE: gsub with integer arg should not fail; DATE:2013-11-06
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(1, "[1]", "2", 1), "2;1"); // NOTE: text is integer (lua / php are type-less)
	}
	@Test public void Replace__none() {// PURPOSE: gsub with no replace argument should not fail; EX:d:'orse; DATE:2013-10-14
		fxt.Test__proc__objs__flat(lib, Scrib_lib_ustring.Invk_gsub, ObjectUtl.Ary("text", "regx")						, "text");	// NOTE: repl, limit deliberately omitted
	}
	@Test public void Replace__int() {	// PURPOSE: do not fail if integer is passed in for @replace; PAGE:en.d:λύω DATE:2014-09-02
		Exec_gsub("abcd", 1	 , -1, 1		, "abcd;0");
	}
	@Test public void Replace__double() {	// PURPOSE: do not fail if double is passed in for @replace; PAGE:de.v:Wikivoyage:Wikidata/Test_Modul:Wikidata2 DATE:2016-04-21
		Exec_gsub("abcd", 1	 , -1, 1.23d	, "abcd;0");
	}
	@Test public void Replace__utf8() {	// PURPOSE:do not cut off utf8-strings PAGE:en.d:𠮟 DATE:2020-05-31
		String regx = "^[\t]*(.-)[\t]*$"; // from mwtext.trim
		Exec_gsub("𠮟a", regx, -1, "%1", "𠮟a;1"); // fails with "𠮟;1"
	}
	@Test public void Replace__anypos() {	// PURPOSE:LUAJ_PATTERN_REPLACEMENT; DATE:2019-04-16
		Exec_gsub("'''a'''b", "()'''(.-'*)'''", 1, "z", "zb;1");
	}
	@Test public void Replace__balanced_and_grouping() {	// PURPOSE:LUAJ_PATTERN_REPLACEMENT; DATE:2019-04-16
		Exec_gsub("[[b]]", "%[(%b[])%]"			, -1, "z"		, "z;1"); // NOTE: not "[z]"
	}
	@Test public void Replace__initial() {	// PURPOSE:whitespace being replaced during gsub replacement; DATE:2019-04-21
		Exec_gsub("a b c", "^%s*", -1, "x", "xa b c;1"); // fails if xabxc
	}
	@Test public void Replace__digit__superscript() {// PURPOSE: ¹ is not a \d; PAGE:en.w:Vilnius ISSUE#:617; DATE:2019-11-24;
		Exec_gsub("1796¹", "([%d]+).*", 1, "%1", "1796;1");
	}
	@Test public void Replace__table() {
		Exec_gsub("abcd", "[ac]"		, -1, KeyValUtl.Ary(KeyVal.NewStr("a", "A"), KeyVal.NewStr("c", "C"))	, "AbCd;2");
		Exec_gsub("abc" , "[ab]"		, -1, KeyValUtl.Ary(KeyVal.NewStr("a", "A"))			, "Abc;2");	// PURPOSE: match not in regex should still print itself; in this case [c] is not in tbl regex; DATE:2014-03-31
	}
	@Test public void Replace__table__match() {// PURPOSE: replace using group, not found term; EX:"b" not "%b%" PAGE:en.w:Bannered_routes_of_U.S._Route_60; DATE:2014-08-15
		Exec_gsub("a%b%c", "%%(%w+)%%"	, -1, KeyValUtl.Ary(KeyVal.NewStr("b", "B"))			, "aBc;1");
	}
	@Test public void Replace__proc__recursive() {	// PURPOSE:handle recursive gsub calls; PAGE:en.d:כלב; DATE:2016-01-22
		BryWtr bfr = BryWtr.New();
		Mock_proc__recursive proc_lvl2 = new Mock_proc__recursive(fxt, lib, bfr, 2, null);
		Mock_proc__recursive proc_lvl1 = new Mock_proc__recursive(fxt, lib, bfr, 1, proc_lvl2);
		Mock_proc__recursive proc_root = new Mock_proc__recursive(fxt, lib, bfr, 0, proc_lvl1);
		fxt.Init__cbk(proc_root, proc_lvl1, proc_lvl2);
		Exec_gsub("ab", ".", -1, proc_root.To_scrib_lua_proc(), "ab;2");	// fails if "ab;4"
		GfoTstr.Eq("0;1;2;0;1;2;", bfr.ToStrAndClear());				// fails if "0;1;1;1"
	}
	@Test public void Replace__proc__number() {	// PURPOSE:handle replace-as-number in gproc; PAGE:en.d:seven; DATE:2016-04-27
		Mock_proc__number proc = new Mock_proc__number(0);
		fxt.Init__cbk(proc);
		Exec_gsub("ab", ".", -1, proc.To_scrib_lua_proc(), "12;2");	// fails if "ab;4"
	}
	@Test public void Replace__proc__empty__once() {	// PURPOSE:if a proc returns null, do not replace anything; PAGE:en.d:tracer; DATE:2017-04-22
		Mock_proc__empty proc = new Mock_proc__empty(0, "z", "Z");
		fxt.Init__cbk(proc);
		Exec_gsub("ab", ".", -1, proc.To_scrib_lua_proc(), "ab;2");	// fails if ";2" whic means each letter (".") replaced with null
	}
	@Test public void Replace__proc__empty__many() {	// PURPOSE:replace all matches, not just first; PAGE:en.d:שלום; DATE:2017-04-24
		Mock_proc__empty proc = new Mock_proc__empty(0, "a", "A");
		fxt.Init__cbk(proc);
		Exec_gsub("aba", ".", -1, proc.To_scrib_lua_proc(), "AbA;3");	// fails if "Aba;3" whic means each that A was only matched once
	}
	@Test public void Regx__int() {	// PURPOSE: do not fail if integer is passed in for @regx; PAGE:en.d:λύω DATE:2014-09-02
		Exec_gsub("abcd", 1	 , -1, "A"		, "abcd;0");
	}
	@Test public void Regx__dash() {	// PURPOSE: "-" at end of regx should be literal; EX:[A-]; PAGE:en.d:frei DATE:2016-01-23
		Exec_gsub("abc", "[a-]", -1, "d", "dbc;1");
	}
	@Test public void Regx__word_class() {		// PURPOSE: handle %w in extended regex; PAGE:en.w:A♯_(musical_note) DATE:2015-06-10
		Exec_gsub("(a b)", "[^%w%p%s]", -1, "x", "(a b);0");	// was returning "(x x)" b/c ^%w was incorrectly matching "a" and "b"
	}
	@Test public void Regx__balanced_group() {	// PURPOSE: handle balanced group regex; EX:"%b()"; NOTE:test will fail if run in 1.6 environment; DATE:2013-12-20
		Exec_gsub("(a)", "%b()", 1, "c", "c;1");
	}
	@Test public void Regx__capture() {
		Exec_gsub("aa"			, "(a)%1"				, 1, "%0z", "aaz;1");	// capture; handle %0; PAGE:en.w:Wikipedia:Wikipedia_Signpost/Templates/Voter/testcases; DATE:2015-08-02
		Exec_gsub("aa"			, "(a)%1"				, 1, "%1z", "az;1");	// capture; handle %1+; PAGE:en.w:Wikipedia:Wikipedia_Signpost/Templates/Voter/testcases; DATE:2015-08-02
		Exec_gsub("a\"b'c\"d"	, "([\"'])(.-)%1"		, 1, "%1z", "a\"zd;1");	// capture; http://www.lua.org/pil/20.3.html; {{#invoke:test|gsub_string|a"b'c"d|(["'])(.-)%1|%1z}}
	}
	@Test public void Regx__capture_wo_group() {
		Exec_gsub("Ab", "%u", 1, "<%0>", "<A>b;1");
		Exec_gsub("Ab", "%u", 1, "<%1>", "<A>b;1"); // NOTE: %1 should be same as %0 if no matches; ISSUE#:393; DATE:2019-03-20
		Exec_gsub_fail("Ab", "%u", 1, "<%2>", "invalid capture index %2 in replacement String");
	}
	@Test public void Regx__frontier_pattern() {	// PURPOSE: handle frontier pattern; EX:"%f[%a]"; DATE:2015-07-21
		Exec_gsub("a b c", "%f[%W]", 5, "()", "a() b() c();3");
		Exec_gsub("abC DEF gHI JKm NOP", "%f[%a]%u+%f[%A]", IntUtl.MaxValue, "()", "abC () gHI JKm ();2");	// based on http://lua-users.org/wiki/FrontierPattern
	}
	@Test public void Regx__frontier_pattern_utl() {// PURPOSE: standalone test for \0 logic in frontier pattern; note that verified against PHP: echo(preg_match( "/[\w]/us", "\0" )); DATE:2015-07-21
		GfoTstr.EqObj(BoolUtl.N, Regx_adp_.Match("\0", "a"));		// \0 not matched by a
		GfoTstr.EqObj(BoolUtl.N, Regx_adp_.Match("\0", "0"));		// \0 not matched by numeric 0
		GfoTstr.EqObj(BoolUtl.N, Regx_adp_.Match("\0", "[\\w]"));	// \0 not matched by word_char
		GfoTstr.EqObj(BoolUtl.Y, Regx_adp_.Match("\0", "[\\W]"));	// \0 matched by !word_char
		GfoTstr.EqObj(BoolUtl.Y, Regx_adp_.Match("\0", "[\\x]"));	// \0 matched by any_char
		GfoTstr.EqObj(BoolUtl.Y, Regx_adp_.Match("\0", "[\\X]"));	// \0 matched by !any_char
	}
	@Test public void Luacbk__basic() {
		String text = "ad2f1e3z";
		String regx = "([1d])([2e])([3f])";
		Mock_proc__verify_args proc = new Mock_proc__verify_args(0, new Object[]{"B", "d", "2", "f"}, new Object[]{"Y", "1", "e", "3"});
		fxt.Init__cbk(proc);
		Exec_gsub(text, regx, -1, proc.To_scrib_lua_proc(), "aBYz;2");
	}
	@Test public void Luacbk__balanced() { // PURPOSE:LUAJ_PATTERN_REPLACEMENT; DATE:2019-04-16
		String text = "}a{{b}}c{{d}}";
		String regx = "%b{}"; // "()" is anypos, which inserts find_pos to results
		Mock_proc__verify_args proc = new Mock_proc__verify_args(0, new Object[]{"x", "{{b}}"}, new Object[]{"y", "{{d}}"});
		fxt.Init__cbk(proc);
		Exec_gsub(text, regx, -1, proc.To_scrib_lua_proc(), "}axcy;2");
	}
	@Test public void Luacbk__anypos() {
		String text = "ad2f1e3z";
		String regx = "()([1d])([2e])([3f])"; // "()" is anypos, which inserts find_pos to results
		Mock_proc__verify_args proc = new Mock_proc__verify_args(0
			, new Object[]{"B", 2, "d", "2", "f"}		// NOTE: changed from 1 to 2 b/c of base-1 issues;ISSUE#:726; DATE:2020-05-17;
			, new Object[]{"Y", 5, "1", "e", "3"});     // NOTE: changed from 4 to 5 b/c of base-1 issues;ISSUE#:726; DATE:2020-05-17;
		fxt.Init__cbk(proc);
		Exec_gsub(text, regx, -1, proc.To_scrib_lua_proc(), "aBYz;2");
	}
	@Test public void Luacbk__frontier__eos() { // PURPOSE:frontier pattern should not match end of string; ISSUE#:732; DATE:2020-05-28
		String text = "a";
		String regx = "(a)(%f[%s])";
		String expd = "a;0"; // fails if "b;0" when below is uncommented
		Mock_proc__verify_args proc = new Mock_proc__verify_args(0
			//, new Object[]{"b", "a", ""} // NOTE: used to require these parameters
		);
		fxt.Init__cbk(proc);
		Exec_gsub(text, regx, -1, proc.To_scrib_lua_proc(), expd);
	}
	@Test public void Luacbk__anypos__utf8() { // PURPOSE:handle UTF-8 chars with anypos match ISSUE#:726; DATE:2020-05-29
		String text = "𤭢 a";
		String regx = "()[𤭢a]()";
		String expd = "B C;2";
		Mock_proc__verify_args proc = new Mock_proc__verify_args(0
			, new Object[]{"B", 1, 2} // fails if 3 instead of 2
			, new Object[]{"C", 3, 4}
			);
		fxt.Init__cbk(proc);
		Exec_gsub(text, regx, -1, proc.To_scrib_lua_proc(), expd);
	}
//  Mock_proc__verify_args proc = new Mock_proc__verify_args(0, new Object[]{"x", "{{yes2}}"}, new Object[]{"x", "{{flagicon|USA}}"});
//  fxt.Init__cbk(proc);
//  Exec_gsub("}\n|-\n|28\n|{{yes2}}Win\n|28–0\n|style=\"text-align:left;\"|{{flagicon|USA}}", "%b{}", -1, proc.To_scrib_lua_proc(), "}axbx;2");	}
//
	private void Exec_gsub(String text, Object regx, int limit, Object repl, String expd) {
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(text, regx, repl, limit), expd);
	}
	private void Exec_gsub_fail(String text, Object regx, int limit, Object repl, String expd) {
		try {
			fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_(text, regx, repl, limit), expd);
		} catch (Exception e) {
			GfoTstr.Eq(true, StringUtl.Has(ErrUtl.ToStrLog(e), expd));
			return;
		}
		throw ErrUtl.NewArgs("expected failure");
	}
}
class Mock_proc__recursive extends Mock_proc_stub {	private final Mock_scrib_fxt fxt; private final Scrib_lib lib; private final Mock_proc__recursive inner;
	private final BryWtr bfr;
	public Mock_proc__recursive(Mock_scrib_fxt fxt, Scrib_lib lib, BryWtr bfr, int id, Mock_proc__recursive inner) {super(id, "recur");
		this.fxt = fxt; this.lib = lib; this.inner = inner;
		this.bfr = bfr;
	}
	@Override public KeyVal[] Exec_by_scrib(KeyVal[] args) {
		bfr.AddIntVariable(this.Id()).AddByteSemic();
		if (inner != null)
			fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_gsub, Scrib_kv_utl_.base1_many_("a", ".", inner.To_scrib_lua_proc(), -1), "a;1");
		return args;
	}
}
class Mock_proc__number extends Mock_proc_stub {	private int counter = 0;
	public Mock_proc__number(int id) {super(id, "number");}
	@Override public KeyVal[] Exec_by_scrib(KeyVal[] args) {
		args[0].ValSet(++counter);	// set replace-val to int
		return args;
	}
}
class Mock_proc__empty extends Mock_proc_stub {	private final String find, repl;
	public Mock_proc__empty(int id, String find, String repl) {super(id, "number");
		this.find = find;
		this.repl = repl;
	}
	@Override public KeyVal[] Exec_by_scrib(KeyVal[] args) {
		String text = args[0].ValToStrOrEmpty();
		return StringUtl.Eq(text, find) ? KeyValUtl.Ary(KeyVal.NewStr("0", repl)) : KeyValUtl.AryEmpty;
	}
}
class Mock_proc__verify_args extends Mock_proc_stub {	private final Object[][] expd_ary;
	private int expd_idx = -1;
	public Mock_proc__verify_args(int id, Object[]... expd_ary) {super(id, "number");
		this.expd_ary = expd_ary;
	}
	@Override public KeyVal[] Exec_by_scrib(KeyVal[] args) {
		Object[] expd_args = expd_ary[++expd_idx];
		Object rv = expd_args[0];
		expd_args = (Object[])ArrayUtl.Clone(expd_args, 1);
		Object[] actl_args = XoKeyvalUtl.AryToObjAryVal(args);
		GfoTstr.EqAryObjAry(expd_args, actl_args, "failed lua_cbk");
		return KeyValUtl.Ary(KeyVal.NewInt(0, rv));
	}
}
