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
package gplx.xowa.gui.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
public class Xog_tab_itm_ {
	public static String Tab_name_min(String name, int min) {
		int name_len = String_.Len(name);
		return min == Tab_name_min_disabled || name_len > min ? name : name + String_.Repeat(" ", min - name_len);
	}
	public static String Tab_name_max(String name, int max) {
		int name_len = String_.Len(name);
		return max == Tab_name_max_disabled || name_len <= max ? name : String_.Mid(name, 0, max) + "...";
	}
	public static final int Tab_name_min_disabled = -1, Tab_name_max_disabled = -1;
	public static final Xog_tab_itm Null = null;
}
