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
package gplx.dbs; import gplx.*;
import gplx.dbs.engines.sqlite.*;
public class Db_attach_cmd {
	private final boolean diff_db;
	private final Db_conn conn; private final String attach_name; private final Io_url attach_url;
	private final ListAdp sql_list = ListAdp_.new_();
	Db_attach_cmd(Db_conn conn, String attach_name, Io_url attach_url) {
		this.conn = conn; this.attach_name = attach_name; this.attach_url = attach_url;
		Sqlite_conn_info conn_info = (Sqlite_conn_info)conn.Conn_info();
		this.diff_db = !String_.Eq(conn_info.Url().Raw(), attach_url.Raw());
	}
	public Db_attach_cmd Add_fmt(String msg, String sql_fmt, Object... sql_args) {
		String sql = String_.Format(sql_fmt, sql_args);
		sql = String_.Replace(sql, "<attach_db>", diff_db ? attach_name + "." : "");	// replace <attach> with either "attach_db." or "";
		sql_list.Add(new Db_exec_sql_by_attach_itm(msg, sql));
		return this;
	}
	public void Exec() {
		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.I;
		if (diff_db) conn.Env_db_attach(attach_name, attach_url);
		conn.Txn_bgn(attach_name);	// NOTE: BEGIN TRAN must occur after ATTACH else sqlite will throw error
		int len = sql_list.Count();
		for (int i = 0; i < len; ++i) {
			Db_exec_sql_by_attach_itm itm = (Db_exec_sql_by_attach_itm)sql_list.FetchAt(i);
			usr_dlg.Plog_many("", "", itm.Msg());
			conn.Exec_sql(itm.Sql());
		}
		conn.Txn_end();
		if (diff_db) conn.Env_db_detach(attach_name);
	}
	public static Db_attach_cmd new_(Db_conn conn, String attach_name, Io_url attach_url) {return new Db_attach_cmd(conn, attach_name, attach_url);}
}
class Db_exec_sql_by_attach_itm {
	public Db_exec_sql_by_attach_itm(String msg, String sql) {this.msg = msg; this.sql = sql;}
	public String Msg() {return msg;} private final String msg;
	public String Sql() {return sql;} private final String sql;
}
