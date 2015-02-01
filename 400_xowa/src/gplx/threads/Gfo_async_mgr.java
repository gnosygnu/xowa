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
package gplx.threads; import gplx.*;
import gplx.core.primitives.*;
public class Gfo_async_mgr implements GfoInvkAble {
	private ListAdp queue = ListAdp_.new_();
	private Bool_obj_ref running = Bool_obj_ref.n_();
	private Gfo_async_cmd_mkr cmd_mkr = new Gfo_async_cmd_mkr();
	public void Queue(GfoInvkAble invk, String invk_key, Object... args) {
		Gfo_async_cmd_itm cmd = cmd_mkr.Get(invk, invk_key, args);
		synchronized (queue) {
			queue.Add(cmd);
		}
		synchronized (running) {
			if (running.Val_n()) {
				running.Val_y_();
				gplx.threads.ThreadAdp_.invk_(Invk_run, this, Invk_run).Start();
			}
		}
	}
	public void Run() {
		Gfo_async_cmd_itm cmd = null;
		try {
			while (true) {
				synchronized (queue) {
					if (queue.Count() == 0) break;
					cmd = (Gfo_async_cmd_itm)ListAdp_.Pop(queue);
					cmd.Exec();
				}
			}
		}
		finally {
			synchronized (running) {
				running.Val_n_();
			}
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run))		Run();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_run = "run";
}
