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
import gplx.dbs.diffs.builds.*;
public class Gdif_core {
	private final    Db_conn conn;
	private final    Gdif_job_tbl job_tbl;
	private final    Gdif_cmd_tbl cmd_tbl;
	private final    Gdif_txn_tbl txn_tbl;
	public Gdif_core(Db_conn conn) {
		this.conn = conn;
		this.db = new Gdif_db(conn);
		this.job_tbl = db.Job_tbl();
		this.cmd_tbl = db.Cmd_tbl();
		this.txn_tbl = db.Txn_tbl();
	}
	public Gdif_db Db() {return db;} private final    Gdif_db db;
	public Gdif_job_itm New_job(String name, String made_by) {
		int job_id = conn.Sys_mgr().Autonum_next(job_tbl.Tbl_name(), job_tbl.Fld_job_id());
		return job_tbl.Insert(job_id, name, made_by, Datetime_now.Get().XtoUtc(), "");
	}
	public Gdif_cmd_itm New_cmd(Gdif_bldr_ctx ctx, int tid) {
		ctx.Cur_cmd_count++;
		return cmd_tbl.Insert(ctx.Cur_job.Id, ctx.Cur_cmd_count, tid, "");
	}
	public Gdif_txn_itm New_txn(Gdif_bldr_ctx ctx, int cmd_id, int owner_txn) {
		ctx.Cur_txn_count++;
		return txn_tbl.Insert(ctx.Cur_job.Id, ctx.Cur_txn_count, cmd_id, owner_txn);
	}
}
