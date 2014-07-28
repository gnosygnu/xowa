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
import gplx.dbs.*; import gplx.ios.*;
public class Hdump_frag_tbl {
	private Db_stmt stmt_select, stmt_insert, stmt_update, stmt_delete;
	@gplx.Virtual public void Insert(Db_provider provider, int frag_id, int page_id, int frag_tid, byte[] frag_key, byte[] frag_text) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(provider, Tbl_name, Flds_all);
		try {
			stmt_insert.Val_int_(frag_id).Val_int_(page_id).Val_int_(frag_tid).Val_str_by_bry_(frag_key).Val_str_by_bry_(frag_text).Exec_insert();
		}	finally {stmt_insert.Rls();}
	}
	@gplx.Virtual public void Update(Db_provider provider, int frag_id, int page_id, int frag_tid, byte[] frag_key, byte[] frag_text) {
		if (stmt_update == null) stmt_update = Db_stmt_.new_update_(provider, Tbl_name, Flds_all);
		try {
			stmt_update.Val_int_(frag_id).Val_int_(page_id).Val_int_(frag_tid).Val_str_by_bry_(frag_key).Val_str_by_bry_(frag_text).Exec_update();
		}	finally {stmt_update.Rls();}
	}
	@gplx.Virtual public void Select(Db_provider provider, Hdump_frag_row rv, int frag_id) {
		if (stmt_select == null) stmt_select = Db_stmt_.new_select_(provider, Tbl_name, String_.Ary(Fld_frag_id), Flds_all);
		try {				
			DataRdr rdr = stmt_select.Val_int_(frag_id).Exec_select();
			rv.Ctor
			( rdr.ReadInt(Fld_frag_id)
			, rdr.ReadInt(Fld_page_id)
			, rdr.ReadInt(Fld_frag_tid)
			, rdr.ReadBryByStr(Fld_frag_key)
			, rdr.ReadBryByStr(Fld_frag_text)
			);
			rdr.Rls();
		}	finally {stmt_select.Rls();}
	}
	@gplx.Virtual public void Delete(Db_provider provider, int frag_id) {
		if (stmt_delete == null) stmt_delete = Db_stmt_.new_delete_(provider, Tbl_name, Fld_frag_id);
		try {
			stmt_delete.Val_int_(frag_id).Exec_delete();
		}	finally {stmt_delete.Rls();}
	}
	@gplx.Virtual public void Delete_all(Db_provider provider) {
		Db_qry_.delete_tbl_(Tbl_name).Exec_qry(provider);
	}
	public void Rls() {
		stmt_select = stmt_insert = stmt_update = stmt_delete = null;
	}
	public static final String Tbl_name = "html_frag"
	, Fld_frag_id = "frag_id", Fld_page_id = "page_id", Fld_frag_tid = "frag_tid"
	, Fld_frag_key = "frag_key", Fld_frag_text = "frag_text";
	private static final String[] Flds_all = new String[] {Fld_frag_id, Fld_page_id, Fld_frag_tid, Fld_frag_key, Fld_frag_text};
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE IF NOT EXISTS html_frag"
	, "( frag_id                integer             NOT NULL		PRIMARY KEY"
	, ", page_id                integer             NOT NULL"
	, ", frag_tid               integer             NOT NULL"
	, ", frag_key               varchar(255)        NOT NULL"
	, ", frag_text              mediumblob          NOT NULL"
	, ");"
	);
}
class Hdump_frag_tbl_mem extends Hdump_frag_tbl { 	private OrderedHash hash = OrderedHash_.new_();
	@Override public void Insert(Db_provider provider, int frag_id, int page_id, int frag_tid, byte[] frag_key, byte[] frag_text) {
		Hdump_frag_row row = new Hdump_frag_row().Ctor(frag_id, page_id, frag_tid, frag_key, frag_text);
		hash.Add(frag_id, row);
	}
	@Override public void Update(Db_provider provider, int frag_id, int page_id, int frag_tid, byte[] frag_key, byte[] frag_text) {
		Hdump_frag_row row = (Hdump_frag_row)hash.Fetch(frag_id);
		row.Ctor(frag_id, page_id, frag_tid, frag_key, frag_text);
	}
	@Override public void Select(Db_provider provider, Hdump_frag_row rv, int frag_id) {
		Hdump_frag_row row = (Hdump_frag_row)hash.Fetch(frag_id);
		rv.Ctor(row.Frag_id(), row.Page_id(), row.Frag_tid(), row.Frag_key(), row.Frag_text());
	}
	@Override public void Delete(Db_provider provider, int frag_id) {
		hash.Del(frag_id);
	}
	@Override public void Delete_all(Db_provider provider) {
		hash.Clear();
	}
}
