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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Dpl_redirect {
	public static final byte Tid_exclude = 0, Tid_include = 1, Tid_only = 2, Tid_unknown = Byte_.Max_value_127;
	public static byte Parse(byte[] bry) {
		byte key = Dpl_itm_keys.Parse(bry, Dpl_redirect.Tid_exclude);	// NOTE: exclude is default value.
		switch (key) {
			case Dpl_itm_keys.Key_exclude: 			return Tid_exclude;
			case Dpl_itm_keys.Key_include: 			return Tid_include;
			case Dpl_itm_keys.Key_only: 			return Tid_only;
			default:								throw Err_mgr._.unhandled_(key);
		}
	}
}
