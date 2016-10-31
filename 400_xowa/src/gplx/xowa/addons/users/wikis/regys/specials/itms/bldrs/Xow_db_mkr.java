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
package gplx.xowa.addons.users.wikis.regys.specials.itms.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*; import gplx.xowa.addons.users.wikis.regys.specials.*; import gplx.xowa.addons.users.wikis.regys.specials.itms.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.sys.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.core.ios.streams.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.site_stats.*;
import gplx.xowa.langs.cases.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.fsdb.data.*; import gplx.xowa.files.origs.*;
import gplx.xowa.addons.users.wikis.regys.dbs.*;
public class Xow_db_mkr {
	public static Xodb_wiki_mgr Create_wiki2(Xodb_wiki_data data) {
		// create db
		Xodb_wiki_mgr wiki_mgr = new Xodb_wiki_mgr(data.Domain());
		wiki_mgr.Dbs__add(Xodb_wiki_db.Make(Xodb_wiki_db_tid.Tid__core, data.Core_url()));

		// create tbls: wiki
		Xodb_wiki_db core_db = wiki_mgr.Dbs__get_core();
		Db_conn core_conn = core_db.Conn();
		core_db.Tbls__add(Bool_.Y
		, Xowd_cfg_tbl_.New(core_conn)
		, new Xowd_xowa_db_tbl(core_conn, Bool_.N)
		, new Xowd_site_ns_tbl(core_conn, Bool_.N)
		, new Xow_site_stats_tbl(core_conn, Bool_.N)
		, new Xowd_page_tbl(core_conn, Bool_.N)
		, new Xowd_text_tbl(core_conn, Bool_.N, data.Text_zip_tid())
		//	if (props.Layout_text().Tid_is_all_or_few()) {	// create in advance else will fail for v2; import wiki -> wiki loads and tries to load categories; v2 category processes and builds tbl; DATE:2015-03-22
		, new Xowd_cat_core_tbl(core_conn, Bool_.N)
		, new Xowd_cat_link_tbl(core_conn, Bool_.N)
		);

		// insert data: wiki
		Xowd_site_ns_tbl.Get_by_key(core_db).Insert(Xow_ns_mgr_.default_(Xol_case_mgr_.U8()));
		Xow_site_stats_tbl.Get_by_key(core_db).Update(0, 0, 0);
		Db_cfg_tbl cfg_tbl = Db_cfg_tbl.Get_by_key(core_db, Xowd_cfg_tbl_.Tbl_name);
		cfg_tbl.Insert_str(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__main_page, "Main_Page");
		cfg_tbl.Insert_str(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__modified_latest, Datetime_now.Get().XtoStr_fmt(DateAdp_.Fmt_iso8561_date_time));
		Xopg_db_mgr.Create(core_db, Xow_ns_.Tid__main, Bry_.new_a7("Main_Page"), Bry_.new_a7("Main page created"));
		Xowd_core_db_props props = new Xowd_core_db_props(2, Xow_db_layout.Itm_all, Xow_db_layout.Itm_all, Xow_db_layout.Itm_all, Io_stream_tid_.Tid__raw, Io_stream_tid_.Tid__raw, Bool_.N, Bool_.N);
		props.Cfg_save(cfg_tbl);
		Xowd_xowa_db_tbl.Get_by_key(core_db).Upsert(0, Xow_db_file_.Tid__core, core_db.Url().NameAndExt(), "", -1, Guid_adp_.New_str());

		// create tbls: fsdb
		core_db.Tbls__add(Bool_.Y
		, new Fsm_mnt_tbl(core_conn, Bool_.N)
		, new Fsm_atr_tbl(core_conn, Bool_.N)
		, new Fsm_bin_tbl(core_conn, Bool_.N, Fsm_mnt_mgr.Mnt_idx_main)
		, new Fsd_dir_tbl(core_conn, Bool_.N)
		, new Fsd_fil_tbl(core_conn, Bool_.N, Fsm_mnt_mgr.Mnt_idx_main)
		, new Fsd_thm_tbl(core_conn, Bool_.N, Fsm_mnt_mgr.Mnt_idx_main, Bool_.Y)
		, new Xof_orig_tbl(core_conn, Bool_.N)
		);

		// insert data: fsdb
		Fsm_mnt_mgr.Patch_core(cfg_tbl);
		Fsm_atr_tbl.Get_by_key(core_db).Insert(Fsm_mnt_mgr.Mnt_idx_main, core_db.Url().NameAndExt());
		cfg_tbl.Insert_int("core", "mnt.insert_idx", Fsm_mnt_mgr.Mnt_idx_user);

		return wiki_mgr;
	}
	public static void Create_wiki(Xoa_app app, String domain, String url_str) {
		// create wiki
		Io_url url = Io_url_.new_any_(url_str);
		Xow_ns_mgr ns_mgr = Xow_ns_mgr_.default_(Xol_case_mgr_.U8());
		Xow_wiki_.Create(app.User().Key(), ns_mgr, domain, url.OwnerDir());

		// more create; need wiki-reference to create fsdb and create page
		Xowe_wiki wiki = new Xowe_wiki((Xoae_app)app, app.Lang_mgr().Get_by_or_en(Bry_.Empty), ns_mgr, Xow_domain_itm_.parse(Bry_.new_u8(domain)), url.OwnerDir());
		gplx.fsdb.Fsdb_db_mgr__v2_bldr.Make_core_file_main(wiki, url, domain, gplx.xowa.wikis.data.Xow_db_layout.Itm_all);
		wiki.Init_db_mgr();
		wiki.Init_by_wiki__force_and_mark_inited();
		wiki.Data__core_mgr().Dbs__make_by_id(1, Xow_db_file_.Tid__core, "", 0, ".xowa");
		wiki.Data__core_mgr().Db__core().Tbl__db().Commit_all(wiki.Data__core_mgr());

		// create default Main_Page
		new Xopg_db_mgr(wiki).Create(Xow_ns_.Tid__main, Bry_.new_a7("Main_Page"), Bry_.new_a7("Main page created"));
	}
}
/*
xowa_cfg
xowa_db
site_ns
site_stats

page
text

cat_core
cat_link

fsdb_mnt
fsdb_dba
fsdb_dbb
fsdb_dir
fsdb_fil
fsdb_thm
orig_reg

search_link
search_link_reg
search_word

css_core
css_file
*/
