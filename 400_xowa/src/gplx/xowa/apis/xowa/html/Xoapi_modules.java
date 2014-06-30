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
import gplx.xowa.apis.xowa.html.modules.*;
public class Xoapi_modules implements GfoInvkAble {
	public void Init_by_kit(Xoa_app app) {
		popups.Init_by_app(app);
	}
	public Xoapi_collapsible	Collapsible()	{return collapsible;} private Xoapi_collapsible collapsible = new Xoapi_collapsible();
	public Xoapi_navframe		Navframe()		{return navframe;} private Xoapi_navframe navframe = new Xoapi_navframe();
	public Xoapi_toc			Toc()			{return toc;} private Xoapi_toc toc = new Xoapi_toc();
	public Xoapi_popups			Popups()		{return popups;} private Xoapi_popups popups = new Xoapi_popups();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_collapsible))	 		return collapsible;
		else if	(ctx.Match(k, Invk_navframe))	 			return navframe;
		else if	(ctx.Match(k, Invk_toc))	 				return toc;
		else if	(ctx.Match(k, Invk_popups))	 				return popups;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_collapsible = "collapsible", Invk_navframe = "navframe", Invk_toc = "toc", Invk_popups = "popups";
}
