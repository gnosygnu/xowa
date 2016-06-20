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
package gplx.dbs.diffs.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
public class Gdif_job_tbl implements Rls_able {
	private String tbl_name = "gdif_job";
	private String fld_job_id, fld_name, fld_made_by, fld_made_on, fld_data;
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    Db_conn conn; private Db_stmt stmt_insert;
	public Gdif_job_tbl(Db_conn conn) {
		this.conn = conn;
		fld_job_id = flds.Add_int_pkey("job_id"); fld_name = flds.Add_str("name", 255); fld_made_by = flds.Add_str("made_by", 255); fld_made_on = flds.Add_date("made_on"); fld_data = flds.Add_text("data");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;}
	public String Fld_job_id() {return fld_job_id;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public Gdif_job_itm Insert(int id, String name, String made_by, DateAdp made_on, String data) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int(fld_job_id		, id)
		.Val_str(fld_name		, name)
		.Val_str(fld_made_by	, made_by)
		.Val_date(fld_made_on	, made_on)
		.Val_str(fld_data		, data)
		.Exec_insert();
		return new Gdif_job_itm(id, name, made_by, made_on);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
}
