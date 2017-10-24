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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.brys.fmtrs.*; import gplx.core.threads.*;
public class Xoi_cmd_mgr implements Gfo_invk {
	List_adp cmds = List_adp_.New();
	public Xoi_cmd_mgr(Xoi_setup_mgr install_mgr) {this.app = install_mgr.App(); this.install_mgr = install_mgr;} private Xoae_app app; Xoi_setup_mgr install_mgr;
	public Xoae_app App() {return app;}
	public void Canceled_y_() {canceled = true;} private boolean canceled = false;
	public boolean Working() {return working;} private boolean working;
	public void Working_(boolean v) {
		working = v;
		app.Bldr__running_(v);
	}		
	private void Process_async(Gfo_thread_cmd cmd) {
		byte init_rslt = cmd.Async_init();
		if (init_rslt == Gfo_thread_cmd_.Init_ok) {
			cmd.Async_run();
			int async_sleep_interval = cmd.Async_sleep_interval();
			boolean async_prog_enabled = cmd.Async_prog_enabled();
			int async_sleep_sum = 0;
			while (cmd.Async_running()) {
				if (canceled) {this.Working_(Bool_.N); return;}
				if (async_prog_enabled) cmd.Async_prog_run(async_sleep_sum);
				Thread_adp_.Sleep(async_sleep_interval);
				async_sleep_sum += async_sleep_interval;	// NOTE: this is not exact
			}
		}
		boolean term_pass = cmd.Async_term();
		if (cmd.Async_next_cmd() != null && init_rslt != Gfo_thread_cmd_.Init_cancel_all && term_pass)
			Run_async(cmd.Async_next_cmd());
		else
			this.Working_(Bool_.N);
	}
	private void Run_async(Gfo_thread_cmd cmd) {Thread_adp_.Start_by_val(cmd.Async_key(), this, Invk_process_async, cmd);}
	private void Cmds_run() {
		if (working) {
			app.Gui_mgr().Kit().Ask_ok("", "", "An import is in progress. Please wait for it to complete. If you want to do multiple imports at once, see Dashboard/Import/Offline.");	// HOME
			return;
		}
		int cmds_len = cmds.Count();
		if (cmds_len == 0) return;
		for (int i = 0; i < cmds_len - 1; i++) {
			Gfo_thread_cmd cur_cmd = (Gfo_thread_cmd)cmds.Get_at(i);
			Gfo_thread_cmd nxt_cmd = (Gfo_thread_cmd)cmds.Get_at(i + 1);
			cur_cmd.Cmd_ctor();
			cur_cmd.Async_next_cmd_(nxt_cmd);
		}
		Gfo_thread_cmd cmd = (Gfo_thread_cmd)cmds.Get_at(0);
		cmds.Clear();
		this.Working_(Bool_.Y);
		app.Bldr__running_(true);
		this.Run_async(cmd);
	}
	Object Dump_add_many(GfoMsg m) {
		int args_len = m.Args_count();
		if (args_len < 4) throw Err_.new_wo_type("Please provide the following: wiki name, wiki date, dump_type, and one command; EX: ('simple.wikipedia.org', 'latest', 'pages-articles', 'wiki.download')");
		String wiki_key = m.Args_getAt(0).Val_to_str_or_empty();
		String wiki_date = m.Args_getAt(1).Val_to_str_or_empty();
		String dump_type = m.Args_getAt(2).Val_to_str_or_empty();
		Gfo_thread_cmd cmd = null;
		for (int i = 3; i < args_len; i++) {
			Keyval kv = m.Args_getAt(i);
			String kv_val = kv.Val_to_str_or_empty();
			if (String_.Eq(kv_val, Wiki_cmd_custom))
				return Dump_add_many_custom(wiki_key, wiki_date, dump_type, false);
			else {
				cmd = Dump_cmd_new(wiki_key, wiki_date, dump_type, kv.Val_to_str_or_empty());
				cmds.Add(cmd);
			}
		}
		return cmd;	// return last cmd
	}
	public Gfo_thread_cmd Dump_add_many_custom(String wiki_key, String wiki_date, String dump_type, boolean dumpfile_cmd) {
		String[] custom_cmds = (app.Cfg().Get_bool_app_or("xowa.bldr.import.unzip_bz2_file", false)) // CFG: Cfg__
			? String_.Ary(Xoi_cmd_wiki_download.Key_wiki_download, Xoi_cmd_wiki_unzip.KEY_dump, Xoi_cmd_wiki_import.KEY)
			: String_.Ary(Xoi_cmd_wiki_download.Key_wiki_download, Xoi_cmd_wiki_import.KEY);
		int custom_cmds_len = custom_cmds.length;
		Gfo_thread_cmd cmd = null;
		for (int j = 0; j < custom_cmds_len; j++) {
			cmd = Dump_cmd_new(wiki_key, wiki_date, dump_type, custom_cmds[j]);
			if (dumpfile_cmd) {
				if		(String_.Eq(cmd.Async_key(), Xoi_cmd_wiki_download.Key_wiki_download)) continue;	// skip download if wiki.dump_file
				else if	(String_.Eq(cmd.Async_key(), Xoi_cmd_wiki_unzip.KEY_dump)) {
					Xowe_wiki wiki = app.Wiki_mgr().Get_by_or_make(Bry_.new_u8(wiki_key));
					if (wiki.Import_cfg().Src_fil_xml()  != null) continue;	// skip unzip if xml exists
				}
				else if (String_.Eq(cmd.Async_key(), Xoi_cmd_wiki_import.KEY)) {
					((Xoi_cmd_wiki_import)cmd).Import_move_bz2_to_done_(false);
				}
			}
			cmds.Add(cmd);
		}
		return cmd;
	}
	Gfo_thread_cmd Dump_cmd_new(String wiki_key, String wiki_date, String dump_type, String cmd_key) {
		if		(String_.Eq(cmd_key, Xoi_cmd_wiki_download.Key_wiki_download))			return new Xoi_cmd_wiki_download().Ctor_download_(install_mgr, wiki_key, wiki_date, dump_type).Owner_(this);
		else if	(String_.Eq(cmd_key, Xoi_cmd_wiki_unzip.KEY_dump))						return new Xoi_cmd_wiki_unzip(install_mgr, wiki_key, wiki_date, dump_type).Owner_(this);
		else if	(String_.Eq(cmd_key, Xoi_cmd_wiki_import.KEY))							return new Xoi_cmd_wiki_import(install_mgr, wiki_key, wiki_date, dump_type).Owner_(this);
		else if	(String_.Eq(cmd_key, Xoi_cmd_category2_build.KEY))						return new Xoi_cmd_category2_build(install_mgr, wiki_key).Owner_(this);
		else if	(String_.Eq(cmd_key, Xoi_cmd_category2_page_props.KEY_category2))		return new Xoi_cmd_category2_page_props(install_mgr, wiki_key, wiki_date).Owner_(this);
		else if	(String_.Eq(cmd_key, Xoi_cmd_category2_categorylinks.KEY_category2))	return new Xoi_cmd_category2_categorylinks(install_mgr, wiki_key, wiki_date).Owner_(this);
		else if	(String_.Eq(cmd_key, Xoi_cmd_search2_build.KEY))						return new Xoi_cmd_search2_build(install_mgr, wiki_key).Owner_(this);
		else																			throw Err_.new_unhandled(cmd_key);
	}
	public static final    String Wiki_cmd_custom = "wiki.custom", Wiki_cmd_dump_file = "wiki.dump_file";
	public Gfo_thread_cmd Cmd_add(GfoMsg m) {Gfo_thread_cmd rv = Cmd_clone(m); cmds.Add(rv); return rv;}
	Gfo_thread_cmd Cmd_clone(GfoMsg m) {
		String cmd_key = m.ReadStr("v");
		if		(String_.Eq(cmd_key, Gfo_thread_cmd_download.KEY))						return new Gfo_thread_cmd_download().Init("downloading", m.ReadStr("src"), Bry_fmtr_eval_mgr_.Eval_url(app.Url_cmd_eval(), m.ReadBry("trg"))).Url_eval_mgr_(app.Url_cmd_eval()).Owner_(this).Ctor(app.Usr_dlg(), app.Gui_mgr().Kit());
		else if	(String_.Eq(cmd_key, Gfo_thread_cmd_unzip.KEY))							return new Gfo_thread_cmd_unzip().Url_eval_mgr_(app.Url_cmd_eval()).Owner_(this).Init(app.Usr_dlg(), app.Gui_mgr().Kit(), app.Prog_mgr().App_decompress_bz2(), app.Prog_mgr().App_decompress_zip(), app.Prog_mgr().App_decompress_gz(), Bry_fmtr_eval_mgr_.Eval_url(app.Url_cmd_eval(), m.ReadBry("src")), Bry_fmtr_eval_mgr_.Eval_url(app.Url_cmd_eval(), m.ReadBry("trg")));
		else if	(String_.Eq(cmd_key, Gfo_thread_cmd_replace.KEY))						return new Gfo_thread_cmd_replace().Url_eval_mgr_(app.Url_cmd_eval()).Owner_(this).Init(app.Usr_dlg(), app.Gui_mgr().Kit(), Bry_fmtr_eval_mgr_.Eval_url(app.Url_cmd_eval(), m.ReadBry("fil")));
		else if	(String_.Eq(cmd_key, Xoi_cmd_wiki_goto_page.KEY))						return new Xoi_cmd_wiki_goto_page(app, m.ReadStr("v")).Owner_(this);
		else if	(String_.Eq(cmd_key, Xoi_cmd_msg_ok.KEY))								return new Xoi_cmd_msg_ok(app.Usr_dlg(), app.Gui_mgr().Kit(), m.ReadStr("v")).Owner_(this);
		else if	(String_.Eq(cmd_key, Xoi_cmd_imageMagick_download.KEY_imageMagick))		return new Xoi_cmd_imageMagick_download(app.Usr_dlg(), app.Gui_mgr().Kit(), Bry_fmtr_eval_mgr_.Eval_url(app.Url_cmd_eval(), m.ReadBry("trg"))).Owner_(this);
		else if	(String_.Eq(cmd_key, Wiki_cmd_dump_file))								return Wiki_cmd_dump_file_make(m);
		else																			throw Err_.new_unhandled(cmd_key);
	}
	Gfo_thread_cmd Wiki_cmd_dump_file_make(GfoMsg m) {	// note: might be used directly in home-wiki pages to download files
		Xoi_cmd_dumpfile dumpfile = new Xoi_cmd_dumpfile().Parse_msg(m);
		return dumpfile.Exec(this);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_process_async))				Process_async((Gfo_thread_cmd)m.CastObj("v"));
		else if	(ctx.Match(k, Invk_dump_add_many))				return Dump_add_many(m);
		else if	(ctx.Match(k, Invk_cmd_add))					return Cmd_add(m);
		else if	(ctx.Match(k, Invk_run))						Cmds_run();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_process_async = "process_async", Invk_dump_add_many = "dump_add_many", Invk_run = "run", Invk_cmd_add = "cmd_add";
	static final String GRP_KEY = "xowa.install_mgr.cmd_mgr";
}
