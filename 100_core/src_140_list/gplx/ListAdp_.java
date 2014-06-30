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
import gplx.lists.*; /*EnumerAble,ComparerAble*/
public class ListAdp_ {
	public static ListAdp as_(Object obj) {return obj instanceof ListAdp ? (ListAdp)obj : null;}
	public static ListAdp new_() {return new ListAdp_obj();}
	public static ListAdp size_(int v) {return new ListAdp_obj(v);}
	public static ListAdp many_(Object... ary) {return new ListAdp_obj().AddMany(ary);}
	public static final ListAdp Null = new ListAdp_null();
	public static void DelAt_last(ListAdp list) {list.DelAt(list.Count() - 1);}
	public static Object Pop(ListAdp list) {
		int lastIdx = list.Count() - 1;
		Object rv = list.FetchAt(lastIdx);
		list.DelAt(lastIdx);
		return rv;
	}
	public static Object Pop_first(ListAdp list) {	// NOTE: dirty way of implementing FIFO queue; should not be used with lists with many members
		Object rv = list.FetchAt(0);
		list.DelAt(0);
		return rv;
	}
	public static void DisposeAll(ListAdp list) {
		for (int i = 0; i < list.Count(); i++)
			((RlsAble)list.FetchAt(i)).Rls();
	}
	public static ListAdp new_ary_(Object ary) {	
		int ary_len = Array_.Len(ary);
		ListAdp rv = size_(ary_len);
		for (int i = 0; i < ary_len; i++)
			rv.Add(Array_.Get(ary, i));
		return rv;
	}
	public static final int Capacity_initial = 8;
	public static final int NotFound = -1, Base1 = 1, LastIdxOffset = 1, CountToPos = 1;
}
class ListAdp_obj extends ListAdp_base implements ListAdp {
	public ListAdp_obj() {super();}		
	public ListAdp_obj(int v) {super(v);}	
}
class ListAdp_null implements ListAdp {
	public boolean Has_none() {return true;}
	public int Count() {return 0;}
	public Object FetchAt(int i) {return null;}
	public Object FetchAtLast() {return null;}
	public Object FetchAtOr(int i, Object or) {return null;}
	public Object PopLast() {return null;}
	public void Add(Object o) {}
	public void AddAt(int i, Object o) {}
	public ListAdp AddMany(Object... ary) {return this;}
	public void Del(Object o) {}
	public void DelAt(int i) {}
	public void Del_range(int bgn, int end) {}
	public void Clear() {}
	public void Clear_max(int max) {}
	public int LastIndex() {return -1;}
	public int IndexOf(Object o) {return ListAdp_.NotFound;}
	public void MoveTo(int elemPos, int newPos) {}
	public boolean RangeCheck(int v) {return false;}
	public void ResizeBounds(int i) {}
	public Object XtoAry(Class<?> memberType) {return Object_.Ary_empty;}
	public Object XtoAryAndClear(Class<?> memberType) {return Object_.Ary_empty;}
	public String XtoStr() {return "< NULL LIST >";}
	public String[] XtoStrAry() {return new String[0];}
	public java.util.Iterator iterator() {return Iterator_null._;}
	public void Reverse() {}
	public void SetAt(int i, Object o) {}
	public void Sort() {}
	public void SortBy(ComparerAble comparer) {}
	public void Shuffle() {}
}
