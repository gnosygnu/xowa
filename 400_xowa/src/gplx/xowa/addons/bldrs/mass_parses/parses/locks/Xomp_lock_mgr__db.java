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
package gplx.xowa.addons.bldrs.mass_parses.parses.locks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
public class Xomp_lock_mgr__db implements Xomp_lock_mgr {
	private final    Db_conn conn;
	private final    Xomp_lock_tbl lock_tbl;
	private final    Xomp_lock_req_tbl req_tbl;
	private final    int wait_time;
	public Xomp_lock_mgr__db(Db_conn conn, int wait_time) {
		this.conn = conn;
		this.lock_tbl = new Xomp_lock_tbl(conn);
		this.req_tbl = new Xomp_lock_req_tbl(conn);
		this.wait_time = wait_time;
	}
	public void Remake() {
		conn.Meta_tbl_remake(lock_tbl);
		conn.Meta_tbl_remake(req_tbl);
	}
	public int Uid_prv__get(String machine_name) {
		// insert into req_tbl
		req_tbl.Insert(machine_name);

		// loop until req is 1st record in req_tbl
		while (true) {
			String machine_name_1st = req_tbl.Select_1st();
			if (String_.Eq(machine_name, machine_name_1st))
				break;
			else {
				Gfo_usr_dlg_.Instance.Note_many("", "", "waiting for lock: ~{0}", machine_name);
				gplx.core.threads.Thread_adp_.Sleep(wait_time);
			}
		}

		// get next uid and fill pages
		return lock_tbl.Select();
	} 
	public void Uid_prv__rls(String machine_name, int uid_prv) {
		lock_tbl.Update(uid_prv);
		req_tbl.Delete(machine_name);
	}
}
