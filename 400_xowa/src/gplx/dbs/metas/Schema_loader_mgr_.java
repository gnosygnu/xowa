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
package gplx.dbs.metas; import gplx.*; import gplx.dbs.*;
import gplx.dbs.qrys.*;
public class Schema_loader_mgr_ {
	public static final Schema_loader_mgr Null = new Schema_loader_mgr__null();
	public static final Schema_loader_mgr Sqlite = new Schema_loader_mgr__sqlite();
}
class Schema_loader_mgr__null implements Schema_loader_mgr {
	public void Load(Schema_db_mgr db_mgr, Db_conn conn) {}
}
class Schema_loader_mgr__sqlite implements Schema_loader_mgr {
	public void Load(Schema_db_mgr db_mgr, Db_conn conn) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.schema.load.bgn: conn=~{0}", conn.Conn_info().Xto_api());
		Meta_tbl_mgr tbl_mgr = db_mgr.Tbl_mgr();
		Db_qry__select_in_tbl qry = Db_qry__select_in_tbl.new_("sqlite_master", String_.Ary_empty, String_.Ary("type", "name", "sql"), Db_qry__select_in_tbl.Order_by_null);
		Db_rdr rdr = conn.Stmt_new(qry).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				String type_str = rdr.Read_str("type");
				String name = rdr.Read_str("name");
				String sql = rdr.Read_str("sql");
				int type_int = Meta_itm_tid.Xto_int(type_str);
				switch (type_int) {
					case Meta_itm_tid.Tid_table:
						Meta_tbl_itm tbl_itm = new Meta_tbl_itm(name, sql);
						tbl_mgr.Add(tbl_itm);
						break;
					case Meta_itm_tid.Tid_index:	break;	// noop for now
					default:						throw Err_.new_unhandled(type_str);
				}
			}
		}	finally {rdr.Rls();}
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.schema.load.end");
	}
}
