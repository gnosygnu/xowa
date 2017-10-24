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
package gplx.xowa.addons.wikis.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
public class Srch_db_upgrade {
	private final    Xow_wiki wiki;
	public Srch_db_upgrade(Xow_wiki wiki, Srch_db_mgr search_db_mgr) {
		this.wiki = wiki;
	}
	public void Upgrade() {
		if (!wiki.App().Mode().Tid_is_gui()) return;	// ignore if html-server or drd-app
		Xoae_app app = ((Xoae_app)wiki.App());
		boolean ok = app.Gui_mgr().Kit().Ask_ok_cancel("", "", String_.Concat_lines_nl_skip_last
		( "XOWA would like to upgrade your search database for " + wiki.Domain_str() + "."
		, ""
		, "* Press OK to upgrade. This may take an hour for English Wikipedia."
		, "* Press Cancel to skip. You will not be able to search."
		, ""
		, "If you cancel, XOWA will ask you to upgrade this wiki again next time you restart the application."
		, ""
		, "Note that you can run this upgrade process manually by doing:"
		, "  Main Menu -> Tools -> Wiki Maintenance -> Search"
		));
		if (!ok) return;
		Xowe_wiki wikie = (Xowe_wiki)wiki;
		gplx.xowa.addons.wikis.searchs.bldrs.Srch_bldr_mgr_.Setup(wikie);
		gplx.xowa.bldrs.Xob_bldr bldr = app.Bldr();
		bldr.Cmd_mgr().Add(new gplx.xowa.bldrs.cmds.utils.Xob_alert_cmd(bldr, wikie).Msg_("search upgrade finished"));
		gplx.core.threads.Thread_adp_.Start_by_key("search_upgrade", app.Bldr(), gplx.xowa.bldrs.Xob_bldr.Invk_run);
	}
	public static final int 
	  Version__link_score_alpha			= 0		// in 2016-02 android alpha
	, Version__initial					= 1
	, Version__page_count				= 2
	, Version__link_score				= 3
	;
}
