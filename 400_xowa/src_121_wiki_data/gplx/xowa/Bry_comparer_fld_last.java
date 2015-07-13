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
package gplx.xowa; import gplx.*;
public class Bry_comparer_fld_last implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		byte[] lhs = (byte[])lhsObj, rhs = (byte[])rhsObj;
		int lhs_bgn = Bry_finder.Find_bwd(lhs, Byte_ascii.Pipe); if (lhs_bgn == Bry_.NotFound) lhs_bgn = -1;
		int rhs_bgn = Bry_finder.Find_bwd(rhs, Byte_ascii.Pipe); if (rhs_bgn == Bry_.NotFound) rhs_bgn = -1;
		return Bry_.Compare(lhs, lhs_bgn + 1, lhs.length, rhs, rhs_bgn + 1, rhs.length);
	}
	public static final Bry_comparer_fld_last _ = new Bry_comparer_fld_last(); 
}
