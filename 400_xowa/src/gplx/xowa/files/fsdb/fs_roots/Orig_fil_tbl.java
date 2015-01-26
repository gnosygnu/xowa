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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.dbs.*;
interface Orig_fil_tbl extends RlsAble {
	void Ctor(Db_conn conn, boolean created);
	Orig_fil_itm Select_itm(byte[] ttl);
	void Insert(Orig_fil_itm fil_itm);
}
class Orig_fil_tbl_mem implements Orig_fil_tbl {
	private Hash_adp_bry hash;
	public void Ctor(Db_conn conn, boolean created) {hash = Hash_adp_bry.cs_();}	// NOTE: cs_ b/c ttl-based
	public Orig_fil_itm Select_itm(byte[] ttl) {return (Orig_fil_itm)hash.Get_by_bry(ttl);}
	public void Insert(Orig_fil_itm fil_itm) {hash.Add(fil_itm.Fil_name(), fil_itm);}
	public void Rls() {}
}
class Orig_fil_tbl_sql implements Orig_fil_tbl {
	private Db_conn conn;
	private Db_stmt stmt_select, stmt_insert;
	public void Ctor(Db_conn conn, boolean created) {
		this.conn = conn;
		if (created) Create_table(conn);
	}
	public Orig_fil_itm Select_itm(byte[] ttl) {
		if (stmt_select == null) stmt_select = conn.New_stmt(Db_qry_.select_().From_(Tbl_name).Cols_all_().Where_(Db_crt_.eq_(Fld_fil_name, "")));
		Orig_fil_itm rv = Orig_fil_itm.Null;
		DataRdr rdr = stmt_select.Clear().Val_bry(ttl).Exec_select();
		if (rdr.MoveNextPeer())
			rv = new Orig_fil_itm().Init_by_load(rdr);
		rdr.Rls();
		return rv;
	}
	private Db_stmt Insert_stmt() {return Db_stmt_.new_insert_(conn, Tbl_name, Fld_fil_uid, Fld_fil_name, Fld_fil_ext_id, Fld_fil_w, Fld_fil_h, Fld_fil_dir_url);}
	public void Insert(Orig_fil_itm fil_itm) {
		if (stmt_insert == null) stmt_insert = Insert_stmt();
		stmt_insert.Clear()
		.Val_int(fil_itm.Fil_uid())
		.Val_bry_as_str(fil_itm.Fil_name())
		.Val_int(fil_itm.Fil_ext_id())
		.Val_int(fil_itm.Fil_w())
		.Val_int(fil_itm.Fil_h())
		.Val_bry_as_str(fil_itm.Fil_dir_url())
		.Exec_insert();
	}	
	public void Rls() {
		stmt_select.Rls();
		stmt_insert.Rls();
	}
	private void Create_table(Db_conn p) {
		Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql);
		Sqlite_engine_.Idx_create(p, Idx_main);
	}
	private static final String Tbl_name = "orig_fil"
	, Fld_fil_uid = "fil_uid", Fld_fil_name = "fil_name", Fld_fil_ext_id = "fil_ext_id"
	, Fld_fil_w = "fil_w", Fld_fil_h = "fil_h"
	, Fld_fil_dir_url = "fil_dir_url"
	;
	private static final Db_idx_itm
	  Idx_main     = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS orig_fil__main ON orig_fil (fil_name);")
	;
	static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS orig_fil"
	,	"( fil_uid          integer             NOT NULL    PRIMARY KEY"
	,	", fil_name         varchar(1024)       NOT NULL"
	,	", fil_ext_id       integer             NOT NULL"
	,	", fil_w            integer             NOT NULL"
	,	", fil_h            integer             NOT NULL"
	,	", fil_dir_url      varchar(1024)       NOT NULL"	// NOTE: don't put dir in separate table; note that entire root_dir_wkr is not built to scale due to need for recursively loading all files
	,	");"
	);
}
