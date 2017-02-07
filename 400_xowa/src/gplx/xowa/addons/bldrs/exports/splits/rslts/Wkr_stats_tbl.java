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
package gplx.xowa.addons.bldrs.exports.splits.rslts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
public class Wkr_stats_tbl implements Rls_able {
	private final    String tbl_name;
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_wkr_id, fld_wkr_count, fld_wkr_size;
	public final    Db_conn conn;
	public Wkr_stats_tbl(Db_conn conn) {
		this.conn = conn; conn.Rls_reg(this);
		this.tbl_name				= "wkr_stats";
		this.fld_wkr_id				= flds.Add_int_pkey("wkr_id");
		this.fld_wkr_count			= flds.Add_int("wkr_count");
		this.fld_wkr_size			= flds.Add_long("wkr_size");
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public Db_stmt Insert_stmt() {return conn.Stmt_insert(tbl_name, flds);}
	public void Insert(Db_stmt stmt, int wkr_id, int wkr_count, long wkr_size) {
		stmt.Clear()
			.Val_int(fld_wkr_id		, wkr_id)
			.Val_int(fld_wkr_count	, wkr_count)
			.Val_long(fld_wkr_size	, wkr_size)
			.Exec_insert();
	}
	public Wkr_stats_itm[] Select_all() {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select_all(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Wkr_stats_itm itm = new Wkr_stats_itm(rdr.Read_int(fld_wkr_id), rdr.Read_int(fld_wkr_count), rdr.Read_int(fld_wkr_size));
				list.Add(itm);
			}
			rdr.Rls();
		} finally {rdr.Rls();}
		return (Wkr_stats_itm[])list.To_ary_and_clear(Wkr_stats_itm.class);
	}
	public Wkr_stats_itm Select_all__summary() {
		Db_rdr rdr = conn.Stmt_sql(String_.Format("SELECT Sum({0}) AS {0}, Sum({1}) AS {1} FROM {2}", fld_wkr_count, fld_wkr_size, tbl_name)).Exec_select__rls_auto();	// ANSI.Y
		try {
			if (rdr.Move_next())
				return new Wkr_stats_itm(-1, rdr.Read_int(fld_wkr_count), rdr.Read_long(fld_wkr_size));
			else
				throw Err_.new_wo_type("failed to get sum of wkr_size", "url", conn.Conn_info().Raw());
		} finally {rdr.Rls();}
	}
	public void Rls() {}
}
