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
package gplx.xowa.setup.addons; import gplx.*; import gplx.xowa.*; import gplx.xowa.setup.*;
public class Xoi_addon_mgr implements GfoInvkAble {
	public Xoi_firefox_installer Firefox() {return firefox;} private Xoi_firefox_installer firefox = new Xoi_firefox_installer();
	public void Init_by_app(Xoae_app app) {
		firefox.Init_by_app(app);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_firefox)) 		return firefox;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_firefox = "firefox";
}
