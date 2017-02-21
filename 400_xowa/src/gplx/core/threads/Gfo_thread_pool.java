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
package gplx.core.threads; import gplx.*; import gplx.core.*;
public class Gfo_thread_pool implements Gfo_invk {
	private Object thread_lock = new Object();
	private List_adp queue = List_adp_.New();
	private GfoMsg run_msg = GfoMsg_.new_cast_(Invk_run_wkr);
	private boolean running = false;
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} public Gfo_thread_pool Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v; return this;} private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Noop;
	public void Clear() {synchronized (thread_lock) {queue.Clear(); running = false;}}
	public Gfo_thread_pool Add_at_end(Gfo_thread_wkr wkr) {
		synchronized (thread_lock) {queue.Add(wkr);}
		return this;
	}
	public void Resume() {
		synchronized (thread_lock) {
			running = false;
		}
		this.Run();
	}
	public void Run() {
		Gfo_thread_wkr wkr = null;
		synchronized (thread_lock) {
			if (running) return;								// already running; discard run request and rely on running-wkr to call Run when done
			int len = queue.Count(); if (len == 0) return;		// nothing in list; occurs when last item calls Run when done
			running = true;
			wkr = (Gfo_thread_wkr)List_adp_.Pop_first(queue);
		}			
		Thread_adp_.Start_by_msg(wkr.Thread__name(), this, run_msg.Clear().Add("v", wkr));
	}
	private void Run_wkr(Gfo_thread_wkr wkr) {
		try {wkr.Thread__exec();}
		catch (Exception e) {
			usr_dlg.Warn_many("", "", "uncaught exception while running thread; name=~{0} err=~{1}", wkr.Thread__name(), Err_.Message_gplx_full(e));
		}
		finally {
			if (wkr.Thread__resume())
				this.Resume();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run_wkr))		Run_wkr((Gfo_thread_wkr)m.ReadObj("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_run_wkr = "run_wkr";
}
