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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.ios.*; import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.dbs.*; import gplx.dbs.cfgs.*;
public class Xowd_html_tbl implements RlsAble {
	private final String tbl_name = "html"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_page_id, fld_html_tid, fld_html_data;
	private final Db_conn conn; private Db_stmt stmt_select, stmt_insert, stmt_delete;
	private final Io_stream_zip_mgr zip_mgr = Xoa_app_.Utl__zip_mgr(); private final byte zip_tid;
	public Xowd_html_tbl(Db_conn conn, byte zip_tid) {
		this.conn = conn; this.zip_tid = zip_tid;
		fld_page_id			= flds.Add_int("page_id");
		fld_html_tid		= flds.Add_int("html_tid");
		fld_html_data		= flds.Add_bry("html_data");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds));}
	public void Create_idx() {conn.Ddl_create_idx(Gfo_usr_dlg_.I, Db_meta_idx.new_unique_by_tbl(tbl_name, "main", fld_page_id, fld_html_tid));}
	public void Insert_bgn() {conn.Txn_bgn(); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public int Insert(int page_id, int tid, byte[] data) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		data = zip_mgr.Zip(zip_tid, data);
		stmt_insert.Clear().Val_int(fld_page_id, page_id).Val_int(fld_html_tid, tid).Val_bry(fld_html_data, data).Exec_insert();
		return data.length;
	}
	public void Delete(int page_id) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, String_.Ary(fld_page_id));
		stmt_delete.Clear().Crt_int(fld_page_id, page_id).Exec_delete();
	}
	public void Select_by_page(ListAdp rv, int page_id) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, String_.Ary(fld_page_id));
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_page_id, page_id).Exec_select__rls_manual();
		try {
			while (rdr.Move_next()) {
				Xowd_html_row row = new Xowd_html_row(rdr.Read_int(fld_page_id), rdr.Read_int(fld_html_tid), zip_mgr.Unzip(zip_tid, rdr.Read_bry(fld_html_data)));
				rv.Add(row);
			}
		}
		finally {rdr.Rls();}
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public static void Assert_col__page_html_db_id(Xowd_db_mgr db_mgr) {
		Db_cfg_tbl cfg_tbl = db_mgr.Tbl__cfg();
		String exists = cfg_tbl.Select_str_or(Xow_cfg_consts.Grp__wiki_schema, Xowd_db_file_schema_props.Key__col_page_html_text_id, "n");
		if (String_.Eq(exists, "y")) return;
		Xowd_page_tbl pg_tbl = db_mgr.Tbl__page();
		Db_conn conn = db_mgr.Db__core().Conn();
		conn.Ddl_append_fld(pg_tbl.Tbl_name(), pg_tbl.Fld_html_db_id());		// TODO: currently NULL; change to NOT NULL DEFAULT -1; ALTER TABLE page ADD html_db_id int NULL;
//			conn.Ddl_append_fld(pg_tbl.Tbl_name(), pg_tbl.Fld_page_redirect_id());	// TODO: currently NULL; change to NOT NULL DEFAULT -1; ALTER TABLE page ADD html_db_id int NULL;
		cfg_tbl.Insert_yn(Xow_cfg_consts.Grp__wiki_schema, Xowd_db_file_schema_props.Key__col_page_html_text_id, Bool_.Y);
		pg_tbl.Hdump_enabled_(Bool_.Y);
	}
}
