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
package gplx.xowa.cfgs2; import gplx.*; import gplx.xowa.*;
public class Xocfg_regy implements GfoInvkAble {
	public Xocfg_regy(Xoae_app app) {
		app_cfg = new Xocfg_root(app, Xocfg_root_.Tid_app);
	}
	public Xocfg_root App() {return app_cfg;} private Xocfg_root app_cfg;
	public Xocfg_root Get_or_null(String key) {
		if		(String_.Eq(key, Key_app))		return app_cfg;
		else									throw Err_.new_unhandled(key);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))				return Get_or_null(m.ReadStrOr("v", Key_app));
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_get = "get";
	public static final String Key_app = null;
}
