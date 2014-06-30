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
package gplx.xowa; import gplx.*;
public class Xow_sys_cfg implements GfoInvkAble {
	public Xow_sys_cfg(Xow_wiki wiki) {}
	public boolean Xowa_cmd_enabled() {return xowa_cmd_enabled;} public Xow_sys_cfg Xowa_cmd_enabled_(boolean v) {xowa_cmd_enabled = v; return this;} private boolean xowa_cmd_enabled;
	public boolean Xowa_proto_enabled() {return xowa_proto_enabled;} public Xow_sys_cfg Xowa_proto_enabled_(boolean v) {xowa_proto_enabled = v; return this;} private boolean xowa_proto_enabled;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_xowa_cmd_enabled_))			xowa_cmd_enabled	= m.ReadYn("v");
		else if	(ctx.Match(k, Invk_xowa_cmd_enabled_))			xowa_proto_enabled	= m.ReadYn("v");
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_xowa_cmd_enabled_ = "xowa_cmd_enabled_";
}
