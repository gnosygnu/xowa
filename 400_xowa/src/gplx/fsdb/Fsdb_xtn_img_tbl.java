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
public class Fsdb_xtn_img_tbl {
	private Db_provider provider;
	private Db_stmt stmt_insert, stmt_select_by_id;
	public Fsdb_xtn_img_tbl(Db_provider provider, boolean created) {
		this.provider = provider;
		if (created) Create_table();
	}
	public void Rls() {
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_select_by_id != null) {stmt_select_by_id.Rls(); stmt_select_by_id = null;}
	}
	public void Create_table() {
		Sqlite_engine_.Tbl_create(provider, Tbl_name, Tbl_sql); 
	}
	public Db_stmt Insert_stmt() {return Db_stmt_.new_insert_(provider, Tbl_name, Fld_img_id, Fld_img_w, Fld_img_h);}
	public void Insert(int id, int w, int h) {
		if (stmt_insert == null) stmt_insert = Insert_stmt();
		try {
			stmt_insert.Clear()
			.Val_int_(id)
			.Val_int_(w)
			.Val_int_(h)
			.Exec_insert();
		}	catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	public Db_stmt Select_itm_by_id_stmt() {return Db_stmt_.new_select_(provider, Tbl_name, String_.Ary(Fld_img_id), Fld_img_w, Fld_img_h); }
	public Fsdb_xtn_img_itm Select_itm_by_id(int id) {
		if (stmt_select_by_id == null) stmt_select_by_id = this.Select_itm_by_id_stmt();
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = stmt_select_by_id.Clear()
				.Val_int_(id)
				.Exec_select();
			if (rdr.MoveNextPeer())
				return new Fsdb_xtn_img_itm().Init_by_load(id, rdr.ReadInt(Fld_img_w), rdr.ReadInt(Fld_img_h));
			else
				return Fsdb_xtn_img_itm.Null;
		}
		finally {rdr.Rls();}
	}
	public static final String Tbl_name = "fsdb_xtn_img", Fld_img_id = "img_id", Fld_img_w = "img_w", Fld_img_h = "img_h";
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS fsdb_xtn_img"
	,	"( img_id            integer             NOT NULL    PRIMARY KEY"
	,	", img_w             integer             NOT NULL"
	,	", img_h             integer             NOT NULL"
	,	");"
	);
}
