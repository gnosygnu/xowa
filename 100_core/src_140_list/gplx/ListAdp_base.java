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
import gplx.lists.*;
public abstract class ListAdp_base implements ListAdp, GfoInvkAble {
	public Object Xto_ary_and_clear(Class<?> memberType) {Object rv = Xto_ary(memberType); this.Clear(); return rv;}
	public Object Xto_ary(Class<?> memberType) {
		Object rv = Array_.Create(memberType, count);
		for (int i = 0; i < count; i++)
			Array_.Set(rv, i, list[i]);
		return rv;
	}
	public Object[] Xto_obj_ary() {
		Object[] rv = new Object[count];
		for (int i = 0; i < count; ++i)
			rv[i] = list[i];
		return rv;
	}
	public java.util.Iterator iterator() {
		if (count == 0)
			return Iterator_null._;
		else
			return new Iterator_objAry(list, count);
	}
	public ListAdp AddMany(Object... ary) {for (Object o : ary) Add_base(o); return this;}

	public boolean Has_none() {return count == 0;}
	public int Idx_last() {return Count() - 1;}
	public int Count() {return count;}
	public int LastIndex() {return count - 1;}
	protected Object FetchAt_base(int index) {if (index >= count || index < 0) throw Err_.missing_idx_(index, count);
		return list[index];
	}
	protected void Add_base(Object o) {
		if (count == Array_.LenAry(list)) Resize_expand();
		list[count] = o;
		count++;
	}
	protected int Del_base(Object o) {
		int index = IndexOf_base(o); if (index == ListAdp_.NotFound) return ListAdp_.NotFound;
		this.DelAt(index);
		return index;
	}
	public void Del_range(int delBgn, int delEnd) {
		BoundsChk(delBgn, delEnd, count);
		if (delBgn == 0 && delEnd == count - 1) {	// entire list deleted; call .Clear, else will have 0 elem array
			this.Clear();
			return;
		}
		int delLen = (delEnd - delBgn) + 1; // EX: 0,2 creates 3 len ary
		int newLen = count - delLen;
		Object[] newList = new Object[newLen];
		if (delBgn != 0)			// copy elements < delBgn; skip if delBgn == 0
			Array_.CopyTo(list, 0, newList, 0, delBgn);
		if (delEnd != count -1 )	// copy elements > delEnd; skip if delEnd == lastIdx			
			Array_.CopyTo(list, delEnd + 1, newList, delBgn, newLen - delBgn);
		list = newList;
		count = list.length;
	}
	public Object PopLast() {
		int lastIdx = count - 1;
		Object rv = FetchAt(lastIdx);
		DelAt(lastIdx);
		return rv;
	}
	void BoundsChk(int bgn, int end, int len) {
		if (	bgn >= 0 && bgn < len
			&&	end >= 0 && end < len
			&&	bgn <= end
			)	return;
		throw Err_.new_("bounds check failed").Add("bgn", bgn).Add("end", end).Add("len", len);
	}
	public boolean RangeCheck(int v) {return v >= 0 && v < count;}
	protected int IndexOf_base(Object o) {
		for (int i = 0; i < count; i++)
			if (Object_.Eq(list[i], o)) return i;
		return ListAdp_.NotFound;
	}
	@gplx.Virtual public void Clear() {
		for (int i = 0; i < count; i++)
			list[i] = null;
		count = 0;
//			if (Capacity() != ListAdp_.Capacity_initial) list = new Object[ListAdp_.Capacity_initial]; // COMMENT:2012-06-16
	}
	public void Clear_max(int max) {this.Clear(); if (Capacity() > max) list = new Object[max];}
	@gplx.Virtual public void DelAt(int index) {if (index >= count || index < 0) throw Err_.missing_idx_(index, count);
		Collapse(index);
		count--;
	}
	public void MoveTo(int src, int trg) {if (src >= count || src < 0) throw Err_.missing_idx_(src, count); if (trg >= count || trg < 0) throw Err_.missing_idx_(trg, count);
		if (src == trg) return;	// position not changed
		Object o = list[src];
		int dif = trg > src ? 1 : -1;
		for (int i = src; i != trg; i += dif) 
			list[i] = list[i + dif];
		list[trg] = o;
	}
	protected void AddAt_base(int pos, Object o) {
		if (count + 1 >= Array_.LenAry(list)) Resize_expand();
		for (int i = count; i > pos; i--)
			list[i] = list[i - 1];
		list[pos] = o;
		count = count + 1;
	}
	public void ResizeBounds(int i) {
		Resize_expand(i);
	}
	public String XtoStr() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < count; i++) {
			if (i != 0) sb.Add_char_crlf();
			Object val = list[i];
			sb.Add_obj(Object_.Xto_str_strict_or_empty(val));
		}
		return sb.XtoStr();
	}
	public void SetAt(int i, Object o) {list[i] = o;}
	public void Sort() {SortBy(null);}
	public void SortBy(ComparerAble comparer) {ListAdp_Sorter.new_().Sort(list, count, true, comparer);}
	public void Reverse() {
		int mid = count / 2;				// no need to reverse pivot; ex: for 3 elements, only 1 and 3 need to be exchanged; 2 stays inplace
		for (int lhs = 0; lhs < mid; lhs++) {
			int rhs = count - lhs - 1;		// -1 b/c list[count] is not real element
			Object temp = list[lhs];
			list[lhs] = list[rhs];
			list[rhs] = temp;
		}
	}
	@gplx.Virtual public void Shuffle() {// REF: Fisher-Yates shuffle
		RandomAdp random = RandomAdp_.new_();
		for (int i = count; i > 1; i--) {
			int rndIdx = random.Next(i);
			Object tmp = list[rndIdx];
			list[rndIdx] = list[i-1];
			list[i-1] = tmp;
		}
	}
	public String[] XtoStrAry() {return (String[])Xto_ary(String.class);}
	public Object FetchAt(int i) {return FetchAt_base(i);}
	public Object FetchAtLast() {if (count == 0) throw Err_.invalid_op_("cannot call FetchAtLast on empty list"); return FetchAt_base(count - 1);}
	public Object FetchAtOr(int i, Object or) {return (i >= count) ? or : FetchAt(i);}
	public void Add(Object item) {Add_base(item);}
	public void AddAt(int i, Object o) {AddAt_base(i, o);}
	public void Del(Object item) {Del_base(item);}
	public int IndexOf(Object o) {return IndexOf_base(o);}

	Object[] list; int count;
	public ListAdp_base() {
		list = new Object[ListAdp_.Capacity_initial];
	}
	public ListAdp_base(int capacity) {
		list = new Object[capacity];
	}
	void Resize_expand() {Resize_expand(count * 2);}
	void Resize_expand(int newCount) {
		Object[] trg = new Object[newCount];
		for (int i = 0; i < count; i++) {
			trg[i] = list[i];
			list[i] = null;
		}
		list = trg;
	}
	void Collapse(int index) {
		for (int i = index; i < count; i++) {
			list[i] = (i == count - 1) ? null : list[i + 1];
		}
	}
	@gplx.Internal protected int Capacity() {return Array_.LenAry(list);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_len))			return count;
		else if	(ctx.Match(k, Invk_get_at))			return FetchAt(m.ReadInt("v"));
		else	return GfoInvkAble_.Rv_unhandled;
//			return this;
	}	private static final String Invk_len = "len", Invk_get_at = "get_at";
}
