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
package gplx.dbs.schemas.updates; import gplx.*; import gplx.dbs.*; import gplx.dbs.schemas.*;
import gplx.dbs.engines.sqlite.*;
public class Schema_update_cmd_ {
	public static Schema_update_cmd Make_tbl_create(String tbl_name, String tbl_sql, Db_idx_itm... tbl_idxs) {return new Schema_update_cmd__tbl_create(tbl_name, tbl_sql, tbl_idxs);}
}
class Schema_update_cmd__tbl_create implements Schema_update_cmd {
	private final String tbl_sql; private final Db_idx_itm[] tbl_idxs;
	public Schema_update_cmd__tbl_create(String tbl_name, String tbl_sql, Db_idx_itm... tbl_idxs) {
		this.tbl_name = tbl_name; this.tbl_sql = tbl_sql; this.tbl_idxs = tbl_idxs;
	}
	public String Name() {return "schema.tbl.create." + tbl_name;} private final String tbl_name;
	public boolean Exec_is_done() {return exec_is_done;} private boolean exec_is_done;
	public void Exec(Schema_db_mgr db_mgr, Db_conn conn) {
		if (db_mgr.Tbl_mgr().Has(tbl_name)) return;
		Gfo_usr_dlg_._.Log_many("", "", "schema.tbl.create: tbl=~{0}", tbl_name);
		Sqlite_engine_.Tbl_create(conn, tbl_name, tbl_sql);
		Sqlite_engine_.Idx_create(conn, tbl_idxs);
		exec_is_done = true;
	}
}
