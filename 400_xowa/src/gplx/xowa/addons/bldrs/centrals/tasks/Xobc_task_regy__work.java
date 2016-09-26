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
import gplx.core.gfobjs.*; import gplx.core.progs.*; import gplx.core.threads.*;	
import gplx.xowa.drds.powers.*;
import gplx.xowa.addons.bldrs.centrals.steps.*; import gplx.xowa.addons.bldrs.centrals.cmds.*;
public class Xobc_task_regy__work extends Xobc_task_regy__base {
	private final    Thread_adp_mgr thread_mgr = new Thread_adp_mgr(1000, 5000);
	private final    Xobc_cmd_ctx ctx;
	public Xobc_task_regy__work(Xobc_task_mgr task_mgr, Xoa_app app) {super(task_mgr, "work");
		this.ctx = new Xobc_cmd_ctx(app);
	}
	public void Del_work(int task_id) {
		Xobc_task_itm task = this.Get_by(task_id);
		if (task.Step() != null) task.Step().Step_cleanup();
		this.Del_task(task, task_mgr.Todo_mgr());
	}
	private void Del_task(Xobc_task_itm task, Xobc_task_regy__base trg) {
		task_mgr.User_db().Work_task_tbl().Delete(task.Task_id());
		task_mgr.Transfer(this, trg, task);
	}
	public void Run_next() {
		Xod_power_mgr_.Instance.Wake_lock__get("task_mgr");
		int len = this.Len();
		for (int i = 0; i < len; ++i) {
			Xobc_task_itm task = this.Get_at(i);
			Xobc_cmd_itm cmd = task.Step().Cmd();
			if (gplx.core.bits.Bitmask_.Has_byte(Gfo_prog_ui_.Status__runnable, cmd.Prog_status())) {	// must be runnable
				Run_task(task, cmd);
				break;
			}
		}
	}
	public void Run_task(Xobc_task_itm task, Xobc_cmd_itm cmd) {
		task.Task_status_(gplx.core.progs.Gfo_prog_ui_.Status__working);
		task_mgr.Send_json("xo.bldr.work.prog__start__recv", task.Save_to(Gfobj_nde.New()));
		thread_mgr.Add(cmd.Cmd_uid(), Thread_adp_.Start_by_val("xobc: " + cmd.Cmd_name(), cmd, cmd, Xobc_cmd__base.Invk__exec, ctx));
	}
	public void Stop_cur() {
		Xod_power_mgr_.Instance.Wake_lock__rls("task_mgr");
		int len = this.Len();
		for (int i = 0; i < len; ++i) {
			Xobc_task_itm task = this.Get_at(i);
			Xobc_cmd_itm cmd = task.Step().Cmd();
			if (gplx.core.bits.Bitmask_.Has_byte(Gfo_prog_ui_.Status__working, cmd.Prog_status())) {	// must be runnable
				Stop_task(cmd.Cmd_uid());
				break;
			}
		}
	}
	public void Redo_cur() {
		int len = this.Len();
		for (int i = 0; i < len; ++i) {
			Xobc_task_itm task = this.Get_at(i);
			Xobc_step_itm step = task.Step();
			Xobc_cmd_itm cmd = step.Cmd();
			if (gplx.core.bits.Bitmask_.Has_byte(Gfo_prog_ui_.Status__fail, cmd.Prog_status())) {	// must be runnable
				Redo_task(task, step, cmd);
				break;
			}
		}
	}
	private void Stop_task(String cmd_uid) {
		thread_mgr.Halt(cmd_uid, Thread_halt_cbk_.Noop);
	}
	private void Redo_task(Xobc_task_itm task, Xobc_step_itm step, Xobc_cmd_itm cmd) {
		cmd = step.Step_fallback_to(cmd.Cmd_fallback());
		cmd.Cmd_clear();
		task.Task_status_(gplx.core.progs.Gfo_prog_ui_.Status__working);
		task_mgr.Send_json("xo.bldr.work.prog__start__recv", task.Save_to(Gfobj_nde.New()));
		thread_mgr.Add(cmd.Cmd_uid(), Thread_adp_.Start_by_val("xobc: " + cmd.Cmd_name(), cmd, cmd, Xobc_cmd__base.Invk__exec, ctx));
	}
	public void On_done(Xobc_cmd_itm cmd) {
		Xobc_task_itm task = this.Get_by(cmd.Task_id());
		Xobc_step_itm step = task.Step();
		boolean step_is_done = step.Cmd_is_last();
		boolean task_is_done = step_is_done && task.Step_is_last();
		task_mgr.Send_json("xo.bldr.work.prog__done__recv", Gfobj_nde.New().Add_int("task_id", task.Task_id()).Add_bool("task_is_done", task_is_done));

		// step.done -> task.done || step.next
		if (step_is_done) {
			step.Step_cleanup();
			task_mgr.User_db().Done_step_tbl().Insert(cmd.Task_id(), cmd.Step_id());
			// task.done
			if (task_is_done) {
				task.Task_status_(Gfo_prog_ui_.Status__done);
				task_mgr.User_db().Done_task_tbl().Insert(cmd.Task_id(), task.Task_seqn());	// NOTE: this will order by todo's sort, not by actually completed sorted
				Del_task(task, task_mgr.Done_mgr());
			}
			// step.next
			else {
				int next_step = task_mgr.Data_db().Tbl__step_map().Select_step_id(task.Task_id(), step.Step_seqn() + 1);
				task_mgr.Step_mgr().Load(task, next_step, Xobc_cmd__base.Seqn__0);
			}
		}
		// step.work -> cmd.next
		else {
			step.Cmd_idx_next_();
		}

		// release wake_lock; will be acquired when task is run_next; DATE:2016-06-29
		Xod_power_mgr_.Instance.Wake_lock__rls("task_mgr");

		// task_regy.done
		if (task_is_done) {
			if (this.Len() > 0)
				this.Run_next();
		}
		// task_regy.work
		else {
			cmd = task.Step().Cmd();
			task_mgr.User_db().Work_task_tbl().Update(cmd.Task_id(), task.Task_seqn(), cmd.Step_id(), cmd.Cmd_id());
			this.Run_next();
		}
	}
	public void On_suspended(Xobc_cmd_itm cmd) {
		Xobc_task_itm task = this.Get_by(cmd.Task_id());
		task.Task_status_(Gfo_prog_ui_.Status__suspended);
		task_mgr.Send_json("xo.bldr.work.stop_cur__recv", Gfobj_nde.New().Add_int("task_id", task.Task_id()));
	}
	public void On_fail(Xobc_cmd_itm task, boolean resume, String msg) {
		Xod_power_mgr_.Instance.Wake_lock__rls("task_mgr");
		task_mgr.Send_json("xo.bldr.work.prog__fail__recv", Gfobj_nde.New().Add_int("task_id", task.Task_id()).Add_str("err", msg).Add_bool("resume", resume));
	}
	public void On_stat(int task_id, String msg) {
		task_mgr.Send_json("xo.bldr.work.prog__stat__recv", Gfobj_nde.New().Add_int("task_id", task_id).Add_str("msg", msg));
	}
}
