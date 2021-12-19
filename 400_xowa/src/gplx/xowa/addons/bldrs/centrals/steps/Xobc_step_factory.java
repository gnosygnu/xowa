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
package gplx.xowa.addons.bldrs.centrals.steps;
import gplx.core.brys.evals.Bry_eval_mgr;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.StringRef;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xow_wiki;
import gplx.xowa.addons.bldrs.centrals.Xobc_task_mgr;
import gplx.xowa.addons.bldrs.centrals.cmds.Xobc_cmd__download;
import gplx.xowa.addons.bldrs.centrals.cmds.Xobc_cmd__fsdb_delete;
import gplx.xowa.addons.bldrs.centrals.cmds.Xobc_cmd__move_fils;
import gplx.xowa.addons.bldrs.centrals.cmds.Xobc_cmd__unzip;
import gplx.xowa.addons.bldrs.centrals.cmds.Xobc_cmd__verify_dir;
import gplx.xowa.addons.bldrs.centrals.cmds.Xobc_cmd__verify_fil;
import gplx.xowa.addons.bldrs.centrals.cmds.Xobc_cmd__wiki_reg;
import gplx.xowa.addons.bldrs.centrals.cmds.Xobc_cmd_itm;
import gplx.xowa.addons.bldrs.centrals.dbs.Xobc_data_db;
import gplx.xowa.addons.bldrs.centrals.dbs.datas.imports.Xobc_import_step_itm;
import gplx.xowa.addons.bldrs.centrals.dbs.datas.imports.Xobc_import_type;
import gplx.xowa.addons.bldrs.centrals.hosts.Host_eval_itm;
import gplx.xowa.addons.bldrs.centrals.hosts.Host_eval_wkr;
import gplx.xowa.addons.bldrs.centrals.tasks.Xobc_task_itm;
import gplx.xowa.addons.bldrs.centrals.utils.Bry_eval_wkr__builder_central;
import gplx.xowa.addons.bldrs.exports.packs.files.Pack_zip_name_bldr;
import gplx.xowa.wikis.domains.Xow_abrv_xo_;
import gplx.xowa.wikis.domains.Xow_domain_itm;
public class Xobc_step_factory {
	private final Xobc_task_mgr task_mgr;
	private final Xobc_data_db data_db;
	private final Bry_eval_mgr eval_mgr = Bry_eval_mgr.Dflt(); private final Host_eval_itm host_eval = new Host_eval_itm();
	private final Host_eval_wkr eval_wkr__host_regy = new Host_eval_wkr();
	public Xobc_step_factory(Xobc_task_mgr task_mgr, Xobc_data_db data_db, Io_url wiki_dir) {
		this.task_mgr = task_mgr;
		this.data_db = data_db;
		eval_mgr.Add_many(new Bry_eval_wkr__builder_central(wiki_dir));
	}
	public void Load(Xobc_task_itm task, int step_id, int cmd_idx) {
		int step_type = data_db.Tbl__step_regy().Select_type(step_id);
		switch (step_type) {
			case Xobc_step_itm.Type__wiki_import: Load_wiki_import(task, step_id, cmd_idx); break;
			default: throw ErrUtl.NewUnhandled(step_type);
		}
	}
	private void Load_wiki_import(Xobc_task_itm task, int step_id, int cmd_idx) {
		int step_seqn = data_db.Tbl__step_map().Select_one(task.Task_id(), step_id).Step_seqn;
		Xobc_import_step_itm import_itm = data_db.Tbl__import_step().Select_one(step_id); StringRef step_name = StringRef.NewEmpty();
		Xobc_cmd_itm[] cmds = Make_wiki_import_cmds(import_itm, task.Task_id(), step_id, step_name, step_seqn);
		Xobc_step_itm step = new Xobc_step_itm(step_id, step_seqn, cmds).Cmd_idx_(cmd_idx);
		step.Step_name_(StringUtl.Format("{0}&nbsp;&middot;({1}/{2})", step_name.Val(), step_seqn + List_adp_.Base1, task.Step_count()));
		task.Step_(step);
		step.Cmd().Load_checkpoint();
		if (step.Cmd().Prog_status() == gplx.core.progs.Gfo_prog_ui_.Status__suspended)
			task.Task_status_(step.Cmd().Prog_status());
	}
	private Xobc_cmd_itm[] Make_wiki_import_cmds(Xobc_import_step_itm import_itm, int task_id, int step_id, StringRef step_name, int step_seqn) {
		List_adp list = List_adp_.New();
		Xow_domain_itm domain_itm = Xow_abrv_xo_.To_itm(import_itm.Wiki_abrv());
		String wiki_domain = domain_itm.Domain_str();
		String file_name = import_itm.Import_name;
		step_name.ValSet(file_name);
		eval_wkr__host_regy.Domain_itm_(domain_itm);
		String src_http_url = host_eval.Eval_src_fil(data_db, import_itm.Host_id, domain_itm, file_name);
		Io_url zip_file_url  = Eval_url(Bry_eval_wkr__builder_central.Make_str(Bry_eval_wkr__builder_central.Type__download_fil, wiki_domain, file_name));
		Io_url unzip_dir_url = Eval_url(Bry_eval_wkr__builder_central.Make_str(Bry_eval_wkr__builder_central.Type__unzip_dir, wiki_domain, file_name));
		Io_url wiki_dir_url  = Eval_url(Bry_eval_wkr__builder_central.Make_str(Bry_eval_wkr__builder_central.Type__wiki_dir, wiki_domain, file_name));

		// if lucene, move to /data/search/
		if (import_itm.Import_type == Xobc_import_type.Tid__wiki__lucene) {
			wiki_dir_url = gplx.xowa.addons.wikis.fulltexts.Xosearch_fulltext_addon.Get_index_dir(wiki_dir_url);
		}

		Io_url checksum_url	 = unzip_dir_url.GenSubFil(file_name + ".md5");
		int cmd_idx = 0;
		list.Add(new Xobc_cmd__download			(task_mgr, task_id, step_id, cmd_idx++, src_http_url, zip_file_url, import_itm.Import_size_zip));
		list.Add(new Xobc_cmd__verify_fil		(task_mgr, task_id, step_id, cmd_idx++, zip_file_url, import_itm.Import_md5, import_itm.Import_size_zip));
		list.Add(new Xobc_cmd__unzip			(task_mgr, task_id, step_id, cmd_idx++, zip_file_url, unzip_dir_url, import_itm.Import_size_raw));
		list.Add(new Xobc_cmd__verify_dir		(task_mgr, task_id, step_id, cmd_idx++, checksum_url, zip_file_url));
		// list.Add(new Xobc_cmd__wiki_merge	(task_mgr, task_id, step_id, cmd_idx++, merge_mgr, wiki_domain, unzip_dir_url, import_itm.Import_prog_data_max, import_itm.Import_prog_row_max, step_seqn));
		list.Add(new Xobc_cmd__move_fils		(task_mgr, task_id, step_id, cmd_idx++, unzip_dir_url, wiki_dir_url));
		switch (import_itm.Import_type) {
			case Xobc_import_type.Tid__wiki__core:		list.Add(new Xobc_cmd__wiki_reg		(task_mgr, task_id, step_id, cmd_idx++, wiki_dir_url, wiki_domain)); break;
			case Xobc_import_type.Tid__fsdb__delete:	list.Add(new Xobc_cmd__fsdb_delete	(task_mgr, task_id, step_id, cmd_idx++, Pack_zip_name_bldr.To_wiki_url(wiki_dir_url, zip_file_url.OwnerDir()))); break;
		}
		return (Xobc_cmd_itm[])list.ToAryAndClear(Xobc_cmd_itm.class);
	}
	private Io_url Eval_url(String src) {return Io_url_.new_any_(StringUtl.NewU8(eval_mgr.Eval(BryUtl.NewU8(src))));}
	public static Xow_wiki Get_wiki_by_abrv(Xoa_app app, byte[] wiki_abrv) {
		Xow_domain_itm domain_itm = Xow_abrv_xo_.To_itm(wiki_abrv);
		return app.Wiki_mgri().Get_by_or_make_init_y(domain_itm.Domain_bry());
	}
}
