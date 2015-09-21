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
package gplx.xowa.tdbs.hives; import gplx.*; import gplx.xowa.*; import gplx.xowa.tdbs.*;
public class Bry_comparer_bgn_eos implements gplx.lists.ComparerAble {
	public Bry_comparer_bgn_eos(int bgn) {this.bgn = bgn;} private int bgn;
	public int compare(Object lhsObj, Object rhsObj) {
		byte[] lhs = (byte[])lhsObj, rhs = (byte[])rhsObj;
		return Bry_.Compare(lhs, bgn, lhs.length, rhs, bgn, rhs.length);
	}
}
