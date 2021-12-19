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
package gplx.xowa.addons.wikis.directorys.specials.items.bldrs;
import gplx.types.commons.GfoGuidUtl;
import gplx.core.ios.streams.Io_stream_tid_;
import gplx.dbs.Db_conn;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.cfgs.Db_cfg_tbl;
import gplx.fsdb.data.Fsd_dir_tbl;
import gplx.fsdb.data.Fsd_fil_tbl;
import gplx.fsdb.data.Fsd_thm_tbl;
import gplx.fsdb.meta.Fsm_atr_tbl;
import gplx.fsdb.meta.Fsm_bin_tbl;
import gplx.fsdb.meta.Fsm_mnt_mgr;
import gplx.fsdb.meta.Fsm_mnt_tbl;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.addons.wikis.ctgs.dbs.Xodb_cat_link_tbl;
import gplx.xowa.addons.wikis.searchs.dbs.Srch_db_cfg_;
import gplx.xowa.addons.wikis.searchs.dbs.Srch_db_mgr;
import gplx.xowa.addons.wikis.searchs.dbs.Srch_db_upgrade;
import gplx.xowa.addons.wikis.searchs.dbs.Srch_link_tbl;
import gplx.xowa.addons.wikis.searchs.dbs.Srch_word_tbl;
import gplx.xowa.files.origs.Xof_orig_tbl;
import gplx.xowa.langs.cases.Xol_case_mgr_;
import gplx.xowa.wikis.data.Xow_db_file_;
import gplx.xowa.wikis.data.Xow_db_layout;
import gplx.xowa.wikis.data.Xowd_cfg_tbl_;
import gplx.xowa.wikis.data.Xowd_core_db_props;
import gplx.xowa.wikis.data.site_stats.Xowd_site_stats_tbl;
import gplx.xowa.wikis.data.tbls.Xowd_cat_core_tbl;
import gplx.xowa.wikis.data.tbls.Xowd_page_tbl;
import gplx.xowa.wikis.data.tbls.Xowd_site_ns_tbl;
import gplx.xowa.wikis.data.tbls.Xowd_text_tbl;
import gplx.xowa.wikis.data.tbls.Xowd_xowa_db_tbl;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.wikis.nss.Xow_ns_mgr_;
public class Xow_db_mkr {
	public static Xodb_wiki_mgr Create_wiki(Xodb_wiki_data data, String wiki_name, byte[] mainpage_name, byte[] mainpage_text) {
		// create db
		Xodb_wiki_mgr wiki_mgr = new Xodb_wiki_mgr(data.Domain());
		wiki_mgr.Dbs__add(Xodb_wiki_db.Make(Xodb_wiki_db_tid.Tid__core, data.Core_url()));

		// create tbls: wiki
		Xodb_wiki_db core_db = wiki_mgr.Dbs__get_core();
		Db_conn core_conn = core_db.Conn();
		core_db.Tbls__add(BoolUtl.Y
		, Xowd_cfg_tbl_.New(core_conn)
		, new Xowd_xowa_db_tbl(core_conn, BoolUtl.N)
		, new Xowd_site_ns_tbl(core_conn, BoolUtl.N)
		, new Xowd_site_stats_tbl(core_conn, BoolUtl.N)
		, new Xowd_page_tbl(core_conn, BoolUtl.N)
		, new Xowd_text_tbl(core_conn, BoolUtl.N, data.Text_zip_tid())
		);

		// upgrade tbl: page for categories; NOTE: should change page_tbl to do this automatically
		core_conn.Meta_fld_append(Xowd_page_tbl.TBL_NAME, DbmetaFldItm.NewInt(Xowd_page_tbl.FLD__page_cat_db_id).DefaultValSet(-1));

		// create tbls: cat; may want to do "if (props.Layout_text().Tid_is_all_or_few())"	// create in advance else will fail for v2; import wiki -> wiki loads and tries to load categories; v2 category processes and builds tbl; DATE:2015-03-22
		core_db.Tbls__add(BoolUtl.Y
		, new Xowd_cat_core_tbl(core_conn, BoolUtl.N)
		, new Xodb_cat_link_tbl(core_conn)
		);

		// create tbls; search_word
		Srch_word_tbl search_word_tbl = new Srch_word_tbl(core_conn);
		search_word_tbl.Create_tbl(); search_word_tbl.Create_idx();

		// create tbls; search_link
		Srch_link_tbl[] search_link_tbls = new Srch_link_tbl[1];
		Srch_db_mgr.Tbl__link__ary__set(search_link_tbls, 0, core_conn);
		for (Srch_link_tbl link : search_link_tbls) {
			link.Create_tbl();
			link.Create_idx__link_score();
			link.Create_idx__page_id();
		}

		// insert cfg; search version
		Db_cfg_tbl cfg_tbl = Db_cfg_tbl.Get_by_key(core_db, Xowd_cfg_tbl_.Tbl_name);
		cfg_tbl.Upsert_int(Srch_db_cfg_.Grp__search__cfg, Srch_db_cfg_.Key__version_id, Srch_db_upgrade.Version__link_score);

		// insert data: wiki
		Xowd_xowa_db_tbl.Get_by_key(core_db).Upsert(0, Xow_db_file_.Tid__core, core_db.Url().NameAndExt(), "", -1, GfoGuidUtl.NewAsstr());
		Xowd_site_ns_tbl.Get_by_key(core_db).Insert(Xow_ns_mgr_.default_(Xol_case_mgr_.U8()));
		Xowd_site_stats_tbl.Get_by_key(core_db).Update(0, 0, 0);

		// insert data: cfg
		Xowd_core_db_props props = new Xowd_core_db_props(2, Xow_db_layout.Itm_all, Xow_db_layout.Itm_all, Xow_db_layout.Itm_all, Io_stream_tid_.Tid__raw, Io_stream_tid_.Tid__raw, BoolUtl.N, BoolUtl.N);
		props.Cfg_save(cfg_tbl);

		Xowd_cfg_tbl_.Upsert__create(cfg_tbl, data.Domain(), wiki_name, mainpage_name);

		// insert data: page
		Xopg_db_mgr.Create
			( Xowd_page_tbl.Get_by_key(core_db)
			, Xowd_text_tbl.Get_by_key(core_db), Xow_db_file_.Uid__core
			, Xowd_site_ns_tbl.Get_by_key(core_db)
			, Db_cfg_tbl.Get_by_key(core_db, Xowd_cfg_tbl_.Tbl_name)
			, Xow_ns_.Tid__main, mainpage_name, mainpage_text
			, -1);

		// create tbls: fsdb
		core_db.Tbls__add(BoolUtl.Y
		, new Fsm_mnt_tbl(core_conn, BoolUtl.N)
		, new Fsm_atr_tbl(core_conn, BoolUtl.N)
		, new Fsm_bin_tbl(core_conn, BoolUtl.N, Fsm_mnt_mgr.Mnt_idx_main)
		, new Fsd_dir_tbl(core_conn, BoolUtl.N)
		, new Fsd_fil_tbl(core_conn, BoolUtl.N, Fsm_mnt_mgr.Mnt_idx_main)
		, new Fsd_thm_tbl(core_conn, BoolUtl.N, Fsm_mnt_mgr.Mnt_idx_main, BoolUtl.Y)
		, new Xof_orig_tbl(core_conn, BoolUtl.N)
		);

		// insert data: fsdb
		Fsm_mnt_mgr.Patch_core(cfg_tbl);
		Fsm_atr_tbl.Get_by_key(core_db).Insert(Fsm_mnt_mgr.Mnt_idx_main, core_db.Url().NameAndExt());
		cfg_tbl.Insert_int("core", "mnt.insert_idx", Fsm_mnt_mgr.Mnt_idx_user);

		return wiki_mgr;
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
