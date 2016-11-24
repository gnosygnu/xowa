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
public class Thread_adp_ {
		public static void Sleep(int milliseconds) {
		try {Thread.sleep(milliseconds);} catch (InterruptedException e) {throw Err_.new_exc(e, "core", "thread interrupted", "milliseconds", milliseconds);}
	}
		public static Thread_adp Start_by_key(String thread_name, Gfo_invk invk_itm, String invk_cmd)					{return Start(thread_name, Cancelable_.Never, invk_itm, invk_cmd		, GfoMsg_.new_cast_(invk_cmd));}
	public static Thread_adp Start_by_val(String thread_name, Gfo_invk invk_itm, String invk_cmd, Object val)		{return Start(thread_name, Cancelable_.Never, invk_itm, invk_cmd		, GfoMsg_.new_cast_(invk_cmd).Add("v", val));}
	public static Thread_adp Start_by_msg(String thread_name, Gfo_invk invk_itm, GfoMsg invk_msg)					{return Start(thread_name, Cancelable_.Never, invk_itm, invk_msg.Key()	, invk_msg);}
	public static Thread_adp Start_by_key(String thread_name, Cancelable cxl, Gfo_invk invk_itm, String invk_cmd)				{return Start(thread_name, cxl, invk_itm, invk_cmd		, GfoMsg_.new_cast_(invk_cmd));}
	public static Thread_adp Start_by_val(String thread_name, Cancelable cxl, Gfo_invk invk_itm, String invk_cmd, Object val)	{return Start(thread_name, cxl, invk_itm, invk_cmd		, GfoMsg_.new_cast_(invk_cmd).Add("v", val));}
	private static Thread_adp Start(String thread_name, Cancelable cxl, Gfo_invk invk_itm, String invk_key, GfoMsg invk_msg) {
		Thread_adp rv = new Thread_adp(thread_name, cxl, invk_itm, invk_key, invk_msg);
		rv.Thread__start();
		return rv;
	}
	public static final    String Name_null = null;
}
