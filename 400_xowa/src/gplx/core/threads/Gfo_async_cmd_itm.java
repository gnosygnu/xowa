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
