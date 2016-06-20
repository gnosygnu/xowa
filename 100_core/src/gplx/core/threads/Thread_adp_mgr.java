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
public class Thread_adp_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	private final    int sleep_time, quit_time;
	public Thread_adp_mgr(int sleep_time, int quit_time) {this.sleep_time = sleep_time; this.quit_time = quit_time;}
	public void Add(String key, Thread_adp thread) {
		Thread_halt_itm itm = null;
		synchronized (hash) {
			itm = (Thread_halt_itm)hash.Get_by(key);
			if (itm != null && !itm.Thread.Thread__is_alive())
				hash.Del(key);
		}
		itm = new Thread_halt_itm(key, thread);
		hash.Add(key, itm);
	}
	public void Halt(String uid, Thread_halt_cbk cbk) {
		Thread_halt_itm itm = (Thread_halt_itm)hash.Get_by(uid);
		Halt_by_wkr(itm, cbk);
	}
	public void Halt_all(Thread_halt_cbk cbk) {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Thread_halt_itm itm = (Thread_halt_itm)hash.Get_at(i);
			Halt_by_wkr(itm, cbk);
		}
	}
	private void Halt_by_wkr(Thread_halt_itm itm, Thread_halt_cbk cbk) {
		Thread_halt_wkr halt_wkr = new Thread_halt_wkr(this, itm, cbk, sleep_time, quit_time);
		Thread_adp_.Start_by_key("thread_mgr.halt", halt_wkr, Thread_halt_wkr.Invk__halt);
		synchronized (hash) {
			hash.Del(itm.Key);
		}
	}
	public void Del(String key) {
		synchronized (hash) {
			hash.Del(key);
		}
	}
}
class Thread_halt_itm {
	public Thread_halt_itm(String key, Thread_adp thread) {this.Key = key; this.Thread = thread;}
	public final    String Key;
	public final    Thread_adp Thread;
}
