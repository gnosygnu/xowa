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
package gplx.xowa.addons.users.wikis.regys; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*;
import gplx.xowa.addons.users.wikis.regys.dbs.*;
import gplx.xowa.addons.users.wikis.regys.specials.itms.*; import gplx.xowa.addons.users.wikis.regys.specials.lists.*;
import gplx.xowa.htmls.bridges.*;
import gplx.dbs.*;
import gplx.xowa.specials.*;
public class Xou_regy_addon implements Xoax_addon_itm, Xoax_addon_itm__special, Xoax_addon_itm__json {
	public Xow_special_page[] Special_pages() {
		return new Xow_special_page[]
		{ Xouw_itm_special.Prototype
		, Xouw_list_special.Prototype
		};
	}
	public Bridge_cmd_itm[] Json_cmds() {
		return new Bridge_cmd_itm[]
		{ Xouw_itm_bridge.Prototype
		};
	}

	public String Addon__key() {return ADDON__KEY;} private static final String ADDON__KEY = "xowa.user.wiki.regy";
//		public static Xou_regy_addon Addon__get(Xow_wiki wiki) {
//			Xou_regy_addon rv = (Xou_regy_addon)wiki.Addon_mgr().Itms__get_or_null(ADDON__KEY);
//			if (rv == null) {
//				rv = new Xou_regy_addon(wiki.App().User().User_db_mgr().Conn());
//				wiki.Addon_mgr().Itms__add(rv);
//			}
//			return rv;
//		}
	public static void Init(Xoae_app app) {
		// exit if none found
		Db_conn conn = app.User().User_db_mgr().Conn();
		if (!conn.Meta_tbl_exists(Xou_wiki_tbl.Tbl_name_dflt)) return;

		// register
		Xouw_db_mgr db_mgr = new Xouw_db_mgr(conn);
		Xou_wiki_itm[] itms = db_mgr.Tbl__wiki().Select_all();
		int len = itms.length;
		for (int i = 0; i < len; ++i) {
			Xou_wiki_itm itm = itms[i];
			Xowe_wiki wiki = new Xowe_wiki
			( app
			, gplx.xowa.langs.Xol_lang_itm_.Lang_en_make(app.Lang_mgr())
			, gplx.xowa.wikis.nss.Xow_ns_mgr_.default_(gplx.xowa.langs.cases.Xol_case_mgr_.A7())
			, gplx.xowa.wikis.domains.Xow_domain_itm_.parse(Bry_.new_u8(itm.Domain()))
			, itm.Url().OwnerDir());
			wiki.Appe().Wiki_mgr().Add(wiki);
			wiki.Init_by_wiki__force_and_mark_inited();
			wiki.Db_mgr_as_sql().Save_mgr().Create_enabled_(true);
			app.User().Wikii().Xwiki_mgr().Add_by_atrs(itm.Domain(), itm.Domain());
		}
	}
}
