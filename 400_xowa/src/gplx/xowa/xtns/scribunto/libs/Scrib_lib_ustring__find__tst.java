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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*; import gplx.xowa.xtns.scribunto.engines.mocks.*;
public class Scrib_lib_ustring__find__tst {
	private final Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Scrib_lib lib;
	@Before public void init() {
		fxt.Clear();
		lib = fxt.Core().Lib_ustring().Init();
	}
	@Test  public void Basic() {
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
	@Test   public void Arg_int() {	// PURPOSE: allow int find; PAGE:ro.w:Innsbruck DATE:2015-09-12
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_find, Scrib_kv_utl_.base1_many_(123, "2", 1, Bool_.N), "2;2");
	}
	@Test   public void Return_int() {
		fxt.Test__proc__kvps__vals(lib, Scrib_lib_ustring.Invk_find, Scrib_kv_utl_.base1_many_("a", "()", 2, Bool_.N), 2, 1, 2);
	}
	@Test  public void Surrogate() {	// PURPOSE: handle surrogates in Find PAGE:zh.w:南北鐵路_(越南); DATE:2014-08-28
		Exec_find("aé𡼾\nbî𡼾\n"	, "\n"		, 1, Bool_.N, "4;4");				// 4 b/c \n starts at pos 4 (super 1)
		Exec_find("aé𡼾\nbî𡼾\n"	, "\n"		, 5, Bool_.N, "8;8");				// 8 b/c \n starts at pos 8 (super 1)
	}
	private void Exec_find(String text, String regx, int bgn, boolean plain, String expd) {
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_ustring.Invk_find, Scrib_kv_utl_.base1_many_(text, regx, bgn, plain), expd);
	}
}
