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
import gplx.core.gfobjs.*;
import gplx.xowa.addons.bldrs.centrals.cmds.*; import gplx.xowa.addons.bldrs.centrals.steps.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
public class Xobc_task_regy__todo extends Xobc_task_regy__base {
	public Xobc_task_regy__todo(Xobc_task_mgr task_mgr) {super(task_mgr, "todo");}
	public void Add_work(int task_id) {
		Xobc_user_db user_db = task_mgr.User_db();
		Xobc_task_itm task = task_mgr.Todo_mgr().Get_by(task_id);
		task.Task_status_(gplx.core.progs.Gfo_prog_ui_.Status__init);
		task.Task_seqn_(task_mgr.Work_mgr().Len());

		// get step_id; default is 1st step with seqn=0, but skip any steps that have already been downloaded; handles user accidentally removing item from work;
		int step_seqn = Xobc_step_itm.Seqn__0;
		Hash_adp done_steps = user_db.Done_step_tbl().Select_all(task_id);
		if (done_steps != null)
			step_seqn = task_mgr.Data_db().Tbl__step_map().Select_seqn_but_skip_done(task_id, done_steps);
		int step_id = task_mgr.Data_db().Tbl__step_map().Select_step_id(task_id, step_seqn);

		// load task, cur_step, and all cmds
		task_mgr.Step_mgr().Load(task, step_id, Xobc_cmd__base.Seqn__0);

		// do transfer
		user_db.Work_task_tbl().Insert(task_id, task.Task_seqn(), step_id, Xobc_cmd__base.Seqn__0);
		task_mgr.Transfer(this, task_mgr.Work_mgr(), task);
	}
	public void Del_todo(int task_id) {
		Xobc_task_itm task = this.Get_by(task_id);
		task_mgr.User_db().Done_task_tbl().Insert(task_id, task.Task_seqn());
		task_mgr.Transfer(this, task_mgr.Done_mgr(), task);
	}
}
