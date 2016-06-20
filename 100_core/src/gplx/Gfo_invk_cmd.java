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
package gplx;
public class Gfo_invk_cmd {
	private final    Gfo_invk itm; private final    String cmd; private final    GfoMsg msg;		
	public Gfo_invk_cmd(Gfo_invk itm, String cmd, GfoMsg msg) {
		this.itm = itm; this.cmd = cmd; this.msg = msg;
	}
	public Object Exec()								{return itm.Invk(GfsCtx.Instance, 0, cmd, msg);}
	public Object Exec_by_ctx(GfsCtx ctx, GfoMsg msg)	{return itm.Invk(ctx, 0, cmd, msg);}

	public static final    Gfo_invk_cmd Noop = new Gfo_invk_cmd(Gfo_invk_.Noop, "", GfoMsg_.Null);
        public static Gfo_invk_cmd New_by_key(Gfo_invk itm, String cmd) {return New_by_val(itm, cmd, null);}
        public static Gfo_invk_cmd New_by_val(Gfo_invk itm, String cmd, Object val) {
		return new Gfo_invk_cmd(itm, cmd, (val == null) ? GfoMsg_.Null : GfoMsg_.new_parse_(cmd).Add("v", val));
	}
}
