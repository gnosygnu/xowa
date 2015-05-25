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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
import gplx.xowa.users.data.*;
import gplx.xowa.files.*; import gplx.xowa.files.caches.*;
import gplx.xowa.wikis.*;
public class Xouv_user implements Xou_user {
	public Xouv_user(String key) {this.key = key;}
	public String					Key() {return key;} private String key;
	public Xou_db_file				Data__db_file() {return db_file;} private Xou_db_file db_file;
	public Xou_cache_mgr			File__cache_mgr() {return cache_mgr;} private Xou_cache_mgr cache_mgr;
	public Xou_file_itm_finder		File__xfer_itm_finder() {return xfer_itm_finder;} private Xou_file_itm_finder xfer_itm_finder;
	public void Init_db(Xoa_wiki_mgr wiki_mgr, Io_url user_root_dir) {
		Io_url db_url = user_root_dir.OwnerDir().GenSubFil("xowa.user." + key + ".sqlite3");	// EX: /xowa/user/xowa.user.anonymous.sqlite3
		Db_conn_bldr_data db_conn_bldr = Db_conn_bldr.I.Get_or_new(db_url);
		this.db_file = new Xou_db_file(db_conn_bldr.Conn());
		db_file.Init_assert();
		this.cache_mgr = new Xou_cache_mgr(wiki_mgr, user_root_dir, db_file);
		this.xfer_itm_finder = new Xou_file_itm_finder(cache_mgr);
	}
}
