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
public class Array__tst {
	@Test  public void Resize_add() {
		tst_Resize_add(ary_(), ary_(1), ary_(1));		// 0 + 1 = 1
		tst_Resize_add(ary_(0), ary_(), ary_(0));		// 1 + 0 = 1
		tst_Resize_add(ary_(0), ary_(1), ary_(0, 1));	// 1 + 1 = 2
	}	void tst_Resize_add(int[] source, int[] added, int[] expd) {Tfds.Eq_ary(expd, (int[])Array_.Resize_add(source, added));}
	@Test  public void Resize() {
		tst_Resize(ary_(0), 0, ary_());				// 1 -> 0
		tst_Resize(ary_(0, 1), 1, ary_(0));			// 2 -> 1
	}	void tst_Resize(int[] source, int length, int[] expd) {Tfds.Eq_ary(expd, (int[])Array_.Resize(source, length));}
	@Test  public void Insert() {
		tst_Insert(ary_obj(0, 1, 4, 5), ary_obj(2, 3), 2, ary_obj(0, 1, 2, 3, 4, 5));
	}	void tst_Insert(Object[] cur, Object[] add, int addPos, Object[] expd) {Tfds.Eq_ary(expd, Array_.Insert(cur, add, addPos));}
	@Test  public void ReplaceInsert() {
		tst_ReplaceInsert(ary_obj(0, 1, 4, 5)		, ary_obj(1, 2, 3), 1, 1, ary_obj(0, 1, 2, 3, 4, 5));
		tst_ReplaceInsert(ary_obj(0, 1, 2, 4, 5)	, ary_obj(1, 2, 3), 1, 2, ary_obj(0, 1, 2, 3, 4, 5));
		tst_ReplaceInsert(ary_obj(0, 1, 2, 3, 4, 5)	, ary_obj(1, 2, 3), 1, 3, ary_obj(0, 1, 2, 3, 4, 5));
		tst_ReplaceInsert(ary_obj(0, 1, 9, 4, 5)	, ary_obj(2, 3)   , 2, 1, ary_obj(0, 1, 2, 3, 4, 5));
	}	void tst_ReplaceInsert(Object[] cur, Object[] add, int curReplacePos, int addInsertPos, Object[] expd) {Tfds.Eq_ary(expd, Array_.ReplaceInsert(cur, add, curReplacePos, addInsertPos));}
	Object[] ary_obj(Object... ary) {return ary;}			
	int[] ary_(int... ary) {return ary;}
}
