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
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.wikis.ctgs.dbs.*;
import gplx.xowa.addons.wikis.directorys.dbs.*;
public class Xow_wiki_factory {
	public static Xowe_wiki Load_personal(Xoae_app app, byte[] domain, Io_url dir_url) {
		// upgrade wiki directly at db
		Upgrade_wiki(app, domain, dir_url);

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
	private static void Upgrade_wiki(Xoae_app app, byte[] domain, Io_url dir_url) {
		// get conn
		Io_url core_db_url = gplx.xowa.wikis.data.Xow_db_file__core_.Find_core_fil_or_null(dir_url, String_.new_u8(domain));
		if (core_db_url == null) {
			throw Err_.new_wo_type("failed to find core_db for wiki; wiki=~{domain} dir=~{dir_url}", domain, dir_url);
		}
		Db_conn core_db_conn = Db_conn_bldr.Instance.Get_or_fail(core_db_url);

		// > v4.2.0
		// cat_link: if cat_link.cl_sortkey_prefix doesn't exist, then cat_link is old format; drop it and add the new one
		try {
			if (!core_db_conn.Meta_fld_exists(Xodb_cat_link_tbl.TBL_NAME, Xodb_cat_link_tbl.FLD__cl_sortkey_prefix)) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "xo.personal:cat_link upgrade; fil=~{0}", core_db_url.Raw());
				core_db_conn.Meta_tbl_delete(Xodb_cat_link_tbl.TBL_NAME);
				core_db_conn.Meta_tbl_assert(new Xodb_cat_link_tbl(core_db_conn));
			}
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "xo.personal:cat_link upgrade failed; err=~{0}", Err_.Message_gplx_log(e));
		}

		// page.cat_db_id: if page.cat_db_id doesn't exist, then add it
		try {
			if (!core_db_conn.Meta_fld_exists(Xowd_page_tbl.TBL_NAME, Xowd_page_tbl.FLD__page_cat_db_id)) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "xo.personal:page.page_cat_db_id upgrade; fil=~{0}", core_db_url.Raw());
				core_db_conn.Meta_fld_append(Xowd_page_tbl.TBL_NAME, Dbmeta_fld_itm.new_int(Xowd_page_tbl.FLD__page_cat_db_id).Default_(-1));
			}
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "xo.personal:page.page_cat_db_id upgrade failed; err=~{0}", Err_.Message_gplx_log(e));
		}

		// verify json
		Xowdir_wiki_props_mgr core_db_props = Xowdir_wiki_props_mgr_.New_xowa(app, core_db_url);
		core_db_props.Verify(Bool_.N, String_.new_u8(domain), core_db_url);

		// check for page_ids < 1
//			Xowd_page_tbl page_tbl = new Xowd_page_tbl(core_db_conn, Bool_.N);
//			int[] page_ids = page_tbl.Select_invalid();
//			int page_ids_len = page_ids.length;
//			if (page_ids_len > 0) {
//				int next_id = cfg_tbl.Select_int("db", "page.id_next");
//				for (int i = 0; i < page_ids_len; i++) {
//					int old_page_id = page_ids[i];
//					int new_page_id = next_id + i;
//					Xopg_db_mgr.Update_page_id(wiki, old_id, new_id);
//				}
//				cfg_tbl.Upsert_int("db", "page.id_next", next_id + page_ids_len);
//			}
	}
}
