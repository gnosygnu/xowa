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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
//	public class Dpl_page {
//		public byte[] Ttl_bry() {return ttl_bry;} public Dpl_page Ttl_bry_(byte[] v) {ttl_bry = v; return this;} private byte[] ttl_bry;
//	}
class Dpl_page_sorter implements gplx.lists.ComparerAble {
	public Dpl_page_sorter(Dpl_itm itm) {this.itm = itm;} private Dpl_itm itm;
	public int compare(Object lhsObj, Object rhsObj) {
		Xodb_page lhs = (Xodb_page)lhsObj;
		Xodb_page rhs = (Xodb_page)rhsObj;
		int multiplier = itm.Sort_ascending() == Bool_.Y_byte ? 1 : -1;
		switch (itm.Sort_tid()) {
			case Dpl_sort.Tid_categorysortkey: 		
			case Dpl_sort.Tid_categoryadd: 			return multiplier * Bry_.Compare(lhs.Ttl_page_db(), rhs.Ttl_page_db()); 
		}
		return CompareAble_.Same;
	}
}
