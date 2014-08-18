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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*;
public class Hdump_text_tbl {
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public Db_provider Provider() {return provider;} public void Provider_(Db_provider v) {this.Rls_all(); provider = v;} private Db_provider provider;
	@gplx.Virtual public void Delete_by_page(int page_id) {
		if (stmt_delete == null) stmt_delete = Db_stmt_.new_delete_(provider, Tbl_name, Fld_page_id);
		try {
			stmt_delete.Clear().Val_int_(page_id).Exec_delete();
		}	catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Insert(int page_id, int tid, int idx, int version, byte[] data) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(provider, Tbl_name, Flds_all);
		try {
			stmt_insert.Clear().Val_int_(page_id).Val_int_(tid).Val_int_(idx).Val_int_(version).Val_str_by_bry_(data).Exec_insert();
		}	catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Select_by_page(ListAdp rv, int page_id) {
		if (stmt_select == null) stmt_select = Db_stmt_.new_select_(provider, Tbl_name, String_.Ary(Fld_page_id), Flds_all);
		try {
			DataRdr rdr = stmt_select.Clear().Val_int_(page_id).Exec_select();
			while(rdr.MoveNextPeer()) {
				Hdump_text_row row = new Hdump_text_row
				( rdr.ReadInt(Fld_page_id)
				, rdr.ReadInt(Fld_text_tid)
				, rdr.ReadInt(Fld_text_idx)
				, rdr.ReadInt(Fld_text_version)
				, rdr.ReadBryByStr(Fld_text_data)
				);
				rv.Add(row);
			}
			rdr.Rls();
		}	finally {stmt_select.Rls();}
	}
	public void Rls_all() {
		if (stmt_select != null) {stmt_select.Rls(); stmt_select = null;}
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_delete != null) {stmt_delete.Rls(); stmt_delete = null;}
		provider = null;
	}
	public static final String Tbl_name = "html_text"
	, Fld_page_id = "page_id", Fld_text_tid = "text_tid", Fld_text_idx = "text_idx"
	, Fld_text_version = "text_version", Fld_text_data = "text_data";
	private static final String[] Flds_all = new String[] {Fld_page_id, Fld_text_tid, Fld_text_idx, Fld_text_version, Fld_text_data};
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE IF NOT EXISTS html_text"
	, "( page_id                integer             NOT NULL"
	, ", text_tid               integer             NOT NULL"
	, ", text_idx               integer             NOT NULL"
	, ", text_version           integer             NOT NULL"
	, ", text_data              mediumblob          NOT NULL"
	, ");"
	);
	public static final Db_idx_itm
	  Idx_core = Db_idx_itm.sql_("CREATE UNIQUE INDEX IF NOT EXISTS html_text__core ON html_text (page_id, text_tid, text_idx);")
	;
}
class Hdump_text_tbl_mem extends Hdump_text_tbl { 	private HashAdp pages = HashAdp_.new_();
	@Override public void Insert(int page_id, int tid, int idx, int version, byte[] data) {
		Hdump_text_row row = new Hdump_text_row(page_id, tid, idx, version, data);
		ListAdp rows = Get_or_new(pages, page_id);
		rows.Add(row);
	}
	@Override public void Select_by_page(ListAdp rv, int page_id) {
		ListAdp rows = Get_or_new(pages, page_id);
		int len = rows.Count();
		for (int i = 0; i < len; ++i) {
			Hdump_text_row row = (Hdump_text_row)rows.FetchAt(i);
			rv.Add(row);
		}
	}
	@Override public void Delete_by_page(int page_id) {pages.Del(page_id);}
	private static ListAdp Get_or_new(HashAdp pages, int page_id) {
		ListAdp rv = (ListAdp)pages.Fetch(page_id);
		if (rv == null) {
			rv = ListAdp_.new_();
			pages.Add(page_id, rv);
		}
		return rv;
	}
	public static final Db_provider Null_provider = null;
}
