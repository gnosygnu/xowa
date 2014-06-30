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
package gplx.xowa.users.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
class Xou_db_xtn_tbl {
	private Db_provider provider;
	private Db_stmt_bldr stmt_bldr;
	public void Db_init(Db_provider provider) {this.provider = provider;}
	public void Db_save(Xou_db_xtn_itm itm) {
		if (stmt_bldr == null) stmt_bldr = new Db_stmt_bldr(Tbl_name, String_.Ary(Fld_xtn_key), Fld_xtn_version).Init(provider);
		Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
		switch (itm.Cmd_mode()) {
			case Db_cmd_mode.Create:	stmt.Clear().Val_str_(itm.Key()).Val_str_(itm.Version()).Exec_insert(); break;
			case Db_cmd_mode.Update:	stmt.Clear()					.Val_str_(itm.Version()).Val_str_(itm.Key()).Exec_update(); break;
			case Db_cmd_mode.Delete:	stmt.Clear().Val_str_(itm.Key()).Exec_delete();	break;
			case Db_cmd_mode.Ignore:	break;
			default:					throw Err_.unhandled(itm.Cmd_mode());
		}
		itm.Cmd_mode_(Db_cmd_mode.Ignore);
	}
	public void Db_term() {
		if (stmt_bldr != null) stmt_bldr.Rls();
	}
	public void Db_when_new(Db_provider provider) {
		Sqlite_engine_.Tbl_create(provider, Tbl_name, Tbl_sql);
		Sqlite_engine_.Idx_create(provider, Idx_key);
	}
	public void Select_all(OrderedHash list) {
		list.Clear();
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = Db_qry_.select_tbl_(Tbl_name).Exec_qry_as_rdr(provider);
			while (rdr.MoveNextPeer()) {
				Xou_db_xtn_itm itm = new Xou_db_xtn_itm();
				itm.Init_by_load(rdr);
				list.Add(itm.Key(), itm);
			}
		}
		catch (Exception e) {throw Err_.err_(e, "stmt failed");}
		finally {rdr.Rls();}
	}		
	private static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE xowa_xtn"
	, "( xtn_id            integer       NOT NULL        PRIMARY KEY     AUTOINCREMENT"
	, ", xtn_key           varchar(255)"
	, ", xtn_version       varchar(255)"
	, ");"
	);
	public static final String Tbl_name = "xowa_xtn"
	, Fld_xtn_key = "xtn_key", Fld_xtn_version = "xtn_version"
	;
	private static final Db_idx_itm
		Idx_key     		= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS xowa_xtn__key ON xowa_xtn (xtn_key);")
	;
}
