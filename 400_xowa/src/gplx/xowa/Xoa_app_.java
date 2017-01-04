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
package gplx.xowa; import gplx.*;
import gplx.core.log_msgs.*;
import gplx.xowa.guis.views.boots.*; import gplx.xowa.apps.boots.*; 
public class Xoa_app_ {
	public static void Run(String... args) {
		Xoa_cmd_arg_mgr arg_mgr = Xoa_cmd_arg_mgr.new_();
		try {
			Xoa_boot_mgr boot_mgr = new Xoa_boot_mgr();
			boot_mgr.Run(args, arg_mgr);
		} catch (Error e) {	
			if (arg_mgr.App_type().Tid_is_gui())
				Xog_error_win.Run(Err_.Message_lang(e), Err_.Trace_lang(e));
			else
				throw e;
		}
	}
	public static final String		Name			= "xowa";
	public static final String		Version			= "4.0.0.1701";	// RELEASE:2017-01-03 20:30
	public static String	Build_date		= "2012-12-30 00:00:00";
	public static String	Op_sys_str;
	public static String	User_agent		= "";

	public static Gfo_usr_dlg		Usr_dlg()			{return usr_dlg;}			public static void Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v;} private static Gfo_usr_dlg usr_dlg;
	public static Gfo_usr_dlg		New__usr_dlg__console() {
		Gfo_usr_dlg rv = new Gfo_usr_dlg_base(new Gfo_usr_dlg__log_base(), Gfo_usr_dlg__gui_.Console);
		rv.Log_wkr().Queue_enabled_(true);
		return rv;
	}

	public static final    Gfo_msg_grp Nde = Gfo_msg_grp_.prj_(Name);
}
