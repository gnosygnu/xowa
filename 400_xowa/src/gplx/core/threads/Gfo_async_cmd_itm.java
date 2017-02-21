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
public class Gfo_async_cmd_itm implements Gfo_invk {
	private Gfo_invk invk; private String invk_key; private GfoMsg msg = GfoMsg_.new_cast_("");
	public Gfo_async_cmd_itm Init(Gfo_invk invk, String invk_key, Object... args) {
		this.invk = invk; this.invk_key = invk_key;
		msg.Args_reset();
		msg.Clear();
		int len = args.length; 
		for (int i = 0; i < len; i += 2) {
			String key = (String)args[i];
			Object val = args[i + 1];
			msg.Add(key, val);
		}
		return this;
	}
	public void Exec() {
		Gfo_invk_.Invk_by_msg(invk, invk_key, msg);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_exec))		Exec();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_exec = "exec";
	public static final    Gfo_async_cmd_itm[] Ary_empty = new Gfo_async_cmd_itm[0];
}
