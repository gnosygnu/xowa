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
package gplx.xowa.htmls.core.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.dbs.*; import gplx.core.brys.*;
public class Xoh_page_tbl implements Rls_able {
	private final    String fld_page_id, fld_head_flag, fld_body_flag, fld_display_ttl, fld_content_sub, fld_sidebar_div, fld_body;
	private Db_stmt stmt_select, stmt_insert, stmt_delete, stmt_update;
	private final    Int_flag_bldr body_flag_bldr = new Int_flag_bldr().Pow_ary_bld_(3, 2);	// 8 different zip types; 4 different hzip types
	public Xoh_page_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_page_id			= flds.Add_int_pkey("page_id");
		this.fld_head_flag			= flds.Add_int("head_flag");
		this.fld_body_flag			= flds.Add_int("body_flag");
		this.fld_display_ttl		= flds.Add_str("display_ttl", 1024);
		this.fld_content_sub		= flds.Add_str("content_sub", 1024);
		this.fld_sidebar_div		= flds.Add_str("sidebar_div", 2048);
		this.fld_body				= flds.Add_bry("body");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = "html";
	public Dbmeta_fld_list Flds() {return flds;} private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert_bgn() {conn.Txn_bgn("html__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert(Xoh_page hpg, int zip_tid, int hzip_tid, byte[] body) {Insert(hpg.Page_id(), hpg.Head_mgr().Flag(), zip_tid, hzip_tid, hpg.Display_ttl(), hpg.Content_sub(), hpg.Sidebar_div(), body);}
	public void Insert(int page_id, int head_flag, int zip_tid, int hzip_tid, byte[] display_ttl, byte[] content_sub, byte[] sidebar_div, byte[] body) {
		int body_flag = body_flag_bldr.Set(0, zip_tid).Set(1, hzip_tid).Encode();
		Insert(page_id, head_flag, body_flag, display_ttl, content_sub, sidebar_div, body);
	}
	public void Insert(int page_id, int head_flag, int body_flag, byte[] display_ttl, byte[] content_sub, byte[] sidebar_div, byte[] body) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_page_id, page_id);
		Fill_stmt(stmt_insert, head_flag, body_flag, display_ttl, content_sub, sidebar_div, body);
		stmt_insert.Exec_insert();
	}
	public void Update(Xoh_page hpg, int zip_tid, int hzip_tid, byte[] body) {Update(hpg.Page_id(), hpg.Head_mgr().Flag(), zip_tid, hzip_tid, hpg.Display_ttl(), hpg.Content_sub(), hpg.Sidebar_div(), body);}
	public void Update(int page_id, int head_flag, int zip_tid, int hzip_tid, byte[] display_ttl, byte[] content_sub, byte[] sidebar_div, byte[] body) {
		if (stmt_update == null) stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_page_id);
		int body_flag = body_flag_bldr.Set(0, zip_tid).Set(1, hzip_tid).Encode();
		stmt_update.Clear();
		Fill_stmt(stmt_update, head_flag, body_flag, display_ttl, content_sub, sidebar_div, body);
		stmt_update.Crt_int(fld_page_id, page_id).Exec_update();
	}
	public void Delete(int page_id) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_page_id);
		stmt_delete.Clear().Crt_int(fld_page_id, page_id).Exec_delete();
	}
	public boolean Select_by_page(Xoh_page hpg) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_page_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_page_id, hpg.Page_id()).Exec_select__rls_manual();
		try {
			if (rdr.Move_next()) {
				int body_flag = rdr.Read_int(fld_body_flag);
				body_flag_bldr.Decode(body_flag);
				hpg.Ctor_by_db(rdr.Read_int(fld_head_flag), rdr.Read_bry_by_str(fld_display_ttl), rdr.Read_bry_by_str(fld_content_sub), rdr.Read_bry_by_str(fld_sidebar_div), body_flag_bldr.Get_as_int(0), body_flag_bldr.Get_as_int(1), rdr.Read_bry(fld_body));
				return true;
			}
			return false;
		}
		finally {rdr.Rls();}
	}
	public boolean Select_as_row(Xoh_page_row rv, int page_id) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_page_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_page_id, page_id).Exec_select__rls_manual();
		try {
			if (rdr.Move_next()) {
				rv.Load
				( page_id
				, rdr.Read_int(fld_head_flag)
				, rdr.Read_int(fld_body_flag)
				, rdr.Read_bry_by_str(fld_display_ttl)
				, rdr.Read_bry_by_str(fld_content_sub)
				, rdr.Read_bry_by_str(fld_sidebar_div)
				, rdr.Read_bry(fld_body)
				);
				return true;
			}
			return false;
		}
		finally {rdr.Rls();}
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_update = Db_stmt_.Rls(stmt_update);
	}
	public void Fill_stmt(Db_stmt stmt, int head_flag, int body_flag, byte[] display_ttl, byte[] content_sub, byte[] sidebar_div, byte[] body) {
		stmt.Val_int(fld_head_flag, head_flag).Val_int(fld_body_flag, body_flag)
			.Val_bry_as_str(fld_display_ttl, Bry_.Coalesce_to_empty(display_ttl)).Val_bry_as_str(fld_content_sub, Bry_.Coalesce_to_empty(content_sub)).Val_bry_as_str(fld_sidebar_div, Bry_.Coalesce_to_empty(sidebar_div)).Val_bry(fld_body, body);
	}
}
