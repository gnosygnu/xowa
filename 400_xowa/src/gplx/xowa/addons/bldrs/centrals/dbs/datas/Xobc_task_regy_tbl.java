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
package gplx.xowa.addons.bldrs.centrals.dbs.datas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.centrals.cmds.*; import gplx.xowa.addons.bldrs.centrals.tasks.*;
public class Xobc_task_regy_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_task_id, fld_task_seqn, fld_step_count, fld_task_key, fld_task_name;
	private final    Db_conn conn; private Db_stmt insert_stmt;
	public Xobc_task_regy_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "task_regy";
		this.fld_task_id			= flds.Add_int_pkey("task_id");
		this.fld_task_seqn			= flds.Add_int("task_seqn");
		this.fld_step_count			= flds.Add_int("step_count");
		this.fld_task_key			= flds.Add_str("task_key", 255);
		this.fld_task_name			= flds.Add_str("task_name", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name; 
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
		conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "task_seqn", fld_task_seqn));
	}
	public void Select_all(Xobc_task_regy__base todo_regy) {
		todo_regy.Clear();
		Db_rdr rdr = conn.Stmt_select_order(tbl_name, flds, String_.Ary_empty, fld_task_seqn).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int task_seqn = rdr.Read_int(fld_task_seqn);
				if (task_seqn == Xobc_task_regy_itm.Seqn__obsolete) continue;	// WORKAROUND: do not show old tasks; should add a status column, but don't want to change schema yet; DATE:2016-10-20

				int task_id = rdr.Read_int(fld_task_id);
				int step_count = rdr.Read_int(fld_step_count);
				String task_key = rdr.Read_str(fld_task_key);
				String task_name = rdr.Read_str(fld_task_name);
				todo_regy.Add(new Xobc_task_itm(task_id, task_seqn, step_count, task_key, task_name));
			}
		} finally {rdr.Rls();}
	}
	public int Select_by_key(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_task_key).Exec_select__rls_auto();
		try		{return rdr.Move_next() ? rdr.Read_int(fld_task_id) : -1;}
		finally {rdr.Rls();}
	}
	public String Select_key_by_id_or_null(int id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_task_id).Crt_int(fld_task_id, id).Exec_select__rls_auto();
		try		{return rdr.Move_next() ? rdr.Read_str(fld_task_key) : null;}
		finally {rdr.Rls();}
	}
	public void Insert(int task_id, int task_seqn, int step_count, String task_key, String task_name) {
		if (insert_stmt == null) insert_stmt = conn.Stmt_insert(tbl_name, flds);
		insert_stmt.Clear().Val_int(fld_task_id, task_id).Val_int(fld_task_seqn, task_seqn).Val_int(fld_step_count, step_count)
			.Val_str(fld_task_key, task_key).Val_str(fld_task_name, task_name)
			.Exec_insert();
	}
	public Xobc_task_regy_itm[] Select_by_wiki(byte[] wiki_domain) {
		String sql = Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  *"
		, "FROM    task_regy"
		, "WHERE   task_key LIKE '{0}%'"	// DEPENDENCY:Xobc_task_key
		, "AND     task_seqn != 999999"
		), wiki_domain);
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_sql(sql).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				list.Add(new Xobc_task_regy_itm
				( rdr.Read_int("task_id")
				, rdr.Read_int("task_seqn")
				, rdr.Read_bry_by_str("task_key")
				, rdr.Read_bry_by_str("task_name")
				, rdr.Read_int("step_count")
				));
			}
		} finally {rdr.Rls();}
		return (Xobc_task_regy_itm[])list.To_ary_and_clear(Xobc_task_regy_itm.class);
	}
	public void Delete(int task_id) {
		conn.Stmt_delete(tbl_name, fld_task_id).Crt_int(fld_task_id, task_id).Exec_delete();
	}
	public void Rls() {
		insert_stmt = Db_stmt_.Rls(insert_stmt);
	}
}
