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
package gplx.core.threads; import gplx.*; import gplx.core.*;
public class Gfo_thread_pool implements GfoInvkAble {
	private Object thread_lock = new Object();
	private ListAdp queue = ListAdp_.new_();
	private GfoMsg run_msg = GfoMsg_.new_cast_(Invk_run_wkr);
	private boolean running = false;
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} public Gfo_thread_pool Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v; return this;} private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Null;
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
			wkr = (Gfo_thread_wkr)ListAdp_.Pop_first(queue);
		}			
		Thread_adp_.Run_invk_msg(wkr.Name(), this, run_msg.Clear().Add("v", wkr));
	}
	private void Run_wkr(Gfo_thread_wkr wkr) {
		try {wkr.Exec();}
		catch (Exception e) {
			usr_dlg.Warn_many("", "", "uncaught exception while running thread; name=~{0} err=~{1}", wkr.Name(), Err_.Message_gplx_brief(e));
		}
		finally {
			if (wkr.Resume())
				this.Resume();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run_wkr))		Run_wkr((Gfo_thread_wkr)m.ReadObj("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_run_wkr = "run_wkr";
}
