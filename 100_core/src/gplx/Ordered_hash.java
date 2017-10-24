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
public interface Ordered_hash extends Hash_adp, List_adp__getable {
	void Add_at(int i, Object o);
	Ordered_hash Add_many_str(String... ary);
	int Idx_of(Object item);
	void Sort();
	void Sort_by(ComparerAble comparer);
	void Resize_bounds(int i);
	Object To_ary(Class<?> t);
	Object To_ary_and_clear(Class<?> t);
	void Move_to(int src, int trg);
	void Lock();
}
