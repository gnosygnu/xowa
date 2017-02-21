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
import java.lang.*;
public class Thread_adp implements Runnable {
	private final    String thread_name; private final    Cancelable cxl; private final    boolean cxlable;
	private final    Gfo_invk invk_itm; private final    String invk_cmd; private final    GfoMsg invk_msg;
	private Thread thread;
	@gplx.Internal protected Thread_adp(String thread_name, Cancelable cxl, Gfo_invk invk_itm, String invk_cmd, GfoMsg invk_msg) {
		this.thread_name = thread_name; this.cxl = cxl; this.cxlable = cxl != Cancelable_.Never;
		this.invk_itm = invk_itm; this.invk_cmd = invk_cmd; this.invk_msg = invk_msg;
	}
	public String	Thread__name()			{return thread_name;}
	public void		Thread__cancel()		{cxl.Cancel();}
	public boolean	Thread__cancelable()	{return cxlable;}
	public boolean	Thread__is_alive()		{return thread == null ? false : thread.isAlive();}	
	public void		Thread__interrupt()		{thread.interrupt();}								
	public void run() {
		try	 {
			Gfo_invk_.Invk_by_msg(invk_itm, invk_cmd, invk_msg);
		}
		catch (Exception e) {	// catch exception
			Gfo_log_.Instance.Warn("thread.failed", "thread_name", thread_name, "cmd", invk_cmd, "err", Err_.Message_gplx_log(e));
		}
	}
	public void Thread__start() {
				this.thread = (thread_name == null) ? new Thread(this) : new Thread(this, thread_name);
		thread.start();
			}
	public static final    Thread_adp Noop = new Thread_adp(Thread_adp_.Name_null, Cancelable_.Never, Gfo_invk_.Noop, "", GfoMsg_.Null);
}
