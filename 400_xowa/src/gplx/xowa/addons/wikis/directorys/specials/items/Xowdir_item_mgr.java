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
			id = Xowdir_db_utl.Wiki_id__next(app);
		}

		Xowdir_db_mgr db_mgr = new Xowdir_db_mgr(app.User().User_db_mgr().Conn());
		Io_url dir_url = Io_url_.new_dir_infer(dir_str);
		Xowdir_wiki_itm old_item = db_mgr.Tbl__wiki().Select_by_key_or_null(domain);

		// validate
		mainpage_name = String_.Replace(mainpage_name, " ", "_");
		String err_msg = Validate(app, db_mgr, itm_is_new, domain, name, dir_url, mainpage_name);
		if (err_msg != null) {
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.wiki_directory.notify__recv", gplx.core.gfobjs.Gfobj_nde.New().Add_str("msg_text", err_msg));
			return;
		}

		// upsert into user_db.wiki_list
		Io_url fil_url = dir_url.GenSubFil(domain + ".xowa");
		Xowdir_wiki_json json = Xowdir_wiki_json.New_empty();
		json.Name_(name);
		json.Main_page_(mainpage_name);
		String wiki_json = json.To_str(json_wtr);
		db_mgr.Tbl__wiki().Upsert(id, domain, fil_url, wiki_json);

		if (itm_is_new) {
			// create the actual wiki
			byte[] mainpage_text = Io_mgr.Instance.LoadFilBryOr(Xowdir_item_html.Addon_dir(app).GenSubFil_nest("res", "page", "Main_Page.txt"), Bry_.Empty);
			Xow_db_mkr.Create_wiki(new Xodb_wiki_data(domain, fil_url), name, Bry_.new_u8(mainpage_name), mainpage_text);

			// load it
			Xow_wiki_factory.Load_personal((Xoae_app)app, Bry_.new_u8(domain), dir_url);

			// navigate to it
			app.Gui__cbk_mgr().Send_redirect(cbk_trg, "/site/" + domain + "/wiki/" + mainpage_name);
		}
		else {
			if (old_item != null) {
				// try to move file into new dir
				// COMMENTED: need to be able to close all connections else move will fail
				//Io_url old_fil = old_item.Url();
				//String old_dir = old_fil.OwnerDir().Raw();
				//if (!String_.Eq(old_dir, dir_str)) {
				//	Io_url new_fil = dir_url.GenSubFil(old_fil.NameAndExt());
				//	Io_mgr.Instance.MoveFil(old_fil, new_fil);
				//}
			}

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
	public void Reindex_search(Json_nde args) {
		String domain = args.Get_as_str("domain");
		Xowe_wiki wiki = (Xowe_wiki)app.Wiki_mgri().Get_by_or_null(Bry_.new_u8(domain));

		// update page count; needed else search cannot generate correct ranges when normalizing search_scores
		int page_count = wiki.Data__core_mgr().Tbl__page().Select_count_all();
		if (page_count == -1) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "negative page count while reindexing search; domain=~{0}", domain);
		}
		wiki.Data__core_mgr().Db__core().Tbl__site_stats().Update(page_count, page_count, 0);
		wiki.Data__core_mgr().Db__core().Tbl__site_stats().Select(wiki.Stats());

		// run reindexer
		gplx.xowa.addons.wikis.searchs.bldrs.Srch_bldr_mgr_.Setup(wiki);
		app.Bldr().Run();

		// clear cache
		gplx.xowa.addons.wikis.searchs.Srch_search_addon.Get(wiki).Clear_rslts_cache();;

		// send notify
		app.Gui__cbk_mgr().Send_notify(cbk_trg, "search reindex done");
	}
	private String Validate(Xoa_app app, Xowdir_db_mgr db_mgr, boolean itm_is_new, String domain, String name, Io_url dir_url, String mainpage_name) {
		// domain
		if (itm_is_new) {
			if (String_.Len_eq_0(domain))
				return "Domain cannot be empty: " + domain;
			if (db_mgr.Tbl__wiki().Select_by_key_or_null(domain) != null)
				return "Domain already exists: " + domain;
			if (String_.Len(domain) > 63)
				return "Domain must be 63 characters or less: " + domain;
			if (!Is_valid_domain_name(Bry_.new_u8(domain)))
				return "Domain is invalid; can only have letters, numbers, or a dot. If a dash exists, it cannot be at the start or the end: " + domain;
		}

		// name
		if (String_.Len_eq_0(name))
			return "Name cannot be empty: " + name;
		if (String_.Len(name) > 255)
			return "Name must: be 255 characters or less: " + name;

		// dir
		String dir_str = dir_url.Raw();
		if (String_.Len_eq_0(dir_str))
			return "Folder cannot be empty: " + dir_str;

		// try to create fil
		Io_mgr.Instance.CreateDirIfAbsent(dir_url);
		Io_url tmp_fil = dir_url.GenSubFil("xowa.temp.txt");
		Io_mgr.Instance.SaveFilStr(tmp_fil, "temp");
		if (!String_.Eq(Io_mgr.Instance.LoadFilStr(tmp_fil), "temp"))
			return "Folder could not be created: " + dir_str;
		Io_mgr.Instance.DeleteFil(tmp_fil);

		// mainpage_name
		if (itm_is_new) {
			byte[] mainpage_name_bry = Bry_.new_u8(mainpage_name);
			Xoa_ttl ttl = Xoa_ttl.Parse(app.User().Wikii(), mainpage_name_bry);
			if (ttl == null)
				return "Main Page has invalid characters. Please see the new wiki help page for more info: " + mainpage_name;

			Bry_bfr tmp = Bry_bfr_.New();
			byte[] ucase_1st = app.User().Wikii().Lang().Case_mgr().Case_build_1st_upper(tmp, mainpage_name_bry, 0, mainpage_name_bry.length);
			if (!Bry_.Eq(mainpage_name_bry, ucase_1st))
				return "Main Page must start with an uppercase letter.";
		}

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
