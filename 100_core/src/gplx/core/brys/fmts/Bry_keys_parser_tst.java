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
package gplx.core.brys.fmts; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
import org.junit.*;
public class Bry_keys_parser_tst {
	private final    Bry_keys_parser_fxt fxt = new Bry_keys_parser_fxt();
	@Test  public void None()			{fxt.Test("a");}
	@Test  public void One()			{fxt.Test("~{a}"				, "a");}
	@Test  public void Many()			{fxt.Test("~{a}b~{c}d~{e}"		, "a", "c", "e");}
	@Test  public void Dupe()			{fxt.Test("~{a}b~{a}"			, "a");}
	@Test  public void Bug__space()		{fxt.Test("~{a}~{b} ~{c}"		, "a", "b", "c");}	// DATE:2016-08-02
}
class Bry_keys_parser_fxt {
	public void Test(String fmt, String... expd) {
		Tfds.Eq_ary(expd, String_.Ary(Bry_fmt_parser_.Parse_keys(Bry_.new_u8(fmt))));
	}
}
