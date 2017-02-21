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
package gplx.dbs.diffs.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
public class Gdif_txn_itm {
	public Gdif_txn_itm(int job_id, int txn_id, int cmd_id, int owner_txn) {
		this.Job_id = job_id; this.Txn_id = txn_id; this.Cmd_id = cmd_id; this.Owner_txn = owner_txn;
	}
	public final int Job_id;
	public final int Txn_id;
	public final int Cmd_id;
	public final int Owner_txn;

	public static final int Owner_txn__null = 0;
}
