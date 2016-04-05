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
package gplx.xowa.addons.apps.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
public class Srch_db_upgrade {
	private final    Xow_wiki wiki;
	private boolean upgrade_prompted;
	public Srch_db_upgrade(Xow_wiki wiki, Srch_db_mgr search_db_mgr) {
		this.wiki = wiki;
	}
	public void Upgrade() {
		if (!wiki.App().Mode().Tid_is_gui()) return;	// ignore if html-server or drd-app
		if (upgrade_prompted) return;
		upgrade_prompted = true;
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
		gplx.xowa.addons.apps.searchs.bldrs.Srch_bldr_mgr_.Setup(wikie);
		gplx.xowa.bldrs.Xob_bldr bldr = app.Bldr();
		bldr.Cmd_mgr().Add(new gplx.xowa.bldrs.cmds.utils.Xob_alert_cmd(bldr, wikie).Msg_("search upgrade finished"));
		gplx.core.threads.Thread_adp_.invk_("search_upgrade", app.Bldr(), gplx.xowa.bldrs.Xob_bldr.Invk_run).Start();
	}
	public static final int 
	  Version__link_score_alpha			= 0		// in 2016-02 android alpha
	, Version__initial					= 1
	, Version__page_count				= 2
	, Version__link_score				= 3
	;
}
