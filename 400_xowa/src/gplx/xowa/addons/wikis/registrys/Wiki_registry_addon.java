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
package gplx.xowa.addons.wikis.registrys; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.xowa.specials.*;
public class Wiki_registry_addon implements Xoax_addon_itm, Xoax_addon_itm__special {
	public Xow_special_page[] Special_pages() {
		return new Xow_special_page[]
		{ gplx.xowa.addons.wikis.registrys.lists.Xow_list_special.Prototype
		, gplx.xowa.addons.wikis.registrys.infos.Xow_info_special.Prototype
		};
	}

	public String Addon__key() {return "xowa.apps.wikis.registrys";}
}
