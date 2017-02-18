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
package gplx.xowa.addons.wikis.directorys.specials.items.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*; import gplx.xowa.addons.wikis.directorys.specials.items.*;
public class Xow_wiki_factory {
	public static Xowe_wiki Load_personal(Xoae_app app, byte[] domain, Io_url dir_url) {
		// upgrade wiki directly at db
		Xow_wiki_upgrade_.Upgrade_wiki(app, domain, dir_url);

		// create the wiki
		Xowe_wiki rv = new Xowe_wiki
		( app
		, gplx.xowa.langs.Xol_lang_itm_.Lang_en_make(app.Lang_mgr())
		, gplx.xowa.wikis.nss.Xow_ns_mgr_.default_(gplx.xowa.langs.cases.Xol_case_mgr_.U8())
		, gplx.xowa.wikis.domains.Xow_domain_itm_.parse(domain)
		, dir_url);

		// register it in app.Wikis; note that this must occur before initialization
		app.Wiki_mgr().Add(rv);

		// do more initialization
		rv.Init_by_wiki__force_and_mark_inited();
		rv.Db_mgr_as_sql().Save_mgr().Create_enabled_(true);

		// register it for the url-bar; EX: test.me.org/wiki/Main_Page
		app.User().Wikii().Xwiki_mgr().Add_by_atrs(domain, domain);

		// add an xwiki to xowa.home
		rv.Xwiki_mgr().Add_by_atrs("xowa.home", "home");

		// HACK: remove CC copyright message; should change to option
		rv.Msg_mgr().Get_or_make(Bry_.new_a7("wikimedia-copyright")).Atrs_set(Bry_.Empty, false, false);
		return rv;
	}
}
