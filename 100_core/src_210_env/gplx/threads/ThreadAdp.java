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
import java.lang.*;
public class ThreadAdp implements Runnable {
	private String name; private GfoInvkAble invk; private String cmd; private GfoMsg msg;
	@gplx.Internal protected ThreadAdp(String name, GfoInvkAble invk, String cmd, GfoMsg msg) {
		this.name = name; this.invk = invk; this.cmd = cmd; this.msg = msg;
		this.ctor_ThreadAdp();
	}
		public ThreadAdp Start() {thread.start(); return this;}
	public void Interrupt() {thread.interrupt();}
	public void Join() {
		try {
			thread.join();		
		}
		catch (Exception e) {
			Err_.Noop(e);
		}
	}
//	public void Stop() {thread.stop();}
	public boolean IsAlive() {return thread.isAlive();}
	void ctor_ThreadAdp() {
		if (name == null)
			thread = new Thread(this);
		else
			thread = new Thread(this, name);
	}
	@Override public void run() {
		invk.Invk(GfsCtx._, 0, cmd, msg);
	}
	public Thread Under_thread() {return thread;} private Thread thread;
		public static final ThreadAdp Null = new ThreadAdp(ThreadAdp_.Name_null, GfoInvkAble_.Null, "", GfoMsg_.Null);
}
