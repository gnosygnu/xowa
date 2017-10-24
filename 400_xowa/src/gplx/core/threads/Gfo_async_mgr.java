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
import gplx.core.primitives.*;
public class Gfo_async_mgr implements Gfo_invk {
	private List_adp queue = List_adp_.New();
	private Bool_obj_ref running = Bool_obj_ref.n_();
	private Gfo_async_cmd_mkr cmd_mkr = new Gfo_async_cmd_mkr();
	public void Queue(Gfo_invk invk, String invk_key, Object... args) {
		Gfo_async_cmd_itm cmd = cmd_mkr.Get(invk, invk_key, args);
		synchronized (queue) {
			queue.Add(cmd);
		}
		synchronized (running) {
			if (running.Val_n()) {
				running.Val_y_();
				Thread_adp_.Start_by_key(Invk_run, this, Invk_run);
			}
		}
	}
	public void Run() {
		Gfo_async_cmd_itm cmd = null;
		try {
			while (true) {
				synchronized (queue) {
					if (queue.Count() == 0) break;
					cmd = (Gfo_async_cmd_itm)List_adp_.Pop(queue);
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
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_run = "run";
}
