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
package gplx.xowa.addons.builds.centrals.jobs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*; import gplx.xowa.addons.builds.centrals.*;
import gplx.core.progs.*; import gplx.core.gfobjs.*;
import gplx.core.ios.zips.*; import gplx.xowa.guis.cbks.*;
public class Xobc_job_itm__unzip extends Gfo_prog_ui_base implements Xobc_job_itm {
	private final    Xog_cbk_mgr cbk_mgr;
	private long nxt_time;
	public Xobc_job_itm__unzip(Xog_cbk_mgr cbk_mgr, String job_uid, Io_url url) {
		this.cbk_mgr = cbk_mgr; this.job_uid = job_uid;
		this.Prog__end_(10);
	}
	public String			Job_uid()		{return job_uid;} private final    String job_uid;
	public String			Job_name()		{return job_name;} private final    String job_name = "unzipping";
	public Xobc_job_itm		Owner()			{return Xobc_job_itm_.Null;}
	public Xobc_job_itm[]	Subs()			{return Xobc_job_itm_.Ary_empty;}
	public void		Task__exec() {
	}
	public void		Write_to_nde(Bry_bfr tmp_bfr, gplx.core.gfobjs.Gfobj_nde nde) {
		nde.Add_str	("job_uid"				, job_uid);
		nde.Add_str	("job_name"				, job_name);
		nde.Add_long("prog_data_cur"		, this.Prog__cur());
		nde.Add_long("prog_data_end"		, this.Prog__end());
		nde.Add_long("prog_time_cur"		, 0);
		nde.Add_long("prog_time_end"		, this.Prog__end());
	}
	@Override public byte Prog__notify__working(long cur, long end) {
		long cur_time = gplx.core.envs.Env_.TickCount();
		if (cur_time < nxt_time) return Gfo_prog_ui_.State__started;	// message came too soon. ignore it
		nxt_time = cur_time + 200;

		Gfobj_nde nde = Gfobj_nde.New();
		nde.Add_str("job_uid", job_uid);
		nde.Add_str("task_type", "verify");
		nde.Add_long("prog_cur", cur);
		nde.Add_long("prog_end", end);
		cbk_mgr.Send_json("work__update_progress", nde);

		return super.Prog__notify__working(cur, end);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		Task__exec();
		return this;
	}
}
