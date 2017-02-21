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
public class Gfo_thread_grp implements Gfo_invk {
	private final    Object thread_lock = new Object();
	private final    List_adp list = List_adp_.New();
	private int active_cur;
	public Gfo_thread_grp(String key) {this.key = key;}
	public String Key() {return key;} private final    String key;
	public boolean Autorun() {return autorun;} public Gfo_thread_grp Autorun_(boolean v) {autorun = v; return this;} private boolean autorun = true;
	public int Active_max() {return active_max;} public Gfo_thread_grp Active_max_(int v) {active_max = v; return this;} private int active_max = 1;
	public void Add(Gfo_thread_itm... ary) {
		synchronized (thread_lock) {
			list.Add_many((Object[])ary);
		}
		if (autorun)
			this.Run();
	}
	public void Run() {
		int len = list.Len(); if (len == 0) return;		// nothing in list; occurs when last item calls Run
		for (int i = 0; i < len; ++i) {
			if (active_cur == active_max) break;		// already at limit; return
			Gfo_thread_itm itm = null;
			synchronized (thread_lock) {
				itm = (Gfo_thread_itm)List_adp_.Pop_first(list);
				++active_cur;
			}
			Thread_adp_.Start_by_msg(itm.Thread__name(), this, GfoMsg_.new_cast_(Invk_run_wkr).Add("v", itm));
		}			
	}
	public void Stop_all() {
		synchronized (thread_lock) {
			int len = list.Len();
			for (int i = 0; i < len; ++i) {
				Gfo_thread_itm itm = (Gfo_thread_itm)list.Get_at(i);
				itm.Thread__stop();
			}
			active_cur = 0;
			list.Clear();
		}
	}
	public void Del(String key) {
		synchronized (thread_lock) {
			List_adp deleted = List_adp_.New();
			int len = list.Len();
			for (int i = 0; i < len; ++i) {
				Gfo_thread_itm itm = (Gfo_thread_itm)list.Get_at(i);
				if (itm.Thread__can_delete(key))
					deleted.Add(itm);
			}
			len = deleted.Len();
			for (int i = 0; i < len; ++i) {
				Gfo_thread_itm itm = (Gfo_thread_itm)deleted.Get_at(i);
				itm.Thread__stop();
				list.Del(itm);
			}
		}
	}
	private void Run_wkr(Gfo_thread_itm itm) {
		try		{itm.Thread__exec();}
		catch	(Exception e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "uncaught exception while running thread; name=~{0} err=~{1}", itm.Thread__name(), Err_.Message_gplx_log(e));}
		finally {
			synchronized (thread_lock) {
				--active_cur;
			}
			this.Run();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run_wkr))		Run_wkr((Gfo_thread_itm)m.ReadObj("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_run_wkr = "run_wkr";
}
