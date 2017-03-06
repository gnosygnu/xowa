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
package gplx.xowa.addons.wikis.directorys.specials.items.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*; import gplx.xowa.addons.wikis.directorys.specials.items.*;
import gplx.xowa.wikis.dbs.*;
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
		Xodb_save_mgr save_mgr = rv.Db_mgr_as_sql().Save_mgr();
		save_mgr.Create_enabled_(true);
		save_mgr.Update_modified_on_enabled_(true);

		// register it for the url-bar; EX: test.me.org/wiki/Main_Page
		app.User().Wikii().Xwiki_mgr().Add_by_atrs_offline(String_.new_u8(domain), String_.new_u8(domain));

		// add an xwiki to xowa.home
		rv.Xwiki_mgr().Add_by_atrs("xowa.home", "home");

		// HACK: remove CC copyright message; should change to option
		rv.Msg_mgr().Get_or_make(Bry_.new_a7("wikimedia-copyright")).Atrs_set(Bry_.Empty, false, false);
		return rv;
	}
}
