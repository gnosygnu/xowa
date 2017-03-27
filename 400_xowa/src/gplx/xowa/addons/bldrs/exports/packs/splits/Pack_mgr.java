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
package gplx.xowa.addons.bldrs.exports.packs.splits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
import gplx.core.progs.*; import gplx.core.ios.zips.*; import gplx.core.ios.streams.*; import gplx.core.security.*;
import gplx.dbs.*;
import gplx.xowa.wikis.data.*;
import gplx.xowa.addons.bldrs.centrals.dbs.*; import gplx.xowa.addons.bldrs.centrals.dbs.datas.imports.*; import gplx.xowa.addons.bldrs.centrals.steps.*;
import gplx.xowa.addons.bldrs.exports.splits.mgrs.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
// NOTE: used for experimental pack / split approach (html,file,search in one db)
class Pack_mgr {
	public void Exec(Xowe_wiki wiki, long pack_size_max) {
		// init
		Io_url wiki_dir = wiki.Fsys_mgr().Root_dir();
		wiki.Init_assert();
		String wiki_date = wiki.Props().Modified_latest().XtoStr_fmt("yyyyMMdd");

		// build pack list
		Pack_list pack_list = Make_list(wiki, wiki_date, pack_size_max);

		// get import_tbl
		byte[] wiki_abrv = wiki.Domain_itm().Abrv_xo();
		Xobc_data_db bc_db = Xobc_data_db.New(wiki.App().Fsys_mgr());
		Db_conn bc_conn = bc_db.Conn();
		bc_db.Delete_by_import(wiki_abrv, wiki_date);
		bc_conn.Txn_bgn("xobc_import_insert");

		// loop packs and (a) zip raws; (b) create entry
		Hash_algo hash_algo = Hash_algo_.New__md5();
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		int len = pack_list.Len();
		int task_id = bc_conn.Sys_mgr().Autonum_next("task_regy.task_id");
		bc_db.Tbl__task_regy().Insert(task_id, task_id, len, wiki.Domain_str(), wiki.Domain_str());
		for (int i = 0; i < len; ++i)
			Make_pack(wiki, wiki_dir, wiki_abrv, wiki_date, bc_db, hash_algo, tmp_bfr, pack_list.Get_at(i), task_id);
		bc_conn.Txn_end();
	}
	private static Pack_list Make_list(Xow_wiki wiki, String wiki_date, long pack_size_max) {
		// init; delete dir;
		String wiki_abrv = String_.new_a7(wiki.Domain_itm().Abrv_wm());
		Io_url wiki_dir = wiki.Fsys_mgr().Root_dir();
		Io_url pack_root  = wiki_dir.GenSubDir_nest("tmp", "pack");
		Io_url split_root = wiki_dir.GenSubDir_nest("tmp", "split");
		Io_mgr.Instance.DeleteDirDeep(pack_root);
		Io_mgr.Instance.CreateDirIfAbsent(pack_root);
		Io_url[] fils = Io_mgr.Instance.QueryDir_fils(split_root);
		List_adp list = List_adp_.New();

		// delete pack dir
		Pack_list rv = new Pack_list();
		long pack_size_cur = 0;
		int len = fils.length;
		for (int i = 0; i < len; ++i) {
			Io_url fil = fils[i];
			long fil_size = Io_mgr.Instance.QueryFil(fil).Size();
			if (Split_file_tid_.To_tid(fil) == Split_file_tid_.Tid__temp) continue;	// ignore temp file
			list.Add(fil);

			// calc pack size
			long pack_size_new = pack_size_cur + fil_size;
			if (	pack_size_new > pack_size_max 
				||	i == len - 1) {
				int pack_idx = rv.Len();
				Io_url fil_1st = (Io_url)list.Get_at(0);
				int ns_id = Split_file_tid_.Get_ns_by_url(fil_1st);
				Io_url zip_url = pack_root.GenSubFil(Split_file_tid_.Make_file_name(wiki_abrv, wiki_date, rv.Len(), ns_id, ".zip"));
				Pack_itm itm = new Pack_itm(pack_idx, Xobc_import_type.Tid__pack, zip_url, (Io_url[])list.To_ary_and_clear(Io_url.class));
				rv.Add(itm);
				pack_size_cur = 0;
			}
			else
				pack_size_cur = pack_size_new;
		}
		return rv;
	}
	private static void Make_pack(Xowe_wiki wiki, Io_url wiki_dir, byte[] wiki_abrv, String wiki_date, Xobc_data_db bc_db, Hash_algo hash_algo, Bry_bfr tmp_bfr, Pack_itm itm, int task_id) {
		// hash raws			
		Io_url zip_url = itm.Zip_url();
		Gfo_log_.Instance.Prog("hashing raw: " + zip_url.NameAndExt());
		Io_url md5_url = wiki_dir.GenSubFil(zip_url.NameOnly() + ".md5");
		long raw_size = 0;
		Io_url[] raw_urls = itm.Raw_urls();
		int raw_urls_len = raw_urls.length;
		for (int i = 0; i < raw_urls_len; ++i) {
			Io_url raw_url = raw_urls[i];
			IoStream raw_stream = Io_mgr.Instance.OpenStreamRead(raw_url);
			byte[] raw_md5 = null;
			try {raw_md5 = hash_algo.Hash_stream_as_bry(Gfo_prog_ui_.Noop, raw_stream);}
			finally {raw_stream.Rls();}
			tmp_bfr.Add(raw_md5).Add_byte_space().Add_byte(Byte_ascii.Star).Add_str_a7(raw_url.NameAndExt()).Add_byte_nl();
			raw_size += raw_stream.Len();
		}
		Io_mgr.Instance.SaveFilBfr(md5_url, tmp_bfr);

		// calc prog_size_end
		long prog_size_end = 0; int prog_count_end =0;
		for (int i = 0; i < raw_urls_len; ++i) {
			Io_url raw_url = raw_urls[i];
			if (Split_file_tid_.To_tid(raw_url) != Split_file_tid_.Tid__data) continue;
			Db_conn src_conn = Db_conn_bldr.Instance.Get_or_noop(raw_url);
			Wkr_stats_tbl tbl = new Wkr_stats_tbl(src_conn);
			Wkr_stats_itm stat = tbl.Select_all__summary();
			prog_count_end += stat.Count;
			prog_size_end += stat.Size;
		}

		// compress raws
		Gfo_log_.Instance.Prog("compressing raw");
		Io_zip_compress_cmd__jre zip_cmd = new Io_zip_compress_cmd__jre();
		raw_urls = (Io_url[])Array_.Insert(raw_urls, new Io_url[] {md5_url}, 0);	// add ".md5" to .zip
		zip_cmd.Exec_hook(Gfo_prog_ui_.Noop, raw_urls, zip_url, "", 0, 0);

		// hash zip
		Gfo_log_.Instance.Prog("hashing zip");
		IoStream zip_stream = Io_mgr.Instance.OpenStreamRead(zip_url);
		byte[] zip_md5 = null;
		try		{zip_md5 = hash_algo.Hash_stream_as_bry(Gfo_prog_ui_.Noop, zip_stream);}
		finally {zip_stream.Rls();}
		long zip_len = Io_mgr.Instance.QueryFil(zip_url).Size();

		// generate import
		Gfo_log_.Instance.Prog("generating tasks");
		int step_id = bc_db.Conn().Sys_mgr().Autonum_next("step_regy.step_id");
		int sm_id = bc_db.Conn().Sys_mgr().Autonum_next("step_map.sm_id");
		bc_db.Tbl__step_regy().Insert(step_id, Xobc_step_itm.Type__wiki_import);
		bc_db.Tbl__step_map().Insert(sm_id, task_id, step_id, itm.Idx());
		bc_db.Tbl__import_step().Insert(step_id, gplx.xowa.addons.bldrs.centrals.dbs.datas.Xobc_host_regy_tbl.Host_id__archive_org, wiki_abrv, wiki_date, zip_url.NameAndExt(), itm.Type(), Xobc_zip_type.Type__zip, zip_md5, zip_len, raw_size, prog_size_end, prog_count_end);

		// cleanup
		Io_mgr.Instance.DeleteFil(md5_url);
	}
}
