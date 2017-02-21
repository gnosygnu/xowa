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
public class Xoud_bmk_itm_tbl implements Rls_able {
	private final    String tbl_name = "bmk_itm"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_id, fld_owner, fld_sort, fld_name, fld_wiki, fld_url, fld_comment;
	private Db_stmt stmt_update_sort;
	public Xoud_bmk_itm_tbl(Db_conn conn) {
		this.conn = conn;
		fld_id						= flds.Add_int_pkey_autonum("itm_id");
		fld_owner					= flds.Add_int("itm_owner");
		fld_sort					= flds.Add_int("itm_sort");
		fld_name					= flds.Add_str("itm_name"		, 255);
		fld_wiki					= flds.Add_str("itm_wiki"		, 255);
		fld_url						= flds.Add_str("itm_url"		, 1024);
		fld_comment					= flds.Add_str("itm_comment"	, 4096);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds.To_fld_ary()));}
	public void Insert(int owner, int sort, byte[] name, byte[] wiki, byte[] url, byte[] comment) {
		Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_owner, owner).Val_int(fld_sort, sort).Val_bry_as_str(fld_name, name)
			.Val_bry_as_str(fld_wiki, wiki).Val_bry_as_str(fld_url, url).Val_bry_as_str(fld_comment, comment)
			.Exec_insert();
	}
	public void Update(int id, int owner, int sort, byte[] name, byte[] wiki, byte[] url, byte[] comment) {
		Db_stmt stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt_update.Clear()
			.Val_int(fld_owner, owner).Val_int(fld_sort, sort).Val_bry_as_str(fld_name, name)
			.Val_bry_as_str(fld_wiki, wiki).Val_bry_as_str(fld_url, url).Val_bry_as_str(fld_comment, comment)
			.Crt_int(fld_id, id)
			.Exec_update();
	}
	public void Update_sort(int id, int sort) {
		if (stmt_update_sort == null) stmt_update_sort = conn.Stmt_update(tbl_name, String_.Ary(fld_id), fld_sort);
		stmt_update_sort.Clear().Val_int(fld_sort, sort).Crt_int(fld_id, id).Exec_update();;
	}
	public void Delete(int id) {
		Db_stmt stmt_delete = conn.Stmt_delete(tbl_name, fld_id);
		stmt_delete.Clear().Crt_int(fld_id, id).Exec_delete();
	}
	public Xoud_bmk_itm_row[] Select_grp(int owner) {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select_order(tbl_name, flds, String_.Ary(fld_owner), fld_sort)
			.Crt_int(fld_owner, owner)
			.Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				list.Add(new_row(rdr));
		}
		finally {rdr.Rls();}
		return (Xoud_bmk_itm_row[])list.To_ary_and_clear(Xoud_bmk_itm_row.class);
	}
	public int Select_sort_next(int owner) {
		Db_rdr rdr = conn.Stmt_select_max(tbl_name, fld_sort, fld_owner).Crt_int(fld_owner, owner).Exec_select__rls_manual();
		try {
			int rv = 0;
			if (rdr.Move_next()) {
				Object rv_obj = rdr.Read_obj(fld_sort);
				rv = rv_obj == null ? 0 : Int_.cast(rv_obj) + 1;
			}
			return rv;
		}
		finally {rdr.Rls();}
	}
	public Xoud_bmk_itm_row Select_or_null(int id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_id).Crt_int(fld_id, id).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? new_row(rdr) : null;
		}
		finally {rdr.Rls();}
	}
	private Xoud_bmk_itm_row new_row(Db_rdr rdr) {
		return new Xoud_bmk_itm_row
		( rdr.Read_int(fld_id)
		, rdr.Read_int(fld_owner)
		, rdr.Read_int(fld_sort)
		, rdr.Read_bry_by_str(fld_name)
		, rdr.Read_bry_by_str(fld_wiki)
		, rdr.Read_bry_by_str(fld_url)
		, rdr.Read_bry_by_str(fld_comment)
		);
	}
	public void Rls() {
		stmt_update_sort = null;
	}
}
