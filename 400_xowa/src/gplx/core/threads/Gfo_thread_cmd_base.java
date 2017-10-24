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
public class Gfo_thread_cmd_base implements Gfo_thread_cmd {
	@gplx.Virtual public String Async_key() {return "undefined";}
	public void Cmd_ctor() {}
	public Gfo_thread_cmd_base Ctor(Gfo_usr_dlg usr_dlg, Gfui_kit kit) {this.usr_dlg = usr_dlg; this.kit = kit; return this;} protected Gfo_usr_dlg usr_dlg; protected Gfui_kit kit;
	public Gfo_invk Owner() {return owner;} public Gfo_thread_cmd_base Owner_(Gfo_invk v) {owner = v; return this;} Gfo_invk owner;
	public Bry_fmtr_eval_mgr Url_eval_mgr() {return url_eval_mgr;} public Gfo_thread_cmd_base Url_eval_mgr_(Bry_fmtr_eval_mgr v) {url_eval_mgr = v; return this;} Bry_fmtr_eval_mgr url_eval_mgr;
	public Gfo_thread_cmd Async_next_cmd() {return next_cmd;} public void Async_next_cmd_(Gfo_thread_cmd v) {next_cmd = v;} Gfo_thread_cmd next_cmd;
	@gplx.Virtual public int Async_sleep_interval()	{return Gfo_thread_cmd_.Async_sleep_interval_1_second;}
	@gplx.Virtual public boolean Async_prog_enabled()	{return false;}
	@gplx.Virtual public byte Async_init() {return Gfo_thread_cmd_.Init_ok;}
	@gplx.Virtual public boolean Async_term() {return true;}
	@gplx.Virtual public void Async_prog_run(int async_sleep_sum) {}
	@gplx.Virtual public void Async_bgn() {}
	@gplx.Virtual public boolean Async_running() {return false;} 
	@gplx.Virtual public void Async_run() {}
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))					return owner;
		else if	(ctx.Match(k, Invk_async_bgn))				Async_bgn();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_owner = "owner", Invk_async_bgn = "async_bgn";
}
