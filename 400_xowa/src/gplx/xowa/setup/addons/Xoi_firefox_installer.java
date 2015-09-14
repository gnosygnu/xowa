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
package gplx.xowa.setup.addons; import gplx.*; import gplx.xowa.*; import gplx.xowa.setup.*;
import gplx.ios.*;
import gplx.xowa.apps.fsys.*;
public class Xoi_firefox_installer implements GfoInvkAble {
	private Io_url src_xpi, trg_xpi;
	private Io_url trg_xpi_package;
	private ProcessAdp program = new ProcessAdp();
	public void Init_by_app(Xoae_app app) {
		src_xpi = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("firefox", "xowa_viewer", "default", "xowa_viewer@piotrex.xpi");
		trg_xpi = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("firefox", "xowa_viewer", "install", "xowa_viewer@piotrex.xpi");
		trg_xpi_package = trg_xpi.OwnerDir().GenSubDir("package");
		Xoa_fsys_eval cmd_eval = app.Url_cmd_eval();
		ProcessAdp.ini_(this, app.Usr_dlg(), program, cmd_eval, ProcessAdp.Run_mode_async,  0, "firefox", "\"~{url}\"", "url");
	}
	public void Install_via_process() {
		Generate();
		program.Run(trg_xpi.Raw());
	}
	public void Generate() {
		Io_mgr.I.CopyFil(src_xpi, trg_xpi, true);
		Io_zip_mgr_base._.Unzip_to_dir(trg_xpi, trg_xpi_package);
		Pref_gen();
		Io_zip_mgr_base._.Zip_dir(trg_xpi_package, trg_xpi);
	}
	private void Pref_gen() {
		Io_url prefs_fil = trg_xpi_package.GenSubFil_nest("defaults", "preferences", "prefs.js");
		String prefs_str = Io_mgr.I.LoadFilStr(prefs_fil);
		prefs_str = Pref_update(prefs_str, "extensions.xowa_viewer.xowa_app", Env_.AppUrl().Raw());
		Io_mgr.I.SaveFilStr(prefs_fil, prefs_str);
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
		if		(ctx.Match(k, Invk_program)) 		return program;
		if		(ctx.Match(k, Invk_install)) 		Install_via_process();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_program = "program", Invk_install = "install";
}
