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
package gplx.xowa.apps.setups; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.apps.versions.*; import gplx.core.envs.*;
public class Xoa_setup_mgr {
	public static void Launch(Xoae_app app) {
		Delete_old_files(app);
		gplx.xowa.addons.apps.cfgs.upgrades.Xocfg_upgrade_mgr.Upgrade_cfg_0(app);
		Run_cfg_for_os(app);
	}
	private static void Delete_old_files(Xoae_app app) {
		String version_previous = gplx.xowa.guis.views.Xog_startup_tabs.Version_previous(app);
		Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		Io_url root_dir = app.Fsys_mgr().Root_dir();
		Delete_old_dir(usr_dlg, version_previous, "1.8.2.1"		, root_dir.GenSubDir_nest("user", "anonymous", "lang"));
		Delete_old_dir(usr_dlg, version_previous, "1.8.2.1"		, root_dir.GenSubDir_nest("user", "anonymous", "wiki", "#cfg"));
		Delete_old_dir(usr_dlg, version_previous, "1.10.2.1"	, root_dir.GenSubDir_nest("bin", "any", "javascript"));
		Delete_old_dir(usr_dlg, version_previous, "1.10.2.1"	, root_dir.GenSubDir_nest("bin", "any", "xowa", "html", "modules"));
	}
	public static void Delete_old_dir(Gfo_usr_dlg usr_dlg, String version_prv, String version_del, Io_url dir) {	// TEST:
		if (Xoa_version_.Compare(version_prv, version_del) != CompareAble_.Less) return;
		usr_dlg.Log_many("", "", "setup:checking if dir exists: version_prv=~{0} version_del=~{1} dir=~{2}", version_prv, version_del, dir.Raw());
		if (!Io_mgr.Instance.ExistsDir(dir)) return;
		usr_dlg.Log_many("", "", "setup:deleting dir", version_prv, version_del, dir.Raw());
		Io_mgr.Instance.DeleteDirDeep(dir);
	}
	private static void Run_cfg_for_os(Xoae_app app) {
		// exit if wnt or drd
		byte op_sys_tid = Op_sys.Cur().Tid();
		switch (op_sys_tid) {
			case Op_sys.Tid_drd: 
			case Op_sys.Tid_wnt: return;
		}

		// get list of OS for which script has been run; exit if run
		String Cfg__os_script_list = "xowa.app.setup.os_script_list";
		String op_sys_name = Xoa_app_.Op_sys_str;
		String setup_completed = app.Cfg().Get_str_app_or(Cfg__os_script_list, "");
		String[] plats_ary = String_.Split(setup_completed, ";");
		int plats_ary_len = plats_ary.length;
		for (int i = 0; i < plats_ary_len; i++) {
			if (String_.Eq(plats_ary[i], op_sys_name)) return;
		}

		// run script_fil
		Io_url script_fil = app.Fsys_mgr().Root_dir().GenSubFil_nest("bin", op_sys_name, "xowa", "script", "setup_lua.sh");
		String exe = "sh";
		String arg = String_.Format("\"{0}\" \"{1}\"", script_fil.Raw(), app.Fsys_mgr().Root_dir());
		boolean pass = false; String fail = "";
		try {pass = new Process_adp().Exe_url_(Io_url_.new_fil_(exe)).Args_str_(arg).Run_wait_sync().Exit_code_pass();}
		catch (Exception e) {
			fail = Err_.Message_gplx_full(e);
		}
		if (!pass)
			app.Usr_dlg().Prog_many("", "", "process exec failed: ~{0} ~{1} ~{2}", exe, arg, fail);

		// update cfg
		setup_completed += op_sys_name + ";";
		app.Cfg().Set_str_app(Cfg__os_script_list, setup_completed);
	}
}
