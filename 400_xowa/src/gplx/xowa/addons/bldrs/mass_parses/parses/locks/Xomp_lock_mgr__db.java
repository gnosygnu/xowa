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
