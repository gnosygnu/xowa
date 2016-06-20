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
package gplx.xowa.addons.bldrs.centrals.steps; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.brys.evals.*; import gplx.core.primitives.*;
import gplx.xowa.addons.bldrs.centrals.tasks.*; import gplx.xowa.addons.bldrs.centrals.cmds.*; import gplx.xowa.addons.bldrs.centrals.steps.*; import gplx.xowa.addons.bldrs.centrals.utils.*;
import gplx.xowa.addons.bldrs.centrals.dbs.*; import gplx.xowa.addons.bldrs.centrals.dbs.datas.*; import gplx.xowa.addons.bldrs.centrals.dbs.datas.imports.*;
import gplx.xowa.addons.bldrs.exports.merges.*;
import gplx.xowa.wikis.domains.*;
public class Xobc_step_factory {
	private final    Xobc_task_mgr task_mgr;
	private final    Xobc_data_db data_db;
	private final    Bry_eval_mgr eval_mgr = Bry_eval_mgr.Dflt(), eval_mgr__host_regy = Bry_eval_mgr.Dflt(); 
	private final    Bry_eval_wkr__host_regy eval_wkr__host_regy = new Bry_eval_wkr__host_regy();
	public Xobc_step_factory(Xobc_task_mgr task_mgr, Xobc_data_db data_db, Io_url wiki_dir) {
		this.task_mgr = task_mgr;
		this.data_db = data_db;
		eval_mgr.Add_many(new Bry_eval_wkr__builder_central(wiki_dir));
		eval_mgr__host_regy.Add_many(eval_wkr__host_regy);
	}
	public void Load(Xobc_task_itm task, int step_id, int cmd_idx) {
		int step_type = data_db.Tbl__step_regy().Select_type(step_id);
		switch (step_type) {
			case Xobc_step_itm.Type__wiki_import: Load_wiki_import(task, step_id, cmd_idx); break;
			default: throw Err_.new_unhandled_default(step_type);
		}
	}
	private void Load_wiki_import(Xobc_task_itm task, int step_id, int cmd_idx) {
		int step_seqn = data_db.Tbl__step_map().Select_one(task.Task_id(), step_id).Step_seqn;
		Xobc_import_step_itm import_itm = data_db.Tbl__import_step().Select_one(step_id); String_obj_ref step_name = String_obj_ref.empty_();
		Xobc_cmd_itm[] cmds = Make_wiki_import_cmds(import_itm, task.Task_id(), step_id, step_name, step_seqn);
		Xobc_step_itm step = new Xobc_step_itm(step_id, step_seqn, cmds).Cmd_idx_(cmd_idx);
		step.Step_name_(String_.Format("{0}&nbsp;&middot;({1}/{2})", step_name.Val(), step_seqn + List_adp_.Base1, task.Step_count()));
		task.Step_(step);
		step.Cmd().Load_checkpoint();
		if (step.Cmd().Prog_status() == gplx.core.progs.Gfo_prog_ui_.Status__working)
			task.Task_status_(step.Cmd().Prog_status());
	}
	private Xobc_cmd_itm[] Make_wiki_import_cmds(Xobc_import_step_itm import_itm, int task_id, int step_id, String_obj_ref step_name, int step_seqn) {
		List_adp list = List_adp_.New();
		Xow_domain_itm domain_itm = Xow_abrv_xo_.To_itm(import_itm.Wiki_abrv());
		String wiki_domain = domain_itm.Domain_str();
		String file_name = import_itm.Import_name;
		step_name.Val_(file_name);
		eval_wkr__host_regy.Domain_itm_(domain_itm);
		String src_http_url = data_db.Bld_src_http_url(eval_mgr__host_regy, import_itm.Host_id, file_name);
		Io_url zip_file_url  = Eval_url(Bry_eval_wkr__builder_central.Make_str(Bry_eval_wkr__builder_central.Type__download_fil, wiki_domain, file_name));
		Io_url unzip_dir_url = Eval_url(Bry_eval_wkr__builder_central.Make_str(Bry_eval_wkr__builder_central.Type__unzip_dir, wiki_domain, file_name));
		Io_url wiki_dir_url  = Eval_url(Bry_eval_wkr__builder_central.Make_str(Bry_eval_wkr__builder_central.Type__wiki_dir, wiki_domain, file_name));
		list.Add(new Xobc_cmd__download			(task_mgr, task_id, step_id, 0, src_http_url, zip_file_url, import_itm.Import_size_zip));
		list.Add(new Xobc_cmd__verify_fil		(task_mgr, task_id, step_id, 1, zip_file_url, import_itm.Import_md5, import_itm.Import_size_zip));
		list.Add(new Xobc_cmd__unzip			(task_mgr, task_id, step_id, 2, zip_file_url, unzip_dir_url, import_itm.Import_size_raw));
		list.Add(new Xobc_cmd__verify_dir		(task_mgr, task_id, step_id, 3, unzip_dir_url, String_.Replace(file_name, ".zip", ".md5"), zip_file_url));
		// list.Add(new Xobc_cmd__wiki_merge	(task_mgr, task_id, step_id, 4, merge_mgr, wiki_domain, unzip_dir_url, import_itm.Import_prog_data_max, import_itm.Import_prog_row_max, step_seqn));
		list.Add(new Xobc_cmd__move_fils		(task_mgr, task_id, step_id, 4, unzip_dir_url, wiki_dir_url));
		if (import_itm.Import_type == Xobc_import_type.Tid__wiki__core) {
			list.Add(new Xobc_cmd__wiki_reg		(task_mgr, task_id, step_id, 5, wiki_dir_url, wiki_domain));
		}
		return (Xobc_cmd_itm[])list.To_ary_and_clear(Xobc_cmd_itm.class);
	}
	private Io_url Eval_url(String src) {return Io_url_.new_any_(String_.new_u8(eval_mgr.Eval(Bry_.new_u8(src))));}
}
