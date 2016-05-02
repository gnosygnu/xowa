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
import gplx.core.gfobjs.*; import gplx.xowa.guis.cbks.*;
import gplx.xowa.addons.builds.centrals.jobs.*;
class Xobc_file_itm implements GfoInvkAble {
	public Xobc_file_itm(Xog_cbk_mgr cbk_mgr, String job_uid, String job_name) {
		this.job_uid = job_uid;
		this.job_name = job_name;
		this.tasks = new Xobc_job_itm[]
		{ new Xobc_job_itm__verify(cbk_mgr, "77801297-db0a-47b4-af95-0db1eb63a192", Io_url_.new_fil_("C:\\xowa\\wiki\\en.wikipedia.org\\en.wikipedia.org-core.xowa"))
		};
		this.tasks_len = tasks.length;
	}
	public String		Job_uid()	{return job_uid;}	private final    String job_uid;		// guid
	public String		Job_name()	{return job_name;}	private final    String job_name;		// Simple Wikipedia: html
	public String		Job_type()	{return job_type;}	private final    String job_type = "gplx.xowa.bldr.wikis.copy_part";
	public Xobc_job_itm[] Tasks() {return tasks;} private Xobc_job_itm[] tasks; private int tasks_len = 0;
	public void Pause() {
		for (int i = 0; i < tasks_len; ++i) {
			Xobc_job_itm task = tasks[i];
			if (	!task.Prog__paused()		// ignore already paused tasks
				&&	task.Prog__started()		// only pause tasks if started; don't pause inited tasks
				)
				task.Prog__pause();
		}
	}
	public void Resume() {
		for (int i = 0; i < tasks_len; ++i) {
			Xobc_job_itm task = tasks[i];
			if (task.Prog__paused())
				task.Prog__resume();
			else {
				gplx.core.threads.Thread_adp_.invk_("job: " + task.Job_name(), task, "").Start();
				break;	// only start one
			}
		}
	}
	public void Cancel() {
		for (int i = 0; i < tasks_len; ++i) {
			Xobc_job_itm task = tasks[i];
			task.Prog__cancel();
		}
	}
	public void Write_to_nde(Bry_bfr tmp_bfr, Gfobj_nde itm) {
		itm.Add_str	("job_uid"				, job_uid);
		itm.Add_str	("job_name"				, job_name);
		Gfobj_ary_nde ary_nde = itm.New_ary_nde("subs");
		Gfobj_nde[] ary = new Gfobj_nde[tasks_len];
		ary_nde.Ary_nde_(ary);
		long prog_time_end = 0;
		long prog_data_end = 0;
		for (int i = 0; i < tasks_len; ++i) {
			Xobc_job_itm sub_itm = tasks[i];
			Gfobj_nde sub_nde = Gfobj_nde.New();
			ary[i] = sub_nde;
			sub_itm.Write_to_nde(tmp_bfr, sub_nde);
			prog_time_end += sub_itm.Prog__end();
		}
		itm.Add_str	("prog_data_end_str"	, gplx.core.ios.Io_size_.To_str_new(tmp_bfr, prog_data_end, 2));
		itm.Add_str	("prog_time_end_str"	, Time_dhms_.To_str(tmp_bfr, prog_time_end, true, 2));
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(String_.Eq(k, Invk__pause))		this.Pause();
		else if	(String_.Eq(k, Invk__resume))		this.Resume();
		else if	(String_.Eq(k, Invk__cancel))		this.Cancel();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk__pause = "pause", Invk__resume = "resume", Invk__cancel = "cancel";
}
/*
              { prog_finished : 0
              , prog_data_cur : 0
              , prog_data_end : 10
              , prog_data_end_str : '1.81 gb'
              , prog_time_cur : 0
              , prog_time_end : 30
              , prog_time_end_str : '30'
              , jobs:
                [
                  { job_type : 'gplx.xowa.core.security.verify'
                  , job_name : 'verifying'
                  , job_sort : 0
                  , job_uid  : '77801297-db0a-47b4-af95-0db1eb63a192'
                  , prog_data_cur : 0
                  , prog_data_end : 1819264175
                  , prog_time_cur : 0
                  , prog_time_end : 10
                  }
                , { job_type : 'gplx.xowa.core.ios.zips.zip_decompress'
                  , job_name : 'unzipping'
                  , job_sort : 1
                  , job_uid  : '79fbfebe-0165-42b0-b71a-c66f95cb7fd1'
                  , prog_data_cur : 0
                  , prog_data_end : 1819264175
                  , prog_time_cur : 0
                  , prog_time_end : 20
                  }
                ]
              }
*/
