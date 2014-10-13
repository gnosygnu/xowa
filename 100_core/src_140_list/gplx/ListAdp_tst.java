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
public class ListAdp_tst {
	@Before public void setup() {
		list = ListAdp_.new_();
		listBase = (ListAdp_base)list;
	}	ListAdp list; ListAdp_base listBase;
	@Test  public void Add() {
		Tfds.Eq(0, list.Count());

		list.Add("0");
		Tfds.Eq(1, list.Count());
	}
	@Test  public void Add_changeCapacity() {
		int capacity = ListAdp_.Capacity_initial;
		for (int i = 0; i < capacity; i++)
			list.Add("0");
		Tfds.Eq(capacity, list.Count());
		Tfds.Eq(capacity, listBase.Capacity());

		list.Add(capacity);	// forces resize
		Tfds.Eq(capacity + 1, list.Count());
		Tfds.Eq(capacity * 2, listBase.Capacity());
	}
	@Test  public void FetchAt() {
		list.Add("0");

		Tfds.Eq("0", list.FetchAt(0));
	}
	@Test  public void Fetch_many() {
		list_AddMany("0", "1");

		Tfds.Eq("0", list.FetchAt(0));
		Tfds.Eq("1", list.FetchAt(1));
	}
	@Test  public void FetchAt_fail() {
		try {list.FetchAt(0);}
		catch (Exception exc) {Err_.Noop(exc); return;}
		Tfds.Fail("FetchAt should fail for out of bound index");
	}
	@Test  public void DelAt() {
		list.Add("0");
		Tfds.Eq(1, list.Count());

		list.DelAt(0);
		Tfds.Eq(0, list.Count());
	}
	@Test  public void DelAt_shiftDown() {
		list_AddMany("0", "1");
		Tfds.Eq(list.Count(), 2);

		list.DelAt(0);
		Tfds.Eq(1, list.Count());
		Tfds.Eq("1", list.FetchAt(0));
	}
	@Test  public void DelAt_fail() {
		try {list.DelAt(0);}
		catch (Exception exc) {Err_.Noop(exc); return;}
		Tfds.Fail("DelAt should fail for out of bound index");
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
		Tfds.Eq("0", list.FetchAt(0));
	}
	@Test  public void Del_matchFirst() {
		list_AddMany("0", "1", "0");
		Tfds.Eq(3, list.Count());

		list.Del("0");
		tst_Enumerator("1", "0");
	}
	@Test  public void Has_none() {
		Tfds.Eq_true(list.Has_none());
		list.Add("0");
		Tfds.Eq_false(list.Has_none());
		list.DelAt(0);
		Tfds.Eq_true(list.Has_none());
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
			Tfds.Eq(Int_.Xto_str(pos++), val);
			tst_Enumerator("0", "1", "2");
		}
	}
	@Test  public void Clear() {
		int capacity = ListAdp_.Capacity_initial;
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
		String[] ary = (String[])list.Xto_ary(String.class);
		Tfds.Eq_nullNot(ary);
		Tfds.Eq(2, Array_.Len(ary));
	}
	@Test  public void XtoAry_empty() {
		String[] ary = (String[])list.Xto_ary(String.class);
		Tfds.Eq_nullNot(ary);
		Tfds.Eq(0, Array_.Len(ary));
	}
	@Test  public void XtoStr() {
		list_AddMany("0", "1", "2");
		Tfds.Eq("0\r\n1\r\n2", list.XtoStr());
	}
	@Test  public void XtoStr_empty() {
		Tfds.Eq("", list.XtoStr());
	}
	@Test  public void Shuffle() {
		for (int i = 0; i < 25; i++)
			list.Add(i);

		list.Shuffle();
		int hasMovedCount = 0;
		for (int i = 0; i < list.Count(); i++) {
			int val = Int_.cast_(list.FetchAt(i));
			if (val != i) hasMovedCount++;
		}
		Tfds.Eq_true(hasMovedCount > 0, "all documents have the same index");	// NOTE: may still fail occasionally (1%)

		int count = list.Count();
		for (int i = 0; i < count; i++)
			list.Del(i);
		Tfds.Eq(0, list.Count(), "shuffled list does not have the same contents as original list");
	}
	@Test  public void Shuffle_empty() {list.Shuffle();}
	@Test  public void MoveTo() {
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
		Tfds.Eq_ary_str(expd, list.XtoStrAry());
	}
	ListAdp_tst run_ClearAndAdd(String... ary) {
		list.Clear();
		for (int i = 0; i < Array_.Len(ary); i++) {
			String val = ary[i];
			list.Add(val);
		}
		return this;
	}
	ListAdp_tst run_MoveTo(int elemPos, int newPos) {list.MoveTo(elemPos, newPos); return this;}
	ListAdp_tst tst_Order(String... expd) {
		String[] actl = (String[])list.Xto_ary(String.class);
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
