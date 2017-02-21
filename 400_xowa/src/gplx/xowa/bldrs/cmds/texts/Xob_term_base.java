/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
