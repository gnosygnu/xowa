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
package gplx.core.threads;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.files.BryFmtrEvalMgrUtl;
import gplx.libs.files.Io_mgr;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.gfui.kits.core.*;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.types.commons.KeyVal;
public class Gfo_thread_cmd_replace implements Gfo_thread_cmd {
	public Gfo_thread_cmd Init(Gfo_usr_dlg usr_dlg, Gfui_kit kit, Io_url fil) {
		this.usr_dlg = usr_dlg; this.kit = kit; this.fil = fil;
		return this;
	}	Gfui_kit kit; Gfo_usr_dlg usr_dlg; Io_url fil;
	public Gfo_invk Owner() {return owner;} public Gfo_thread_cmd_replace Owner_(Gfo_invk v) {owner = v; return this;} Gfo_invk owner;
	public BryFmtrEvalMgr Url_eval_mgr() {return url_eval_mgr;} public Gfo_thread_cmd_replace Url_eval_mgr_(BryFmtrEvalMgr v) {url_eval_mgr = v; return this;} BryFmtrEvalMgr url_eval_mgr;
	public String Async_key() {return KEY;}
	public void Cmd_ctor() {}
	public Gfo_thread_cmd Async_next_cmd() {return next_cmd;} public void Async_next_cmd_(Gfo_thread_cmd v) {next_cmd = v;} Gfo_thread_cmd next_cmd;
	public int Async_sleep_interval()	{return Gfo_thread_cmd_.Async_sleep_interval_1_second;}
	public boolean Async_prog_enabled()	{return false;}
	public byte Async_init() {
		if (!Io_mgr.Instance.ExistsFil(fil)) {kit.Ask_ok(GRP_KEY, "file_missing", "File does not exist: '~{0}'", fil.Raw()); return Gfo_thread_cmd_.Init_cancel_step;}
		return Gfo_thread_cmd_.Init_ok;
	}
	public boolean Async_term() {return true;}
	public void Async_prog_run(int async_sleep_sum) {}
	public boolean Async_running() {return false;} 
	public void Async_run() {Exec_find_replace();}	// NOTE: do not run async; if multiple commands for same file then they will not always work
	public void Exec_find_replace() {
		String raw = Io_mgr.Instance.LoadFilStr(fil);
		int pairs_len = pairs.Len();
		for (int i = 0; i < pairs_len; i++) {
			KeyVal kv = (KeyVal)pairs.GetAt(i);
			raw = StringUtl.Replace(raw, kv.KeyToStr(), kv.ValToStrOrNull());
		}
		Io_mgr.Instance.SaveFilStr(fil, raw);
		usr_dlg.Prog_many(GRP_KEY, "done", "replace completed: ~{0} ~{1}", fil.Raw(), pairs_len);
	}
	public List_adp pairs = List_adp_.New();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_async_bgn))				Exec_find_replace();
		else if	(ctx.Match(k, Invk_owner))					return owner;
		else if	(ctx.Match(k, Invk_fil_))					fil = BryFmtrEvalMgrUtl.Eval_url(url_eval_mgr, m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_add))					pairs.Add(KeyVal.NewStr(m.ReadStr("find"), m.ReadStr("replace")));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_async_bgn = "async_bgn", Invk_owner = "owner", Invk_fil_ = "fil_", Invk_add = "add";
	static final String GRP_KEY = "gfo.thread.file.download";
	public static final String KEY = "text.replace";
}
