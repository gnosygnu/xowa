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
	}	void tst_ReplaceInsert(Object[] cur, Object[] add, int curReplacePos, int addInsertPos, Object[] expd) {Tfds.Eq_ary(expd, Array_.Replace_insert(cur, add, curReplacePos, addInsertPos));}
	Object[] ary_obj(Object... ary) {return ary;}			
	int[] ary_(int... ary) {return ary;}
}
