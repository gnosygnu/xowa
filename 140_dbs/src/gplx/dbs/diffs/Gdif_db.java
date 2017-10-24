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
