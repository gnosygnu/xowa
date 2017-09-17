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
	public static final int		Version_id		= 537;
	public static final String		Version			= "4.5.15.1709";
	public static String	Build_date		= "2012-12-30 00:00:00";
	public static String	Build_date_fmt	= "yyyy-MM-dd HH:mm:ss";
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
