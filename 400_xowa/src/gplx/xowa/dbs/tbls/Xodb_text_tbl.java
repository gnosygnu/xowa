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
package gplx.xowa.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.ios.*;
public class Xodb_text_tbl {
	public Xodb_text_tbl(Xodb_mgr_sql db_mgr) {this.db_mgr = db_mgr; zip_mgr = db_mgr.Wiki().App().Zip_mgr();} private Xodb_mgr_sql db_mgr; private Io_stream_zip_mgr zip_mgr;
	public void Delete_all(Db_conn conn) {conn.Exec_qry(Db_qry_.delete_tbl_(Tbl_name));}
	public Db_stmt Insert_stmt(Db_conn prov) {return Db_stmt_.new_insert_(prov, Tbl_name, Fld_page_id, Fld_old_text);}
	public void Insert(Db_stmt stmt, int page_id, byte[] text, byte storage_type) {
		stmt.Clear().Val_int(page_id).Val_bry(text).Exec_insert();
	}
	public void Update(int file_id, int page_id, byte[] text) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			Db_conn conn = db_mgr.Fsys_mgr().Get_by_idx(file_id).Conn();
			stmt = Db_stmt_.new_update_(conn, Tbl_name, String_.Ary(Fld_page_id), Fld_old_text);
			stmt.Val_bry(text).Val_int(page_id).Exec_update();
		}	finally {stmt.Rls();}		
	}
	public byte[] Select(int file_id, int page_id) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			Db_conn conn = db_mgr.Fsys_mgr().Get_by_idx(file_id).Conn();
			stmt = Db_stmt_.new_select_(conn, Tbl_name, String_.Ary(Fld_page_id), Fld_old_text);
			byte[] rv = (byte[])stmt.Val_int(page_id).Exec_select_val();
			rv = zip_mgr.Unzip(db_mgr.Data_storage_format(), rv);
			return rv;
		}	finally {stmt.Rls();}
	}
	public void Select_in(Cancelable cancelable, Xodb_file file, OrderedHash hash) {
		DataRdr rdr = DataRdr_.Null; 
		Db_stmt stmt = Db_stmt_.Null;
		try {
			int len = hash.Count();
			ListAdp pages = ListAdp_.new_();
			for (int i = 0; i < len; i++) {
				Xodb_page page = (Xodb_page)hash.FetchAt(i);
				if (page.Text_db_id() == file.Id())
					pages.Add(page);
			}
			len = pages.Count();
			if (len == 0) return;
			Object[] args_ary = new Object[len];
			for (int i = 0; i < len; i++) {
				if (cancelable.Canceled()) return;
				args_ary[i] = 0;
			}
			stmt = Db_stmt_.new_select_in_(file.Conn(), Tbl_name, Fld_page_id, args_ary);
			for (int i = 0; i < len; i++) {
				if (cancelable.Canceled()) return;
				Xodb_page page = (Xodb_page)pages.FetchAt(i);
				stmt.Val_int(page.Id());
			}
			rdr = stmt.Exec_select();
			while (rdr.MoveNextPeer()) {
				if (cancelable.Canceled()) return;
				int page_id = rdr.ReadInt(Fld_page_id);
				byte[] old_text = rdr.ReadBry(Fld_old_text);
				old_text = zip_mgr.Unzip(db_mgr.Data_storage_format(), old_text);
				Xodb_page page = (Xodb_page)hash.Fetch(Int_obj_val.new_(page_id));
				page.Text_(old_text);
			}
		}	finally {rdr.Rls(); stmt.Rls();}
	}
	public static final String Tbl_name = "text", Fld_page_id = "page_id", Fld_old_text = "old_text";
}
