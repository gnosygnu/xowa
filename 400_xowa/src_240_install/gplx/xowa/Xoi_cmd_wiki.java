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
package gplx.xowa; import gplx.*;
import gplx.gfui.*;
import gplx.core.threads.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.utils.*;
class Xoi_cmd_wiki_download extends Gfo_thread_cmd_download implements Gfo_thread_cmd {	private Xoi_setup_mgr install_mgr; private String wiki_key, dump_date, dump_type;
	public Xoi_cmd_wiki_download Ctor_download_(Xoi_setup_mgr install_mgr, String wiki_key, String dump_date, String dump_type) {
		this.install_mgr = install_mgr;
		this.wiki_key = wiki_key;
		this.dump_date = dump_date;
		this.dump_type = dump_type;
		this.Owner_(install_mgr);
		return this;
	}
	@gplx.Virtual public String Download_file_ext() {return ".xml.bz2";}	// wiki.download is primarily used for dump files; default to .xml.bz2; NOTE: changed from ".xml"; DATE:2013-11-07
	@Override public String Async_key() {return Key_wiki_download;}  public static final String Key_wiki_download = "wiki.download";
	@Override public byte Async_init() {
		Xoae_app app = install_mgr.App();
		Xob_dump_file dump_file = Xob_dump_file.new_(wiki_key, dump_date, dump_type);
		boolean connected = Xob_dump_file_.Connect_first(dump_file, install_mgr.Dump_mgr().Server_urls());
		if (connected)
			app.Usr_dlg().Note_many("", "", "url: ~{0}", dump_file.File_url());
		else {
			if (!Dump_servers_offline_msg_shown) {
				app.Gui_mgr().Kit().Ask_ok("", "", "all dump servers are offline: ~{0}", String_.AryXtoStr(install_mgr.Dump_mgr().Server_urls()));
				Dump_servers_offline_msg_shown = true;
			}
		}
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_key_or_make(dump_file.Wiki_type().Domain_bry());
		Io_url root_dir = wiki.Fsys_mgr().Root_dir();
		Io_url[] trg_fil_ary = Io_mgr._.QueryDir_args(root_dir).FilPath_("*." + dump_type + Download_file_ext() + "*").ExecAsUrlAry();
		Io_url trg = trg_fil_ary.length == 0 ? root_dir.GenSubFil(dump_file.File_name()) : trg_fil_ary[0];
		this.Ctor(app.Usr_dlg(), app.Gui_mgr().Kit());
		this.Init("download", dump_file.File_url(), trg);
		return super.Async_init();
	}
	private static boolean Dump_servers_offline_msg_shown = false;
}
class Xoi_cmd_wiki_unzip extends Gfo_thread_cmd_unzip implements Gfo_thread_cmd {	public static final String KEY_dump = "wiki.unzip";
	public Xoi_cmd_wiki_unzip(Xoi_setup_mgr install_mgr, String wiki_key, String dump_date, String dump_type) {this.install_mgr = install_mgr; this.Owner_(install_mgr); this.wiki_key = wiki_key; this.dump_date = dump_date; this.dump_type = dump_type;} private Xoi_setup_mgr install_mgr; String wiki_key, dump_date, dump_type;
	@Override public String Async_key() {return KEY_dump;}
	@Override public byte Async_init() {
		Xoae_app app = install_mgr.App(); Gfui_kit kit = app.Gui_mgr().Kit();
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_key_or_make(Bry_.new_utf8_(wiki_key));
		Io_url wiki_dir = wiki.Import_cfg().Src_dir();
		Io_url[] urls = Io_mgr._.QueryDir_args(wiki_dir).Recur_(false).FilPath_("*.xml.bz2").ExecAsUrlAry();
		if (urls.length == 0) {
			kit.Ask_ok(GRP_KEY, "dump.unzip_latest.file_missing", "Could not find a dump file for ~{0} in ~{1}", wiki_key, wiki_dir.Raw());
			return Gfo_thread_cmd_.Init_cancel_step;
		}
		Io_url src = urls[urls.length - 1];
		Io_url trg = app.Fsys_mgr().Wiki_dir().GenSubFil_nest(wiki_key, src.NameOnly());	// NOTE: NameOnly() will strip trailing .bz2; EX: a.xml.bz2 -> a.xml
		super.Init(app.Usr_dlg(), app.Gui_mgr().Kit(), app.Prog_mgr().App_decompress_bz2(), app.Prog_mgr().App_decompress_zip(), app.Prog_mgr().App_decompress_gz(), src, trg);
		this.Term_cmd_for_src_(Term_cmd_for_src_move);
		this.Term_cmd_for_src_url_(app.Fsys_mgr().Wiki_dir().GenSubFil_nest("#dump", "done", src.NameAndExt()));
		if (Io_mgr._.ExistsFil(trg)) {
			int rslt = kit.Ask_yes_no_cancel(GRP_KEY, "target_exists", "Target file already exists: '~{0}'.\nDo you want to delete it?", trg.Raw());
			switch (rslt) {
				case Gfui_dlg_msg_.Btn_yes:		Io_mgr._.DeleteFil(trg); break;
				case Gfui_dlg_msg_.Btn_no:		return Gfo_thread_cmd_.Init_cancel_step;
				case Gfui_dlg_msg_.Btn_cancel:	return Gfo_thread_cmd_.Init_cancel_all;
				default:						throw Err_mgr._.unhandled_(rslt);
			}
		}
		return Gfo_thread_cmd_.Init_ok;
	}
	static final String GRP_KEY = "xowa.thread.dump.unzip";
}
class Xoi_cmd_wiki_image_cfg extends Gfo_thread_cmd_replace implements Gfo_thread_cmd {	public Xoi_cmd_wiki_image_cfg(Xoae_app app, Io_url url) {this.app = app; super.Init(app.Usr_dlg(), app.Gui_mgr().Kit(), url);} private Xoae_app app;
	@Override public void Async_run() {
		super.Async_run();
		app.Cfg_mgr().Set_by_app("app.files.download.enabled", "y");
		app.Cfg_mgr().Db_save_txt();
	}
	static final String GRP_KEY = "xowa.thread.dump.image_cfg";
	public static final String KEY_dump = "wiki.image_cfg";
}
class Xoi_cmd_wiki_goto_page extends Gfo_thread_cmd_base implements Gfo_thread_cmd {
	public Xoi_cmd_wiki_goto_page(Xoae_app app, String page) {this.app = app; this.page = page; this.Ctor(app.Usr_dlg(), app.Gui_mgr().Kit());} private Xoae_app app; String page;
	@Override public void Async_run()	{kit.New_cmd_sync(this).Invk(GfsCtx.new_(), 0, Invk_goto_page, GfoMsg_.Null);}
	private void Goto_page(String page)			{app.Gui_mgr().Browser_win().Page__navigate_by_url_bar(page);}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_goto_page))				Goto_page(page);
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_goto_page = "goto_page";
	public static final String KEY = "wiki.goto_page";
}
class Xoi_cmd_imageMagick_download extends Gfo_thread_cmd_download implements Gfo_thread_cmd {//		private static final byte[] Bry_windows_zip = Bry_.new_ascii_("-windows.zip");
//		static final String Src_imageMagick = "ftp://ftp.sunet.se/pub/multimedia/graphics/ImageMagick/binaries/";
	public Xoi_cmd_imageMagick_download(Gfo_usr_dlg usr_dlg, Gfui_kit kit, Io_url trg) {this.Ctor(usr_dlg, kit); this.trg = trg;} Io_url trg;
	@Override public byte Async_init() {	// <a href="ImageMagick-6.8.1-9-Q16-x86-windows.zip">
//			byte[] raw = xrg.Exec_as_bry(Src_imageMagick);
//			int find_pos = Bry_finder.Find_fwd(raw, Bry_windows_zip);				if (find_pos == Bry_.NotFound) return Fail();
//			int bgn_pos = Bry_finder.Find_bwd(raw, Byte_ascii.Quote, find_pos);	if (bgn_pos == Bry_.NotFound) return Fail();
//			++bgn_pos;
//			int end_pos = Bry_finder.Find_fwd(raw, Byte_ascii.Quote, bgn_pos);		if (end_pos == Bry_.NotFound) return Fail();
//			String src = Src_imageMagick + String_.new_ascii_(Bry_.Mid(raw, bgn_pos, end_pos));
		String src = "http://ftp.sunet.se/pub/multimedia/graphics/ImageMagick/binaries/ImageMagick-6.8.8-1-Q16-x86-windows.zip";
		this.Init("downloading", src, trg);
		return super.Async_init();
	}
	byte Fail() {
		kit.Ask_ok(GRP_KEY, "windows_not_found", "Could not find Windows binary. Please download ImageMagick directly from the site.");
		return Gfo_thread_cmd_.Init_cancel_step;
	}
	public static final String KEY_imageMagick = "download.imageMagick";
	static final String GRP_KEY = "xowa.install.cmds.download.imageMagick";
}
class Xoi_cmd_msg_ok extends Gfo_thread_cmd_base implements Gfo_thread_cmd {
	public Xoi_cmd_msg_ok(Gfo_usr_dlg usr_dlg, Gfui_kit kit, String msg) {this.msg = msg; this.Ctor(usr_dlg, kit);} private String msg;
	@Override public boolean Async_term()	{
		kit.Ask_ok("msg_ok", "msg", msg);
		return true;
	}
	public static final String KEY = "msg.ok";
}
//	class Gfo_thread_exec_sync : Gfo_thread_cmd_base, Gfo_thread_cmd {
//		public Gfo_thread_exec_sync(Gfo_usr_dlg usr_dlg, Gfui_kit kit, Io_url exec_url, String exec_args) {this.Ctor(usr_dlg, kit); this.exec_url = exec_url; this.exec_args = exec_args;} Io_url exec_url; String exec_args;
//		public override byte Async_init() {
//			if (!kit.Ask_yes_no(GRP_KEY, "confirm", "You are about to run '~{0}'. Please confirm that XOWA requested you to run '~{0}' and that you trust it to run on your machine.", exec_url.NameAndExt())) {
//				usr_dlg.Warn_many(GRP_KEY, "confirm.fail", "program was untrusted: ~{0} ~{1}", exec_url.Raw(), exec_args);
//				return Gfo_thread_cmd_.Init_cancel_all;
//			}
//			usr_dlg.Prog_many("exec_sync", "bgn", "running process. please wait; ~{0} ~{1}", exec_url.Raw(), exec_args);
//			return Gfo_thread_cmd_.Init_ok;
//		}
//		public override void Async_run() {
//			ProcessAdp process = new ProcessAdp().Exe_url_(exec_url).Args_str_(exec_args).Prog_dlg_(usr_dlg);
//			process.Run_mode_(ProcessAdp.Run_mode_sync_block);
//			process.Run();
//		}
//		public static final String KEY = "exec.sync";
//		static final String GRP_KEY = "exec.sync";
//	}
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
		wiki = app.Wiki_mgr().Get_by_key_or_make(Bry_.new_ascii_(wiki_key));
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
