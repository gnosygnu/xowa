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
import gplx.dbs.*;
public class Fsdb_mnt_tbl {
	public static void Create_table(Db_provider p) {
		Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql);
	}
	public static void Insert(Db_provider p, int id, String name, String url) {
		Db_stmt stmt = Insert_stmt(p);
		try {Insert(stmt, id, name, url);}
		finally {stmt.Rls();}
	}
	private static Db_stmt Insert_stmt(Db_provider p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_mnt_id, Fld_mnt_name, Fld_mnt_url);}
	private static void Insert(Db_stmt stmt, int id, String name, String url) {
		stmt.Clear()
		.Val_int_(id)
		.Val_str_(name)
		.Val_str_(url)
		.Exec_insert();
	}	
	public static Db_stmt Update_stmt(Db_provider p) {return Db_stmt_.new_update_(p, Tbl_name, String_.Ary(Fld_mnt_id), Fld_mnt_name, Fld_mnt_url);}
	public static void Update(Db_stmt stmt, int id, String name, String url) {
		stmt.Clear()
		.Val_str_(name)
		.Val_str_(url)
		.Val_int_(id)
		.Exec_update();
	}	
	public static Fsdb_mnt_itm[] Select_all(Db_provider p) {
		Db_qry qry = Db_qry_.select_().From_(Tbl_name);
		DataRdr rdr = DataRdr_.Null;
		ListAdp list = ListAdp_.new_();
		try {
			rdr = p.Exec_qry_as_rdr(qry);
			while (rdr.MoveNextPeer()) {
				Fsdb_mnt_itm itm = new Fsdb_mnt_itm(rdr.ReadInt(Fld_mnt_id), rdr.ReadStr(Fld_mnt_name), rdr.ReadStr(Fld_mnt_url));
				list.Add(itm);
			}
		}
		finally {rdr.Rls();}
		return (Fsdb_mnt_itm[])list.Xto_ary_and_clear(Fsdb_mnt_itm.class);
	}
	public static final String Tbl_name = "fsdb_mnt", Fld_mnt_id = "mnt_id", Fld_mnt_name = "mnt_name", Fld_mnt_url = "mnt_url";
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS fsdb_mnt"
	,	"( mnt_id            integer             NOT NULL    PRIMARY KEY"
	,	", mnt_name          varchar(255)        NOT NULL"
	,	", mnt_url           varchar(255)        NOT NULL"
	,	");"
	);
}
