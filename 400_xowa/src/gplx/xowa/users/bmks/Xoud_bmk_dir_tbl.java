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
package gplx.xowa.users.bmks; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
public class Xoud_bmk_dir_tbl implements Rls_able {
	private final    String tbl_name = "bmk_dir"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_id, fld_owner, fld_sort, fld_name;
	public Xoud_bmk_dir_tbl(Db_conn conn) {
		this.conn = conn;
		fld_id						= flds.Add_int_pkey_autonum("dir_id");
		fld_owner					= flds.Add_int("dir_owner");
		fld_sort					= flds.Add_int("dir_sort");
		fld_name					= flds.Add_str("dir_name", 255);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds.To_fld_ary()));}
	public void Insert(int owner, int sort, byte[] name) {
		Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_owner, owner).Val_int(fld_sort, sort).Val_bry_as_str(fld_name, name)
			.Exec_insert();
	}
	public void Update(int id, int owner, int sort, byte[] name) {
		Db_stmt stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt_update.Clear()
			.Val_int(fld_owner, owner).Val_int(fld_sort, sort).Val_bry_as_str(fld_name, name)
			.Crt_int(fld_id, id)
			.Exec_update();
	}
	public void Delete(int id) {
		Db_stmt stmt_delete = conn.Stmt_delete(tbl_name, fld_id);
		stmt_delete.Clear().Crt_int(fld_id, id).Exec_delete();
	}
//		private Xoud_bmk_dir_row new_row(Db_rdr rdr) {
//			return new Xoud_bmk_dir_row
//			( rdr.Read_int(fld_id)
//			, rdr.Read_int(fld_owner)
//			, rdr.Read_int(fld_sort)
//			, rdr.Read_bry_by_str(fld_name)
//			);
//		}
	public void Rls() {}
}
