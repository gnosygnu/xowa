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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Wbase_prop_mgr_loader_ {
	public static Wbase_prop_mgr_loader New_mock(Keyval... pairs) {
		return new Wbase_prop_mgr_loader__mock(pairs);
	}
	public static Wbase_prop_mgr_loader New_db(Wdata_wiki_mgr wbase_mgr) {
		return new Wbase_prop_mgr_loader__db(wbase_mgr);
	}
}
class Wbase_prop_mgr_loader__mock implements Wbase_prop_mgr_loader {
	private final    Keyval[] pairs;
	public Wbase_prop_mgr_loader__mock(Keyval[] pairs) {
		this.pairs = pairs;
	}
	public Ordered_hash Load() {
		Ordered_hash rv = Ordered_hash_.New();
		for (Keyval kv : pairs) 
			rv.Add(kv.Key(), kv.Val_to_str_or_empty());
		return rv;
	}
}
class Wbase_prop_mgr_loader__db implements Wbase_prop_mgr_loader {
	private final    Wdata_wiki_mgr wbase_mgr;
	public Wbase_prop_mgr_loader__db(Wdata_wiki_mgr wbase_mgr) {
		this.wbase_mgr = wbase_mgr;
	}
	public Ordered_hash Load() {
		gplx.xowa.wikis.data.Xow_db_file wbase_db = wbase_mgr.Wdata_wiki().Data__core_mgr().Db__wbase();
		if (!wbase_db.Conn().Meta_tbl_exists(wbase_db.Tbl__wbase_prop().Tbl_name())) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wbase:prop tbl missing");
			return null;
		}
		return wbase_db.Tbl__wbase_prop().Select_all();
	}
}
