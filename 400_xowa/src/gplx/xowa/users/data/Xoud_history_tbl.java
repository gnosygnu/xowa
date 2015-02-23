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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.schemas.*; import gplx.dbs.schemas.updates.*;
public class Xoud_history_tbl {
	private String tbl_name = "user_history"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_user_id, fld_wiki, fld_page, fld_qarg, fld_time, fld_count;
	private Db_conn conn; private Db_stmt stmt_select_by_page, stmt_select_by_top, stmt_insert, stmt_update, stmt_delete;
	private int user_id;
	public void Conn_(Db_conn new_conn, boolean created, int user_id) {
		this.conn = new_conn; flds.Clear();
		this.user_id = user_id;
		fld_user_id						= flds.Add_int("user_id");
		fld_wiki						= flds.Add_str("history_wiki", 255);
		fld_page						= flds.Add_str("history_page", 255);
		fld_qarg						= flds.Add_str("history_qarg", 255);
		fld_time						= flds.Add_str("history_time", 20);
		fld_count						= flds.Add_int("history_count");
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "pkey", fld_user_id, fld_wiki, fld_page, fld_qarg)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_insert = stmt_update = stmt_delete = stmt_select_by_page = stmt_select_by_top = null;
	}
	public void Insert(String wiki, String page, String qarg, DateAdp time, int count) {
		if (stmt_insert == null) stmt_insert = conn.Rls_reg(conn.Stmt_insert(tbl_name, flds));
		stmt_insert.Clear()
			.Val_int(fld_user_id, user_id).Val_str(fld_wiki, wiki).Val_str(fld_page, page).Val_str(fld_qarg, qarg)
			.Val_str(fld_time, time.XtoStr_fmt_iso_8561()).Val_int(fld_count, count)
			.Exec_insert();
	}
	public void Update(String wiki, String page, String qarg, DateAdp time, int count) {
		if (stmt_update == null) stmt_update = conn.Rls_reg(conn.Stmt_update(tbl_name, String_.Ary(fld_user_id, fld_wiki, fld_page, fld_qarg), fld_time, fld_count));
		stmt_update.Clear()
			.Val_str(fld_time, time.XtoStr_fmt_iso_8561()).Val_int(fld_count, count)
			.Crt_int(fld_user_id, user_id).Crt_str(fld_wiki, wiki).Crt_str(fld_page, page).Crt_str(fld_qarg, qarg)
			.Exec_update();
	}
	public void Delete(String wiki, String page, String qarg) {
		if (stmt_delete == null) stmt_delete = conn.Rls_reg(conn.Stmt_delete(tbl_name, fld_user_id, fld_wiki, fld_page, fld_qarg));
		stmt_delete.Clear()
			.Crt_int(fld_user_id, user_id).Crt_str(fld_wiki, wiki).Crt_str(fld_page, page).Crt_str(fld_qarg, qarg)
			.Exec_delete();
	}
	public Xoud_history_row Select_by_page(String wiki, String page, String qarg) {
		if (stmt_select_by_page == null) stmt_select_by_page = conn.Rls_reg(conn.Stmt_select(tbl_name, flds, fld_user_id, fld_wiki, fld_page, fld_qarg));
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt_select_by_page.Clear()
				.Crt_int(fld_user_id, user_id).Crt_str(fld_wiki, wiki).Crt_str(fld_page, page).Crt_str(fld_qarg, qarg)
				.Exec_select_as_rdr();
			return rdr.Move_next() ? Make_row(rdr) : Xoud_history_row.Null;
		}
		finally {rdr.Rls();}
	}
	public void Select_by_top(ListAdp rv, int count) {
		Db_qry__select_in_tbl qry = new Db_qry__select_in_tbl(tbl_name, flds.To_str_ary(), null, null, null, fld_time + " DESC", " LIMIT " + Int_.Xto_str(count));
		stmt_select_by_top = conn.Stmt_new(qry);
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt_select_by_top.Clear().Exec_select_as_rdr();
			rv.Clear();				
			while (rdr.Move_next()) {
				Xoud_history_row row = Make_row(rdr);
				rv.Add(row);
			}
			rdr.Rls();
		}
		finally {rdr.Rls();}
	}
	private Xoud_history_row Make_row(Db_rdr rdr) {
		return new Xoud_history_row
		( rdr.Read_str(0)
		, rdr.Read_str(1)
		, rdr.Read_str(2)
		, rdr.Read_date_by_str(3)
		, rdr.Read_int(4)
		);
	}
}
