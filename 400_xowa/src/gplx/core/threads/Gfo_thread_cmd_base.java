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
package gplx.core.threads; import gplx.*; import gplx.core.*;
import gplx.gfui.*;
public class Gfo_thread_cmd_base implements Gfo_thread_cmd {
	@gplx.Virtual public String Async_key() {return "undefined";}
	public void Cmd_ctor() {}
	public Gfo_thread_cmd_base Ctor(Gfo_usr_dlg usr_dlg, Gfui_kit kit) {this.usr_dlg = usr_dlg; this.kit = kit; return this;} protected Gfo_usr_dlg usr_dlg; protected Gfui_kit kit;
	public GfoInvkAble Owner() {return owner;} public Gfo_thread_cmd_base Owner_(GfoInvkAble v) {owner = v; return this;} GfoInvkAble owner;
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
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_owner = "owner", Invk_async_bgn = "async_bgn";
}
