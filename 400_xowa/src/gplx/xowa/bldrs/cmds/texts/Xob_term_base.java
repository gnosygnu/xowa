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
package gplx.xowa.bldrs.cmds.texts; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.xmls.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.dbs.*;	
public abstract class Xob_term_base implements Xob_cmd, Gfo_invk {
	public Xob_term_base Ctor(Xob_bldr bldr, Xowe_wiki wiki) {this.wiki = wiki; return this;} private Xowe_wiki wiki;
	public abstract String Cmd_key();
	public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {}
	public void Cmd_end() {
		Xoae_app app = wiki.Appe();

		// dirty wiki list so that next refresh will load wiki
		app.Gui_mgr().Html_mgr().Portal_mgr().Wikis().Itms_reset();

		// clear cache, else import will load new page with old items from cache; DATE:2013-11-21
		app.Free_mem(false);

		// update main page
		byte[] new_main_page = gplx.xowa.langs.msgs.Xow_mainpage_finder.Find_or(wiki, wiki.Props().Siteinfo_mainpage());	// get new main_page from mainpage_finder
		wiki.Props().Main_page_(new_main_page);
		wiki.Data__core_mgr().Db__core().Tbl__cfg().Upsert_bry(gplx.xowa.wikis.data.Xowd_cfg_key_.Grp__wiki_init, gplx.xowa.wikis.data.Xowd_cfg_key_.Key__init__main_page         , new_main_page);

		// remove import marker
		app.Bldr().Import_marker().End(wiki);

		// flag init_needed prior to show; dir_info will show page_txt instead of page_gz;
		wiki.Init_needed_(true);

		// force load; needed to pick up MediaWiki ns for MediaWiki:mainpage
		wiki.Init_assert();

		Cmd_end_hook();
	}
	public abstract void Cmd_end_hook();
	public void Cmd_term() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}
}
