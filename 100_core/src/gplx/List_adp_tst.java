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
public class List_adp_tst {
	@Before public void setup() {
		list = List_adp_.New();
		listBase = (List_adp_base)list;
	}	List_adp list; List_adp_base listBase;
	@Test  public void Add() {
		Tfds.Eq(0, list.Count());

		list.Add("0");
		Tfds.Eq(1, list.Count());
	}
	@Test  public void Add_changeCapacity() {
		int capacity = 8;
		for (int i = 0; i < capacity; i++)
			list.Add("0");
		Tfds.Eq(capacity, list.Count());
		Tfds.Eq(capacity, listBase.Capacity());

		list.Add(capacity);	// forces resize
		Tfds.Eq(capacity + 1, list.Count());
		Tfds.Eq(capacity * 2, listBase.Capacity());
	}
	@Test  public void Get_at() {
		list.Add("0");

		Tfds.Eq("0", list.Get_at(0));
	}
	@Test  public void Fetch_many() {
		list_AddMany("0", "1");

		Tfds.Eq("0", list.Get_at(0));
		Tfds.Eq("1", list.Get_at(1));
	}
	@Test  public void FetchAt_fail() {
		try {list.Get_at(0);}
		catch (Exception exc) {Err_.Noop(exc); return;}
		Tfds.Fail("Get_at should fail for out of bound index");
	}
	@Test  public void Del_at() {
		list.Add("0");
		Tfds.Eq(1, list.Count());

		list.Del_at(0);
		Tfds.Eq(0, list.Count());
	}
	@Test  public void DelAt_shiftDown() {
		list_AddMany("0", "1");
		Tfds.Eq(list.Count(), 2);

		list.Del_at(0);
		Tfds.Eq(1, list.Count());
		Tfds.Eq("1", list.Get_at(0));
	}
	@Test  public void DelAt_fail() {
		try {list.Del_at(0);}
		catch (Exception exc) {Err_.Noop(exc); return;}
		Tfds.Fail("Del_at should fail for out of bound index");
	}
	@Test  public void Del() {
		list.Add("0");
		Tfds.Eq(1, list.Count());

		list.Del("0");
		Tfds.Eq(0, list.Count());
	}
	@Test  public void Del_matchMember() {
		list_AddMany("0", "1");
		Tfds.Eq(2, list.Count());

		list.Del("1");
		Tfds.Eq(1, list.Count());
		Tfds.Eq("0", list.Get_at(0));
	}
	@Test  public void Del_matchFirst() {
		list_AddMany("0", "1", "0");
		Tfds.Eq(3, list.Count());

		list.Del("0");
		tst_Enumerator("1", "0");
	}
	@Test  public void Enumerator() {
		list_AddMany("0", "1", "2");
		tst_Enumerator("0", "1", "2");
	}
	@Test  public void Enumerator_stateLess() { // run 2x, to confirm no state is being cached
		list_AddMany("0", "1", "2");
		tst_Enumerator("0", "1", "2");
		tst_Enumerator("0", "1", "2"); 
	}
	@Test  public void Enumerator_recursive() { // confirm separate enumerator objects are used
		int pos = 0;
		list_AddMany("0", "1", "2");
		for (Object valObj : list) {
			String val = (String)valObj;
			Tfds.Eq(Int_.To_str(pos++), val);
			tst_Enumerator("0", "1", "2");
		}
	}
	@Test  public void Clear() {
		int capacity = 8;
		for (int i = 0; i < capacity + 1; i++)
			list.Add("0");
		Tfds.Eq(capacity * 2, listBase.Capacity());

		list.Clear();
		Tfds.Eq(0, list.Count());
		Tfds.Eq(16, listBase.Capacity()); // check that capacity has increased
	}
	@Test  public void Clear_empty() { // confirm no failure
		list.Clear();
		Tfds.Eq(0, list.Count());
	}
	@Test  public void Reverse() {
		list_AddMany("0", "1", "2");
		
		list.Reverse();
		tst_Enumerator("2", "1", "0");
	}
	@Test  public void Reverse_empty() {list.Reverse();}
	@Test  public void Sort() {
		list_AddMany("2", "0", "1");
		
		list.Sort();
		tst_Enumerator("0", "1", "2");
	}
	@Test  public void Sort_empty() {list.Sort();}
	@Test  public void Xto_bry() {
		list_AddMany("0", "1");
		String[] ary = (String[])list.To_ary(String.class);
		Tfds.Eq_nullNot(ary);
		Tfds.Eq(2, Array_.Len(ary));
	}
	@Test  public void XtoAry_empty() {
		String[] ary = (String[])list.To_ary(String.class);
		Tfds.Eq_nullNot(ary);
		Tfds.Eq(0, Array_.Len(ary));
	}
	@Test  public void Shuffle() {
		for (int i = 0; i < 25; i++)
			list.Add(i);

		list.Shuffle();
		int hasMovedCount = 0;
		for (int i = 0; i < list.Count(); i++) {
			int val = Int_.cast(list.Get_at(i));
			if (val != i) hasMovedCount++;
		}
		Tfds.Eq_true(hasMovedCount > 0, "all documents have the same index");	// NOTE: may still fail occasionally (1%)

		int count = list.Count();
		for (int i = 0; i < count; i++)
			list.Del(i);
		Tfds.Eq(0, list.Count(), "shuffled list does not have the same contents as original list");
	}
	@Test  public void Shuffle_empty() {list.Shuffle();}
	@Test  public void Move_to() {
		run_ClearAndAdd("0", "1", "2").run_MoveTo(0, 1).tst_Order("1", "0", "2");
		run_ClearAndAdd("0", "1", "2").run_MoveTo(0, 2).tst_Order("1", "2", "0");
		run_ClearAndAdd("0", "1", "2").run_MoveTo(2, 1).tst_Order("0", "2", "1");
		run_ClearAndAdd("0", "1", "2").run_MoveTo(2, 0).tst_Order("2", "0", "1");
	}
	@Test  public void Del_range() {
		run_ClearAndAdd("0", "1", "2", "3").tst_DelRange(0, 2, "3");
		run_ClearAndAdd("0", "1", "2", "3").tst_DelRange(0, 3);
		run_ClearAndAdd("0", "1", "2", "3").tst_DelRange(1, 2, "0", "3");
		run_ClearAndAdd("0", "1", "2", "3").tst_DelRange(1, 3, "0");
		run_ClearAndAdd("0", "1", "2", "3").tst_DelRange(0, 3);
		run_ClearAndAdd("0", "1", "2", "3").tst_DelRange(0, 0, "1", "2", "3");
	}
	void tst_DelRange(int bgn, int end, String... expd) {
		list.Del_range(bgn, end);
		Tfds.Eq_ary_str(expd, list.To_str_ary());
	}
	List_adp_tst run_ClearAndAdd(String... ary) {
		list.Clear();
		for (int i = 0; i < Array_.Len(ary); i++) {
			String val = ary[i];
			list.Add(val);
		}
		return this;
	}
	List_adp_tst run_MoveTo(int elemPos, int newPos) {list.Move_to(elemPos, newPos); return this;}
	List_adp_tst tst_Order(String... expd) {
		String[] actl = (String[])list.To_ary(String.class);
		Tfds.Eq_ary(expd, actl);
		return this;
	}
	void list_AddMany(String... ary) {
		for (int i = 0; i < Array_.Len(ary); i++) {
			String val = ary[i];
			list.Add(val);
		}
	}
	void tst_Enumerator(String... expd) {
		int pos = 0;
		int expdLength = Array_.Len(expd);
		for (int i = 0; i < expdLength; i++) {
			String val = expd[i];
			Tfds.Eq(expd[pos++], val);
		}
		Tfds.Eq(pos, expdLength);
	}
}
