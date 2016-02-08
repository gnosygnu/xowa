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
package gplx.dbs.diffs; import gplx.*; import gplx.dbs.*;
import gplx.dbs.diffs.itms.*;
public class Gdif_db {
	public Gdif_db(Db_conn conn) {
		this.conn = conn;
		this.job_tbl = new Gdif_job_tbl(conn);
		this.cmd_tbl = new Gdif_cmd_tbl(conn);
		this.txn_tbl = new Gdif_txn_tbl(conn);
	}
	public Db_conn Conn() {return conn;} private final Db_conn conn;
	public Gdif_job_tbl Job_tbl() {return job_tbl;} private final Gdif_job_tbl job_tbl;
	public Gdif_cmd_tbl Cmd_tbl() {return cmd_tbl;} private final Gdif_cmd_tbl cmd_tbl;
	public Gdif_txn_tbl Txn_tbl() {return txn_tbl;} private final Gdif_txn_tbl txn_tbl;
}
