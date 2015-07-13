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
package gplx.dbs.metas; import gplx.*; import gplx.dbs.*;
public class Meta_itm_tid {
	public static final int Tid_unknown = 0, Tid_table = 1, Tid_index = 2;
	public static final String Key_table = "table", Key_index = "index";
	public static int Xto_int(String s) {
		s = String_.Lower(s);
		if		(String_.Eq(s, Key_table))	return Tid_table;
		else if (String_.Eq(s, Key_index))	return Tid_index;
		else								return Tid_unknown;
	}
}
