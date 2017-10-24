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
package gplx.xowa.bldrs.setups.addons; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
import gplx.core.ios.zips.*; import gplx.core.envs.*;
import gplx.xowa.apps.fsys.*;
public class Xoi_firefox_installer implements Gfo_invk {
	private Io_url src_xpi, trg_xpi;
	private Io_url trg_xpi_package;
	private Process_adp program = new Process_adp();
	public void Init_by_app(Xoae_app app) {
		src_xpi = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("firefox", "xowa_viewer", "default", "xowa_viewer@piotrex.xpi");
		trg_xpi = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("firefox", "xowa_viewer", "install", "xowa_viewer@piotrex.xpi");
		trg_xpi_package = trg_xpi.OwnerDir().GenSubDir("package");
		Xoa_fsys_eval cmd_eval = app.Url_cmd_eval();
		Process_adp.ini_(this, app.Usr_dlg(), program, cmd_eval, Process_adp.Run_mode_async,  0, "firefox", "\"~{url}\"", "url");
	}
	public void Install_via_process() {
		Generate();
		program.Run(trg_xpi.Raw());
	}
	public void Generate() {
		Io_mgr.Instance.CopyFil(src_xpi, trg_xpi, true);
		Io_zip_mgr_base.Instance.Unzip_to_dir(trg_xpi, trg_xpi_package);
		Pref_gen();
		Io_zip_mgr_base.Instance.Zip_dir(trg_xpi_package, trg_xpi);
	}
	private void Pref_gen() {
		Io_url prefs_fil = trg_xpi_package.GenSubFil_nest("defaults", "preferences", "prefs.js");
		String prefs_str = Io_mgr.Instance.LoadFilStr(prefs_fil);
		prefs_str = Pref_update(prefs_str, "extensions.xowa_viewer.xowa_app", Env_.AppUrl().Raw());
		Io_mgr.Instance.SaveFilStr(prefs_fil, prefs_str);
	}
	public static String Pref_update(String src, String key, String val) {		
		String find = String_.Format("pref(\"{0}\"", key); // EX: 'pref("key"'
		int bgn = String_.FindFwd(src, find);								// look for 'pref...'
		if (bgn == String_.Find_none) return src;	// key not found; return;
		int end = String_.FindFwd(src, "\n", bgn + String_.Len(find));	// look for '\n'; note that this will trim any comments; EX: pref("key", "val"); // comment will be lost
		if (end == String_.Find_none) return src;	// nl not found; return;
		String repl = String_.Format("{0}, \"{1}\");", find, val);	// EX: 'pref("key", "val");'
		return 		String_.Mid(src, 0, bgn)
				+	repl
				+	String_.Mid(src, end);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_install)) 		Install_via_process();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final    String Invk_install = "install";
}
