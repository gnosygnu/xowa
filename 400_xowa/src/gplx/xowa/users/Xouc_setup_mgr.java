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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
import gplx.core.envs.*;
public class Xouc_setup_mgr {
	public static void Setup_run_check(Xoae_app app) {
		byte op_sys_tid = Op_sys.Cur().Tid();
		switch (op_sys_tid) {
			case Op_sys.Tid_drd: 
			case Op_sys.Tid_wnt: return;
		}
		String op_sys_name = Xoa_app_.Op_sys_str;
		String setup_completed = app.Cfg().Get_str_app_or("xowa.app.session.setup_completed", "");
		String[] plats_ary = String_.Split(setup_completed, ";");
		int plats_ary_len = plats_ary.length;
		for (int i = 0; i < plats_ary_len; i++) {
			if (String_.Eq(plats_ary[i], op_sys_name)) return;
		}
		Io_url setup_url = app.Fsys_mgr().Root_dir().GenSubFil_nest("bin", op_sys_name, "xowa", "script", "setup_lua.sh");

		String exe = "sh";
		String arg = String_.Format("\"{0}\" \"{1}\"", setup_url.Raw(), app.Fsys_mgr().Root_dir());
		boolean pass = false; String fail = "";
		try {pass = new Process_adp().Exe_url_(Io_url_.new_fil_(exe)).Args_str_(arg).Run_wait_sync().Exit_code_pass();}
		catch (Exception e) {
			fail = Err_.Message_gplx_full(e);
		}
		if (!pass)
			app.Usr_dlg().Prog_many("", "", "process exec failed: ~{0} ~{1} ~{2}", exe, arg, fail);

		setup_completed += op_sys_name + ";";
		app.Cfg().Set_str_app("xowa.app.session.setup_completed", setup_completed);
	}
}
