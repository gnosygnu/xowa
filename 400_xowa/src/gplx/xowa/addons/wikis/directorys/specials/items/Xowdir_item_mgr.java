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
package gplx.xowa.addons.wikis.directorys.specials.items; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*;
import gplx.langs.jsons.*;
import gplx.dbs.sys.*; import gplx.xowa.addons.wikis.directorys.dbs.*; import gplx.xowa.addons.wikis.directorys.specials.items.bldrs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.langs.cases.*;
class Xowdir_item_mgr {
	private final    Xoa_app app;
	private final    Json_wtr json_wtr = new Json_wtr();
	private gplx.xowa.guis.cbks.Xog_cbk_trg cbk_trg = gplx.xowa.guis.cbks.Xog_cbk_trg.New(Xowdir_item_special.Prototype.Special__meta().Ttl_bry());
	public Xowdir_item_mgr(Xoa_app app) {
		this.app = app;
	}
	public void Save(Json_nde args) {Save(args.Get_as_int("id"), args.Get_as_str("domain"), args.Get_as_str("name"), args.Get_as_str("dir"), args.Get_as_str("mainpage"));}
	public void Save(int id, String domain, String name, String dir_str, String mainpage_name) {
		boolean itm_is_new = false;
		// get next id if none provided
		if (id == -1) {
			itm_is_new = true;
			Db_sys_mgr sys_mgr = new Db_sys_mgr(app.User().User_db_mgr().Conn());
			id = sys_mgr.Autonum_next("user.wikis.id");
		}

		Xowdir_db_mgr db_mgr = new Xowdir_db_mgr(app.User().User_db_mgr().Conn());
		Io_url dir_url = Io_url_.new_dir_infer(dir_str);

		// validate
		String err_msg = Validate(app, db_mgr, domain, name, dir_url, mainpage_name);
		if (err_msg != null) {
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.wiki_directory.notify__recv", gplx.core.gfobjs.Gfobj_nde.New().Add_str("msg_text", err_msg));
			return;
		}

		// upsert into user_db.wiki_list
		Io_url fil_url = dir_url.GenSubFil(domain + ".xowa");
		Xowdir_wiki_json json = Xowdir_wiki_json.New_dflt();
		json.Name_(name);
		db_mgr.Tbl__wiki().Upsert(id, domain, fil_url, json.To_str(json_wtr));

		if (itm_is_new) {
			// create the actual wiki
			byte[] mainpage_text = Io_mgr.Instance.LoadFilBryOr(Xowdir_item_html.Addon_dir(app).GenSubFil_nest("res", "page", "Main_Page.txt"), Bry_.Empty);
			Xow_db_mkr.Create_wiki(new Xodb_wiki_data(domain, fil_url), Bry_.new_u8(mainpage_name), mainpage_text);

			// load it
			Xow_wiki_factory.Load_personal((Xoae_app)app, Bry_.new_u8(domain), dir_url);

			// navigate to it
			app.Gui__cbk_mgr().Send_redirect(cbk_trg, "/site/" + domain + "/wiki/" + mainpage_name);
		}
		else {
			// navigate back to wiki_directory
			app.Gui__cbk_mgr().Send_redirect(cbk_trg, "/site/home/wiki/Special:XowaWikiDirectory");
		}
	}
	public void Delete(Json_nde args) {Delete(args.Get_as_int("id"));}
	public void Delete(int id) {
		// get item by id
		Xowdir_db_mgr db_mgr = new Xowdir_db_mgr(app.User().User_db_mgr().Conn());
		Xowdir_wiki_itm itm = db_mgr.Tbl__wiki().Select_by_id_or_null(id);
		if (itm == null) throw Err_.new_wo_type("wiki does not exist", "id", id);

		// delete it
		db_mgr.Tbl__wiki().Delete_by_id(id);

		// navigate back to wiki_directory
		app.Gui__cbk_mgr().Send_redirect(cbk_trg, "/site/home/wiki/Special:XowaWikiDirectory");
	}
	private String Validate(Xoa_app app, Xowdir_db_mgr db_mgr, String domain, String name, Io_url dir_url, String mainpage_name) {
		// domain
		if (String_.Len_eq_0(domain))
			return "Domain cannot be empty: " + domain;
		if (db_mgr.Tbl__wiki().Select_by_key_or_null(domain) != null)
			return "Domain already exists: " + domain;
		if (String_.Len(domain) > 63)
			return "Domain must be 63 characters or less: " + domain;
		if (!Is_valid_domain_name(Bry_.new_u8(domain)))
			return "Domain is invalid; can only have letters, numbers, or a dot. If a dash exists, it cannot be at the start or the end: " + domain ;

		// name
		if (String_.Len_eq_0(name))
			return "Name cannot be empty: " + name;
		if (String_.Len(name) > 255)
			return "Name must: be 255 characters or less: " + name;

		// dir
		String dir_str = dir_url.Raw();
		if (String_.Len_eq_0(dir_str))
			return "Folder cannot be empty: " + dir_str;
		Io_mgr.Instance.CreateDirIfAbsent(dir_url);
		if (!Io_mgr.Instance.ExistsDir(dir_url))
			return "Folder could not be created: " + dir_str;

		// mainpage_name
		byte[] mainpage_name_bry = Bry_.new_u8(mainpage_name);
		Xoa_ttl ttl = Xoa_ttl.Parse(app.User().Wikii(), mainpage_name_bry);
		if (ttl == null)
			return "Main Page has invalid characters. Please see the new wiki help page for more info: " + mainpage_name;

		Bry_bfr tmp = Bry_bfr_.New();
		byte[] ucase_1st = app.User().Wikii().Lang().Case_mgr().Case_build_1st_upper(tmp, mainpage_name_bry, 0, mainpage_name_bry.length);
		if (!Bry_.Eq(mainpage_name_bry, ucase_1st))
			return "Main Page must start with an uppercase letter.";

		// valid returns null
		return null;
	}
	private static boolean Is_valid_domain_name(byte[] src) {
		int len = src.length;
		if (len > 63) return false;
		for (int i = 0; i < len; i++) {
			byte b = src[i];
			// alpha-num is valid
			if (Byte_ascii.Is_ltr(b) || Byte_ascii.Is_num(b))
				continue;

			// hyphens are only valid at start or end
			if (b == Byte_ascii.Dash) {
				if (i != 0 || i != len - 1)
					continue;
			}

			// allow dots; EX: en.wikipedia.org
			if (b == Byte_ascii.Dot)
				continue;

			// else, invalid
			return false;
		}
		return true;
	}
}
