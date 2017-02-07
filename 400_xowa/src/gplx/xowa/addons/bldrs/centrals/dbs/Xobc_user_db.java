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
package gplx.xowa.addons.bldrs.centrals.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.centrals.dbs.users.*;
public class Xobc_user_db {
	public Xobc_user_db(Db_conn conn) {
		this.work_task_tbl = new Xobc_work_task_tbl(conn);
		this.done_task_tbl = new Xobc_done_task_tbl(conn);
		this.done_step_tbl = new Xobc_done_step_tbl(conn);
		conn.Meta_tbl_assert(work_task_tbl, done_task_tbl, done_step_tbl);
	}
	public Xobc_work_task_tbl Work_task_tbl() {return work_task_tbl;} private final    Xobc_work_task_tbl work_task_tbl;
	public Xobc_done_task_tbl Done_task_tbl() {return done_task_tbl;} private final    Xobc_done_task_tbl done_task_tbl;
	public Xobc_done_step_tbl Done_step_tbl() {return done_step_tbl;} private final    Xobc_done_step_tbl done_step_tbl;
}
