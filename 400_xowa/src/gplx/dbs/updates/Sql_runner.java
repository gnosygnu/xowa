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
package gplx.dbs.updates; import gplx.*; import gplx.dbs.*;
import gplx.dbs.stmts.*;
/*
sql =
UPDATE  page
SET     page_score = page_len
WHERE   page_id >= ?
AND     page_id <  ?	
state = -1|100000
*/
public class Sql_runner {
	private final    Db_stmt_arg_list list = new Db_stmt_arg_list();
	public Db_conn Conn() {return conn;} public void Conn_(Db_conn v) {conn = v;} private Db_conn conn;
	public boolean Quiet() {return quiet;} public void Quiet_(boolean v) {quiet = v;} private boolean quiet;
	public String Sql_fmt() {return sql_fmt;} public void Sql_fmt_(String v) {sql_fmt = v;} private String sql_fmt;
//	public Db_stmt_arg[] Sql_args() {return sql_args;} public void Sql_args_(Db_stmt_arg[] v) {sql_args = v;} private Db_stmt_arg[] sql_args;
	public String Msg() {return msg;} public void Msg_(String v) {msg = v;} private String msg;
	public String Fill_next(String state) {
		String[] vals = String_.Split(state, "|");
		int val_lo = Int_.parse(vals[0]);
		int interval = Int_.parse(vals[1]);
		int val_hi = val_lo + interval;

		Db_stmt_arg arg = list.Get_at(0);
		arg.Val = val_lo;
		arg = list.Get_at(1);
		arg.Val = val_hi;

		return String_.Concat_with_str("|", Int_.To_str(val_hi), vals[1]);
	}
	public void Run() {
		Db_stmt stmt = conn.Stmt_sql(sql_fmt);
		// foreach (itme) Db_stmt_arg_list list = Db_stmt_arg_list
		Gfo_usr_dlg_.Instance.Note_many("", "", msg);
		stmt.Exec_update();
		// increment ranges
	}
}
