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
package gplx.xowa.users.prefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Prefs_rename_mgr {
	private ListAdp list = ListAdp_.new_();
	public Prefs_rename_mgr() {
		List_add(list, "app.cfgs.get('app.gui.html.portal.wikis.visible', 'app').val", "app.cfgs.get('xowa.api.html.page.toggles.get(''offline-wikis'').visible', 'app').val");
	}
	public boolean Check(Io_url url) {
		String cur_str = Io_mgr._.LoadFilStr_args(url).MissingIgnored_().Exec();
		boolean cur_str_changed = false;
		int list_len = list.Count();
		for (int i = 0; i < list_len; ++i) {
			Prefs_rename_itm itm = (Prefs_rename_itm)list.FetchAt(i);
			if (String_.Has(cur_str, itm.Src())) {
				cur_str_changed = true;
				cur_str = String_.Replace(cur_str, itm.Src(), itm.Trg());
				Gfo_usr_dlg_._.Log_many("", "", "cfg.replace: src=~{src} trg = ~{trg}", itm.Src(), itm.Trg());
			}
		}
		if (cur_str_changed)
			Io_mgr._.SaveFilStr(url, cur_str);
		return cur_str_changed;
	}
	private static void List_add(ListAdp list, String src, String trg) {list.Add(new Prefs_rename_itm(src, trg));}
        public static final Prefs_rename_mgr _ = new Prefs_rename_mgr();
}
class Prefs_rename_itm {
	public Prefs_rename_itm(String src, String trg) {this.src = src; this.trg = trg;}
	public String Src() {return src;} private String src;
	public String Trg() {return trg;} private String trg;
}