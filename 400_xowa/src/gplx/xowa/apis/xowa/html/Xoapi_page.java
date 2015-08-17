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
package gplx.xowa.apis.xowa.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
import gplx.core.btries.*;
public class Xoapi_page implements GfoInvkAble {
	public void Ctor_by_app(Xoae_app app) {
		toggle_mgr.Ctor_by_app(app);
	}
	public Xoapi_toggle_mgr Toggle_mgr() {return toggle_mgr;} private final Xoapi_toggle_mgr toggle_mgr = new Xoapi_toggle_mgr();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_toggles)) 			return toggle_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_toggles = "toggles";
}
