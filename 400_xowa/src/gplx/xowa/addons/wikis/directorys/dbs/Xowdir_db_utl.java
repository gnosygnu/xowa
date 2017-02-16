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
package gplx.xowa.addons.wikis.directorys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.sys.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.langs.jsons.*;
public class Xowdir_db_utl {
	private static final String Grp__personal = "xowa.personal_wikis", Key__personal__wiki_json = "xowa.personal_wikis.wiki_json";
	public static void Wiki_json__upsert(Xowe_wiki wiki, String wiki_json) {
		wiki.Data__core_mgr().Db__core().Tbl__cfg().Upsert_str(Grp__personal, Key__personal__wiki_json, wiki_json);
	}
	public static void Wiki_json__verify(Xoae_app app, byte[] domain_bry, Io_url core_db_url, Db_conn core_db_conn) {
		String domain_str = String_.new_u8(domain_bry);
		boolean dirty = false;

		// get wiki_json from core_db.xowa_cfg
		Db_cfg_tbl core_cfg_tbl = new Db_cfg_tbl(core_db_conn, Xowd_cfg_tbl_.Tbl_name);
		String wiki_cfg_str = core_cfg_tbl.Select_str_or(Grp__personal, Key__personal__wiki_json, null);

		// not in core_db; check user_db.user_wiki
		Xowdir_wiki_json wiki_json_itm = null;
		if (wiki_cfg_str == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wiki_json not found in core_db; url=~{0}", core_db_url);
			dirty = true;
			Xowdir_db_mgr user_db_mgr = new Xowdir_db_mgr(app.User().User_db_mgr().Conn());
			Xowdir_wiki_itm user_wiki_row = user_db_mgr.Tbl__wiki().Select_by_key_or_null(domain_str);
			if (user_wiki_row != null) {
				wiki_json_itm = user_wiki_row.Json();
			}
		}
		else {
			wiki_json_itm = Xowdir_wiki_json.New_by_json(new Json_parser(), wiki_cfg_str);
		}

		// not in core_db or user_db; infer
		if (wiki_json_itm == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wiki_json not found in core_db or user_db; url=~{0}", core_db_url);
			dirty = true;
			wiki_json_itm = Wiki_json__infer(core_db_url, core_cfg_tbl);
		}

		// check domain exists
		if (String_.Eq(wiki_json_itm.Domain(), String_.Empty)) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wiki_json does not have domain; url=~{0}", core_db_url);
			dirty = true;
			wiki_json_itm.Domain_(core_cfg_tbl.Select_str_or(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__domain, core_db_url.NameOnly()));
		}

		// something changed; save it
		if (dirty) {
			Xowdir_db_mgr user_db_mgr = new Xowdir_db_mgr(app.User().User_db_mgr().Conn());
			Xowdir_wiki_itm user_wiki_row = user_db_mgr.Tbl__wiki().Select_by_key_or_null(domain_str);

			String wiki_json_str = wiki_json_itm.To_str(new gplx.langs.jsons.Json_wtr());
			core_cfg_tbl.Update_str(Grp__personal, Key__personal__wiki_json, wiki_json_str);
			user_db_mgr.Tbl__wiki().Upsert(user_wiki_row.Id(), user_wiki_row.Domain(), user_wiki_row.Url(), wiki_json_str);
		}
	}
	public static Xowdir_wiki_json Wiki_json__get_or_create(Io_url core_db_url, Db_conn core_db_conn) {
		Xowdir_wiki_json rv = null;

		Db_cfg_tbl core_cfg_tbl = new Db_cfg_tbl(core_db_conn, Xowd_cfg_tbl_.Tbl_name);
		String wiki_cfg_str = core_cfg_tbl.Select_str_or(Grp__personal, Key__personal__wiki_json, null);

		if (wiki_cfg_str == null) {
			rv = Wiki_json__infer(core_db_url, core_cfg_tbl);
			core_cfg_tbl.Insert_str(Grp__personal, Key__personal__wiki_json, rv.To_str(new gplx.langs.jsons.Json_wtr()));
		}
		else {
			rv = Xowdir_wiki_json.New_by_json(new gplx.langs.jsons.Json_parser(), wiki_cfg_str);
		}
		return rv;
	}
	private static Xowdir_wiki_json Wiki_json__infer(Io_url core_db_url, Db_cfg_tbl core_cfg_tbl) {
		String domain    = core_cfg_tbl.Select_str_or(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__domain, core_db_url.NameOnly());
		String main_page = core_cfg_tbl.Select_str_or(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__main_page, Xoa_page_.Main_page_str);
		return new Xowdir_wiki_json(domain, domain, main_page);
	}
	public static int Wiki_id__next(Xoa_app app) {
		Db_sys_mgr sys_mgr = new Db_sys_mgr(app.User().User_db_mgr().Conn());
		return sys_mgr.Autonum_next("user.wikis.id");
	}
}
