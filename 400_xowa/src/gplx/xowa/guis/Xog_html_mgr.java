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
package gplx.xowa.guis; import gplx.*; import gplx.xowa.*;
import gplx.xowa.htmls.portal.*;
public class Xog_html_mgr implements Gfo_invk {
	public Xog_html_mgr(Xoae_app app) {portal_mgr = new Xoa_portal_mgr(app);}
	public Xoa_portal_mgr Portal_mgr() {return portal_mgr;} private Xoa_portal_mgr portal_mgr;
	public String Auto_focus_id() {return auto_focus_id;} private String auto_focus_id = "";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_auto_focus_id_))				auto_focus_id = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_portal))						return portal_mgr;
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_auto_focus_id_ = "auto_focus_id_", Invk_portal = "portal";
}
