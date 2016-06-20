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
package gplx.dbs.stmts; import gplx.*; import gplx.dbs.*;
public class Db_stmt_mgr {
	private final    List_adp fmt_list = List_adp_.New();
	private final    Db_stmt_arg_list arg_list = new Db_stmt_arg_list();
	public boolean Mode_is_stmt() {return mode_is_stmt;} public Db_stmt_mgr Mode_is_stmt_(boolean v) {mode_is_stmt = v; return this;} private boolean mode_is_stmt = true;
	public void Clear() {arg_list.Clear(); fmt_list.Clear(); bfr.Clear();}
	public Bry_bfr Bfr() {return bfr;} private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Add_var_many(Object... ary) {
		for (Object o : ary)
			fmt_list.Add(o);
	}
	public void Add_crt_str(String key, String val) {			
		fmt_list.Add(mode_is_stmt ? stmt_arg_placeholder : gplx.dbs.sqls.Sql_qry_wtr_.Quote_arg(val));
		arg_list.Add(Bool_.Y, Dbmeta_fld_tid.Tid__str, key, val);
	}
	public void Add_crt_int(String key, int val) {			
		fmt_list.Add(mode_is_stmt ? stmt_arg_placeholder : Int_.To_str(val));			
		arg_list.Add(Bool_.Y, Dbmeta_fld_tid.Tid__int, key, val);
	}
	public void Write_fmt(Bry_fmt fmt) {
		fmt.Bld_many(bfr, (Object[])fmt_list.To_ary_and_clear(Object.class));
	}
	public String Make_sql(Bry_fmt fmt) {	// should only be called publicly for debugging purposes
		Write_fmt(fmt);
		return bfr.To_str_and_clear();
	}
	public Db_stmt Make_stmt(Db_conn conn, Bry_fmt fmt) {
		return conn.Stmt_sql(Make_sql(fmt));
	}
	public void Fill_stmt_and_clear(Db_stmt stmt) {
		stmt.Clear();
		arg_list.Fill(stmt);
		fmt_list.Clear();	// NOTE: also clear fmt_list, since Fill_stmt can be called without calling Make_sql / Make_stmt
	}
	private static final String stmt_arg_placeholder = "?";
}
