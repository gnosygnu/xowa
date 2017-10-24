/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.metas.*; import gplx.dbs.metas.updates.*;
public class Xoud_regy_tbl {
	public static final String Tbl_name = "user_regy", Fld_regy_grp = "regy_grp", Fld_regy_key = "regy_key", Fld_regy_val = "regy_val";
	private static final String[] Flds__all = new String[] {Fld_regy_grp, Fld_regy_key, Fld_regy_val};
	public static final Db_idx_itm Idx_core = Db_idx_itm.sql_("CREATE UNIQUE INDEX user_regy_core ON user_regy (regy_grp, regy_key);");
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE user_regy"
	, "( regy_grp           nvarchar(255)           NOT NULL           -- EX: xowa.schema.site"
	, ", regy_key           nvarchar(255)           NOT NULL           -- EX: next_id"
	, ", regy_val           nvarchar(255)           NOT NULL           -- EX: 1"
	, ");"
	);
	private Db_stmt stmt_select_grp, stmt_select_key, stmt_insert, stmt_update, stmt_delete;
	public Db_conn Conn() {return conn;}
	public Xoud_regy_tbl Conn_(Db_conn v, boolean created) {
		this.Rls_all(); conn = v;
		if (created) {
			Schema_update_cmd cmd = Schema_update_cmd_.Make_tbl_create(Xoud_regy_tbl.Tbl_name	, Xoud_regy_tbl.Tbl_sql		, Xoud_regy_tbl.Idx_core);
			cmd.Exec(null, conn);
//				conn.Meta_tbl_create(meta);
		}
		return this;
	} private Db_conn conn;
	@gplx.Virtual public void Insert(String grp, String key, String val) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(conn, Tbl_name, Flds__all);
		stmt_insert.Clear().Val_str(grp).Val_str(key).Val_str(val).Exec_insert();
	}
	@gplx.Virtual public void Update(String grp, String key, String val) {
		if (stmt_update == null) stmt_update = Db_stmt_.new_update_(conn, Tbl_name, String_.Ary(Fld_regy_grp, Fld_regy_key), Fld_regy_val);
		stmt_update.Clear().Val_str(val).Val_str(grp).Val_str(key).Exec_update();
	}
	@gplx.Virtual public void Delete(String grp, String key) {
		if (stmt_delete == null) stmt_delete = Db_stmt_.new_delete_(conn, Tbl_name, Fld_regy_grp, Fld_regy_key);
		stmt_delete.Clear().Val_str(grp).Val_str(key).Exec_delete();
	}
	@gplx.Virtual public void Select_by_grp(List_adp rv, String grp) {
		if (stmt_select_grp == null) stmt_select_grp = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_regy_grp), Flds__all, Db_qry__select_in_tbl.Order_by_null));
		Db_rdr rdr = stmt_select_grp.Clear().Val_str(grp).Exec_select__rls_manual();
		try {
			while (rdr.Move_next()) {
				Xoud_regy_row row = Make_row(rdr);
				rv.Add(row);
			}
		}
		finally {rdr.Rls();}
	}
	@gplx.Virtual public String Select_val(String grp, String key) {
		if (stmt_select_key == null) stmt_select_key = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_regy_grp, Fld_regy_key), Flds__all, Db_qry__select_in_tbl.Order_by_null));
		Db_rdr rdr = stmt_select_key.Clear().Val_str(Fld_regy_grp, grp).Val_str(Fld_regy_key, key).Exec_select__rls_manual();
		String rv = null;
		if (rdr.Move_next())
			rv = rdr.Read_str(Fld_regy_val);
		rdr.Rls();
		return rv;
	}
	private Xoud_regy_row Make_row(Db_rdr rdr) {
		return new Xoud_regy_row
		( rdr.Read_str(Fld_regy_grp)
		, rdr.Read_str(Fld_regy_key)
		, rdr.Read_str(Fld_regy_val)
		);
	}
	public void Rls_all() {
		if (stmt_select_grp != null) {stmt_select_grp.Rls(); stmt_select_grp = null;}
		if (stmt_select_key != null) {stmt_select_key.Rls(); stmt_select_key = null;}
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_update != null) {stmt_update.Rls(); stmt_update = null;}
		if (stmt_delete != null) {stmt_delete.Rls(); stmt_delete = null;}
		conn = null;
	}
}
