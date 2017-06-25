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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
import gplx.xowa.apps.gfs.*; import gplx.xowa.wikis.*;
public class Xou_fsys_mgr implements Gfo_invk {
	private Io_url wiki_root_dir; private Io_url cur_root;
	public Xou_fsys_mgr(Io_url user_dir) {
		this.cur_root = user_dir;
		app_root_dir = cur_root.GenSubDir("app");
		app_temp_dir = app_root_dir.GenSubDir("tmp");
		app_temp_html_dir = app_temp_dir.GenSubDir("html");
		app_data_history_fil = app_root_dir.GenSubFil_nest("data", "history", "page_history.csv");
		wiki_root_dir = cur_root.GenSubDir("wiki");
	}
	public Io_url Root_dir()					{return cur_root;}
	public Io_url Wiki_root_dir()				{return wiki_root_dir;}
	public Io_url Wiki_html_dir(String wiki)	{return wiki_root_dir.GenSubDir_nest(wiki, "html");}
	public Io_url App_root_dir()                {return app_root_dir;} private Io_url app_root_dir;
	public Io_url App_data_history_fil()		{return app_data_history_fil;} private Io_url app_data_history_fil;
	public Io_url App_temp_dir()				{return app_temp_dir;} private Io_url app_temp_dir;
	public Io_url App_temp_html_dir()			{return app_temp_html_dir;} private Io_url app_temp_html_dir;
	public Io_url App_data_dir()				{return app_root_dir.GenSubDir_nest("data");}
	public Io_url App_data_cfg_dir()			{return app_root_dir.GenSubDir_nest("data", "cfg");}
	public static final String Name_user_system_cfg = "user_system_cfg.gfs";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_root_dir))		return cur_root;
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_root_dir = "root_dir";
}
