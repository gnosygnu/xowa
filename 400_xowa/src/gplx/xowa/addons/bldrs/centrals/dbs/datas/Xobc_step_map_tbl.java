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
public class Xobc_step_map_tbl implements Db_tbl {		
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_sm_id, fld_step_seqn, fld_task_id, fld_step_id;
	private final    Db_conn conn; private Db_stmt insert_stmt;
	public Xobc_step_map_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "step_map";
		this.fld_sm_id				= flds.Add_int_pkey("sm_id");
		this.fld_task_id			= flds.Add_int("task_id");
		this.fld_step_id			= flds.Add_int("step_id");
		this.fld_step_seqn			= flds.Add_int("step_seqn");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public int Select_step_id(int task_id, int step_seqn) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_task_id, fld_step_seqn).Crt_int(fld_task_id, task_id).Crt_int(fld_step_seqn, step_seqn).Exec_select__rls_auto();
		try {
			if (rdr.Move_next())
				return rdr.Read_int(fld_step_id);
			else
				throw Err_.new_("", "xobc:could not find step_id", "task_id", task_id, "step_seqn", step_seqn);
		}
		finally {rdr.Rls();}
	}
	public Xobc_step_map_itm Select_one(int task_id, int step_id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_task_id, fld_step_id).Crt_int(fld_task_id, task_id).Crt_int(fld_step_id, step_id).Exec_select__rls_auto();
		try {
			if (rdr.Move_next())
				return new Xobc_step_map_itm
				( rdr.Read_int(fld_sm_id)
				, rdr.Read_int(fld_task_id)
				, rdr.Read_int(fld_step_id)
				, rdr.Read_int(fld_step_seqn)
				);
			else
				throw Err_.new_("", "bldr.central:could not find step_id", "task_id", task_id, "step_id", step_id);
		}
		finally {rdr.Rls();}
	}
	public int Select_seqn_but_skip_done(int task_id, Hash_adp step_ids) {
		Db_rdr rdr = conn.Stmt_select_order(tbl_name, flds, String_.Ary(fld_task_id), fld_step_seqn).Crt_int(fld_task_id, task_id).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int step_id = rdr.Read_int("step_id");
				if (step_ids.Has(step_id))
					return rdr.Read_int("step_seqn");
			}
		} finally {rdr.Rls();}
		throw Err_.new_("", "xobc:could not find next sort", "task_id", task_id);
	}
	public List_adp Select_all(int task_id) {
		List_adp rv = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select_order(tbl_name, flds, String_.Ary(fld_task_id), fld_step_seqn).Crt_int(fld_task_id, task_id).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				rv.Add(rdr.Read_int("step_id"));
			}
		} finally {rdr.Rls();}
		return rv;
	}
	public void Insert(int sm_id, int task_id, int step_id, int step_seqn) {
		if (insert_stmt == null) insert_stmt = conn.Stmt_insert(tbl_name, flds);
		insert_stmt.Clear().Val_int(fld_sm_id, sm_id).Val_int(fld_task_id, task_id).Val_int(fld_step_id, step_id).Val_int(fld_step_seqn, step_seqn)
			.Exec_insert();
	}
	public void Delete_by_task_id(int task_id) {
		conn.Stmt_delete(tbl_name, fld_task_id).Crt_int(fld_task_id, task_id).Exec_delete();
	}
	public void Rls() {
		insert_stmt = Db_stmt_.Rls(insert_stmt);
	}
}
