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
import gplx.dbs.*; import gplx.dbs.qrys.*;
public class Xoud_bmk_tbl implements RlsAble {
	private final String tbl_name = "user_bmk"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_id, fld_wiki, fld_page, fld_qarg, fld_name, fld_comment, fld_tag, fld_sort, fld_time, fld_count;		
	public Xoud_bmk_tbl(Db_conn conn) {
		this.conn = conn;
		fld_id						= flds.Add_int_pkey_autonum("bmk_id");
		fld_wiki					= flds.Add_str("bmk_wiki", 255);
		fld_page					= flds.Add_str("bmk_page", 255);
		fld_qarg					= flds.Add_str("bmk_qarg", 255);
		fld_name					= flds.Add_str("bmk_name", 255);
		fld_comment					= flds.Add_str("bmk_comment", 2048);
		fld_tag						= flds.Add_str("bmk_tag", 2048);
		fld_sort					= flds.Add_int("bmk_sort");
		fld_count					= flds.Add_int("bmk_count");
		fld_time					= flds.Add_str("bmk_time", 20);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final Db_conn conn;
	public void Insert(String wiki, String page, String qarg, String name, String comment, String tag, int sort, DateAdp time, int count) {
		Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_str(fld_wiki, wiki).Val_str(fld_page, page).Val_str(fld_qarg, qarg)
			.Val_str(fld_name, name).Val_str(fld_comment, comment).Val_str(fld_tag, tag)
			.Val_int(fld_sort, sort).Val_int(fld_count, count).Val_str(fld_time, time.XtoStr_fmt_iso_8561())
			.Exec_insert();
	}
	public void Delete(int id) {
		Db_stmt stmt_delete = conn.Stmt_delete(tbl_name, fld_id);
		stmt_delete.Clear().Crt_int(fld_id, id).Exec_delete();
	}
	public void Select_all(ListAdp rv) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy).Clear().Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				rv.Add(new_row(rdr));
		}
		finally {rdr.Rls();}
	}
	private Xoud_bmk_row new_row(Db_rdr rdr) {
		return new Xoud_bmk_row
		( rdr.Read_int(fld_id)
		, rdr.Read_str(fld_wiki)
		, rdr.Read_str(fld_page)
		, rdr.Read_str(fld_qarg)
		, rdr.Read_str(fld_name)
		, rdr.Read_str(fld_comment)
		, rdr.Read_str(fld_tag)
		, rdr.Read_int(fld_sort)
		, rdr.Read_int(fld_count)
		, rdr.Read_date_by_str(fld_time)
		);
	}
	public void Rls() {}
}
