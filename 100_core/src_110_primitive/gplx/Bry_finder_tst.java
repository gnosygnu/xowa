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
import gplx.texts.*;
public class Bry_finder_tst {
	@Test  public void Find_fwd() {
		tst_Find_fwd("abcba",  "b", 0, 1);
		tst_Find_fwd("abcba",  "z", 0, -1);
		tst_Find_fwd("abcba",  "b", 1, 1);
		tst_Find_fwd("abcba",  "b", 2, 3);
		tst_Find_fwd("abcba",  "b", 4, -1);
		tst_Find_fwd("abcba", "zb", 4, -1);
		tst_Find_fwd("abcba",  "a", 6, -1);
	}	void tst_Find_fwd(String src, String lkp, int bgn, int expd) {Tfds.Eq(expd, Bry_finder.Find_fwd(Bry_.new_utf8_(src), Bry_.new_utf8_(lkp), bgn));}
	@Test  public void Find_bwd() {
		tst_Find_bwd("abcba",  "b", 4, 3);
		tst_Find_bwd("abcba",  "z", 4, -1);
		tst_Find_bwd("abcba",  "b", 3, 1);
		tst_Find_bwd("abcba",  "b", 2, 1);
		tst_Find_bwd("abcba",  "b", 0, -1);
		tst_Find_bwd("abcba", "zb", 4, -1);
		tst_Find_fwd("abcba",  "a", -1, -1);
		tst_Find_bwd("abcba", "ab", 4, 0);
	}	void tst_Find_bwd(String src, String lkp, int bgn, int expd) {Tfds.Eq(expd, Bry_finder.Find_bwd(Bry_.new_utf8_(src), Bry_.new_utf8_(lkp), bgn));}
	@Test  public void Find_bwd_last_ws() {
		Find_bwd_1st_ws_tst("a b"		, 2, 1);					// basic
		Find_bwd_1st_ws_tst("a   b"		, 3, 1);					// multiple
		Find_bwd_1st_ws_tst("ab"			, 1, Bry_.NotFound);	// none
	}
	void Find_bwd_1st_ws_tst(String src, int pos, int expd) {Tfds.Eq(expd, Bry_finder.Find_bwd_last_ws(Bry_.new_ascii_(src), pos));}
}
