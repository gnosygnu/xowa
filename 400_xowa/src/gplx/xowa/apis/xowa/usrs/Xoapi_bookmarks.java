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
package gplx.xowa.apis.xowa.usrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
import gplx.xowa.gui.history.*; import gplx.xowa.gui.views.*;
public class Xoapi_bookmarks implements GfoInvkAble {
	private Xoa_app app; private Xog_win_itm win;
	public void Ctor_by_app(Xoa_app app) {this.app = app;}
	public void Init_by_kit(Xoa_app app) {this.win = app.Gui_mgr().Browser_win();}
	public boolean Enabled() {return enabled;} private boolean enabled = true;
	public void Enabled_(boolean v) {enabled = v;}
	public void Add(String url_str) {
		if (!enabled) return;
		Xog_tab_itm tab = win.Active_tab(); if (tab == Xog_tab_itm_.Null) return;
		Xoa_page page = tab.Page();
		byte[] wiki_domain = null, ttl_full_txt = null;
		if (url_str == null) {
			wiki_domain = page.Wiki().Domain_bry();
			ttl_full_txt = page.Ttl().Full_txt();
		}
		else {
			Xoa_url url = Xoa_url_parser.Parse_from_url_bar(app, page.Wiki(), url_str);
			wiki_domain = url.Wiki_bry();
			ttl_full_txt = url.Page_bry();
		}
		app.User().Bookmarks_add(wiki_domain, ttl_full_txt);
		app.Usr_dlg().Prog_many("", "", "bookmark added: ~{0}", String_.new_utf8_(ttl_full_txt));
	}
	public void Show() {win.Page__navigate_by_url_bar("home/wiki/Data:Bookmarks");}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled)) 							return Yn.X_to_str(this.Enabled());
		else if	(ctx.Match(k, Invk_enabled_)) 							Enabled_(m.ReadYn("v"));
		else if	(ctx.Match(k, Invk_add)) 								this.Add(m.ReadStrOr("v", null));
		else if	(ctx.Match(k, Invk_show)) 								this.Show();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_enabled = "enabled", Invk_enabled_ = "enabled_", Invk_add = "add", Invk_show = "show";
}
