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
	int Count();
	Object Get_at_last();
	void Add(Object o);
	void Add_at(int i, Object o);
	void Add_many(Object... ary);
	void Del(Object o);
	void Del_at(int i);
	void Del_range(int bgn, int end);
	void Clear();
	int Idx_of(Object o);
	int Idx_last();
	Object To_ary(Class<?> memberType);
	Object To_ary_and_clear(Class<?> memberType);
	String[] To_str_ary();
	String[] To_str_ary_and_clear();
	String To_str();
	Object[] To_obj_ary();
	void Resize_bounds(int i);
	void Move_to(int src, int trg);
	void Reverse();
	void Sort();
	void Sort_by(ComparerAble comparer);
	void Shuffle();
}
class List_adp_obj extends List_adp_base implements List_adp {
	public List_adp_obj() {super();}
	public List_adp_obj(int v) {super(v);}
}
class List_adp_noop implements List_adp {
	public int Count() {return 0;}
	public int Len() {return 0;}
	public Object Get_at(int i) {return null;}
	public Object Get_at_last() {return null;}
	public Object PopLast() {return null;}
	public void Add(Object o) {}
	public void Add_at(int i, Object o) {}
	public void Add_many(Object... ary) {}
	public void Del(Object o) {}
	public void Del_at(int i) {}
	public void Del_range(int bgn, int end) {}
	public void Clear() {}
	public int Idx_last() {return -1;}
	public int Idx_of(Object o) {return List_adp_.Not_found;}
	public void Move_to(int elemPos, int newPos) {}
	public void Resize_bounds(int i) {}
	public Object To_ary(Class<?> memberType) {return Object_.Ary_empty;}
	public Object To_ary_and_clear(Class<?> memberType) {return Object_.Ary_empty;}
	public String[] To_str_ary() {return String_.Ary_empty;}
	public String[] To_str_ary_and_clear() {return To_str_ary();}
	public String To_str() {return "";}
	public Object[] To_obj_ary() {return Object_.Ary_empty;}
	public java.util.Iterator iterator() {return Iterator_null.Instance;}
	public void Reverse() {}
	public void Sort() {}
	public void Sort_by(ComparerAble comparer) {}
	public void Shuffle() {}
}
