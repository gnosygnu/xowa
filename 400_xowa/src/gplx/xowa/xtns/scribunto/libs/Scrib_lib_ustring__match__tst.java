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

import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.xowa.xtns.scribunto.Scrib_kv_utl_;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.engines.mocks.Mock_scrib_fxt;
import org.junit.Before;
import org.junit.Test;

public class Scrib_lib_ustring__match__tst {
	private final Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Scrib_lib lib;
	@Before public void init() {
		fxt.Clear();
		lib = fxt.Core().Lib_ustring().Init();
	}
	@Test public void Basic() {
		Exec_match("abcd"	, "bc"				, 1, "bc");							// basic
		Exec_match("abcd"	, "x"				, 1, StringUtl.NullMark);			// empty
		Exec_match("abcd"	, "a"				, 2, StringUtl.NullMark);			// bgn
		Exec_match("abcd"	, "b(c)"			, 1, "c");							// group
		Exec_match(" a b "	, "^%s*(.-)%s*$"	, 1, "a b");						// trim; NOTE: changed back from "a b;" to "a b"; DATE:2017-04-23; changed from "a b" to "a b;"; DATE:2015-01-30
		Exec_match("abcd"	, "a"				, 0, "a");							// handle 0; note that php/lua is BASE_1, but some modules pass in 0; ru.w:Module:Infocards; DATE:2013-11-08
		Exec_match("abcd"	, "."				, -1, "d");							// -1
		Exec_match("aaa"	, "a"				, 1, "a");							// should return 1st match not many
		Exec_match("aaa"	, "(a)"				, 1, "a");							// should return 1st match only; PAGE:en.d:действительное_причастие_настоящего_времени DATE:2017-04-23
		Exec_match("a b"	, "%S"				, 1, "a");							// %S was returning every match instead of 1st; PAGE:en.w:Bertrand_Russell; DATE:2014-04-02
		Exec_match(1		, "a"				, 1, StringUtl.NullMark);			// Module can pass raw ints; PAGE:en.w:Budget_of_the_European_Union; DATE:2015-01-22
		Exec_match(""		, "a?"				, 1, "");							// no results with ? should return "" not nil; PAGE:en.d:民; DATE:2015-01-30
	}
	@Test public void Args_out_of_order() {
		fxt.Test__proc__kvps__empty(lib, Scrib_lib_ustring.Invk_match, KeyValUtl.Ary(KeyVal.NewInt(2, "[a]")));
	}
	@Test public void Balanced__trailing_whitespace() {	// PURPOSE: match trailing whitespace; PAGE:en.w:Portal:Constructed_languages/Intro DATE:2018-07-02
		Exec_match("[[a]]  b", "%b[]%s*", 1, "[[a]]  ");
	}
	@Test public void Balanced__numbered_1() {	// PURPOSE: handle mix of balanced and regular capture; PAGE:en.w:Bahamas
		Exec_match("[[5]]X99Y", "%b[]X(%d)%1Y", 1, "9");
	}
	@Test public void Balanced__numbered_2() {
		Exec_match("A88B[[5]]X99Y", "A(%d)%1B%b[]X(%d)%2Y", 1, "8;9");
	}
	@Test public void Unicode_alpha() {// ISSUE#:502; DATE:2019-07-01
		Exec_match("ä"	, "%a", 1, "ä");
	}
	@Test public void Number() {// 2019-20-01|ISSUE#:802|passing integer should return NULL, not throw error
		Exec_match_obj("A"	, 0, 0, StringUtl.NullMark);
	}

//		@Test public void Match_viwiktionary() {
//			fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_ustring(), Scrib_lib_ustring.Invk_match);
//			Exec_match("tr"	, "()(r)", 1, ";");						// should return all matches
//			Exec_match("tr"	, "^([b]*).-([c]*)$", 1, ";");						// should return all matches
//		}
	private void Exec_match(Object text, String regx, int bgn, String expd) {
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_match, Scrib_kv_utl_.base1_many_(text, regx, bgn), expd);
	}
	private void Exec_match_obj(Object text, Object regx, int bgn, String expd) {
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_match, Scrib_kv_utl_.base1_many_(text, regx, bgn), expd);
	}
}
