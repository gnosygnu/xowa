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
