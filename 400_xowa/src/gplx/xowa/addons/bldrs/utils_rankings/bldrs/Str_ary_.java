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
package gplx.xowa.addons.bldrs.utils_rankings.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.utils_rankings.*;
class Str_ary_ {
	public static String[][] To_str_ary_ary(String v, String val_dlm, String row_dlm) {// "a|b|c`"
		String[] rows_ary = String_.Split(v, row_dlm);
		int rv_len = rows_ary.length;
		String[][] rv = new String[rv_len][];
		for (int i = 0; i < rv_len; ++i) {
			String row = rows_ary[i];
			String[] vals_ary = String_.Split(row, val_dlm);
			int vals_len = vals_ary.length;
			String[] rv_row = new String[vals_len];
			rv[i] = rv_row;
			for (int j = 0; j < vals_len; ++j)
				rv[i][j] = vals_ary[j];
		}
		return rv;
	}
}
