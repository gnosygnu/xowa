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
package gplx.xowa.addons.apps.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.xowa.specials.*;
public class Fbrow_addon implements Xoax_addon_itm, Xoax_addon_itm__sp {
	public Xows_page[] Pages_ary() {
		return new Xows_page[]
		{ Fbrow_special_page.Prototype
		, Wikis_list_page.Prototype
		};
	}

	public static final    byte[] ADDON_KEY = Bry_.new_a7("xowa.apps.file_browsers");
	public byte[] Addon__key()	{return ADDON_KEY;}
}
