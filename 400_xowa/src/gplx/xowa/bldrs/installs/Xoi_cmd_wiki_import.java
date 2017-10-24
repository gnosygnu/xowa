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
import gplx.core.threads.*; 
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.utils.*;
import gplx.xowa.guis.views.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.addons.wikis.ctgs.bldrs.*;
class Xoi_cmd_wiki_import implements Gfo_thread_cmd {
	private boolean running;
	private Xowe_wiki wiki;
	public Xoi_cmd_wiki_import(Xoi_setup_mgr install_mgr, String wiki_key, String wiki_date, String dump_type) {this.install_mgr = install_mgr; this.Owner_(install_mgr); this.wiki_key = wiki_key; this.wiki_date = wiki_date; this.dump_type = dump_type;} private Xoi_setup_mgr install_mgr; String wiki_key, wiki_date, dump_type;
	public static final String KEY = "wiki.import";
	public void Cmd_ctor() {}
	public String Async_key() {return KEY;}
	public int Async_sleep_interval()	{return Gfo_thread_cmd_.Async_sleep_interval_1_second;}
	public boolean Async_prog_enabled()	{return false;}
	public void Async_prog_run(int async_sleep_sum) {}
	public byte Async_init() {return Gfo_thread_cmd_.Init_ok;}
	public boolean Async_term() {
		install_mgr.App().Usr_dlg().Log_many(GRP_KEY, "import.end", "import.end ~{0} ~{1} ~{2}", wiki_key, wiki_date, dump_type);
		return true;
	}
	public Gfo_invk Owner() {return owner;} public Xoi_cmd_wiki_import Owner_(Gfo_invk v) {owner = v; return this;} Gfo_invk owner;
	public Gfo_thread_cmd Async_next_cmd() {return next_cmd;} public void Async_next_cmd_(Gfo_thread_cmd v) {next_cmd = v;} Gfo_thread_cmd next_cmd;
	public void Async_run() {
		running = true;
		install_mgr.App().Usr_dlg().Log_many(GRP_KEY, "import.bgn", "import.bgn ~{0} ~{1} ~{2}", wiki_key, wiki_date, dump_type);
		Thread_adp_.Start_by_key(this.Async_key(), this, Invk_process_async);
	}
	public boolean Async_running() {
		return running;
	}
	public boolean Import_move_bz2_to_done() {return import_move_bz2_to_done;} public Xoi_cmd_wiki_import Import_move_bz2_to_done_(boolean v) {import_move_bz2_to_done = v; return this;} private boolean import_move_bz2_to_done = true;
	private void Process_async() {
		Xoae_app app = install_mgr.App();
		app.Usr_dlg().Prog_one("", "", "preparing import: ~{0}", wiki_key);
		Xob_bldr bldr = app.Bldr();
		wiki = app.Wiki_mgr().Get_by_or_make(Bry_.new_a7(wiki_key));
		wiki.Init_assert();
		bldr.Cmd_mgr().Clear();
		bldr.Pause_at_end_(false);
		Io_url src_url = wiki.Import_cfg().Src_rdr().Url();
		Process_sql(bldr, src_url);
		bldr.Run();
		app.Usr_dlg().Prog_none(GRP_KEY, "clear", ""); app.Usr_dlg().Note_none(GRP_KEY, "clear", "");
		app.Usere().Available_from_fsys();
		wiki.Init_needed_(true);
		wiki.Html_mgr().Page_wtr_mgr().Init_(true);
		wiki.Init_assert();
		if		(String_.Eq(src_url.Ext(), ".xml")) {
			if (   app.Cfg().Get_bool_app_or("xowa.bldr.import.delete_xml_file", true)	// CFG: Cfg__
				&& Io_mgr.Instance.ExistsFil(src_url.GenNewExt(".bz2"))	// only delete the file if there is a corresponding bz2 file; BUG.GH:#124; DATE:2017-02-02
				)
				Io_mgr.Instance.DeleteFil(src_url);
		}
		else if (String_.Eq(src_url.Ext(), ".bz2")) {
			Io_url trg_fil = app.Fsys_mgr().Wiki_dir().GenSubFil_nest("#dump", "done", src_url.NameAndExt());
			if (import_move_bz2_to_done)
				Io_mgr.Instance.MoveFil_args(src_url, trg_fil, true).Exec();
		}
		running = false;
		wiki.Import_cfg().Src_fil_xml_(null).Src_fil_bz2_(null);	// reset file else error when going from Import/Script to Import/List
		app.Gui_mgr().Kit().New_cmd_sync(this).Invk(GfsCtx.new_(), 0, Invk_open_wiki, GfoMsg_.Null);
	}
	private void Process_sql(Xob_bldr bldr, Io_url dump_url) {
		// setup wiki
		((Xob_cleanup_cmd)bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_util_cleanup)).Delete_tdb_(true).Delete_sqlite3_(true);
		bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_text_init);
		bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_text_page);
		bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_text_css);	
//			if (wiki.Appe().Setup_mgr().Dump_mgr().Search_version() == gplx.xowa.addons.wikis.searchs.specials.Srch_special_page.Version_2)
		gplx.xowa.addons.wikis.searchs.bldrs.Srch_bldr_mgr_.Setup(wiki);
		bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_text_term);	

		// setup category
		if (wiki.Domain_itm().Domain_type_id() != Xow_domain_tid_.Tid__other) {	// do not add category if not wmf; note that wikia wikis will not have category dumps; DATE:2016-10-22
			Xob_download_cmd.Add_if_not_found_many(bldr, wiki, Xob_catlink_cmd.Dump_file_name, Xob_pageprop_cmd.Dump_file_name);
			bldr.Cmd_mgr().Add(new gplx.xowa.addons.wikis.ctgs.bldrs.Xob_pageprop_cmd(bldr, wiki).Src_dir_manual_(dump_url.OwnerDir()));
			bldr.Cmd_mgr().Add(new gplx.xowa.addons.wikis.ctgs.bldrs.Xob_catlink_cmd(bldr, wiki).Src_dir_manual_(dump_url.OwnerDir()));
		}
	}
	private void Open_wiki(String wiki_key) {
		Xog_win_itm main_win = install_mgr.App().Gui_mgr().Browser_win();
		if (main_win.Active_page() == null) return; // will be null when invoked through cmd-line
		byte[] url = Bry_.Add(wiki.Domain_bry(), Xoh_href_.Bry__wiki, wiki.Props().Main_page());
		main_win.Page__navigate_by_url_bar(String_.new_u8(url));
	}	
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_process_async))			Process_async();
		else if	(ctx.Match(k, Invk_owner))					return owner;
		else if	(ctx.Match(k, Invk_open_wiki))				Open_wiki(wiki_key);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_process_async = "run_async", Invk_owner = "owner", Invk_open_wiki = "open_wiki";
	static final String GRP_KEY = "xowa.thread.op.build";
}
