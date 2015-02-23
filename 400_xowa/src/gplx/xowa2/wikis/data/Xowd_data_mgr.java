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
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
import gplx.xowa.*; import gplx.xowa.dbs.*;
public class Xowd_data_mgr {
	private final String domain_str; private final Io_url wiki_root_dir; private final Xow_ns_mgr ns_mgr;
	public Xowd_data_mgr(String domain_str, Io_url wiki_root_dir, Xow_ns_mgr ns_mgr) {
		this.domain_str = domain_str; this.wiki_root_dir = wiki_root_dir; this.ns_mgr = ns_mgr;
	}
	public Xowd_page_regy_tbl		Tbl__page() {return tbl__page;} private final Xowd_page_regy_tbl tbl__page = new Xowd_page_regy_tbl();
	public Xowd_ns_regy_tbl			Tbl__ns()	{return tbl__ns;}	private final Xowd_ns_regy_tbl tbl__ns = new Xowd_ns_regy_tbl();
	public Xowd_db_regy_tbl			Tbl__db()	{return tbl__db;}	private final Xowd_db_regy_tbl tbl__db = new Xowd_db_regy_tbl();
	public Db_url					Url_by_idx(int idx) {if (!Int_.Between(idx, 0, db_urls_len)) throw Err_.new_("database does not exist: idx={0}", idx); return db_urls[idx];}
	private Xodb_file[] db_files; private Db_url[] db_urls; private int db_urls_len;
	private boolean init_done = false;
	public void Init_assert() {
		if (init_done) return;
		init_done = true;
		if (String_.Eq(domain_str, "xowa")) return;
		Db_url core_url = Sqlite_url.load_(wiki_root_dir.GenSubFil_ary(domain_str, ".000.sqlite3"));
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new("", core_url);
		Db_conn core_conn = conn_data.Conn(); boolean created = conn_data.Created();
		int wiki_id = 0;
		boolean version_is_1 = Bool_.N;
		tbl__db.Conn_(core_conn, version_is_1);
		tbl__ns.Conn_(core_conn, version_is_1);
		tbl__page.Conn_(core_conn, created, version_is_1);
		db_files = tbl__db.Select_all(wiki_root_dir);
		db_urls_len = db_files.length;
		db_urls = new Db_url[db_urls_len];
		for (int i = 0; i < db_urls_len; ++i)
			db_urls[i] = Db_url_.sqlite_(db_files[i].Url());
		tbl__ns.Select_all(wiki_id, ns_mgr);
	}
}
