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
package gplx.xowa.htmls.core.htmls.tidy; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.core.envs.*;
import gplx.langs.htmls.*;
public class Xow_tidy_mgr implements Gfo_invk, Xow_tidy_mgr_interface {
	private Xoae_app app;
	private final    Xoh_tidy_wkr_tidy	wkr__tidy = new Xoh_tidy_wkr_tidy();	// NOTE: app-level; not thread-safe; needed b/c of Options and exe/args DATE:2016-07-12
	private final    Xoh_tidy_wkr_jtidy wkr__jtidy = new Xoh_tidy_wkr_jtidy();
	private Xoh_tidy_wkr wkr = Xoh_tidy_wkr_.Wkr_null; // TEST: set default wkr to null
	private boolean enabled = true;
	public void Init_by_wiki(Xowe_wiki wiki) {
		this.app = wiki.Appe();
		Process_adp.ini_(this, app.Usr_dlg(), wkr__tidy, app.Url_cmd_eval(), Process_adp.Run_mode_sync_timeout, 1 * 60, "~{<>bin_plat_dir<>}tidy" + Op_sys.Cur().Fsys_dir_spr_str() +  "tidy", Xoh_tidy_wkr_tidy.Args_fmt, "source", "target");
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
		else if	(ctx.Match(k, Cfg__cmd))							gplx.xowa.apps.progs.Xoa_prog_mgr.Init_cmd(m.ReadStr("v"), wkr__tidy);
		else if	(ctx.Match(k, Cfg__engine)) {
			String engine_str = m.ReadStr("v");
			if		(String_.Eq(engine_str, "tidy"))	wkr = wkr__tidy; // NOTE: app-level; not thread-safe; needed b/c of Options and exe/args DATE:2016-07-12
			else if (String_.Eq(engine_str, "jtidy"))	wkr = wkr__jtidy;
			else										throw Err_.new_unhandled_default(engine_str);
			wkr.Init_by_app(app);
		}
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
