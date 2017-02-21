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
package gplx.xowa.addons.bldrs.files.cksums.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*; import gplx.xowa.addons.bldrs.files.cksums.*;
import gplx.dbs.*; import gplx.fsdb.meta.*;
public class Xocksum_cksum_db {
	public Xocksum_cksum_db(Db_conn conn) {
		this.conn = conn;
		this.tbl__cksum = new Xocksum_cksum_tbl(conn);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public Xocksum_cksum_tbl Tbl__cksum() {return tbl__cksum;} private final    Xocksum_cksum_tbl tbl__cksum;

	public static Xocksum_cksum_db Get(Xowe_wiki wiki) {
		return new Xocksum_cksum_db(wiki.File__fsdb_core().File__abc_file__at(Fsm_mnt_mgr.Mnt_idx_main).Conn());
	}
}
