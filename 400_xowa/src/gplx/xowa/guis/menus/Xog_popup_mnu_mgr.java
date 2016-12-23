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
package gplx.xowa.guis.menus; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.xowa.guis.menus.dom.*;
import gplx.xowa.langs.*;
public class Xog_popup_mnu_mgr implements Gfo_invk {
	private final    Ordered_hash hash = Ordered_hash_.New();
	private final    Xog_mnu_grp[] mnus = new Xog_mnu_grp[6];
	private Xoa_gui_mgr gui_mgr;
	public Xog_popup_mnu_mgr(Xoa_gui_mgr gui_mgr, Xog_menu_mgr menu_mgr) {
		this.gui_mgr = gui_mgr;
		html_page = Ctor(0, Root_key_html_page);	// NOTE: default menu; always build first;
		html_link = Ctor(1, Root_key_html_link);
		html_file = Ctor(2, Root_key_html_file);
		tabs_btns = Ctor(3, Root_key_tabs_btns);
		prog	  = Ctor(4, Root_key_prog);
		info	  = Ctor(5, Root_key_info);
	}
	public Xog_mnu_grp Tabs_btns()	{return tabs_btns;} private Xog_mnu_grp tabs_btns;
	public Xog_mnu_grp Html_page()	{return html_page;} private Xog_mnu_grp html_page;
	public Xog_mnu_grp Html_link()	{return html_link;} private Xog_mnu_grp html_link;
	public Xog_mnu_grp Html_file()	{return html_file;} private Xog_mnu_grp html_file;
	public Xog_mnu_grp Prog()		{return prog;}		private Xog_mnu_grp prog;
	public Xog_mnu_grp Info()		{return info;}		private Xog_mnu_grp info;
	public void Init_by_kit(Xoae_app app) {
		for (int i = 0; i < mnus.length; i++)
			mnus[i].Source_exec(gui_mgr.App().Gfs_mgr());	// NOTE: build menu now; NOTE: do not set default here, or else will override user setting
		app.Cfg().Bind_many_app(this, Cfg__tabs, Cfg__html__basic, Cfg__html__file, Cfg__html__link, Cfg__status);
	}
	public void Lang_changed(Xol_lang_itm lang) {
		for (int i = 0; i < mnus.length; i++)
			Xog_mnu_base.Update_grp_by_lang(gui_mgr.Menu_mgr().Menu_bldr(), lang, mnus[i]);
	}
	private Xog_mnu_grp Ctor(int i, String key) {
		Xog_mnu_grp rv = new Xog_mnu_grp(gui_mgr, true, key);
		hash.Add(key, rv);
		mnus[i] = rv;
		return rv;
	}
	private void Source_(Xog_mnu_grp mnu, String source) {mnu.Source_(source);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__tabs))			Source_(tabs_btns	, m.ReadStr("v"));
		else if	(ctx.Match(k, Cfg__html__basic))	Source_(html_page	, m.ReadStr("v"));
		else if	(ctx.Match(k, Cfg__html__link))		Source_(html_link	, m.ReadStr("v"));
		else if	(ctx.Match(k, Cfg__html__file))		Source_(html_file	, m.ReadStr("v"));
		else if	(ctx.Match(k, Cfg__status))			Source_(prog		, m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Root_key_tabs_btns = "browser.tabs.btns", Root_key_prog = "browser.prog", Root_key_info = "browser.info"
	, Root_key_html_page = "html_box", Root_key_html_link = "browser.html.link", Root_key_html_file = "browser.html.file";
	private static final String 
	  Cfg__tabs			= "xowa.gui.menus.tabs.source"
	, Cfg__html__basic	= "xowa.gui.menus.html.basic"
	, Cfg__html__link	= "xowa.gui.menus.html.link"
	, Cfg__html__file	= "xowa.gui.menus.html.file"
	, Cfg__status		= "xowa.gui.menus.status.source";
}
