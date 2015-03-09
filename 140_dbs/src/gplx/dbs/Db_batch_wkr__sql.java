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
public class Db_batch_wkr__sql implements Db_batch_wkr {
	private final Db_conn conn; private String sql;
	public Db_batch_wkr__sql(Db_conn conn, String... lines) {this.conn = conn; this.sql = String_.Concat_lines_nl_skip_last(lines);}
	public String Sql() {return sql;}
	public Db_batch_wkr__sql Sql_(String... lines) {sql = String_.Concat_lines_nl_skip_last(lines); return this;}
	public void Batch_bgn() {
		conn.Txn_bgn();
		conn.Exec_sql(sql);
		conn.Txn_end();
	}
	public void Batch_end() {
	}
}
