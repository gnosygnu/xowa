/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
