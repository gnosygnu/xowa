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
package gplx.xowa.htmls.core.htmls.tidy; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.core.envs.*;
import gplx.langs.htmls.*;
import gplx.xowa.apps.fsys.*;
public class Xoa_tidy_mgr implements Gfo_evt_itm {
	public Xoa_tidy_mgr() {this.evt_mgr = new Gfo_evt_mgr(this);}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private final    Gfo_evt_mgr evt_mgr;
	public Xoh_tidy_wkr_tidy Wkr_tidy() {return wkr_tidy;} private Xoh_tidy_wkr_tidy wkr_tidy = new Xoh_tidy_wkr_tidy();	// NOTE: app-level; not thread-safe; needed b/c of Options and exe/args DATE:2016-07-12
	public void Init_by_app(Xoae_app app) {
		wkr_tidy.Init_by_app(app);
		Xoa_fsys_eval cmd_eval = app.Url_cmd_eval();
		Process_adp.ini_(this, app.Usr_dlg(), wkr_tidy, cmd_eval, Process_adp.Run_mode_sync_timeout, 1 * 60, "~{<>bin_plat_dir<>}tidy" + Op_sys.Cur().Fsys_dir_spr_str() +  "tidy", Xoh_tidy_wkr_tidy.Args_fmt, "source", "target");
	}
	public boolean Enabled() {return enabled;} private boolean enabled = true;
	public void Enabled_toggle() {Enabled_(!enabled);}
	public byte Wkr_tid() {return wkr_tid;} private byte wkr_tid = Xoh_tidy_wkr_.Tid_jtidy;
	private void Enabled_(boolean v) {
		this.enabled = v;
		Gfo_evt_mgr_.Pub_val(this, Evt__enabled_changed, v);
	}
	public void Wkr_tid_(byte v) {
		this.wkr_tid = v;
		Gfo_evt_mgr_.Pub_val(this, Evt__engine_changed, v);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))				return Yn.To_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))				Enabled_(m.ReadYn("v"));
		else if	(ctx.Match(k, Invk_enabled_toggle))			Enabled_(!enabled);
		else if	(ctx.Match(k, Invk_engine_type))			return Xoh_tidy_wkr_.Xto_key(wkr_tid);
		else if	(ctx.Match(k, Invk_engine_type_))			Wkr_tid_(Xoh_tidy_wkr_.Xto_tid(m.ReadStr("v")));
		else if	(ctx.Match(k, Invk_engine_type_list))		return Xoh_tidy_wkr_.Options__list;
		else if	(ctx.Match(k, Invk_lib))					return wkr_tidy;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_enabled = "enabled", Invk_enabled_ = "enabled_", Invk_enabled_toggle = "enabled_toggle", Invk_lib = "lib"
	, Invk_engine_type = "engine_type", Invk_engine_type_ = "engine_type_", Invk_engine_type_list = "engine_type_list"
	;
	public static final String Evt__enabled_changed = "enabled_changed", Evt__engine_changed = "engine_changed";
}
