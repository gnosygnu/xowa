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
import gplx.core.brys.fmtrs.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*;
public class Gfo_thread_cmd_download implements Gfo_thread_cmd {
	public Gfo_thread_cmd Ctor(Gfo_usr_dlg usr_dlg, Gfui_kit kit) {this.usr_dlg = usr_dlg; this.kit = kit; xrg.Prog_dlg_(usr_dlg); return this;}
	public Gfo_thread_cmd_download Init(String prog_fmt_hdr, String src, Io_url trg) {
		this.src = src; this.trg = trg;
		xrg.Prog_fmt_hdr_(prog_fmt_hdr).Init(src, trg);
		return this;
	}	String src; protected Gfui_kit kit; Gfo_usr_dlg usr_dlg; Io_url trg;
	public Gfo_invk Owner() {return owner;} public Gfo_thread_cmd_download Owner_(Gfo_invk v) {owner = v; return this;} Gfo_invk owner;
	public Bry_fmtr_eval_mgr Url_eval_mgr() {return url_eval_mgr;} public Gfo_thread_cmd_download Url_eval_mgr_(Bry_fmtr_eval_mgr v) {url_eval_mgr = v; return this;} Bry_fmtr_eval_mgr url_eval_mgr;
	public void Cmd_ctor() {}
	public Gfo_thread_cmd Async_next_cmd() {return next_cmd;} public void Async_next_cmd_(Gfo_thread_cmd v) {next_cmd = v;} Gfo_thread_cmd next_cmd;
	@gplx.Virtual public String Async_key() {return KEY;}
	public int Async_sleep_interval()	{return Gfo_thread_cmd_.Async_sleep_interval_1_second;}
	public boolean Async_prog_enabled()	{return false;}
	@gplx.Virtual public byte Async_init() {
		if (Io_mgr.Instance.ExistsFil(trg)) {
			int rslt = kit.Ask_yes_no_cancel(GRP_KEY, "target_exists", "Target file already exists: '~{0}'.\nDo you want to delete it?", trg.Raw());
			switch (rslt) {
				case Gfui_dlg_msg_.Btn_yes:		Io_mgr.Instance.DeleteFil(trg); break;
				case Gfui_dlg_msg_.Btn_no:		return Gfo_thread_cmd_.Init_cancel_step;
				case Gfui_dlg_msg_.Btn_cancel:	return Gfo_thread_cmd_.Init_cancel_all;
				default:						throw Err_.new_unhandled(rslt);
			}
		}
		usr_dlg.Prog_many(GRP_KEY, "download.bgn", "contacting web server: '~{0}'", src);	// update progress; some servers (like WMF dump servers) are slow to respond
		return Gfo_thread_cmd_.Init_ok;
	}
	public boolean Async_term() {
		usr_dlg.Prog_many(GRP_KEY, "clear", "");
		return download_pass;
	}
	public void Async_prog_run(int async_sleep_sum) {}
	public boolean Async_running() {return xrg.Prog_running();} 
	public void Async_run() {Thread_adp_.Start_by_key(gplx.xowa.apps.Xoa_thread_.Key_bldr_download, this, Invk_async_bgn);}
	private void Download() {
		download_pass = true;
		if (!xrg.Exec()) {
			xrg.Prog_running_(false);
			download_pass = false;
			kit.Ask_ok(GRP_KEY, "download.fail", "download failed. Please select 'read from file' if you've already downloaded a dump: url=~{0} error=~{1}", src, xrg.Rslt_err_str());
		}
	}	boolean download_pass = true;
	protected gplx.core.ios.IoEngine_xrg_downloadFil xrg = Io_mgr.Instance.DownloadFil_args("", Io_url_.Empty);
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_async_bgn))				Download();
		else if	(ctx.Match(k, Invk_owner))					return owner;
		else if	(ctx.Match(k, Invk_src_))					src = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_trg_))					trg = Bry_fmtr_eval_mgr_.Eval_url(url_eval_mgr, m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_async_bgn = "async_bgn", Invk_owner = "owner", Invk_src_ = "src_", Invk_trg_ = "trg_";
	static final String GRP_KEY = "gfo.thread.file.download";
	public static final String KEY = "file.download";
}
