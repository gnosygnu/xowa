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
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
public class Xob_wdata_pid_sql extends Xob_wdata_pid_base {
	Xodb_mgr_sql db_mgr; Xodb_wdata_pids_tbl tbl; Db_stmt stmt; Db_conn conn;
	@Override public String Wkr_key() {return KEY;} public static final String KEY = "import.sql.wdata.pid";	
	@Override public void Pid_bgn() {
		db_mgr = wiki.Db_mgr_as_sql();
		tbl = db_mgr.Tbl_wdata_pids();			
		conn = db_mgr.Core_data_mgr().Conn_wdata();
		stmt = tbl.Insert_stmt(conn);
		conn.Txn_mgr().Txn_bgn_if_none();
	}
	@Override public void Pid_add(byte[] lang_key, byte[] ttl, byte[] pid) {
		tbl.Insert(stmt, lang_key, ttl, pid);
	}
	@Override public void Pid_end() {
		conn.Txn_mgr().Txn_end_all();
		stmt.Rls();
		db_mgr.Core_data_mgr().Index_create(wiki.Appe().Usr_dlg(), Byte_.Ary(Xowd_db_file_.Tid_core, Xowd_db_file_.Tid_wikidata), Index_wdata_pids);
	}
	private static final Db_idx_itm Index_wdata_pids	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_pids__src ON wdata_pids (wp_src_lang, wp_src_ttl);");
}
