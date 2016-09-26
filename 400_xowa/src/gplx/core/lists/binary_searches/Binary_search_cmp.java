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
import gplx.core.lists.*;
interface Binary_search_cmp {
	int Compare(Object comp);
}
class Binary_search_cmp__comparable implements Binary_search_cmp {
	private final    CompareAble val;
	public Binary_search_cmp__comparable(CompareAble val) {this.val = val;}
	public int Compare(Object comp) {
		return val.compareTo((CompareAble)comp);
	}
}
class Binary_search_cmp__comparer implements Binary_search_cmp {
	private final    Binary_comparer comparer; private final    Object val;
	public Binary_search_cmp__comparer(Binary_comparer comparer, Object val) {this.comparer = comparer; this.val = val;}
	public int Compare(Object comp) {
		return comparer.Compare_val_to_obj(val, comp);
	}
}
