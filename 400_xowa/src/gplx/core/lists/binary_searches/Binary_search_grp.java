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
package gplx.core.lists.binary_searches; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
interface Binary_search_grp {
	int Len();
	Object Get_at(int i);
}
class Binary_search_grp__ary implements Binary_search_grp {
	private final    Object[] ary;
	public Binary_search_grp__ary(Object[] ary) {this.ary = ary;}
	public int Len() {return ary.length;}
	public Object Get_at(int i) {return ary[i];}
}
class Binary_search_grp__list implements Binary_search_grp {
	private final    List_adp list;
	public Binary_search_grp__list(List_adp list) {this.list = list;}
	public int Len() {return list.Len();}
	public Object Get_at(int i) {return list.Get_at(i);}
}
