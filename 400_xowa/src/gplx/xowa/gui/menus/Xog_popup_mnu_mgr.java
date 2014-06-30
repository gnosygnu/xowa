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
package gplx.xowa.gui.menus; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.xowa.gui.menus.dom.*;
public class Xog_popup_mnu_mgr implements GfoInvkAble {
	private OrderedHash hash = OrderedHash_.new_();
	private Xoa_gui_mgr gui_mgr;
	private Xog_mnu_grp[] mnus = new Xog_mnu_grp[6];
	public Xog_popup_mnu_mgr(Xoa_gui_mgr gui_mgr, Xog_menu_mgr menu_mgr) {
		this.gui_mgr = gui_mgr;
		html_page = Ctor(0, Root_key_html_page, Xog_menu_mgr_src.Html_page);	// NOTE: default menu; always build first; NOTE: set default here (fires before cfg)
		html_link = Ctor(1, Root_key_html_link, Xog_menu_mgr_src.Html_link);
		html_file = Ctor(2, Root_key_html_file, Xog_menu_mgr_src.Html_file);
		tabs_btns = Ctor(3, Root_key_tabs_btns, Xog_menu_mgr_src.Tabs_btns);
		prog	  = Ctor(4, Root_key_prog, Xog_menu_mgr_src.Prog);
		info	  = Ctor(5, Root_key_info, Xog_menu_mgr_src.Info);
	}
	public Xog_mnu_grp Tabs_btns()	{return tabs_btns;} private Xog_mnu_grp tabs_btns;
	public Xog_mnu_grp Html_page()	{return html_page;} private Xog_mnu_grp html_page;
	public Xog_mnu_grp Html_link()	{return html_link;} private Xog_mnu_grp html_link;
	public Xog_mnu_grp Html_file()	{return html_file;} private Xog_mnu_grp html_file;
	public Xog_mnu_grp Prog()		{return prog;}		private Xog_mnu_grp prog;
	public Xog_mnu_grp Info()		{return info;}		private Xog_mnu_grp info;
	public void Init_by_kit() {
		for (int i = 0; i < mnus.length; i++)
			mnus[i].Source_exec(gui_mgr.App().Gfs_mgr());	// NOTE: build menu now; NOTE: do not set default here, or else will override user setting
	}
	public void Lang_changed(Xol_lang lang) {
		for (int i = 0; i < mnus.length; i++)
			Xog_mnu_base.Update_grp_by_lang(gui_mgr.Menu_mgr().Menu_bldr(), lang, mnus[i]);
	}
	public Xog_mnu_grp Get_or_new(String key) {			
		Xog_mnu_grp rv = (Xog_mnu_grp)hash.Fetch(key);
		if (rv == null) {
			rv = new Xog_mnu_grp(gui_mgr, true, key);
			hash.Add(key, rv);
		}
		return rv;
	}	
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))		return Get_or_new(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_get = "get";		
	public static final String Root_key_tabs_btns = "browser.tabs.btns"
	, Root_key_html_page = "html_box", Root_key_html_link = "browser.html.link", Root_key_html_file = "browser.html.file"
	, Root_key_prog = "browser.prog", Root_key_info = "browser.info"
	;
	private Xog_mnu_grp Ctor(int i, String key, String src) {
		Xog_mnu_grp rv = Get_or_new(key).Source_default_(src);
		mnus[i] = rv;
		return rv;
	}
}
