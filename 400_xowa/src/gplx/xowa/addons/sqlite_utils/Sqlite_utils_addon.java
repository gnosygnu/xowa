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
package gplx.xowa.addons.sqlite_utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*;
public class Sqlite_utils_addon implements Xoax_addon_itm {
	public Sqlite_utils_addon(Xow_wiki wiki) {
	}
	public byte[]				Addon__key() {return Key_const;} public static final byte[] Key_const = Bry_.new_a7("xowa.sqlite_utils");
	public static Sqlite_utils_addon Get(Xow_wiki wiki) {
		Sqlite_utils_addon rv = (Sqlite_utils_addon)wiki.Addon_mgr().Itms__get_or_null(Key_const);
		if (rv == null) {
			rv = new Sqlite_utils_addon(wiki);
			wiki.Addon_mgr().Itms__add(rv);
		}
		return rv;
	}
}
