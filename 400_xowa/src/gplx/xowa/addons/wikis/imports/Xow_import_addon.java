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
package gplx.xowa.addons.wikis.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.xowa.specials.*;
public class Xow_import_addon implements Xoax_addon_itm, Xoax_addon_itm__special {
	private final    Ordered_hash cbks = Ordered_hash_.New();
	public Xow_special_page[] Special_pages() {
		return new Xow_special_page[]
		{ Xow_import_special.Prototype
		};
	}
	public void Dir_selected_cbks__add(Xow_import_dir_cbk cbk) {
		if (!cbks.Has(cbk.Key()))
			cbks.Add(cbk.Key(), cbk);
	}
	public Xow_import_dir_cbk Dir_selected_cbks__get_by(String key) {return (Xow_import_dir_cbk)cbks.Get_by(key);}

	public String Addon__key() {return ADDON__KEY;} private static final String ADDON__KEY = "xowa.apps.file_browsers";
	public static Xow_import_addon Addon__get(Xow_wiki wiki) {
		Xow_import_addon rv = (Xow_import_addon)wiki.Addon_mgr().Itms__get_or_null(ADDON__KEY);
		if (rv == null) {
			rv = new Xow_import_addon();
			wiki.Addon_mgr().Itms__add(rv);
		}
		return rv;
	}
}
