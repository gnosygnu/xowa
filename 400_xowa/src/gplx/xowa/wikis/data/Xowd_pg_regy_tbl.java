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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
public class Xowd_pg_regy_tbl {
	private String tbl_name = "wiki_page_regy";
	private String fld_db_id, fld_page_id, fld_page_ns, fld_page_title, fld_page_is_redirect, fld_page_touched, fld_page_len, fld_page_random_int, fld_page_text_db_id, fld_page_html_db_id, fld_page_redirect_id;
	private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String[] flds_select() {return String_.Ary(fld_page_id, fld_page_ns, fld_page_title, fld_page_touched, fld_page_is_redirect, fld_page_len, fld_page_text_db_id, fld_page_html_db_id, fld_page_redirect_id);}
	private Db_conn conn; private Db_stmt stmt_select_all_by_ttl, stmt_select_all_by_id, stmt_select_id_by_ttl, stmt_insert;
	private int db_id;
	public String Tbl_name()					{return tbl_name;}
	public Db_meta_fld Fld_html_db_id()			{return flds.Get_by(fld_page_html_db_id);}
	public Db_meta_fld Fld_page_redirect_id()	{return flds.Get_by(fld_page_redirect_id);}
	public void Conn_(Db_conn new_conn, boolean created, boolean schema_is_1, int db_id, boolean hdump_enabled) {
		this.conn = new_conn; flds.Clear(); this.db_id = db_id;
		String page_text_db_id_key = "page_text_db_id";
		if (schema_is_1) {
			tbl_name = "page";
			page_text_db_id_key = "page_file_idx";
			fld_db_id = Db_meta_fld.Key_null;
		}
		else {
			fld_db_id			= flds.Add_int("db_id");
		}
		fld_page_id				= flds.Add_int("page_id");				// int(10); unsigned -- MW:same
		fld_page_ns				= flds.Add_int("page_namespace");		// int(11);          -- MW:same
		fld_page_title			= flds.Add_str("page_title", 255);		// varbinary(255);   -- MW:blob
		fld_page_is_redirect	= flds.Add_int("page_is_redirect");		// tinyint(3);       -- MW:same
		fld_page_touched		= flds.Add_str("page_touched", 14);		// binary(14);       -- MW:blob; NOTE: should be revision!rev_timestamp, but needs extra join
		fld_page_len			= flds.Add_int("page_len");				// int(10); unsigned -- MW:same except NULL REF: WikiPage.php!updateRevisionOn;"
		fld_page_random_int		= flds.Add_int("page_random_int");		// MW:XOWA
		fld_page_text_db_id		= flds.Add_int(page_text_db_id_key);	// MW:XOWA
		if (schema_is_1 && !hdump_enabled) {
			fld_page_html_db_id = Db_meta_fld.Key_null;
			fld_page_redirect_id = Db_meta_fld.Key_null;
		}
		else {
			Hdump_enabled_(Bool_.Y);
		}
		if (created) {
			Db_meta_tbl meta_tbl = Db_meta_tbl.new_(tbl_name, flds.To_fld_ary()
			, Db_meta_idx.new_unique_by_tbl_wo_null(tbl_name, "pkey"		, fld_db_id, fld_page_id)
			, Db_meta_idx.new_normal_by_tbl_wo_null(tbl_name, "title"		, fld_db_id, fld_page_ns, fld_page_title, fld_page_id, fld_page_len, fld_page_is_redirect)
			, Db_meta_idx.new_normal_by_tbl_wo_null(tbl_name, "random"		, fld_db_id, fld_page_ns, fld_page_random_int)
			);
			conn.Exec_create_tbl_and_idx(meta_tbl);
		}
		stmt_insert = stmt_select_all_by_ttl = stmt_select_all_by_id = stmt_select_id_by_ttl = null;
	}
	private void Hdump_enabled_(boolean v) {
		if (v) {
			fld_page_html_db_id		= flds.Add_int_dflt("page_html_db_id", -1);		// MW:XOWA
			fld_page_redirect_id	= flds.Add_int_dflt("page_redirect_id", -1);	// MW:XOWA
		}
		else {
			fld_page_html_db_id		= Db_meta_fld.Key_null;
			fld_page_redirect_id	= Db_meta_fld.Key_null;
		}
	}
	public boolean Select_by_ttl(Xodb_page rv, Xow_ns ns, byte[] ttl) {
		Db_rdr rdr = Db_rdr_.Null;
		try {
			if (stmt_select_all_by_ttl == null) stmt_select_all_by_ttl = conn.Rls_reg(conn.Stmt_select(tbl_name, flds.To_str_ary(), String_.Ary_wo_null(fld_db_id, fld_page_ns, fld_page_title)));
			rdr = stmt_select_all_by_ttl.Clear().Crt_int(fld_db_id, db_id).Crt_int(fld_page_ns, ns.Id()).Crt_str(fld_page_title, String_.new_utf8_(ttl)).Exec_select_as_rdr();
			if (rdr.Move_next()) {
				Read_page__all(rv, rdr);
				return true;
			}
		}
		finally {rdr.Rls();}
		return false;
	}
	public boolean Select_by_id(Xodb_page rv, int page_id) {
		Db_rdr rdr = Db_rdr_.Null;
		try {
			if (stmt_select_all_by_id == null) stmt_select_all_by_id = conn.Rls_reg(conn.Stmt_select(tbl_name, flds_select(), String_.Ary_wo_null(fld_db_id, fld_page_id)));
			rdr = stmt_select_all_by_id.Clear().Crt_int(fld_db_id, db_id).Crt_int(fld_page_id, page_id).Exec_select_as_rdr();
			if (rdr.Move_next()) {
				Read_page__all(rv, rdr);
				return true;
			}
		}
		finally {rdr.Rls();}
		return false;
	}
	public int Select_id(int ns_id, byte[] ttl) {
		Db_rdr rdr = Db_rdr_.Null; 
		if (stmt_select_id_by_ttl == null) stmt_select_id_by_ttl = conn.Rls_reg(conn.Stmt_select(tbl_name, flds_select(), String_.Ary_wo_null(fld_db_id, fld_page_ns, fld_page_title)));
		try {
			rdr = stmt_select_id_by_ttl.Crt_int(fld_db_id, db_id).Crt_int(fld_page_ns, ns_id).Crt_bry_as_str(fld_page_title, ttl).Exec_select_as_rdr();
			return rdr.Move_next() ? rdr.Read_int(fld_page_id) : Xodb_mgr_sql.Page_id_null;
		}	finally {rdr.Rls();}
	}
	public void Insert(int page_id, int ns_id, byte[] ttl_wo_ns, boolean page_is_redirect, DateAdp modified_on, int page_len, int random_int, int file_idx, int html_db_id) {
		if (stmt_insert == null) stmt_insert = conn.Rls_reg(conn.Stmt_insert(tbl_name, flds.To_str_ary()));
		stmt_insert.Clear()
		.Val_int(fld_db_id, db_id)
		.Val_int(fld_page_id, page_id)
		.Val_int(fld_page_ns, ns_id)
		.Val_str(fld_page_title, String_.new_utf8_(ttl_wo_ns))
		.Val_byte(fld_page_is_redirect, (byte)(page_is_redirect ? 1 : 0))
		.Val_str(fld_page_touched, modified_on.XtoStr_fmt(Page_touched_fmt))
		.Val_int(fld_page_len, page_len)
		.Val_int(fld_page_random_int, random_int)
		.Val_int(fld_page_text_db_id, file_idx)
		.Exec_insert();
	}
	public void Update_html_db_id(int page_id, int html_db_id) {
		Db_stmt stmt = conn.Stmt_update(tbl_name, String_.Ary_wo_null(fld_db_id, fld_page_id), fld_page_html_db_id);
		stmt.Val_int(fld_page_html_db_id, html_db_id).Crt_int(fld_db_id, db_id).Crt_int(fld_page_id, page_id).Exec_update();
	}
	private void Read_page__all(Xodb_page page, Db_rdr rdr) {
		page.Id_			(rdr.Read_int(fld_page_id));
		page.Ns_id_			(rdr.Read_int(fld_page_ns));
		page.Ttl_wo_ns_		(rdr.Read_bry_by_str(fld_page_title));
		page.Modified_on_	(DateAdp_.parse_fmt(rdr.Read_str(fld_page_touched), Page_touched_fmt));
		page.Type_redirect_	(rdr.Read_bool_by_byte(fld_page_is_redirect));
		page.Text_len_		(rdr.Read_int(fld_page_len));
		page.Text_db_id_	(rdr.Read_int(fld_page_text_db_id));
		page.Html_db_id_	(rdr.Read_int(fld_page_html_db_id));
		page.Redirect_id_	(rdr.Read_int(fld_page_redirect_id));
	}
	private static final String Page_touched_fmt = "yyyyMMddHHmmss";
}
