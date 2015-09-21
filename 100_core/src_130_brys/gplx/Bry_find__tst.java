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
package gplx;
import org.junit.*;
public class Bry_find__tst {
	private Bry_find__fxt fxt = new Bry_find__fxt();
	@Test  public void Find_fwd() {
		fxt.Test_Find_fwd("abcba",  "b", 0, 1);
		fxt.Test_Find_fwd("abcba",  "z", 0, -1);
		fxt.Test_Find_fwd("abcba",  "b", 1, 1);
		fxt.Test_Find_fwd("abcba",  "b", 2, 3);
		fxt.Test_Find_fwd("abcba",  "b", 4, -1);
		fxt.Test_Find_fwd("abcba", "zb", 4, -1);
		fxt.Test_Find_fwd("abcba",  "a", 6, -1);
	}
	@Test  public void Find_bwd() {
		fxt.Test_Find_bwd("abcba",  "b", 4, 3);
		fxt.Test_Find_bwd("abcba",  "z", 4, -1);
		fxt.Test_Find_bwd("abcba",  "b", 3, 1);
		fxt.Test_Find_bwd("abcba",  "b", 2, 1);
		fxt.Test_Find_bwd("abcba",  "b", 0, -1);
		fxt.Test_Find_bwd("abcba", "zb", 4, -1);
		fxt.Test_Find_fwd("abcba",  "a", -1, -1);
		fxt.Test_Find_bwd("abcba", "ab", 4, 0);
	}
	@Test  public void Find_bwd_last_ws() {
		fxt.Test_Find_bwd_1st_ws_tst("a b"			, 2, 1);					// basic
		fxt.Test_Find_bwd_1st_ws_tst("a   b"		, 3, 1);					// multiple
		fxt.Test_Find_bwd_1st_ws_tst("ab"			, 1, Bry_.NotFound);		// none
	}
	@Test  public void Trim_fwd_space_tab() {
		fxt.Test_Trim_fwd_space_tab(" a b"			, 1);
		fxt.Test_Trim_fwd_space_tab("\ta b"			, 1);
		fxt.Test_Trim_fwd_space_tab(" \ta b"		, 2);
		fxt.Test_Trim_fwd_space_tab("a bc"			, 0);
		fxt.Test_Trim_fwd_space_tab(""				, 0);
		fxt.Test_Trim_fwd_space_tab(" \t"			, 2);
	}
	@Test  public void Trim_bwd_space_tab() {
		fxt.Test_Trim_bwd_space_tab("a b "			, 3);
		fxt.Test_Trim_bwd_space_tab("a b\t"			, 3);
		fxt.Test_Trim_bwd_space_tab("a b\t "		, 3);
		fxt.Test_Trim_bwd_space_tab("a bc"			, 4);
		fxt.Test_Trim_bwd_space_tab(""				, 0);
		fxt.Test_Trim_bwd_space_tab(" \t"			, 0);
	}
}
class Bry_find__fxt {
	public void Test_Find_fwd(String src, String lkp, int bgn, int expd) {Tfds.Eq(expd, Bry_find_.Find_fwd(Bry_.new_u8(src), Bry_.new_u8(lkp), bgn));}
	public void Test_Find_bwd(String src, String lkp, int bgn, int expd) {Tfds.Eq(expd, Bry_find_.Find_bwd(Bry_.new_u8(src), Bry_.new_u8(lkp), bgn));}
	public void Test_Find_bwd_1st_ws_tst(String src, int pos, int expd) {Tfds.Eq(expd, Bry_find_.Find_bwd_last_ws(Bry_.new_a7(src), pos));}
	public void Test_Trim_bwd_space_tab(String raw_str, int expd) {
		byte[] raw_bry = Bry_.new_u8(raw_str);
		int actl = Bry_find_.Trim_bwd_space_tab(raw_bry, raw_bry.length, 0);
		Tfds.Eq(expd, actl, raw_str);
	}
	public void Test_Trim_fwd_space_tab(String raw_str, int expd) {
		byte[] raw_bry = Bry_.new_u8(raw_str);
		int actl = Bry_find_.Trim_fwd_space_tab(raw_bry, 0, raw_bry.length);
		Tfds.Eq(expd, actl, raw_str);
	}
}
