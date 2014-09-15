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
public class GfoInvkAbleCmd {
	private GfoMsg m;
	public GfoInvkAble InvkAble() {return invkAble;} private GfoInvkAble invkAble;
	public String Cmd() {return cmd;} private String cmd;
	public Object Arg() {return arg;} private Object arg;
	public Object Invk() {
		return invkAble.Invk(GfsCtx._, 0, cmd, m);
	}
	public static final GfoInvkAbleCmd Null = new GfoInvkAbleCmd();
        public static GfoInvkAbleCmd new_(GfoInvkAble invkAble, String cmd) {return arg_(invkAble, cmd, null);}
        public static GfoInvkAbleCmd arg_(GfoInvkAble invkAble, String cmd, Object arg) {
		GfoInvkAbleCmd rv = new GfoInvkAbleCmd();
		rv.invkAble = invkAble; rv.cmd = cmd; rv.arg = arg;
		rv.m = (arg == null) ? GfoMsg_.Null : GfoMsg_.new_parse_(cmd).Add("v", arg);
		return rv;
	}	GfoInvkAbleCmd() {}
}
