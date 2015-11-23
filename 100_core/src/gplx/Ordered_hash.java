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
import gplx.core.lists.*; /*EnumerAble,ComparerAble*/
public interface Ordered_hash extends Hash_adp {
	Object Get_at(int i);
	void Add_at(int i, Object o);
	int Idx_of(Object item);
	void Sort();
	void Sort_by(ComparerAble comparer);
	void Resize_bounds(int i);
	Object To_ary(Class<?> t);
	Object To_ary_and_clear(Class<?> t);
	void Move_to(int src, int trg);
	void Lock();
}
