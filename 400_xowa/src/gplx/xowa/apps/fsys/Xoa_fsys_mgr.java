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
package gplx.xowa.apps.fsys; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_fsys_mgr implements GfoInvkAble {
	public Xoa_fsys_mgr() {} // DRD
	public Xoa_fsys_mgr(String plat_name, Io_url root_dir) {
		Init_by_boot(plat_name, root_dir);
	}
	public Io_url Root_dir()				{return root_dir;} private Io_url root_dir;
	public byte[] Root_dir_bry()			{return root_dir_bry;} private byte[] root_dir_bry;
	public Io_url File_dir()				{return file_dir;} private Io_url file_dir;
	public Io_url Wiki_dir()				{return wiki_dir;} private Io_url wiki_dir;
	public Io_url Bin_plat_dir()			{return bin_plat_dir;} private Io_url bin_plat_dir;
	public Io_url Bin_any_dir()				{return bin_any_dir;} private Io_url bin_any_dir;
	public Io_url Bin_xowa_dir()			{return bin_any_dir.GenSubDir("xowa");}
	public Io_url Bin_xtns_dir()			{return bin_xtns_dir;} private Io_url bin_xtns_dir;
	public Io_url Cfg_lang_core_dir()		{return cfg_lang_core_dir;} private Io_url cfg_lang_core_dir;
	public Io_url Cfg_wiki_core_dir()		{return cfg_wiki_core_dir;} private Io_url cfg_wiki_core_dir;
	public Io_url Bin_data_os_cfg_fil()		{return bin_plat_dir.GenSubFil_nest("xowa", "cfg", Xoa_gfs_mgr.Cfg_os);}
	public void Init_by_boot(String plat_name, Io_url root_dir) {
		this.root_dir			= root_dir;
		root_dir_bry			= root_dir.To_http_file_bry();
		file_dir				= root_dir.GenSubDir("file");
		bin_plat_dir			= root_dir.GenSubDir("bin").GenSubDir(plat_name);
		bin_any_dir				= root_dir.GenSubDir("bin").GenSubDir("any");
		bin_xtns_dir			= bin_any_dir.GenSubDir_nest("xowa", "xtns");
		cfg_lang_core_dir		= bin_any_dir.GenSubDir_nest("xowa", "cfg", "lang", "core");
		cfg_wiki_core_dir		= bin_any_dir.GenSubDir_nest("xowa", "cfg", "wiki", "core");
		Wiki_dir_				(root_dir.GenSubDir("wiki"));
	}
	public Xoa_fsys_mgr Wiki_dir_(Io_url v) {
		wiki_dir = v;
		return this;
	} 
	public void Init_by_app(GfoInvkAble app_mgr_invk) {// for gfs and app.launcher
		this.app_mgr_invk = app_mgr_invk;
	}	private GfoInvkAble app_mgr_invk;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_apps))					return app_mgr_invk;
		else if	(ctx.Match(k, Invk_root_dir))				return root_dir;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_apps = "apps", Invk_root_dir = "root_dir";
	public static final String DirName_wiki = "wiki", DirName_user = "user";
}
