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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.bulks.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.bldrs.infos.*;
import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.dbs.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*;
class Merge_wkr__core {
	private final    Db_attach_mgr attach_mgr = new Db_attach_mgr();
	private final    Db_tbl_copy copy_mgr = new Db_tbl_copy();
	public void Copy_to_temp(Merge_prog_wkr prog_wkr, Xow_wiki wiki, Db_conn src_conn) {
		Create_dbs(wiki, src_conn);
		Merge_core(wiki, src_conn);
		Merge_srch(wiki, src_conn);
		Merge_file(wiki, src_conn);
	}
	private void Create_dbs(Xow_wiki wiki, Db_conn src_conn) {
		Db_cfg_tbl cfg_tbl = Xowd_cfg_tbl_.New(src_conn, "xowa_cfg__core");
		Xowd_core_db_props core_db_props = Xowd_core_db_props.Cfg_load(src_conn, cfg_tbl);
		Xob_info_session session = Xob_info_session.Load(cfg_tbl);

		Xow_wiki_.Create_sql_backend(wiki, core_db_props, session);
	}
	private void Merge_core(Xow_wiki wiki, Db_conn src_conn) {
		Db_conn trg_conn = wiki.Data__core_mgr().Db__core().Conn();
		copy_mgr.Copy_many(src_conn, trg_conn, "site_ns", "css_core", "css_file"); // NOTE: "xowa_db" skipped; NOTE: css_core must go before Init_by_wiki
		copy_mgr.Copy_one(src_conn, trg_conn, "xowa_cfg__core", "xowa_cfg");
		wiki.Init_by_wiki();

		// make text_db b/c page entries have Load_page_wkr will try to load from text
		Xow_db_file text_db = null;
		if (wiki.Data__core_mgr().Props().Layout_text().Tid_is_lot()) {
			text_db = wiki.Data__core_mgr().Dbs__get_by_tid_or_null(Xow_db_file_.Tid__text);
			if (text_db == null)
				text_db = wiki.Data__core_mgr().Dbs__make_by_tid(Xow_db_file_.Tid__text);
		}
		else
			text_db = wiki.Data__core_mgr().Db__core();
		text_db.Tbl__text().Create_tbl();

		// merge at end for site_stats
		copy_mgr.Copy_many(src_conn, trg_conn, "site_stats");
	}
	private void Merge_srch(Xow_wiki wiki, Db_conn src_conn) {
		Srch_search_addon addon = Srch_search_addon.Get(wiki);

		Srch_db_mgr srch_db_mgr = addon.Db_mgr();
		srch_db_mgr.Create_all();
		srch_db_mgr.Tbl__word().Create_idx();
		int len = srch_db_mgr.Tbl__link__len();
		for (int i = 0; i < len; ++i)
			srch_db_mgr.Tbl__link__get_at(i).Create_idx__link_score();			

		Db_conn trg_conn = addon.Db_mgr().Tbl__word().conn;
		// copy_mgr.Copy_many(src_conn, trg_conn, "search_link_reg");
		Merge_cfg(src_conn, trg_conn, "xowa_cfg__srch");
	}
	private void Merge_file(Xow_wiki wiki, Db_conn src_conn) {
		if (wiki.Type_is_edit())
			((Xowe_wiki)wiki).File_mgr().Init_file_mgr_by_load(wiki);
		else
			wiki.File__mnt_mgr().Ctor_by_load(wiki.File__fsdb_core());
		Db_conn trg_conn = wiki.File__fsdb_core().File__atr_file__at(Fsm_mnt_mgr.Mnt_idx_main).Conn();
		copy_mgr.Copy_many(src_conn, trg_conn, "fsdb_dba", "fsdb_dbb", "fsdb_dir", "fsdb_mnt");
		Merge_cfg(src_conn, trg_conn, "xowa_cfg__file");
	}
	private void Merge_cfg(Db_conn src_conn, Db_conn trg_conn, String src_tbl_name) {
		if (trg_conn.Meta_tbl_exists("xowa_cfg")) {
			attach_mgr.Conn_main_(trg_conn).Conn_links_(new Db_attach_itm("src_db", src_conn));
			attach_mgr.Exec_sql(String_.Concat_lines_nl
			( "INSERT  INTO xowa_cfg"
			, "SELECT  s.cfg_grp, s.cfg_key, s.cfg_val"
			, "FROM    <src_db>" + src_tbl_name + " s"
			, "        LEFT JOIN xowa_cfg t ON s.cfg_grp = t.cfg_grp AND s.cfg_key = t.cfg_key"
			, "WHERE   t.cfg_grp IS NULL"
			));
		}
		else
			copy_mgr.Copy_one (src_conn, trg_conn, src_tbl_name, "xowa_cfg");
	}
}
