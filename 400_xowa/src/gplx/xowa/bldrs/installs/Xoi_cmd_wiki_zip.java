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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.threads.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.utils.*;
class Xoi_cmd_wiki_zip implements Gfo_thread_cmd {
	public Xoi_cmd_wiki_zip(Xoi_setup_mgr install_mgr, String wiki_key, String wiki_date, String dump_type) {this.install_mgr = install_mgr; this.Owner_(install_mgr); this.wiki_key = wiki_key; this.wiki_date = wiki_date; this.dump_type = dump_type;} private Xoi_setup_mgr install_mgr; String wiki_key, wiki_date, dump_type;
	public static final String KEY = "wiki.zip";
	public void Cmd_ctor() {}
	public String Async_key() {return KEY;}
	public int Async_sleep_interval()	{return Gfo_thread_cmd_.Async_sleep_interval_1_second;}
	public boolean Async_prog_enabled()	{return false;}
	public void Async_prog_run(int async_sleep_sum) {}
	public byte Async_init() {return Gfo_thread_cmd_.Init_ok;}
	public boolean Async_term() {
		wiki.Tdb_fsys_mgr().Scan_dirs();
		install_mgr.App().Usr_dlg().Log_many(GRP_KEY, "zip.end", "zip.end ~{0}", wiki_key);
		install_mgr.App().Usr_dlg().Prog_many(GRP_KEY, "zip.done", "zip done");
		return true;
	}
	public GfoInvkAble Owner() {return owner;} public Xoi_cmd_wiki_zip Owner_(GfoInvkAble v) {owner = v; return this;} GfoInvkAble owner;
	public Gfo_thread_cmd Async_next_cmd() {return next_cmd;} public void Async_next_cmd_(Gfo_thread_cmd v) {next_cmd = v;} Gfo_thread_cmd next_cmd;
	public void Async_run() {
		running = true;
		install_mgr.App().Usr_dlg().Log_many(GRP_KEY, "zip.bgn", "zip.bgn ~{0}", wiki_key);
		Thread_adp_.invk_(this.Async_key(), this, Invk_process_async).Start();
	}
	public boolean Async_running() {
		return running;
	}
	boolean running, delete_dirs_page = true, notify_done = true;
	private void Process_async() {
		Xoae_app app = install_mgr.App();
		Xob_bldr bldr = app.Bldr();
		wiki = app.Wiki_mgr().Get_by_or_make(Bry_.new_a7(wiki_key));
		wiki.Init_assert();
		bldr.Cmd_mgr().Clear();
		bldr.Pause_at_end_(false);
		((Xob_deploy_zip_cmd)bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_deploy_zip)).Delete_dirs_page_(delete_dirs_page);
		bldr.Run();
		app.Usr_dlg().Prog_none(GRP_KEY, "clear", "");
		app.Usr_dlg().Note_none(GRP_KEY, "clear", "");
		running = false;
	}	private Xowe_wiki wiki;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_process_async))			Process_async();
		else if	(ctx.Match(k, Invk_owner))					return owner;
		else if	(ctx.Match(k, Invk_delete_dirs_page_))		delete_dirs_page = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_notify_done_))			notify_done = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_process_async = "run_async", Invk_owner = "owner", Invk_delete_dirs_page_ = "delete_dirs_page_", Invk_notify_done_ = "notify_done_";
	private static final String GRP_KEY = "xowa.thread.op.wiki.zip";
}
