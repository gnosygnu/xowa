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
package gplx.dbs.engines.sqlite; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.dbs.qrys.*;
import gplx.dbs.metas.*; import gplx.dbs.metas.parsers.*;
public class Sqlite_schema_mgr {
	private final Db_engine engine; private boolean init = true;
	public Sqlite_schema_mgr(Db_engine engine) {this.engine = engine;}
	public Meta_tbl_mgr Tbl_mgr() {return tbl_mgr;} private final Meta_tbl_mgr tbl_mgr = new Meta_tbl_mgr();
	public Meta_idx_mgr Idx_mgr() {return idx_mgr;} private final Meta_idx_mgr idx_mgr = new Meta_idx_mgr();
	public boolean Tbl_exists(String name) {
		if (init) Init(engine);
		return tbl_mgr.Has(name);
	}
	public boolean Fld_exists(String tbl, String fld) {
		if (init) Init(engine);
		Meta_tbl_itm tbl_itm = tbl_mgr.Get_by(tbl);
		return (tbl_itm == null) ? false : tbl_itm.Flds().Has(fld);
	}
	private void Init(Db_engine engine) {
		init = false;
		Gfo_usr_dlg_.I.Log_many("", "", "db.schema.load.bgn: conn=~{0}", engine.Conn_info().Xto_api());
		Meta_parser__tbl tbl_parser = new Meta_parser__tbl();
		Db_qry__select_in_tbl qry = Db_qry__select_in_tbl.new_("sqlite_master", String_.Ary_empty, String_.Ary("type", "name", "sql"), Db_qry__select_in_tbl.Order_by_null);
		Db_rdr rdr = engine.New_stmt_prep(qry).Exec_select__rls_auto();	
		try {
			while (rdr.Move_next()) {
				String type_str = rdr.Read_str("type");
				String name = rdr.Read_str("name");
				String sql = rdr.Read_str("sql");
				int type_int = Meta_itm_tid.Xto_int(type_str);
				switch (type_int) {
					case Meta_itm_tid.Tid_table:
						if (String_.Eq(name, "sqlite_sequence")) continue;	// ignore b/c of non-orthodox syntax; EX: "CREATE TABLE sqlite_sequence(name, seq)";
						// Meta_tbl_itm tbl_itm = new Meta_tbl_itm(tbl_name, tbl_sql);
						Meta_tbl_itm tbl_itm = tbl_parser.Parse(Bry_.new_u8(sql));
						tbl_mgr.Add(tbl_itm);
						break;
					case Meta_itm_tid.Tid_index:
						Meta_idx_itm idx_itm = new Meta_idx_itm(name, sql);
						idx_mgr.Add(idx_itm);
						break;
					default:
						Gfo_usr_dlg_.I.Log_many("", "", "db.schema.unknown type: conn=~{0} type=~{1} name=~{2} sql=~{3}", engine.Conn_info().Xto_api(), type_str, name, sql);
						break;
				}
			}
		}	finally {rdr.Rls();}
		Gfo_usr_dlg_.I.Log_many("", "", "db.schema.load.end");
	}
}
