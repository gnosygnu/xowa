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
package gplx.xowa.addons.builds.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*; import gplx.xowa.addons.builds.files.*;
import gplx.xowa.wikis.nss.*;
class Xobldr__lnki_temp__create_ {
	public static int[] Ns_ids_by_aliases(Xowe_wiki wiki, String[] aliases) {
		int[] rv = Xobldr__lnki_temp__create_.Ids_by_aliases(wiki.Ns_mgr(), aliases);
		int aliases_len = aliases.length;
		int ids_len = rv.length;
		for (int i = 0; i < aliases_len; i++) {
			String alias = aliases[i];
			int id = i < ids_len ? rv[i] : -1;
			wiki.Appe().Usr_dlg().Note_many("", "", "ns: ~{0} <- ~{1}", Int_.To_str_fmt(id, "0000"), alias);
		}
		if (aliases_len != ids_len) throw Err_.new_wo_type("mismatch in aliases and ids", "aliases", aliases_len, "ids", ids_len);
		return rv;
	}
	private static int[] Ids_by_aliases(Xow_ns_mgr ns_mgr, String[] aliases) {
		List_adp list = List_adp_.new_();
		int len = aliases.length;
		for (int i = 0; i < len; i++) {
			String alias = aliases[i];
			if (String_.Eq(alias, Xow_ns_.Key__main))
				list.Add(ns_mgr.Ns_main());
			else {
				Xow_ns ns = ns_mgr.Names_get_or_null(Bry_.new_u8(alias));
				if (ns != null)
					list.Add(ns);
			}
		}
		len = list.Count();
		int[] rv = new int[len];
		for (int i = 0; i < len; i++) {
			rv[i] = ((Xow_ns)list.Get_at(i)).Id();
		}
		return rv;
	}
}
