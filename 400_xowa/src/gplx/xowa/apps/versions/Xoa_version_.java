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
package gplx.xowa.apps.versions; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_version_ {
	public static int Compare(String lhs_str, String rhs_str) {
		String[] lhs_ary = String_.Split(lhs_str, ".");
		String[] rhs_ary = String_.Split(rhs_str, ".");
		return Compare_as_int(lhs_ary, rhs_ary);
	}
	private static int Compare_as_int(String[] lhs_ary, String[] rhs_ary) {
		int lhs_ary_len = lhs_ary.length;
		int rhs_ary_len = lhs_ary.length;
		int len_comp = Int_.Compare(lhs_ary_len, rhs_ary_len);
		if (len_comp != CompareAble_.Same) return len_comp;
		for (int i = 0; i < lhs_ary_len; ++i) {
			String lhs_itm = lhs_ary[i];
			String rhs_itm = rhs_ary[i];
			int itm_comp = Int_.Compare(Int_.parse_or_(lhs_itm, 0), Int_.parse_or_(rhs_itm, 0));
			if (itm_comp != CompareAble_.Same) return itm_comp;
		}
		return CompareAble_.Same;
	}
}
