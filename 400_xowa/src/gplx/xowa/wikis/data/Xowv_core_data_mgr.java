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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
import gplx.xowa.*; import gplx.xowa.wikis.data.*;
public class Xowv_core_data_mgr implements Xow_core_data_mgr {
	private final String domain_str; private final Io_url wiki_root_dir;
	private Xowd_db_file[] dbs__ary; private boolean init_done = false;
	public Xowv_core_data_mgr(String domain_str, Io_url wiki_root_dir) {
		this.domain_str = domain_str; this.wiki_root_dir = wiki_root_dir;
	}
	public Xow_core_data_map		Map()		{return map;}		private final Xow_core_data_map	map			= new Xow_core_data_map();
	public boolean						Cfg__schema_is_1()	{return Bool_.Y;}
	public int						Cfg__db_id()		{return 1;}
	public Db_cfg_tbl				Tbl__cfg()	{return tbl__cfg;}	private final Db_cfg_tbl			tbl__cfg	= new Db_cfg_tbl();
	public Xodb_xowa_db_tbl			Tbl__db()	{return tbl__db;}	private final Xodb_xowa_db_tbl	tbl__db		= new Xodb_xowa_db_tbl();
	public Xowd_ns_regy_tbl			Tbl__ns()	{return tbl__ns;}	private final Xowd_ns_regy_tbl	tbl__ns		= new Xowd_ns_regy_tbl();
	public Xowd_pg_regy_tbl			Tbl__pg()	{return tbl__pg;}	private final Xowd_pg_regy_tbl	tbl__pg		= new Xowd_pg_regy_tbl();
	public int						Dbs__len()	{return dbs__ary_len;} private int dbs__ary_len;
	public Xowd_db_file				Dbs__get_db_core() {return dbs__ary[0];}
	public Xowd_db_file				Dbs__get_at(int i) {if (!Int_.Between(i, 0, dbs__ary_len)) throw Err_.new_("database does not exist: idx={0}", i); return dbs__ary[i];}
	public Xowd_db_file				Dbs__get_by_tid_nth_or_new(byte tid) {
		Xowd_db_file rv = Xowd_db_file.Null;
		for (int i = 0; i < dbs__ary_len; i++) {
			Xowd_db_file file = dbs__ary[i];
			if (file.Tid() == tid) rv = file;
		}
		if (rv == Xowd_db_file.Null)
			throw Err_.not_implemented_msg_("view_mode doesn't allow creating databases");
		return rv;
	}
	public boolean Init() {
		if (init_done) return false;
		init_done = true;
		if (String_.Eq(domain_str, "xowa")) return true;				// FIXME: ignore "xowa" for now; WHEN:converting xowa to sqlitedb
		Io_url core_db_url = wiki_root_dir.GenSubFil_ary(domain_str, ".000.sqlite3");
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new("", core_db_url);
		Db_conn core_conn = conn_data.Conn(); boolean created = conn_data.Created();	
		if (Op_sys.Cur().Tid_is_drd()) created = Bool_.Y; // WORKAROUND.DRD:Android returns File.exists of false for files in /data/file/; DATE:2015-02-26				
		boolean schema_is_1 = Bool_.Y; int db_id = 1;
		tbl__cfg.Conn_(core_conn, created, schema_is_1, "xowa_cfg", "wiki_cfg_regy");
		tbl__db.Conn_(core_conn, created, schema_is_1);
		tbl__ns.Conn_(core_conn, created, schema_is_1);
		tbl__pg.Conn_(core_conn, created, schema_is_1, db_id, Bool_.Y);
		dbs__ary = tbl__db.Select_all(wiki_root_dir);
		dbs__ary_len = dbs__ary.length;
		return true;
	}
}
