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
public class Xow_tidy_mgr implements Gfo_evt_itm, Xow_tidy_mgr_interface {
	private Xoae_app app; private Xoh_tidy_wkr_tidy tidy_cmd = new Xoh_tidy_wkr_tidy();	// NOTE: app-level; not thread-safe; needed b/c of Options and exe/args DATE:2016-07-12
	private boolean enabled = true; private Xoh_tidy_wkr wkr = Xoh_tidy_wkr_.Wkr_null; // TEST: set default wkr to null
	public Xow_tidy_mgr() {this.evt_mgr = new Gfo_evt_mgr(this);}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private final    Gfo_evt_mgr evt_mgr;
	private void Engine_(String v) {
		if		(String_.Eq(v, "tidy"))		wkr = new Xoh_tidy_wkr_tidy(); // NOTE: app-level; not thread-safe; needed b/c of Options and exe/args DATE:2016-07-12
		else if (String_.Eq(v, "jtidy"))	wkr = new Xoh_tidy_wkr_jtidy();
		else								throw Err_.new_unhandled_default(v);
		wkr.Init_by_app(app);
	}
	public void Init_by_wiki(Xoae_app app) {
		this.app = app;
		tidy_cmd.Init_by_app(app);
		Process_adp.ini_(this, app.Usr_dlg(), tidy_cmd, app.Url_cmd_eval(), Process_adp.Run_mode_sync_timeout, 1 * 60, "~{<>bin_plat_dir<>}tidy" + Op_sys.Cur().Fsys_dir_spr_str() +  "tidy", Xoh_tidy_wkr_tidy.Args_fmt, "source", "target");
		app.Cfg().Bind_many_app(this, Cfg__enabled, Cfg__engine, Cfg__cmd);
	}		
	public void Exec_tidy(Bry_bfr bfr, boolean indent, byte[] page_url) {
		if (!enabled) return;
		if (bfr.Len_eq_0()) return;	// document is empty; do not exec b/c tidy will never generate files for 0 len files, and previous file will remain; DATE:2014-06-04
		Tidy_wrap(bfr);
		wkr.Indent_(indent);
		wkr.Exec_tidy(bfr, page_url);
		Tidy_unwrap(bfr);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__enabled))						this.enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__engine))							Engine_(m.ReadStr("v"));
		else if	(ctx.Match(k, Cfg__cmd))							gplx.xowa.apps.progs.Xoa_prog_mgr.Init_cmd(m.ReadStr("v"), tidy_cmd);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static void Tidy_wrap(Bry_bfr bfr) {
		bfr.Insert_at(0, Wrap_bgn);
		bfr.Add(Wrap_end);
	}
	public static boolean Tidy_unwrap(Bry_bfr bfr) {
		byte[] bfr_bry = bfr.Bfr();
		int find = Bry_find_.Find_fwd(bfr_bry, Gfh_tag_.Body_lhs); if (find == Bry_find_.Not_found) return false;
		bfr.Delete_rng_to_bgn(find + Gfh_tag_.Body_lhs.length);
		find = Bry_find_.Find_bwd(bfr_bry, Gfh_tag_.Body_rhs, bfr.Len()); if (find == Bry_find_.Not_found) return false;
		bfr.Delete_rng_to_end(find);
		return true;
	}
	private static final    byte[]	// MW:includes/parser/Tidy.php|getWrapped
	  Wrap_bgn = Bry_.new_a7
	( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
	+ "<html>"
	+   "<head>"
	+     "<title>test</title>"
	+   "</head>"
	+   "<body>"
	)
	, Wrap_end = Bry_.new_a7
	(   "</body>"
	+ "</html>"
	);
	private static final String 
	  Cfg__enabled			= "xowa.html.tidy.enabled"
	, Cfg__engine			= "xowa.html.tidy.engine"
	, Cfg__cmd				= "xowa.html.tidy.cmd"
	;
}
