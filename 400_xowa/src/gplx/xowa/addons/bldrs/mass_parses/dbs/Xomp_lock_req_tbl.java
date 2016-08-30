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
package gplx.xowa.addons.bldrs.mass_parses.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
public class Xomp_lock_req_tbl implements Db_tbl {
	private final    String fld_machine_name, fld_req_time;
	private final    Db_conn conn;
	public Xomp_lock_req_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = "xomp_lock_req";
		this.fld_machine_name	= flds.Add_str("machine_name", 255);		// EX: "MACHINE1"
		this.fld_req_time		= flds.Add_str("req_time", 32);				// EX: 20160801 010203
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public Dbmeta_fld_list Flds() {return flds;} private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "req_time", fld_req_time)
		));
	}
	public void Insert(String machine_name) {
		conn.Stmt_insert(tbl_name, flds).Clear().Val_str(fld_machine_name, machine_name).Val_str(fld_req_time, Datetime_now.Get_force().XtoStr_fmt_yyyyMMdd_HHmmss()).Exec_insert();
	}
	public String Select_1st() {
		String sql = String_.Format("SELECT * FROM {0} ORDER BY {1} DESC", tbl_name, fld_machine_name);	// ANSI.y
		Db_rdr rdr = conn.Stmt_sql(sql).Exec_select__rls_auto();
		try {
			if (!rdr.Move_next()) throw Err_.new_wo_type("xomp_lock_req has no rows");
			return rdr.Read_str(fld_machine_name);}
		finally {rdr.Rls();}
	}
	public void Delete(String machine_name) {
		conn.Stmt_delete(tbl_name, fld_machine_name).Clear().Crt_str(fld_machine_name, machine_name).Exec_delete();
	}
	public void Rls() {}
}
