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
package gplx.xowa.addons.bldrs.exports.packs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
import gplx.core.ios.*;
import gplx.fsdb.meta.*;
import gplx.xowa.wikis.data.*;
import gplx.xowa.addons.bldrs.centrals.dbs.datas.imports.*;
class Pack_hash_bldr {
	public static Pack_hash Bld(Xow_wiki wiki, Io_url wiki_dir, Io_url pack_dir, String wiki_date, Pack_file_cfg cfg) {
		// boolean pack_text, boolean pack_html, boolean pack_file, DateAdp pack_file_cutoff, boolean pack_fsdb_delete
		Pack_hash rv = new Pack_hash();
		Pack_zip_name_bldr zip_name_bldr = new Pack_zip_name_bldr(pack_dir, wiki.Domain_str(), String_.new_a7(wiki.Domain_itm().Abrv_wm()), wiki_date, cfg.Pack_custom_name());
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();

		// bld custom_files
		if (cfg.Pack_custom())
			return Bld_custom_files(rv, wiki, wiki_dir, zip_name_bldr, cfg.Pack_custom_files());

		// bld html pack
		if (cfg.Pack_html()) {
			int len = db_mgr.Dbs__len();
			for (int i = 0; i < len; ++i) {
				Xow_db_file file = db_mgr.Dbs__get_at(i);
				int pack_tid = Get_pack_tid(file.Tid());
				if (Int_.In(pack_tid, Xobc_import_type.Tid__ignore, Xobc_import_type.Tid__wiki__text)) continue;
				rv.Add(zip_name_bldr, pack_tid, file.Url());
			}
			rv.Consolidate(Xobc_import_type.Tid__wiki__srch);
		}

		// bld text pack
		if (cfg.Pack_text()) {
			int len = db_mgr.Dbs__len();
			for (int i = 0; i < len; ++i) {
				Xow_db_file file = db_mgr.Dbs__get_at(i);
				int pack_tid = Get_pack_tid(file.Tid());
				if (pack_tid == Xobc_import_type.Tid__ignore) continue; // NOTE: will try to add same urls as html, but noops b/c rv.Add checks for unique urls; DATE:2017-04-09
				rv.Add(zip_name_bldr, pack_tid, file.Url());
			}
		}

		// bld lucene pack
		if (cfg.Pack_lucene()) {
			Pack_lucene(rv, wiki, zip_name_bldr, cfg);
		}

		// bld file pack
		if (cfg.Pack_file()) {
			Fsm_mnt_itm mnt_itm = wiki.File__mnt_mgr().Mnts__get_at(Fsm_mnt_mgr.Mnt_idx_main);
			rv.Add(zip_name_bldr, Xobc_import_type.Tid__file__core, wiki_dir.GenSubFil(mnt_itm.Atr_mgr().Db__core().Url_rel()));
			if (db_mgr.Props().Layout_file().Tid_is_lot()) {
				Fsm_bin_mgr bin_mgr = mnt_itm.Bin_mgr();
				int bin_len = bin_mgr.Dbs__len();
				for (int i = 0; i < bin_len; ++i) {
					Fsm_bin_fil bin_fil = bin_mgr.Dbs__get_at(i);
					Io_url bin_fil_url = bin_fil.Url();

					// ignore if bin_fil is earlier than cutoff
					if (cfg.Pack_file_cutoff() != null) {
						DateAdp bin_fil_date = Io_mgr.Instance.QueryFil(bin_fil_url).ModifiedTime();
						if (bin_fil_date.Timestamp_unix() < cfg.Pack_file_cutoff().Timestamp_unix()) continue;
					}
					rv.Add(zip_name_bldr, Xobc_import_type.Tid__file__data, bin_fil_url);
				}
			}
		}

		// bld pack_fsdb_delete
		if (cfg.Pack_fsdb_delete()) {
			gplx.xowa.bldrs.Xob_db_file fsdb_deletion_db = gplx.xowa.bldrs.Xob_db_file.New__deletion_db(wiki);
			if (!Io_mgr.Instance.ExistsFil(fsdb_deletion_db.Url())) throw Err_.new_wo_type("deletion db does not exists: url=" + fsdb_deletion_db.Url().Raw());
			rv.Add(zip_name_bldr, Xobc_import_type.Tid__fsdb__delete, fsdb_deletion_db.Url());
		}
		return rv;
	}
	private static void Pack_lucene(Pack_hash rv, Xow_wiki wiki, Pack_zip_name_bldr zip_name_bldr, Pack_file_cfg cfg) {
		// read files from lucene_dir
		Io_url lucene_dir = gplx.xowa.addons.wikis.fulltexts.Xosearch_fulltext_addon.Get_index_dir(wiki);
		IoItmHash fils = Io_mgr.Instance.QueryDir_args(lucene_dir).ExecAsItmHash();

		// init vars
		int pack_num = 0;
		long size_cur = 0;
		long size_max = cfg.Lucene_max();
		List_adp url_list = List_adp_.New();
		int fil_idx = 0;
		int fils_len = fils.Len();

		// loop over each file
		while (fil_idx < fils_len) {
			IoItmFil fil = (IoItmFil)fils.Get_at(fil_idx);

			// calc size_new
			long size_new = size_cur + fil.Size();

			// if last file, set size_new to max and add file
			boolean add_file = true;
			if (fil_idx == fils_len - 1) {
				size_new = size_max;
				url_list.Add(fil.Url());
				add_file = false;
			}

			// size exceeded; make new pack
			if (size_new >= size_max) {
				rv.Add(Xobc_import_type.Tid__wiki__lucene, zip_name_bldr.Bld_by_suffix("xtn.fulltext_search", pack_num), (Io_url[])url_list.To_ary_and_clear(Io_url.class));
				pack_num++;
				size_cur = 0;
			}
			// size too small; just update
			else {
				size_cur = size_new;
			}

			// add file to list; ignore if last file and added above
			if (add_file)
				url_list.Add(fil.Url());
			fil_idx++;
		}
	}
	private static Pack_hash Bld_custom_files(Pack_hash rv, Xow_wiki wiki, Io_url wiki_dir, Pack_zip_name_bldr zip_name_bldr, String custom_files_blob) {
		String[] custom_files = String_.Split(custom_files_blob, "|");
		int len = custom_files.length;
		for (int i = 0; i < len; ++i) {
			Io_url file_url = wiki_dir.GenSubFil(custom_files[i]);
			rv.Add(zip_name_bldr, Xobc_import_type.Tid__misc, file_url);
		}
		return rv;
	}
	private static int Get_pack_tid(byte db_file_tid) {
		switch (db_file_tid) {
			case Xow_db_file_.Tid__core:		return Xobc_import_type.Tid__wiki__core;
			case Xow_db_file_.Tid__search_core:	
			case Xow_db_file_.Tid__search_link:	return Xobc_import_type.Tid__wiki__srch;
			case Xow_db_file_.Tid__html_solo:
			case Xow_db_file_.Tid__html_data:	return Xobc_import_type.Tid__wiki__html;
			case Xow_db_file_.Tid__cat:
			case Xow_db_file_.Tid__cat_core:
			case Xow_db_file_.Tid__cat_link:	return Xobc_import_type.Tid__wiki__ctg;
			case Xow_db_file_.Tid__file_core:	return Xobc_import_type.Tid__file__core;
			case Xow_db_file_.Tid__file_solo:
			case Xow_db_file_.Tid__file_data:	return Xobc_import_type.Tid__file__data;
			case Xow_db_file_.Tid__text:		return Xobc_import_type.Tid__wiki__text;
			case Xow_db_file_.Tid__wbase:		return Xobc_import_type.Tid__wiki__wbase;
			default:							return Xobc_import_type.Tid__ignore;
		}
	}
}
