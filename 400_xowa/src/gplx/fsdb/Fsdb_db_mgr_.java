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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*; import gplx.xowa.*; import gplx.xowa.wikis.data.*;
public class Fsdb_db_mgr_ {
	public static Fsdb_db_mgr new_detect(String domain_str, Io_url wiki_dir, Io_url file_dir) {
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
		Fsdb_db_mgr rv = null;
		rv = load_or_null(Xowd_db_layout.Itm_few, usr_dlg, wiki_dir, domain_str); if (rv != null) return rv;
		rv = load_or_null(Xowd_db_layout.Itm_lot, usr_dlg, wiki_dir, domain_str); if (rv != null) return rv;
		rv = load_or_null(Xowd_db_layout.Itm_all, usr_dlg, wiki_dir, domain_str); if (rv != null) return rv;
		Io_url url = file_dir.GenSubFil(Fsdb_db_mgr__v1.Mnt_name);						// EX: /xowa/file/en.wikipedia.org/wiki.mnt.sqlite3
		if	(Db_conn_bldr.I.Exists(url)) {
			usr_dlg.Log_many("", "", "fsdb.db_core.v1: url=~{0}", url.Raw());
			return new Fsdb_db_mgr__v1(file_dir);
		}
		usr_dlg.Log_many("", "", "fsdb.db_core.none: wiki_dir=~{0} file_dir=~{1}", wiki_dir.Raw(), file_dir.Raw());
		return null;
	}
	private static Fsdb_db_mgr load_or_null(Xowd_db_layout layout, Gfo_usr_dlg usr_dlg, Io_url wiki_dir, String domain_str) {
		Io_url main_core_url = wiki_dir.GenSubFil(Fsdb_db_mgr__v2_bldr.Main_core_name(layout, domain_str));
		if (!Db_conn_bldr.I.Exists(main_core_url)) return null;
		usr_dlg.Log_many("", "", "fsdb.db_core.v2: type=~{0} url=~{1}", layout.Name(), main_core_url.Raw());
		Db_conn main_core_conn = Db_conn_bldr.I.Get(main_core_url);
		Io_url user_core_url = wiki_dir.GenSubFil(Fsdb_db_mgr__v2_bldr.Make_user_name(domain_str));
		Db_conn user_core_conn = Db_conn_bldr.I.Get(user_core_url);			
		return new Fsdb_db_mgr__v2(Fsdb_db_mgr__v2.Cfg__layout_file__get(main_core_conn), wiki_dir, new Fsdb_db_file(main_core_url, main_core_conn), new Fsdb_db_file(user_core_url, user_core_conn));
	}
}
