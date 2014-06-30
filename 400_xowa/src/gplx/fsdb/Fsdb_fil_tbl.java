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
public class Fsdb_fil_tbl {
	private Db_provider provider;
	private Db_stmt stmt_insert, stmt_update, stmt_select_by_name, stmt_select_by_id;
	public Fsdb_fil_tbl(Db_provider provider, boolean created) {
		this.provider = provider;
		if (created) Create_table();
	}
	private void Create_table() {
		Sqlite_engine_.Tbl_create(provider, Tbl_name, Tbl_sql); 
		Sqlite_engine_.Idx_create(provider, Idx_owner);
	}
	public void Rls() {
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_update != null) {stmt_update.Rls(); stmt_update = null;}
		if (stmt_select_by_name != null) {stmt_select_by_name.Rls(); stmt_select_by_name = null;}
		if (stmt_select_by_id != null) {stmt_select_by_id.Rls(); stmt_select_by_id = null;}
	}
	private Db_stmt Insert_stmt() {return Db_stmt_.new_insert_(provider, Tbl_name, Fld_fil_id, Fld_fil_owner_id, Fld_fil_name, Fld_fil_xtn_id, Fld_fil_ext_id, Fld_fil_bin_db_id, Fld_fil_size, Fld_fil_modified, Fld_fil_hash);}
	public void Insert(int id, int owner_id, String name, int xtn_id, int ext_id, long size, DateAdp modified, String hash, int bin_db_id) {
		if (stmt_insert == null) stmt_insert = Insert_stmt();
		try {
			stmt_insert.Clear()
			.Val_int_(id)
			.Val_int_(owner_id)
			.Val_str_(name)
			.Val_int_(xtn_id)
			.Val_int_(ext_id)
			.Val_int_(bin_db_id)
			.Val_long_(size)
			.Val_str_(Sqlite_engine_.X_date_to_str(modified))
			.Val_str_(hash)
			.Exec_insert();
		}	catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}	
	private Db_stmt Update_stmt() {return Db_stmt_.new_update_(provider, Tbl_name, String_.Ary(Fld_fil_id), Fld_fil_owner_id, Fld_fil_name, Fld_fil_xtn_id, Fld_fil_ext_id, Fld_fil_bin_db_id, Fld_fil_size, Fld_fil_modified, Fld_fil_hash);}
	public void Update(int id, int owner_id, String name, int xtn_id, int ext_id, long size, DateAdp modified, String hash, int bin_db_id) {
		if (stmt_update == null) stmt_update = Update_stmt();
		try {
			stmt_update.Clear()
			.Val_int_(owner_id)
			.Val_str_(name)
			.Val_int_(xtn_id)
			.Val_int_(ext_id)
			.Val_int_(bin_db_id)
			.Val_long_(size)
			.Val_str_(Sqlite_engine_.X_date_to_str(modified))
			.Val_str_(hash)
			.Val_int_(id)
			.Exec_update();
		}	catch (Exception exc) {stmt_update = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}	
	private Db_stmt Select_by_name_stmt() {
		Db_qry qry = Sqlite_engine_.Supports_indexed_by
			? (Db_qry)Db_qry_sql.rdr_("SELECT * FROM fsdb_fil INDEXED BY fsdb_fil__owner WHERE fil_owner_id = ? AND fil_name = ?;") 
			: Db_qry_.select_().From_(Tbl_name).Cols_all_().Where_(gplx.criterias.Criteria_.And_many(Db_crt_.eq_(Fld_fil_owner_id, Int_.MinValue), Db_crt_.eq_(Fld_fil_name, "")))
			;
		return provider.Prepare(qry);
	}
	public Fsdb_fil_itm Select_itm_by_name(int dir_id, String fil_name) {
		if (stmt_select_by_name == null) stmt_select_by_name = Select_by_name_stmt();
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = stmt_select_by_name.Clear()
				.Val_int_(dir_id)
				.Val_str_(fil_name)
				.Exec_select();
			if (rdr.MoveNextPeer())
				return load_(rdr);
			else
				return Fsdb_fil_itm.Null;
		}
		finally {rdr.Rls();}
	}
	private Db_stmt Select_by_id_stmt() {
		Db_qry qry = Db_qry_.select_().From_(Tbl_name).Cols_all_().Where_(Db_crt_.eq_(Fld_fil_id, 0));
		return provider.Prepare(qry);
	}
	public Fsdb_fil_itm Select_itm_by_id(int fil_id) {
		if (stmt_select_by_id == null) stmt_select_by_id = Select_by_id_stmt();
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = stmt_select_by_name.Clear()
				.Val_int_(fil_id)
				.Exec_select();
			if (rdr.MoveNextPeer())
				return load_(rdr);
			else
				return Fsdb_fil_itm.Null;
		}
		finally {rdr.Rls();}
	}
	private Fsdb_fil_itm load_(DataRdr rdr) {
		return new Fsdb_fil_itm().Init(rdr.ReadInt(Fld_fil_id), rdr.ReadInt(Fld_fil_owner_id), rdr.ReadInt(Fld_fil_ext_id), rdr.ReadStr(Fld_fil_name), rdr.ReadInt(Fld_fil_bin_db_id));
	}
	public static final String Tbl_name = "fsdb_fil", Fld_fil_id = "fil_id", Fld_fil_owner_id = "fil_owner_id", Fld_fil_name = "fil_name", Fld_fil_xtn_id = "fil_xtn_id", Fld_fil_ext_id = "fil_ext_id"
	, Fld_fil_size = "fil_size", Fld_fil_modified = "fil_modified", Fld_fil_hash = "fil_hash", Fld_fil_bin_db_id = "fil_bin_db_id"
	;
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS fsdb_fil"
	,	"( fil_id            integer             NOT NULL    PRIMARY KEY"
	,	", fil_owner_id      integer             NOT NULL"
	,	", fil_xtn_id        integer             NOT NULL"
	,	", fil_ext_id        integer             NOT NULL"
	,	", fil_bin_db_id     integer             NOT NULL"	// group ints at beginning of table
	,	", fil_name          varchar(255)        NOT NULL"
	,	", fil_size          bigint              NOT NULL"
	,	", fil_modified      varchar(14)         NOT NULL"	// stored as yyyyMMddHHmmss
	,	", fil_hash          varchar(40)         NOT NULL"
	,	");"
	);
	public static final Db_idx_itm
//		  Idx_name = Db_idx_itm.sql_	("CREATE INDEX IF NOT EXISTS fsdb_fil__name       ON fsdb_fil (fil_name, fil_owner_id, fil_id, fil_ext_id);")
	  Idx_owner = Db_idx_itm.sql_	("CREATE INDEX IF NOT EXISTS fsdb_fil__owner      ON fsdb_fil (fil_owner_id, fil_name, fil_id);")
	;
}
