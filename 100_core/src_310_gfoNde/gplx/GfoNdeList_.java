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
import gplx.lists.*; /*ComparerAble*/
public class GfoNdeList_ {
	public static final GfoNdeList Null = new GfoNdeList_null();
	public static GfoNdeList new_() {return new GfoNdeList_base();}
}
class GfoNdeList_base implements GfoNdeList {
	public int Count() {return list.Count();}
	public GfoNde FetchAt_asGfoNde(int i) {return (GfoNde)list.FetchAt(i);}
	public void Add(GfoNde rcd) {list.Add(rcd);}
	public void Del(GfoNde rcd) {list.Del(rcd);}
	public void Clear() {list.Clear();}
	public void SortBy(ComparerAble comparer) {list.SortBy(comparer);}
	ListAdp list = ListAdp_.new_();
}
class GfoNdeList_null implements GfoNdeList {
	public int Count() {return 0;}
	public GfoNde FetchAt_asGfoNde(int index) {return null;}
	public void Add(GfoNde rcd) {}
	public void Del(GfoNde rcd) {}
	public void Clear() {}
	public void SortBy(ComparerAble comparer) {}
}
