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
import gplx.core.lists.*; /*EnumerAble,ComparerAble*/
public interface List_adp extends EnumerAble, List_adp__getable {
	int Len();
	Object GetAtLast();
	void Add(Object o);
	void AddAt(int i, Object o);
	void AddMany(Object... ary);
	void Del(Object o);
	void DelAt(int i);
	void DelRange(int bgn, int end);
	void Clear();
	int IdxOf(Object o);
	int IdxLast();
	Object ToAry(Class<?> memberType);
	Object ToAryAndClear(Class<?> memberType);
	String[] ToStrAry();
	String[] ToStrAryAndClear();
	String ToStr();
	Object[] ToObjAry();
	void ResizeBounds(int i);
	void MoveTo(int src, int trg);
	void Reverse();
	void Sort();
	void SortBy(ComparerAble comparer);
	void Shuffle();
}
class List_adp_obj extends List_adp_base implements List_adp {
	public List_adp_obj() {super();}
	public List_adp_obj(int v) {super(v);}
}
class List_adp_noop implements List_adp {
	public int Len() {return 0;}
	public Object Get_at(int i) {return null;}
	public Object GetAtLast() {return null;}
	public Object PopLast() {return null;}
	public void Add(Object o) {}
	public void AddAt(int i, Object o) {}
	public void AddMany(Object... ary) {}
	public void Del(Object o) {}
	public void DelAt(int i) {}
	public void DelRange(int bgn, int end) {}
	public void Clear() {}
	public int IdxLast() {return -1;}
	public int IdxOf(Object o) {return List_adp_.Not_found;}
	public void MoveTo(int elemPos, int newPos) {}
	public void ResizeBounds(int i) {}
	public Object ToAry(Class<?> memberType) {return Object_.Ary_empty;}
	public Object ToAryAndClear(Class<?> memberType) {return Object_.Ary_empty;}
	public String[] ToStrAry() {return String_.Ary_empty;}
	public String[] ToStrAryAndClear() {return ToStrAry();}
	public String ToStr() {return "";}
	public Object[] ToObjAry() {return Object_.Ary_empty;}
	public java.util.Iterator iterator() {return Iterator_null.Instance;}
	public void Reverse() {}
	public void Sort() {}
	public void SortBy(ComparerAble comparer) {}
	public void Shuffle() {}
}
