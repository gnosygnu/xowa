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
public class Db_attach_rdr {
	private final boolean diff_db;
	private final Db_conn conn; private final String attach_name; private final Io_url attach_url;
	public Db_attach_rdr(Db_conn conn, String attach_name, Io_url attach_url) {
		this.conn = conn; this.attach_name = attach_name; this.attach_url = attach_url;
		Sqlite_conn_info conn_info = (Sqlite_conn_info)conn.Conn_info();
		this.diff_db = !String_.Eq(conn_info.Url().Raw(), attach_url.Raw());
	}
	public void Attach() {
		try {
			if (diff_db) conn.Env_db_attach(attach_name, attach_url);
		}	catch (Exception e) {Err_.Noop(e); Gfo_usr_dlg_.Instance.Warn_many("", "", "db:failed to attach db; name=~{0} url=~{1}", attach_name, attach_url.Raw());}
	}
	public Db_rdr Exec_as_rdr(String sql) {
		sql = String_.Replace(sql, "<attach_db>", diff_db ? attach_name + "." : "");	// replace <attach> with either "attach_db." or "";
		return conn.Exec_sql_as_rdr_v2(sql);
	}
	public void Detach() {
		if (diff_db) conn.Env_db_detach(attach_name);
	}
}
