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
package gplx.dbs.diffs.builds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
import gplx.dbs.diffs.itms.*;
public class Gdif_bldr_ctx {
	public Gdif_bldr_ctx() {}
	public Gdif_core Core;
	public Gdif_job_itm Cur_job;
	public Gdif_cmd_itm Cur_cmd;
	public Gdif_txn_itm Cur_txn;
	public int Cur_cmd_count;
	public int Cur_txn_count;
	public Gdif_bldr_ctx Init(Gdif_core core, Gdif_job_itm cur_job) {
		this.Core = core; this.Cur_job = cur_job;
		return this;
	}
	public void Clear() {
		Cur_cmd_count = 0; Cur_txn_count = 0;
		Cur_job = null;
		Cur_cmd = null;
		Cur_txn = null;
	}
}
