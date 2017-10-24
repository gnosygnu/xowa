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
package gplx.core.threads; import gplx.*; import gplx.core.*;
import gplx.core.brys.fmtrs.*; import gplx.core.envs.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*; import gplx.xowa.bldrs.cmds.utils.*;
public class Gfo_thread_cmd_unzip implements Gfo_thread_cmd {
	public Gfo_thread_cmd_unzip Init(Gfo_usr_dlg usr_dlg, Gfui_kit kit, Process_adp bzip2_process, Process_adp zip_process, Process_adp gz_process, Io_url src, Io_url trg) {
		this.src = src; this.trg = trg; this.kit = kit; this.usr_dlg = usr_dlg;
		unzip_wkr = new Xob_unzip_wkr().Init(bzip2_process, zip_process, gz_process).Process_run_mode_(Process_adp.Run_mode_async);
		return this;
	}	private Io_url src, trg; private Gfui_kit kit; private Gfo_usr_dlg usr_dlg; private Xob_unzip_wkr unzip_wkr;
	public Gfo_invk Owner() {return owner;} public Gfo_thread_cmd_unzip Owner_(Gfo_invk v) {owner = v; return this;} Gfo_invk owner;
	public void Cmd_ctor() {}
	@gplx.Virtual public String Async_key() {return KEY;}
	public Gfo_thread_cmd Async_next_cmd() {return next_cmd;} public void Async_next_cmd_(Gfo_thread_cmd v) {next_cmd = v;} Gfo_thread_cmd next_cmd;
	public Bry_fmtr_eval_mgr Url_eval_mgr() {return url_eval_mgr;} public Gfo_thread_cmd_unzip Url_eval_mgr_(Bry_fmtr_eval_mgr v) {url_eval_mgr = v; return this;} Bry_fmtr_eval_mgr url_eval_mgr;
	public int Async_sleep_interval()	{return Gfo_thread_cmd_.Async_sleep_interval_1_second;}
	public boolean Async_prog_enabled()	{return true;}
	public void Async_prog_run(int async_sleep_sum) {
		String size_str = " please wait...";
		if (trg.Type_fil()) size_str = gplx.core.ios.Io_size_.To_str(Io_mgr.Instance.QueryFil(trg).Size());
		usr_dlg.Prog_many(GRP_KEY, "unzip", "unzipping: ~{0}", size_str);
	}
	@gplx.Virtual public byte Async_init() {
		if (!Io_mgr.Instance.ExistsFil(src)) {
			kit.Ask_ok(GRP_KEY, "source_missing", "Source file does not exist: '~{0}'", src.Raw());
			return Gfo_thread_cmd_.Init_cancel_step;
		}
		trg_is_dir = trg.Type_dir();
		if (delete_trg_if_exists
			&&	(( trg_is_dir && Io_mgr.Instance.ExistsDir(trg))
				||	(!trg_is_dir && Io_mgr.Instance.ExistsFil(trg)))
			) {
			int rslt = kit.Ask_yes_no_cancel(GRP_KEY, "target_exists", "Target file already exists: '~{0}'.\nDo you want to delete it?", trg.Raw());
			switch (rslt) {
				case Gfui_dlg_msg_.Btn_yes:		if (trg_is_dir) Io_mgr.Instance.DeleteDirDeep(trg); else Io_mgr.Instance.DeleteFil(trg); break;
				case Gfui_dlg_msg_.Btn_no:		return Gfo_thread_cmd_.Init_cancel_step;
				case Gfui_dlg_msg_.Btn_cancel:	return Gfo_thread_cmd_.Init_cancel_all;
			}
		}
		return Gfo_thread_cmd_.Init_ok;
	}
	public boolean Async_running() {return unzip_wkr.Process_exit_code() == Process_adp.Exit_init;}
	public void Async_run() {
		usr_dlg.Prog_many(GRP_KEY, "bgn", "unzipping");
		unzip_wkr.Decompress(src, trg);
	}
	public boolean Async_term() {
		if (rename_dir) {
			Io_url[] dirs = Io_mgr.Instance.QueryDir_args(trg.OwnerDir()).DirOnly_().Recur_(false).ExecAsUrlAry();
			int dirs_len = dirs.length;
			Io_url zip_dir = Io_url_.Empty;
			for (int i = 0; i < dirs_len; i++) {
				Io_url dir = dirs[i];
				if (String_.Has_at_bgn(String_.Lower(dir.NameOnly()), String_.Lower(trg.NameOnly()))) {	// HACK: check that directory starts with archive name; DATE:2013-12-22
					zip_dir = dir;
					break;
				}
			}
			if (zip_dir == Io_url_.Empty) {
				kit.Ask_ok(GRP_KEY, "rename.fail", "unable to find directory: trg=~{0}", trg.Raw());
				return false;
			}
			if (!String_.Eq(String_.Lower(zip_dir.Raw()), String_.Lower(trg.Raw())))	// HACK: inkscape is itself
				Io_mgr.Instance.MoveDirDeep(zip_dir, trg);
		}
		switch (term_cmd_for_src) {
			case Term_cmd_for_src_noop: break;
			case Term_cmd_for_src_delete: Io_mgr.Instance.DeleteFil(src); break;
			case Term_cmd_for_src_move:
				if (term_cmd_for_src_url == Io_url_.Empty) throw Err_.new_wo_type("move specified, but no url");
				Io_mgr.Instance.MoveFil_args(src, term_cmd_for_src_url, true).Exec();
				break;
			default: throw Err_.new_unhandled(term_cmd_for_src);
		}
		usr_dlg.Prog_many(GRP_KEY, "done", "");
		return true;
	}
	public static final byte Term_cmd_for_src_noop = 0, Term_cmd_for_src_delete = 1, Term_cmd_for_src_move = 2;
	boolean rename_dir = false, trg_is_dir = false, delete_trg_if_exists = true;
	public byte Term_cmd_for_src() {return term_cmd_for_src;} public void Term_cmd_for_src_(byte v) {term_cmd_for_src = v;} private byte term_cmd_for_src = Term_cmd_for_src_delete;
	public Io_url Term_cmd_for_src_url() {return term_cmd_for_src_url;} public void Term_cmd_for_src_url_(Io_url v) {this.term_cmd_for_src_url = v;} Io_url term_cmd_for_src_url = Io_url_.Empty;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))					return owner;
		else if	(ctx.Match(k, Invk_src_))					src = Bry_fmtr_eval_mgr_.Eval_url(url_eval_mgr, m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_trg_))					trg = Bry_fmtr_eval_mgr_.Eval_url(url_eval_mgr, m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_rename_dir_))			rename_dir = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_delete_trg_if_exists_))	delete_trg_if_exists = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_term_cmd_for_src_))		term_cmd_for_src = Term_cmd_for_src_parse_(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_owner = "owner", Invk_src_ = "src_", Invk_trg_ = "trg_", Invk_rename_dir_ = "rename_dir_", Invk_delete_trg_if_exists_ = "delete_trg_if_exists_", Invk_term_cmd_for_src_ = "term_cmd_for_src_";
	private static byte Term_cmd_for_src_parse_(String s) {
		if 		(String_.Eq(s, "noop")) 		return Term_cmd_for_src_noop;
		else if (String_.Eq(s, "delete")) 		return Term_cmd_for_src_delete;
		else if (String_.Eq(s, "move")) 		return Term_cmd_for_src_move;
		else									throw Err_.new_unhandled(s);
	}
	static final String GRP_KEY = "xowa.thread.file.unzip";
	public static final String KEY = "file.unzip";
}
