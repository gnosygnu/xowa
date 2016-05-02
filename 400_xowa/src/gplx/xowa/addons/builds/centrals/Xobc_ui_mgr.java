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
package gplx.xowa.addons.builds.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
import gplx.xowa.guis.cbks.*; import gplx.core.gfobjs.*;
public class Xobc_ui_mgr {
	private final    Xoa_dashboard_file_mgr work_mgr, todo_mgr, done_mgr;
	private final    Xog_cbk_mgr cbk_mgr;
	public Xobc_ui_mgr(Xog_cbk_mgr cbk_mgr) {
		this.cbk_mgr = cbk_mgr;
		work_mgr = new Xoa_dashboard_file_mgr(cbk_mgr);
		todo_mgr = new Xoa_dashboard_file_mgr(cbk_mgr);
		done_mgr = new Xoa_dashboard_file_mgr(cbk_mgr);
	}
	public void Init() {
		todo_mgr.Clear();
		todo_mgr.Add(new Xobc_file_itm(cbk_mgr, "0", "Simple Wikipedia: html"));

		Bry_bfr tmp_bfr = Bry_bfr.new_();
		Gfobj_nde root = Gfobj_nde.New();
		Gfobj_nde lists_nde = root.New_nde("lists");
		Gfobj_ary todo_ary	= lists_nde.New_ary_nde("todo");
		todo_mgr.To_nde(tmp_bfr, todo_ary);
		cbk_mgr.Send_json("xo.bldr.core.init__recv", root);
	}
	public void Clear() {
		work_mgr.Clear();
		todo_mgr.Clear();
		done_mgr.Clear();
	}
	public void Todo__move_to_work(String job_uid)	{todo_mgr.Move_one(job_uid, work_mgr, null, "xo.bldr.todo.move_to_work__recv", Gfobj_nde.New().Add_str("job_uid", job_uid));}
	public void Work__resume(String job_uid)		{work_mgr.Exec_one(Xobc_file_itm.Invk__resume, job_uid, "xo.bldr.work.resume__recv", Gfobj_nde.New().Add_str("job_uid", job_uid));}
	public void Work__pause(String job_uid)			{work_mgr.Exec_one(Xobc_file_itm.Invk__pause, job_uid, "xo.bldr.work.pause__recv", Gfobj_nde.New().Add_str("job_uid", job_uid));}
	public void Work__cancel(String job_uid)		{work_mgr.Move_one(job_uid, todo_mgr, Xobc_file_itm.Invk__cancel, "xo.bldr.work.cancel__recv", Gfobj_nde.New().Add_str("job_uid", job_uid));}
	public void Work__pause_all()					{work_mgr.Exec_all(Xobc_file_itm.Invk__pause);}
	public void Work__resume_all() {
		int len = work_mgr.Len();
		for (int i = 0; i < len; ++i) {
			Xobc_file_itm itm = work_mgr.Get_at(i);
			itm.Resume();
		}
		if (len > 0) {	// TODO: if multiple items paused; send back notify for each paused item
			Xobc_file_itm itm = work_mgr.Get_at(0);
			itm.Resume();
			cbk_mgr.Send_json("work__resume__recv", Gfobj_nde.New().Add_str("file_id", itm.Job_uid()));
		}
	}
	public void Work__cancel_all()				{work_mgr.Move_all(todo_mgr, Xobc_file_itm.Invk__cancel);}
	public void Work__complete(String file_id)	{work_mgr.Move_one(file_id, done_mgr);}	// not called by ui called by task wkr
	public void Todo__load(String url) {
		// Xobc_job_itm
	}

	/*
	public void User__rates__load() {}
	public void User__rates__save() {}
	public void Done__save() {}
	public void Done__load() {}
	*/
}
