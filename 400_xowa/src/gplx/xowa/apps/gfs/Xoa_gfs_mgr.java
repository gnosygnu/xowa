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
package gplx.xowa.apps.gfs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.langs.gfs.*;
import gplx.xowa.users.*; import gplx.xowa.apps.fsys.*;
public class Xoa_gfs_mgr implements Gfo_invk, Gfo_invk_root_wkr {
	private final    String user_name;
	public Xoa_gfs_mgr(String user_name, Gfo_invk root_invk, Xoa_fsys_mgr app_fsys_mgr) {
		this.user_name = user_name;
		this.root_invk = root_invk; this.app_fsys_mgr = app_fsys_mgr;
		GfsCore.Instance.AddCmd(root_invk, Xoae_app.Invk_app);
		GfsCore.Instance.AddCmd(root_invk, Xoae_app.Invk_xowa);
	}
	public Gfo_invk Root_invk() {return root_invk;} private final    Gfo_invk root_invk; 
	public Xoa_fsys_mgr App_fsys_mgr() {return app_fsys_mgr;} private final    Xoa_fsys_mgr app_fsys_mgr; 
	public Xoa_app_eval Eval_mgr() {return eval_mgr;} private final    Xoa_app_eval eval_mgr = new Xoa_app_eval();
	public Gfs_wtr Wtr() {return wtr;} private final    Gfs_wtr wtr = new Gfs_wtr();
	public void Run_url(Io_url url) {
		Run_url_for(GfsCore.Instance.Root(), url);
		Gfo_usr_dlg_.Instance.Log_wkr().Log_to_session_fmt("gfs.done: ~{0}", url.Raw());
	}
	public void Run_url_for(Gfo_invk invk, Io_url url) {
		String raw = Io_mgr.Instance.LoadFilStr_args(url).MissingIgnored_().Exec(); if (String_.Len_eq_0(raw)) return;
		Run_str_for(invk, raw);
	}
	public Object Run_str(String raw) {return Run_str_for(GfsCore.Instance.Root(), raw);}
	public Object Run_str_for(Gfo_invk invk, String raw) {return Run_str_for(invk, Xoa_gfs_mgr_.Parse_to_msg(raw));}
	public Object Run_str_for(Gfo_invk invk, GfoMsg root_msg) {
		try {
			Object rv = null;
			GfsCtx ctx = GfsCtx.new_().Fail_if_unhandled_(Fail_if_unhandled).Usr_dlg_(Gfo_usr_dlg_.Instance);
			int len = root_msg.Subs_count();
			for (int i = 0; i < len; ++i)
				rv = GfsCore.Instance.ExecOne_to(ctx, invk, root_msg.Subs_getAt(i));
			return rv;	// return rv from last call
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "error while executing script: err=~{0}", Err_.Message_gplx_full(e));
			return Gfo_invk_.Rv_error;
		}
	}
	private void Run_url_by_type(String type) {
		if		(String_.Eq(type, "xowa_cfg_app"))		Run_url(app_fsys_mgr.Cfg_app_fil());
		else if	(String_.Eq(type, "xowa.user.os"))		gplx.xowa.addons.apps.cfgs.mgrs.dflts.Xocfg_dflt_mgr.Run_os_gfs(user_name, this, app_fsys_mgr);
		else											throw Err_.new_wo_type("invalid gfs type", "type", type);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run_file_by_type))		Run_url_by_type(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_fail_if_unhandled_))		{Fail_if_unhandled = m.ReadYn("v"); ctx.Fail_if_unhandled_(Fail_if_unhandled);}
		else if	(ctx.Match(k, Invk_txns))					{return Gfo_invk_.Noop;}	// FUTURE: handle version for upgrades
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_run_file_by_type = "run_file_by_type", Invk_fail_if_unhandled_ = "fail_if_unhandled_", Invk_txns = "txns";
	public static void Msg_parser_init() {GfsCore.Instance.MsgParser_(gplx.langs.gfs.Gfs_msg_bldr.Instance);}
	public static boolean Fail_if_unhandled = false;
}
