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
package gplx.xowa.apis.xowa; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*;
import gplx.xowa.apis.xowa.html.*;
public class Xoapi_html implements GfoInvkAble {
	public void Ctor_by_app(Xoae_app app) {
		page.Ctor_by_app(app);
	}
	public void Init_by_kit(Xoae_app app) {
		tidy.Init_by_kit(app);
		modules.Init_by_kit(app);
	}
	public Xoapi_tidy		Tidy() {return tidy;} private final Xoapi_tidy tidy = new Xoapi_tidy();
	public Xoapi_modules	Modules() {return modules;} private final Xoapi_modules modules = new Xoapi_modules();
	public Xoapi_skins		Skins() {return skins;} private final Xoapi_skins skins = new Xoapi_skins();
	public Xoapi_page		Page() {return page;} private final Xoapi_page page = new Xoapi_page();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_tidy)) 						return tidy;
		else if	(ctx.Match(k, Invk_modules)) 					return modules;
		else if	(ctx.Match(k, Invk_skins)) 						return skins;
		else if	(ctx.Match(k, Invk_page)) 						return page;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_tidy = "tidy", Invk_modules = "modules", Invk_skins = "skins", Invk_page = "page";
}
