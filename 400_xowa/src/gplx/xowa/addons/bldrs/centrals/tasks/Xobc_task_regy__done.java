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
package gplx.xowa.addons.bldrs.centrals.tasks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
public class Xobc_task_regy__done extends Xobc_task_regy__base {
	public Xobc_task_regy__done(Xobc_task_mgr task_mgr) {super(task_mgr, "done");}
	public void Del_done(int task_id) {
		task_mgr.User_db().Done_task_tbl().Delete(task_id);
		task_mgr.Transfer(this, task_mgr.Todo_mgr(), this.Get_by(task_id));
	}
}
