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
package gplx.xowa.html.hdumps.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.ios.*; import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.dbs.*;
import gplx.xowa.wikis.data.*;
public class Xohd_page_html_tbl {
	private String tbl_name = "wiki_page_html"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_db_id, fld_page_id, fld_html_tid, fld_html_data;
	private Db_conn conn; private Db_stmt stmt_select, stmt_insert, stmt_delete; private int db_id; private byte zip_tid;
	private final Io_stream_zip_mgr zip_mgr = Xoa_app_.Utl__zip_mgr();
	public Db_conn Conn() {return conn;}
	public void Conn_(Db_conn new_conn, boolean created, boolean schema_is_1, int db_id, byte zip_tid) {
		this.conn = new_conn; flds.Clear(); this.db_id = db_id; this.zip_tid = zip_tid;
		if (schema_is_1) {
			fld_db_id		= Db_meta_fld.Key_null;
		}
		else {
			fld_db_id		= flds.Add_int("db_id");
		}
		fld_page_id			= flds.Add_int("page_id");
		fld_html_tid		= flds.Add_int("html_tid");
		fld_html_data		= flds.Add_bry("html_data");
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_select = stmt_insert = stmt_delete = null;
	}
	public int Insert(int page_id, int tid, byte[] data) {
		if (stmt_insert == null) stmt_insert = conn.Rls_reg(conn.Stmt_insert(tbl_name, flds));
		data = zip_mgr.Zip(zip_tid, data);
		stmt_insert.Clear().Val_int(fld_db_id, db_id).Val_int(fld_page_id, page_id).Val_int(fld_html_tid, tid).Val_bry(fld_html_data, data).Exec_insert();
		return data.length;
	}
	public void Delete(int page_id) {
		if (stmt_delete == null) stmt_delete = conn.Rls_reg(conn.Stmt_delete(tbl_name, String_.Ary_wo_null(fld_db_id, fld_page_id)));
		stmt_delete.Clear().Crt_int(fld_db_id, db_id).Crt_int(fld_page_id, page_id).Exec_delete();
	}
	public void Select_by_page(ListAdp rv, int page_id) {
		if (stmt_select == null) stmt_select = conn.Rls_reg(conn.Stmt_select(tbl_name, flds, String_.Ary_wo_null(fld_db_id, fld_page_id)));
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt_select.Clear().Crt_int(fld_db_id, db_id).Crt_int(fld_page_id, page_id).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xohd_page_html_row row = new Xohd_page_html_row
				( rdr.Read_int(fld_page_id)
				, rdr.Read_int(fld_html_tid)
				, zip_mgr.Unzip(zip_tid, rdr.Read_bry(fld_html_data))
				);
				rv.Add(row);
			}
		}
		finally {rdr.Rls();}
	}
	public void Create_idx() {
		conn.Exec_create_idx(Gfo_usr_dlg_._, Db_meta_idx.new_unique_by_tbl_wo_null(tbl_name, "pkey", fld_db_id, fld_page_id, fld_html_tid));
	}
	public static final String Hash_key = "wiki_page_html";
	public static Xohd_page_html_tbl Get_from_db(Xow_core_data_mgr core_data_mgr, Xowd_db_file db_file) {return (Xohd_page_html_tbl)db_file.Tbls__get_by(core_data_mgr, Hash_key);}
	public static Xohd_page_html_tbl Get_from_db__root(Xowe_wiki wiki) {
		gplx.xowa.wikis.data.Xow_core_data_mgr core_data_mgr = wiki.Data_mgr__core_mgr();
		Xowd_db_file hdump_db = core_data_mgr.Dbs__get_by_tid_nth_or_new(Xowd_db_file_.Tid_html);
		Xohd_page_html_tbl rv = new Xohd_page_html_tbl();
		rv.Conn_(hdump_db.Conn(), Bool_.N, core_data_mgr.Cfg__schema_is_1(), core_data_mgr.Cfg__db_id(), core_data_mgr.Cfg__hdump_zip_tid());
		return rv;
	}
}
