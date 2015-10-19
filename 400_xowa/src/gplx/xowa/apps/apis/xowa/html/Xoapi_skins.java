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
package gplx.xowa.apps.apis.xowa.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.apps.apis.xowa.html.skins.*;
public class Xoapi_skins implements GfoInvkAble {
	private Hash_adp hash = Hash_adp_.new_();
	public Xoapi_skins() {
		server.Sidebar_home_enabled_(true);
		hash.Add("desktop", desktop);
		hash.Add("server", server);
	}
	public Xoapi_skin_app_base Desktop() {return desktop;} private Xoapi_skin_app_base desktop = new Xoapi_skin_app_base();
	public Xoapi_skin_app_base Server () {return server ;} private Xoapi_skin_app_base server  = new Xoapi_skin_app_base();
	private GfoInvkAble Get(String key) {return (GfoInvkAble)hash.Get_by(key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))	 			return Get(m.ReadStr("v"));
		return this;
	}
	private static final String Invk_get = "get";
}
