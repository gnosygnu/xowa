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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
public class Xouc_pages_mgr implements Gfo_invk {
	public Xouc_pages_mgr(Xou_cfg config) {}
	public String Home() {return home;} public Xouc_pages_mgr Home_(String v) {home = v; return this;} private String home = Page_xowa;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_home))			return home;
		else if	(ctx.Match(k, Invk_home_))			home = m.ReadStr("v");
		return this;
	}	public static final String Invk_home = "home", Invk_home_ = "home_";
	public static final String Page_xowa = "home/wiki/Main_Page";
}
