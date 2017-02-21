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
