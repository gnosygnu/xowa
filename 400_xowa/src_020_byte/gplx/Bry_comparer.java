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
import gplx.lists.*;
public class Bry_comparer implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		byte[] lhs = (byte[])lhsObj, rhs = (byte[])rhsObj;
		return Bry_.Compare(lhs, 0, lhs.length, rhs, 0, rhs.length);
	}
//		public static int Compare(byte[] lhs, byte[] rhs, int lhs_bgn, int lhs_end, int rhs_bgn, int rhs_end) {
//			int lhs_len = lhs_end - lhs_bgn;
//			for (int i = 0; i < lhs_len; i++) {
//				int lhs_byte = lhs[i + lhs_bgn] & 0xff;	// PATCH.JAVA:need to convert to unsigned byte
//				int rhs_idx = i + rhs_bgn; if (rhs_idx == rhs_end) return CompareAble_.More;
//				int rhs_byte = rhs[rhs_idx]     & 0xff;	// PATCH.JAVA:need to convert to unsigned byte
//				if (lhs_byte == rhs_byte) {
//					if (lhs_byte == Byte_ascii.Pipe) return CompareAble_.Same;
//				}
//				else {
//					if 		(rhs_byte == Byte_ascii.Pipe) return CompareAble_.More;
//					else if (lhs_byte == Byte_ascii.Pipe) return CompareAble_.Less;
//					else 								  return lhs_byte < rhs_byte ? CompareAble_.Less : CompareAble_.More;
//				}
//			} 
//			return Int_.Compare(lhs_len, rhs_end - rhs_bgn);
//		}
	public static final Bry_comparer _ = new Bry_comparer(); Bry_comparer() {}
}
