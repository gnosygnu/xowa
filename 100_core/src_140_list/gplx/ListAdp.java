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
public interface ListAdp extends EnumerAble {
	boolean Has_none();
	int Count();
	Object FetchAt(int i);
	Object FetchAtOr(int i, Object or);
	Object FetchAtLast();
	Object PopLast();
	void Add(Object o);
	void AddAt(int i, Object o);
	ListAdp AddMany(Object... ary);
	void Del(Object o);
	void DelAt(int i);
	void Del_range(int bgn, int end);
	void Clear();
	void Clear_max(int max);
	boolean RangeCheck(int v);
	void ResizeBounds(int i);
	int IndexOf(Object o);
	int LastIndex();
	void MoveTo(int src, int trg);
	void SetAt(int i, Object o);
	String XtoStr();
	String[] XtoStrAry();
	Object Xto_ary(Class<?> memberType);
	Object Xto_ary_and_clear(Class<?> memberType);
	Object[] Xto_obj_ary();

	void Reverse();
	void Sort();
	void SortBy(ComparerAble comparer);
	void Shuffle();
}
