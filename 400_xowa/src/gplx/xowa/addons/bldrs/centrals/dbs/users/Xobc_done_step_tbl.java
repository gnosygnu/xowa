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
package gplx.xowa.addons.bldrs.centrals.dbs.users; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.centrals.cmds.*; import gplx.xowa.addons.bldrs.centrals.tasks.*;
public class Xobc_done_step_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_task_id, fld_step_id;
	private final    Db_conn conn;
	public Xobc_done_step_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "xobc_done_step";
		this.fld_task_id			= flds.Add_int("task_id");
		this.fld_step_id			= flds.Add_int("step_id");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
		conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "main", fld_task_id, fld_step_id));
	}
	public void Insert(int task_id, int step_id) {
		conn.Stmt_insert(tbl_name, fld_task_id, fld_step_id)
			.Val_int(fld_task_id, task_id).Val_int(fld_step_id, step_id)
			.Exec_insert();
	}
	public Hash_adp Select_all(int task_id) {
		Hash_adp rv = null;
		Db_rdr rdr = conn.Stmt_select(tbl_name, String_.Ary(fld_step_id), fld_task_id).Crt_int(fld_task_id, task_id).Exec_select__rls_auto();
		try {
			if (rdr.Move_next()) {
				if (rv == null) rv = Hash_adp_.New();
				rv.Add_as_key_and_val(rdr.Read_int("step_id"));
			}
		}
		finally {rdr.Rls();}
		return null;
	}
	public void Rls() {}
}
