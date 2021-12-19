/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.basics.lists;
import gplx.core.lists.Iterator_null;
import gplx.core.lists.Iterator_objAry;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.lists.ComparerAble;
import gplx.types.commons.lists.ComparerAbleSorter;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.commons.GfoRandom;
import gplx.types.commons.GfoRandomUtl;
public abstract class List_adp_base implements List_adp, Gfo_invk {
	private Object[] list; private int count;
	public List_adp_base(int capacity) {
		this.list = new Object[capacity];
	}
	public java.util.Iterator iterator() {
		if (count == 0)
			return Iterator_null.Instance;
		else
			return new Iterator_objAry(list, count);
	}
	public void AddMany(Object... ary) {for (Object o : ary) Add_base(o);}
	public int Len() {return count;}
	public int IdxLast() {return count - 1;}
	protected Object Get_at_base(int index) {if (index >= count || index < 0) throw ErrUtl.NewMissingIdx(index, count);
		return list[index];
	}
	protected void Add_base(Object o) {
		if (count == list.length) Resize_expand();
		list[count] = o;
		count++;
	}
	protected int Del_base(Object o) {
		int index = IndexOf_base(o); if (index == List_adp_.NotFound) return List_adp_.NotFound;
		this.DelAt(index);
		return index;
	}
	public void DelRange(int delBgn, int delEnd) {
		BoundsChk(delBgn, delEnd, count);
		if (delBgn == 0 && delEnd == count - 1) {    // entire list deleted; call .Clear, else will have 0 elem array
			this.Clear();
			return;
		}
		int delLen = (delEnd - delBgn) + 1; // EX: 0,2 creates 3 len ary
		int newLen = count - delLen;
		Object[] newList = new Object[newLen];
		if (delBgn != 0)            // copy elements < delBgn; skip if delBgn == 0
			ArrayUtl.CopyTo(list, 0, newList, 0, delBgn);
		if (delEnd != count -1 )    // copy elements > delEnd; skip if delEnd == lastIdx            
			ArrayUtl.CopyTo(list, delEnd + 1, newList, delBgn, newLen - delBgn);
		list = newList;
		count = list.length;
	}
	protected int IndexOf_base(Object o) {
		for (int i = 0; i < count; i++)
			if (ObjectUtl.Eq(list[i], o)) return i;
		return List_adp_.NotFound;
	}
	public void Clear() {
		for (int i = 0; i < count; i++)
			list[i] = null;
		count = 0;
	}
	public void DelAt(int index) {if (index >= count || index < 0) throw ErrUtl.NewMissingIdx(index, count);
		Collapse(index);
		count--;
	}
	public void MoveTo(int src, int trg) {if (src >= count || src < 0) throw ErrUtl.NewMissingIdx(src, count); if (trg >= count || trg < 0) throw ErrUtl.NewMissingIdx(trg, count);
		if (src == trg) return;    // position not changed
		Object o = list[src];
		int dif = trg > src ? 1 : -1;
		for (int i = src; i != trg; i += dif) 
			list[i] = list[i + dif];
		list[trg] = o;
	}
	protected void AddAt_base(int pos, Object o) {
		if (count + 1 >= list.length) Resize_expand();
		for (int i = count; i > pos; i--)
			list[i] = list[i - 1];
		list[pos] = o;
		count = count + 1;
	}
	public void ResizeBounds(int i) {
		Resize_expand(i);
	}
	public void Sort() {SortBy(null);}
	public void SortBy(ComparerAble comparer) {new ComparerAbleSorter().Sort(list, count, true, comparer);}
	public void Reverse() {
		int mid = count / 2;                // no need to reverse pivot; ex: for 3 elements, only 1 and 3 need to be exchanged; 2 stays inplace
		for (int lhs = 0; lhs < mid; lhs++) {
			int rhs = count - lhs - 1;        // -1 b/c list[count] is not real element
			Object temp = list[lhs];
			list[lhs] = list[rhs];
			list[rhs] = temp;
		}
	}
	public void Shuffle() {// REF: Fisher-Yates shuffle
		GfoRandom random = GfoRandomUtl.New();
		for (int i = count; i > 1; i--) {
			int rndIdx = random.Next(i);
			Object tmp = list[rndIdx];
			list[rndIdx] = list[i-1];
			list[i-1] = tmp;
		}
	}
	public Object GetAt(int i) {return Get_at_base(i);}
	public Object GetAtLast() {if (count == 0) throw ErrUtl.NewInvalidOp("cannot call Get_at_last on empty list"); return Get_at_base(count - 1);}
	public void Add(Object item) {Add_base(item);}
	public void AddAt(int i, Object o) {AddAt_base(i, o);}
	public void Del(Object item) {Del_base(item);}
	public int IdxOf(Object o) {return IndexOf_base(o);}
	public List_adp_base() {
		list = new Object[Len_initial];
	}
	private static final int Len_initial = 8;
	public Object ToAryAndClear(Class<?> memberType) {Object rv = ToAry(memberType); this.Clear(); return rv;}
	public Object ToAry(Class<?> memberType) {
		Object rv = ArrayUtl.Create(memberType, count);
		for (int i = 0; i < count; i++)
			ArrayUtl.SetAt(rv, i, list[i]);
		return rv;
	}
	public String[] ToStrAryAndClear() {String[] rv = ToStrAry(); this.Clear(); return rv;}
	public String[] ToStrAry() {return (String[])ToAry(String.class);}
	public Object[] ToObjAry() {
		Object[] rv = new Object[count];
		for (int i = 0; i < count; ++i)
			rv[i] = list[i];
		return rv;
	}
	public String ToStr() {
		BryWtr bfr = BryWtr.New();
		for (int i = 0; i < count; ++i)
			bfr.AddStrU8(ObjectUtl.ToStrOrNullMark(list[i])).AddByteNl();
		return bfr.ToStrAndClear();
	}
	private void BoundsChk(int bgn, int end, int len) {
		if (    bgn >= 0 && bgn < len
			&&    end >= 0 && end < len
			&&    bgn <= end
			)    return;
		throw ErrUtl.NewArgs("bounds check failed", "bgn", bgn, "end", end, "len", len);
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
	public int Capacity() {return list.length;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if        (ctx.Match(k, Invk_len))            return count;
		else if    (ctx.Match(k, Invk_get_at))            return GetAt(m.ReadInt("v"));
		else    return Gfo_invk_.Rv_unhandled;
//            return this;
	}   private static final String Invk_len = "len", Invk_get_at = "get_at";
}
