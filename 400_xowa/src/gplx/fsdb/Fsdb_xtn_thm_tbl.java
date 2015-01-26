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
public class Fsdb_xtn_thm_tbl {
	private Fsdb_db_atr_fil atr_fil; private Db_conn conn;
	private Db_stmt stmt_insert, stmt_select_by_fil_w;
	public Fsdb_xtn_thm_tbl(Fsdb_db_atr_fil atr_fil, Db_conn conn, boolean created) {
		this.atr_fil = atr_fil; this.conn = conn;
		if (created) Create_table();
	}
	private void Create_table() {
		Sqlite_engine_.Tbl_create(conn, Tbl_name, Tbl_sql);
		Sqlite_engine_.Idx_create(conn, Idx_name);
	}
	public void Rls() {
		if (stmt_insert != null)			{stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_select_by_fil_w != null)	{stmt_select_by_fil_w.Rls(); stmt_select_by_fil_w = null;}
	}
	private boolean Schema_thm_page() {
		if (schema_thm_page_init) {
			schema_thm_page = atr_fil.Abc_mgr().Cfg_mgr().Schema_thm_page();
			schema_thm_page_init = false;
		}
		return schema_thm_page;
	}	private boolean schema_thm_page, schema_thm_page_init = true;
	private Db_stmt Make_stmt_insert() {
		return	this.Schema_thm_page()
			?	Db_stmt_.new_insert_(conn, Tbl_name, Fld_thm_id, Fld_thm_owner_id, Fld_thm_w, Fld_thm_h, Fld_thm_bin_db_id, Fld_thm_size, Fld_thm_modified, Fld_thm_hash, Fld_thm_time, Fld_thm_page)
			:	Db_stmt_.new_insert_(conn, Tbl_name, Fld_thm_id, Fld_thm_owner_id, Fld_thm_w, Fld_thm_h, Fld_thm_bin_db_id, Fld_thm_size, Fld_thm_modified, Fld_thm_hash, Fld_thm_thumbtime)
			;
	}
	public void Insert(int id, int thm_owner_id, int width, int height, double thumbtime, int page, int bin_db_id, long size, DateAdp modified, String hash) {
		if (stmt_insert == null) stmt_insert = Make_stmt_insert();
		try {
			stmt_insert.Clear()
			.Val_int(id)
			.Val_int(thm_owner_id)
			.Val_int(width)
			.Val_int(height)
			.Val_int(bin_db_id)
			.Val_long(size)
			.Val_str(Sqlite_engine_.X_date_to_str(modified))
			.Val_str(hash);
			if (this.Schema_thm_page()) {
				stmt_insert.Val_double	(gplx.xowa.files.Xof_doc_thumb.Db_save_double(thumbtime));
				stmt_insert.Val_int	(gplx.xowa.files.Xof_doc_page.Db_save_int(page));
			}
			else
				stmt_insert.Val_int(gplx.xowa.files.Xof_doc_thumb.Db_save_int(thumbtime));
			stmt_insert.Exec_insert();
		}	catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	private Db_stmt Select_by_fil_w_stmt() {
		Db_qry_select qry = Db_qry_.select_().From_(Tbl_name).Cols_all_();
		gplx.criterias.Criteria crt 
			= this.Schema_thm_page()
			? gplx.criterias.Criteria_.And_many(Db_crt_.eq_(Fld_thm_owner_id, Int_.MinValue), Db_crt_.eq_(Fld_thm_w, Int_.MinValue), Db_crt_.eq_(Fld_thm_time, Int_.MinValue), Db_crt_.eq_(Fld_thm_page, Int_.MinValue))
			: gplx.criterias.Criteria_.And_many(Db_crt_.eq_(Fld_thm_owner_id, Int_.MinValue), Db_crt_.eq_(Fld_thm_w, Int_.MinValue), Db_crt_.eq_(Fld_thm_thumbtime, Int_.MinValue))
			;
		qry.Where_(crt);
		return conn.New_stmt(qry);
	}
	public boolean Select_itm_by_fil_width(int owner_id, Fsdb_xtn_thm_itm thm) {
		if (stmt_select_by_fil_w == null) stmt_select_by_fil_w = Select_by_fil_w_stmt();
		DataRdr rdr = DataRdr_.Null;
		try {
			stmt_select_by_fil_w.Clear()
				.Val_int(owner_id)
				.Val_int(thm.Width())
				;
			if (this.Schema_thm_page())  {
				stmt_select_by_fil_w.Val_double(gplx.xowa.files.Xof_doc_thumb.Db_save_double(thm.Thumbtime()));
				stmt_select_by_fil_w.Val_int(gplx.xowa.files.Xof_doc_page.Db_save_int(thm.Page()));
			}
			else {
				stmt_select_by_fil_w.Val_int(gplx.xowa.files.Xof_doc_thumb.Db_save_int(thm.Thumbtime()));
			}
			rdr = stmt_select_by_fil_w.Exec_select();
			if (rdr.MoveNextPeer()) {
				thm.Init_by_load(rdr, this.Schema_thm_page());
				return true;
			}
			else
				return false;
		}
		finally {rdr.Rls();}
	}
	public static final String Tbl_name = "fsdb_xtn_thm"
	, Fld_thm_id = "thm_id", Fld_thm_owner_id = "thm_owner_id", Fld_thm_w = "thm_w", Fld_thm_h = "thm_h"
	, Fld_thm_thumbtime = "thm_thumbtime", Fld_thm_time = "thm_time", Fld_thm_page = "thm_page"
	, Fld_thm_bin_db_id = "thm_bin_db_id", Fld_thm_size = "thm_size", Fld_thm_modified = "thm_modified", Fld_thm_hash = "thm_hash";
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS fsdb_xtn_thm"
	,	"( thm_id            integer             NOT NULL    PRIMARY KEY"
	,	", thm_owner_id      integer             NOT NULL"
	,	", thm_w             integer             NOT NULL"
	,	", thm_h             integer             NOT NULL"
	//,	", thm_thumbtime     integer             NOT NULL"	// removed; DATE:2014-01-23
	,	", thm_time          double              NOT NULL"	// replacement for thm_time
	,	", thm_page          integer             NOT NULL"
	,	", thm_bin_db_id     integer             NOT NULL"
	,	", thm_size          bigint              NOT NULL"
	,	", thm_modified      varchar(14)         NOT NULL"	// stored as yyyyMMddHHmmss
	,	", thm_hash          varchar(40)         NOT NULL"
	,	");"
	);
	public static final Db_idx_itm
	  Idx_name = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS fsdb_xtn_thm__owner      ON fsdb_xtn_thm (thm_owner_id, thm_id, thm_w, thm_time, thm_page);")
	;
	public static final DateAdp Modified_null = null;
	public static final String Hash_null = "";
}
