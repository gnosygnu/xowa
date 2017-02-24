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
package gplx.xowa.addons.apps.updates.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.core.gfobjs.*; import gplx.core.progs.*; import gplx.core.progs.rates.*;
import gplx.xowa.guis.cbks.*;
public class Xojs_wkr__base implements Gfo_prog_ui, Gfo_invk {
	private final    Gfo_invk_cmd done_cbk;
	private final    Gfo_invk_cmd fail_cbk;
	private final    Gfo_rate_list rate_list = new Gfo_rate_list(32);
	private final    long notify_delay = 1000; 
	private final    double delta_threshold = .25d;	// allow variance of up to 25% before updating rate
	private final    String js_cbk, task_type;
	private long time_prv;
	private double rate_cur;
	public Xojs_wkr__base(Xog_cbk_mgr cbk_mgr, Xog_cbk_trg cbk_trg, String js_cbk, Gfo_invk_cmd done_cbk, Gfo_invk_cmd fail_cbk, String task_type) {
		this.cbk_mgr = cbk_mgr;
		this.cbk_trg = cbk_trg;
		this.js_cbk = js_cbk;
		this.done_cbk = done_cbk;
		this.fail_cbk = fail_cbk;
		this.task_type = task_type;
		rate_list.Add(1024 * 1024, 1);	// add default rate of 1 MB per second;
	}
	public void Exec() {
		try {
			this.time_prv = gplx.core.envs.System_.Ticks();
			this.Exec_run();
			done_cbk.Exec_by_ctx(GfsCtx.Instance, GfoMsg_.new_cast_("m").Add("v", this));
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to run task; task=~{0} err=~{1}", task_type, Err_.Message_gplx_log(e));
			cbk_mgr.Send_notify(cbk_trg, String_.Format("failed to run task: task={0} err={1}", task_type, Err_.Message_lang(e)));
			if (fail_cbk != null)
				fail_cbk.Exec();
		}
	}
	@gplx.Virtual protected void Exec_run() {}
	public void Exec_async(String thread_name) {
		gplx.core.threads.Thread_adp_.Start_by_key(thread_name + ".download", this, Invk__exec);
	}

	public Xog_cbk_mgr Cbk_mgr() {return cbk_mgr;} private final    Xog_cbk_mgr cbk_mgr;
	public Xog_cbk_trg Cbk_trg() {return cbk_trg;} private final    Xog_cbk_trg cbk_trg;

	public boolean			Canceled()				{return canceled;} private boolean canceled;
	public void			Cancel()				{this.canceled = true;}
	public byte			Prog_status()			{return status;}
	public void			Prog_status_(byte v)	{status = v;} private byte status;
	public long			Prog_data_cur()			{return data_cur;} private long data_cur;
	public long			Prog_data_end()			{return data_end;} private long data_end;
	protected void Prog_data_end_(long v) {this.data_end = v;}
	public void			Prog_notify_by_msg(String msg) {}
	public boolean			Prog_notify_and_chk_if_suspended(long new_data_cur, long new_data_end) {
		if (status == Gfo_prog_ui_.Status__suspended) return true;	// task paused by ui; exit now;
		long time_cur = gplx.core.envs.System_.Ticks();
		if (time_cur < time_prv + notify_delay) return false;		// message came too soon. ignore it

		// update rate
		double rate_now = (rate_list.Add(new_data_cur - data_cur, (time_cur - time_prv))) * 1000;
		double delta = Math_.Abs_double((rate_now - rate_cur) / rate_cur);
		if (	rate_cur == 0					// rate not set
			||	delta > delta_threshold) {		// rate_now is at least 25% different than rate_prv
			if (delta > delta_threshold * 2)	// rate_now is > 50% different
				rate_cur = rate_now;			// update it now
			else {
				double rate_new = ((rate_now - rate_cur) * .05) + rate_cur;	// calc new rate as 5% of difference
                    // Tfds.Dbg(delta, rate_now, rate_cur, rate_new);
				rate_cur = rate_new;
			}
		}

		// update prog vals
		this.time_prv = time_cur;
		this.data_cur = new_data_cur;
		this.data_end = new_data_end;

		cbk_mgr.Send_json(cbk_trg, js_cbk
		, Gfobj_nde.New().Add_str("task_type", task_type).Add_long("prog_data_cur", data_cur).Add_long("prog_data_end", data_end).Add_int("prog_rate", (int)rate_cur));
		return false;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__exec))		this.Exec();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk__exec = "exec";
}
