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
package gplx.xowa.apps.apis.xowa.html.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.html.*;
public class Xoapi_skin_app_base implements GfoInvkAble {
	public void Init_by_kit(Xoae_app app) {
	}
	public boolean Sidebar_home_enabled() {return sidebar_home_enabled;} public void Sidebar_home_enabled_(boolean v) {sidebar_home_enabled = v;} private boolean sidebar_home_enabled;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_sidebar_home_enabled))	 	return Yn.To_str(sidebar_home_enabled);
		else if	(ctx.Match(k, Invk_sidebar_home_enabled_))	 	sidebar_home_enabled = m.ReadYn("v");
		return this;
	}
	private static final String Invk_sidebar_home_enabled = "sidebar_home_enabled", Invk_sidebar_home_enabled_ = "sidebar_home_enabled_";
}
