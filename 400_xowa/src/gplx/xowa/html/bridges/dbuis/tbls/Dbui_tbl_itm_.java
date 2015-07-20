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
package gplx.xowa.html.bridges.dbuis.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.bridges.*; import gplx.xowa.html.bridges.dbuis.*;
public class Dbui_tbl_itm_ {
	public static int Calc_width(Dbui_tbl_itm tbl) {
		int rv = 40; // 40 for button col
		Dbui_col_itm[] ary = tbl.Cols();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			rv += ary[i].Width();
		}
		return rv;
	}
}
