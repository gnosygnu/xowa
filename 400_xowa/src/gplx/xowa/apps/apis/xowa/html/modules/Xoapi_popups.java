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
package gplx.xowa.apps.apis.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.html.*;
import gplx.xowa.htmls.modules.popups.*;
public class Xoapi_popups implements Gfo_invk, Gfo_evt_mgr_owner {
	private Xoae_app app;
	public Xoapi_popups() {
		evt_mgr = new Gfo_evt_mgr(this);
	}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private Gfo_evt_mgr evt_mgr;
	public void Init_by_app(Xoae_app app) {this.app = app;}
	public void Show_more(String popup_id) {
		Xowe_wiki wiki = app.Gui_mgr().Browser_win().Active_tab().Wiki();
		wiki.Html_mgr().Head_mgr().Popup_mgr().Show_more(popup_id);
	}
	public void Show_all(String popup_id) {
		Xowe_wiki wiki = app.Gui_mgr().Browser_win().Active_tab().Wiki();
		wiki.Html_mgr().Head_mgr().Popup_mgr().Show_all(popup_id);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_show_more))	 					Show_more(m.ReadStr("popup_id"));
		else if	(ctx.Match(k, Invk_show_all))	 					Show_all (m.ReadStr("popup_id"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_show_more = "show_more", Invk_show_all = "show_all"
	;
}
