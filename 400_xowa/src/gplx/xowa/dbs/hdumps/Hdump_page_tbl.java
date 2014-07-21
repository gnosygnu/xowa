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
public class Hdump_page_tbl {
	private Db_stmt stmt_select, stmt_insert, stmt_update;
	private Io_stream_zip_mgr zip_mgr; private byte zip_tid;
	public void Ctor(Io_stream_zip_mgr zip_mgr, byte zip_tid) {this.zip_mgr = zip_mgr; this.zip_tid = zip_tid;}
	@gplx.Virtual public void Insert(Db_provider provider, int page_id, byte[] html, int frags_len, int make_id) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(provider, Tbl_name, Flds_all);
		try {
			html = zip_mgr.Zip(zip_tid, html);
			stmt_insert.Val_int_(page_id).Val_bry_(html).Val_int_(frags_len).Val_int_(make_id).Exec_insert();
		}	finally {stmt_insert.Rls();}
	}
	@gplx.Virtual public void Update(Db_provider provider, int page_id, byte[] html, int frags_len, int make_id) {
		if (stmt_update == null) stmt_update = Db_stmt_.new_update_(provider, Tbl_name, Flds_all);
		try {
			html = zip_mgr.Zip(zip_tid, html);
			stmt_update.Val_int_(page_id).Val_bry_(html).Val_int_(frags_len).Val_int_(make_id).Exec_update();
		}	finally {stmt_update.Rls();}
	}
	@gplx.Virtual public void Select(Db_provider provider, Hdump_page_row rv, int page_id) {
		if (stmt_select == null) stmt_select = Db_stmt_.new_select_(provider, Tbl_name, String_.Ary(Fld_page_id), Flds_all);
		try {				
			DataRdr rdr = stmt_select.Exec_select();
			rv.Ctor
			( rdr.ReadInt(Fld_page_id)
			, zip_mgr.Unzip(zip_tid, rdr.ReadBry(Fld_page_text))
			, rdr.ReadInt(Fld_frags_len)
			, rdr.ReadInt(Fld_make_id)
			);
			rdr.Rls();
		}	finally {stmt_select.Rls();}
	}
	@gplx.Virtual public void Delete_all(Db_provider provider) {
		Db_qry_.delete_tbl_(Tbl_name).Exec_qry(provider);
	}
	public void Rls() {
		stmt_select = stmt_insert = stmt_update = null;
		zip_mgr = null;
	}
	public static final String Tbl_name = "html_page"
	, Fld_page_id = "page_id", Fld_page_text = "page_text", Fld_frags_len = "frags_len", Fld_make_id= "make_id";
	private static final String[] Flds_all = new String[] {Fld_page_id, Fld_page_text, Fld_frags_len, Fld_make_id};
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE IF NOT EXISTS html_page"
	, "( page_id                integer             NOT NULL		PRIMARY KEY"
	, ", page_text              mediumblob          NOT NULL"
	, ", frags_len              integer             NOT NULL"
	, ", make_id                integer             NOT NULL"
	, ");"
	);
}
class Hdump_page_tbl_mem extends Hdump_page_tbl { 	private OrderedHash hash = OrderedHash_.new_();
	@Override public void Insert(Db_provider provider, int page_id, byte[] html, int frags_len, int make_id) {
		Hdump_page_row row = new Hdump_page_row().Ctor(page_id, html, frags_len, make_id);
		hash.Add(page_id, row);
	}
	@Override public void Update(Db_provider provider, int page_id, byte[] html, int frags_len, int make_id) {
		Hdump_page_row row = (Hdump_page_row)hash.Fetch(page_id);
		row.Ctor(page_id, html, frags_len, make_id);
	}
	@Override public void Select(Db_provider provider, Hdump_page_row rv, int page_id) {
		Hdump_page_row row = (Hdump_page_row)hash.Fetch(page_id);
		rv.Ctor(row.Id(), row.Html(), row.Frags_len(), row.Make_id());
	}
	@Override public void Delete_all(Db_provider provider) {
		hash.Clear();
	}
}
