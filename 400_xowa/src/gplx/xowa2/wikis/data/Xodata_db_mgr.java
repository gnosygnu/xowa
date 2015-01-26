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
package gplx.xowa2.wikis.data; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.wikis.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa2.wikis.data.tbls.*;
public class Xodata_db_mgr {
	private Io_url wiki_root_dir;
	public Xodata_db_mgr(boolean version_is_1, String domain_str, Io_url wiki_root_dir) {
		this.wiki_root_dir = wiki_root_dir;
		this.key__core = Xodata_db_mgr.Db_key__core__default(this, domain_str, wiki_root_dir);
		this.tbl_page_regy = new Xow_page_regy_tbl(version_is_1, key__core);
	}
	public Xow_page_regy_tbl		Tbl_page_regy() {return tbl_page_regy;} private final Xow_page_regy_tbl tbl_page_regy;
	public Xodata_ns_regy_tbl		Tbl__ns()	{return tbl__ns;}	private final Xodata_ns_regy_tbl tbl__ns = new Xodata_ns_regy_tbl();
	public Xodata_db_tbl			Tbl__dbs_new()	{return new Xodata_db_tbl();}
	private Xodb_file[] db_files; private Db_url[] db_keys; private int db_keys_len;
	public Db_url Key__core() {return key__core;} private Db_url key__core;
	public Db_url Key_by_idx(int idx) {if (!Int_.Between(idx, 0, db_keys_len)) throw Err_.new_("database does not exist: idx={0}", idx); return db_keys[idx];}
	public void Init_by_conn(Db_conn conn) {
		db_files	= Tbl__dbs_new().Select_all(conn, wiki_root_dir);
		db_keys		= Db_keys_make(db_files);
		db_keys_len = db_keys.length;
	}
	private static Db_url Db_key__core__default(Xodata_db_mgr db_mgr, String domain_str, Io_url wiki_root_dir) {
		Io_url core_db_url = wiki_root_dir.GenSubFil_ary(domain_str, ".000.sqlite3");
		return Db_url__sqlite.load_(core_db_url);
	}
	private static Db_url[] Db_keys_make(Xodb_file[] ary) {
		int len = ary.length;
		Db_url[] rv = new Db_url[len];
		for (int i = 0; i < len; ++i) {
			Xodb_file itm = ary[i];
			Io_url itm_url = itm.Url();
			rv[i] = Db_url_.sqlite_(itm_url);
		}
		return rv;
	}
}
