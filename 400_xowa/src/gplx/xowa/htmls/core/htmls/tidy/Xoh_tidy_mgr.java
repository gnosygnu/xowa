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
public class Xoh_tidy_mgr implements GfoInvkAble {
	private Xoh_tidy_wkr wkr = Xoh_tidy_wkr_.Wkr_null; // TEST: set default wkr to null
	private Xoh_tidy_wkr_tidy wkr_tidy = new Xoh_tidy_wkr_tidy(); private Xoh_tidy_wkr_jtidy wkr_jtidy = new Xoh_tidy_wkr_jtidy();
	public void Init_by_app(Xoae_app app) {
		wkr_tidy.Init_by_app(app);
		wkr_jtidy.Init_by_app(app);
		Xoa_fsys_eval cmd_eval = app.Url_cmd_eval();
		Process_adp.ini_(this, app.Usr_dlg(), wkr_tidy, cmd_eval, Process_adp.Run_mode_sync_timeout, 1 * 60, "~{<>bin_plat_dir<>}tidy" + Op_sys.Cur().Fsys_dir_spr_str() +  "tidy", Xoh_tidy_wkr_tidy.Args_fmt, "source", "target");
		Wkr_tid_(Xoh_tidy_wkr_.Tid_jtidy);
	}
	public boolean Enabled() {return enabled;} private boolean enabled = true;
	public void Enabled_toggle() {enabled = !enabled;}
	public void Wkr_tid_(byte v) {
		wkr = v == Xoh_tidy_wkr_.Tid_jtidy
			? (Xoh_tidy_wkr)wkr_jtidy
			: (Xoh_tidy_wkr)wkr_tidy
			;
	}
	public void Run_tidy_html(Xoae_page page, Bry_bfr bfr, boolean indent) {
		if (bfr.Len_eq_0()) return;						// document is empty; do not exec b/c tidy will never generate files for 0 len files, and previous file will remain; DATE:2014-06-04
		Tidy_wrap(bfr);
		wkr.Indent_(indent);
		wkr.Exec_tidy(page, bfr);
		Tidy_unwrap(bfr);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))				return Yn.To_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))				enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_enabled_toggle))			enabled = !enabled;
		else if	(ctx.Match(k, Invk_engine_type))			return Xoh_tidy_wkr_.Xto_key(wkr.Tid());
		else if	(ctx.Match(k, Invk_engine_type_))			Wkr_tid_(Xoh_tidy_wkr_.Xto_tid(m.ReadStr("v")));
		else if	(ctx.Match(k, Invk_engine_type_list))		return Xoh_tidy_wkr_.Options__list;
		else if	(ctx.Match(k, Invk_lib))					return wkr_tidy;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_enabled = "enabled", Invk_enabled_ = "enabled_", Invk_enabled_toggle = "enabled_toggle", Invk_lib = "lib"
	, Invk_engine_type = "engine_type", Invk_engine_type_ = "engine_type_", Invk_engine_type_list = "engine_type_list"
	;
	public static void Tidy_wrap(Bry_bfr bfr) {
		bfr.Insert_at(0, Wrap_bgn);
		bfr.Add(Wrap_end);
	}
	public static boolean Tidy_unwrap(Bry_bfr bfr) {
		byte[] bfr_bry = bfr.Bfr();
		int find = Bry_find_.Find_fwd(bfr_bry, Html_tag_.Body_lhs); if (find == Bry_find_.Not_found) return false;
		bfr.Delete_rng_to_bgn(find + Html_tag_.Body_lhs.length);
		find = Bry_find_.Find_bwd(bfr_bry, Html_tag_.Body_rhs, bfr.Len()); if (find == Bry_find_.Not_found) return false;
		bfr.Delete_rng_to_end(find);
		return true;
	}
	private static final byte[]	// MW:includes/parser/Tidy.php|getWrapped
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
}
