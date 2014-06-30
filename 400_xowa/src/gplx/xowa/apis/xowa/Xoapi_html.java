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
	public void Init_by_kit(Xoa_app app) {
		tidy.Init_by_kit(app);
		modules.Init_by_kit(app);
	}
	public Xoapi_tidy		Tidy() {return tidy;} private Xoapi_tidy tidy = new Xoapi_tidy();
	public Xoapi_modules	Modules() {return modules;} private Xoapi_modules modules = new Xoapi_modules();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_tidy)) 						return tidy;
		else if	(ctx.Match(k, Invk_modules)) 					return modules;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_tidy = "tidy", Invk_modules = "modules";
}
