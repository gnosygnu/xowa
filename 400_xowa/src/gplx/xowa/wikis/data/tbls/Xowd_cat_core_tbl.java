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
import gplx.dbs.*;
public class Xowd_cat_core_tbl implements Db_tbl {
	private final    String tbl_name; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_id, fld_pages, fld_subcats, fld_files, fld_hidden, fld_link_db_id;
	private final    Db_conn conn; private Db_stmt stmt_insert, stmt_update, stmt_select;
	private final    Xowd_cat_core_tbl__in_wkr in_wkr = new Xowd_cat_core_tbl__in_wkr();
	public Db_conn Conn() {return conn;}
	public Xowd_cat_core_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		String fld_link_db_id_name = "";
		if	(schema_is_1)	{tbl_name = "category"; fld_link_db_id_name = "cat_file_idx";}
		else				{tbl_name = "cat_core"; fld_link_db_id_name = "cat_link_db_id";}
		fld_id				= flds.Add_int_pkey	("cat_id");
		fld_pages			= flds.Add_int		("cat_pages");
		fld_subcats			= flds.Add_int		("cat_subcats");
		fld_files			= flds.Add_int		("cat_files");
		fld_hidden			= flds.Add_byte		("cat_hidden");
		fld_link_db_id		= flds.Add_int(fld_link_db_id_name);
		in_wkr.Ctor(this, tbl_name, flds, fld_id);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert_bgn() {conn.Txn_bgn("schema__cat_core__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int id, int pages, int subcats, int files, byte hidden, int link_db_id) {
		stmt_insert.Clear()
			.Val_int(fld_id, id).Val_int(fld_pages, pages).Val_int(fld_subcats, subcats).Val_int(fld_files, files)
			.Val_byte(fld_hidden, hidden).Val_int(fld_link_db_id, link_db_id)
			.Exec_insert();
	}
	public void Update_bgn() {conn.Txn_bgn("schema__cat_core__update"); stmt_update = conn.Stmt_update(tbl_name, String_.Ary(fld_id), fld_hidden);}
	public void Update_end() {conn.Txn_end(); stmt_update = Db_stmt_.Rls(stmt_update);}
	public void Update_by_batch(int id, byte hidden) {
		stmt_update.Clear().Val_byte(fld_hidden, hidden).Crt_int(fld_id, id).Exec_update();
	}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Dbmeta_fld_itm.Str_ary_empty).Exec_delete();;}
	public Xowd_category_itm Select(int id) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_id, id).Exec_select__rls_manual();
		try {return rdr.Move_next() ? new_itm(rdr) : Xowd_category_itm.Null;} finally {rdr.Rls();}
	}
	public void Select_by_cat_id_in(Cancelable cancelable, Ordered_hash rv, int bgn, int end) {
		in_wkr.Init(rv);
		in_wkr.Select_in(cancelable, conn, bgn, end);
	}
	public void Select_by_cat_id_many(Select_in_cbk cbk) {
		int pos = 0;
		Bry_bfr bfr = Bry_bfr_.New();
		Select_in_wkr wkr = Select_in_wkr.New(bfr, tbl_name, String_.Ary(fld_id, fld_pages, fld_subcats, fld_files, fld_hidden), fld_id);
		while (true) {
			pos = wkr.Make_sql_or_null(bfr, cbk, pos);
			if (pos == -1) break;
			Db_rdr rdr = conn.Stmt_sql(bfr.To_str_and_clear()).Exec_select__rls_auto();
			try {
				while (rdr.Move_next())
					cbk.Read_data(rdr);
			} finally {rdr.Rls();}
		}
	}
	public Xowd_category_itm new_itm(Db_rdr rdr) {
		return Xowd_category_itm.load_
		( rdr.Read_int(fld_id)
		, rdr.Read_int(fld_link_db_id)
		, rdr.Read_bool_by_byte(fld_hidden)
		, rdr.Read_int(fld_subcats)
		, rdr.Read_int(fld_files)
		, rdr.Read_int(fld_pages)
		);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_update = Db_stmt_.Rls(stmt_update);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
}
