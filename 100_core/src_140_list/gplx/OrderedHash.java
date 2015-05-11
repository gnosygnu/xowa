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
public interface OrderedHash extends HashAdp {
	Object FetchAt(int i);
	Object FetchAtOr(int i, Object or);
	void AddAt(int i, Object o);
	int IndexOf(Object item);
	void Sort();
	void SortBy(ComparerAble comparer);
	void ResizeBounds(int i);
	Object Xto_ary(Class<?> t);
	Object Xto_ary_and_clear(Class<?> t);
	String XtoStr_ui();
	void MoveTo(int src, int trg);
	void Lock();
}
